package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Identification;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.AddKycIdentificationCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;
import static io.angularpay.userconfig.helpers.CommandHelper.validRequestStatusOrThrow;

@Service
public class AddKycIdentificationCommand extends AbstractCommand<AddKycIdentificationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public AddKycIdentificationCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("AddKycIdentificationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(AddKycIdentificationCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(AddKycIdentificationCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        Identification identification = Identification.builder()
                .reference(UUID.randomUUID().toString())
                .documentReference(request.getKycIdentificationApiModel().getDocumentReference())
                .expiry(request.getKycIdentificationApiModel().getExpiry())
                .passportNumber(request.getKycIdentificationApiModel().getPassportNumber())
                .type(request.getKycIdentificationApiModel().getType())
                .build();
        this.commandHelper.addItemToCollection(found, identification, found.getKycDetails()::getIdentification, found.getKycDetails()::setIdentification);
        return TreeReferenceResponse.builder().userReference(found.getReference()).itemReference(identification.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(AddKycIdentificationCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Collections.emptyList();
    }

    @Override
    public ResourceReferenceResponse map(TreeReferenceResponse treeReferenceResponse) {
        return new ResourceReferenceResponse(treeReferenceResponse.getItemReference());
    }
}
