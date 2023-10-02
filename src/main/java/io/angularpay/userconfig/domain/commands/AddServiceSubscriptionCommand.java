package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.ServiceSubscription;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.AddServiceConfigurationCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.*;

@Service
public class AddServiceSubscriptionCommand extends AbstractCommand<AddServiceConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public AddServiceSubscriptionCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("AddServiceSubscriptionCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(AddServiceConfigurationCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(AddServiceConfigurationCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        initServiceConfigurationIfNecessary(found);
        validateUniqueServiceConfigurationOrThrow(request.getAddServiceConfigurationApiModel().getServiceReference(), found);
        ServiceSubscription serviceSubscription = ServiceSubscription.builder()
                .serviceReference(request.getAddServiceConfigurationApiModel().getServiceReference())
                .subscribe(request.getAddServiceConfigurationApiModel().getSubscribe())
                .build();
        this.commandHelper.addItemToCollection(found, serviceSubscription, found.getServiceConfiguration()::getSubscriptions, found.getServiceConfiguration()::setSubscriptions);
        return TreeReferenceResponse.builder().userReference(found.getReference()).itemReference(serviceSubscription.getServiceReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(AddServiceConfigurationCommandRequest request) {
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
