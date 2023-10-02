package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.domain.UserProfile;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.models.UpdateUserProfileCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;
import static io.angularpay.userconfig.helpers.CommandHelper.validRequestStatusOrThrow;

@Slf4j
@Service
public class UpdateUserProfileCommand extends AbstractCommand<UpdateUserProfileCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public UpdateUserProfileCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("UpdateUserProfileCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(UpdateUserProfileCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(UpdateUserProfileCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        if (Objects.isNull(found.getUserProfile())) {
            found.setUserProfile(UserProfile.builder().build());
        }
        found.getUserProfile().setFirstname(request.getUserProfileApiModel().getFirstname());
        found.getUserProfile().setLastname(request.getUserProfileApiModel().getLastname());
        found.getUserProfile().setDateOfBirth(request.getUserProfileApiModel().getDateOfBirth());
        UserConfiguration response = this.mongoAdapter.updateRequest(found);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(UpdateUserProfileCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Collections.emptyList();
    }

    @Override
    public ResourceReferenceResponse map(TreeReferenceResponse treeReferenceResponse) {
        return new ResourceReferenceResponse(treeReferenceResponse.getUserReference());
    }
}
