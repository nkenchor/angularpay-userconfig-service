package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.KycDetails;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.models.UpdateKycStatusCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;
import static io.angularpay.userconfig.helpers.CommandHelper.validRequestStatusOrThrow;

@Slf4j
@Service
public class UpdateKycStatusCommand extends AbstractCommand<UpdateKycStatusCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public UpdateKycStatusCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("UpdateKycStatusCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(UpdateKycStatusCommandRequest request) {
        return "";
    }

    @Override
    protected TreeReferenceResponse handle(UpdateKycStatusCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        if (Objects.isNull(found.getKycDetails())) {
            found.setKycDetails(KycDetails.builder().build());
        }
        found.getKycDetails().setVerified(request.getKycStatusApiModel().getVerified());
        found.getKycDetails().setVerificationMethod(request.getKycStatusApiModel().getVerificationMethod());
        found.getKycDetails().setVerifiedOn(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString());
        UserConfiguration response = this.mongoAdapter.updateRequest(found);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(UpdateKycStatusCommandRequest request) {
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
