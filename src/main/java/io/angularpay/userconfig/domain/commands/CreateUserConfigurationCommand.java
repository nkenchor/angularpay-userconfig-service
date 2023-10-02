package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.domain.UserProfile;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.CreateUserConfigurationCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.angularpay.userconfig.helpers.CommandHelper.validateNotExistOrThrow;
import static io.angularpay.userconfig.helpers.ObjectFactory.userConfigurationWithDefaults;

@Slf4j
@Service
public class CreateUserConfigurationCommand extends AbstractCommand<CreateUserConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public CreateUserConfigurationCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("CreateUserConfigurationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(CreateUserConfigurationCommandRequest request) {
        return request.getAuthenticatedUser().getUserReference();
    }

    @Override
    protected TreeReferenceResponse handle(CreateUserConfigurationCommandRequest request) {
        validateNotExistOrThrow(this.mongoAdapter, request.getCreateUserConfigurationRequest().getOnboardingReference());
        UserConfiguration userConfigurationWithDefaults = userConfigurationWithDefaults();
        UserProfile userProfile = request.getCreateUserConfigurationRequest().getUserProfile();
        userProfile.setEmail(userProfile.getEmail().toLowerCase());
        UserConfiguration withOtherDetails = userConfigurationWithDefaults.toBuilder()
                .onboardingReference(request.getCreateUserConfigurationRequest().getOnboardingReference())
                .userProfile(userProfile)
                .devices(Collections.singletonList(
                        request.getCreateUserConfigurationRequest().getDevice().toBuilder()
                                .reference(UUID.randomUUID().toString())
                                .build()
                ))
                .build();
        UserConfiguration response = this.mongoAdapter.createRequest(withOtherDetails);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(CreateUserConfigurationCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(Role.ROLE_USERCONFIG_ADMIN, Role.ROLE_SUPER_ADMIN);
    }

    @Override
    public ResourceReferenceResponse map(TreeReferenceResponse treeReferenceResponse) {
        return new ResourceReferenceResponse(treeReferenceResponse.getUserReference());
    }
}
