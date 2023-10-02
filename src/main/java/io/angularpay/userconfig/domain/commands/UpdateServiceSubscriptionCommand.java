package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.models.UpdateServiceConfigurationCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.*;

@Service
public class UpdateServiceSubscriptionCommand extends AbstractCommand<UpdateServiceConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public UpdateServiceSubscriptionCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("UpdateServiceConfigurationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(UpdateServiceConfigurationCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(UpdateServiceConfigurationCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        initServiceConfigurationIfNecessary(found);
        found.getServiceConfiguration().getSubscriptions().forEach(x -> {
            if (request.getServiceReference().equalsIgnoreCase(x.getServiceReference())) {
                x.setSubscribe(request.getUpdateServiceConfigurationApiModel().getSubscribe());
            }
        });
        UserConfiguration response = this.mongoAdapter.updateRequest(found);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(UpdateServiceConfigurationCommandRequest request) {
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
