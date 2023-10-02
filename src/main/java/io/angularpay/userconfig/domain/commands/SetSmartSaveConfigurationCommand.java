package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.SmartSaveConfiguration;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.SetSmartSaveConfigurationCommandRequest;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.*;

@Service
public class SetSmartSaveConfigurationCommand extends AbstractCommand<SetSmartSaveConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public SetSmartSaveConfigurationCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("SetSmartSaveConfigurationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(SetSmartSaveConfigurationCommandRequest request) {
        return this.commandHelper.getRequestOwner(request.getUserReference());
    }

    @Override
    protected TreeReferenceResponse handle(SetSmartSaveConfigurationCommandRequest request) {
        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        validRequestStatusOrThrow(found);
        initServiceConfigurationIfNecessary(found);
        SmartSaveConfiguration smartSaveConfiguration = SmartSaveConfiguration.builder()
                .bankDebitNotificationPhone(request.getSmartSaveConfigurationApiModel().getBankDebitNotificationPhone())
                .smartSaveGoals(request.getSmartSaveConfigurationApiModel().getSmartSaveGoals())
                .build();
        found.getServiceConfiguration().setSmartSaveConfiguration(smartSaveConfiguration);
        UserConfiguration response = this.mongoAdapter.updateRequest(found);
        return TreeReferenceResponse.builder().userReference(response.getReference()).build();
    }

    @Override
    protected List<ErrorObject> validate(SetSmartSaveConfigurationCommandRequest request) {
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
