package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.CommandException;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.helpers.CommandHelper;
import io.angularpay.userconfig.models.EnableUserConfigurationCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.exceptions.ErrorCode.ILLEGAL_SELF_STATUS_CHANGE_ERROR;
import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;

@Slf4j
@Service
public class EnableUserConfigurationCommand extends AbstractCommand<EnableUserConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final CommandHelper commandHelper;

    public EnableUserConfigurationCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            CommandHelper commandHelper) {
        super("EnableUserConfigurationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.commandHelper = commandHelper;
    }

    @Override
    protected String getResourceOwner(EnableUserConfigurationCommandRequest request) {
        return "";
    }

    @Override
    protected TreeReferenceResponse handle(EnableUserConfigurationCommandRequest request) {
        if(request.getUserReference().equalsIgnoreCase(request.getAuthenticatedUser().getUserReference())) {
            throw CommandException.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .errorCode(ILLEGAL_SELF_STATUS_CHANGE_ERROR)
                    .message(ILLEGAL_SELF_STATUS_CHANGE_ERROR.getDefaultMessage())
                    .build();
        }

        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        return this.commandHelper.updateProperty(found, request::getEnabled, found::setEnabled);
    }

    @Override
    protected List<ErrorObject> validate(EnableUserConfigurationCommandRequest request) {
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
