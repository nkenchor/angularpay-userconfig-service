package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.CommandException;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.DeleteUserConfigurationCommandRequest;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.models.TreeReferenceResponse;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.exceptions.ErrorCode.ILLEGAL_SELF_DELETE_ERROR;
import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;

@Slf4j
@Service
public class DeleteUserConfigurationCommand extends AbstractCommand<DeleteUserConfigurationCommandRequest, GenericReferenceResponse>
        implements ResourceReferenceCommand<TreeReferenceResponse, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public DeleteUserConfigurationCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("DeleteUserConfigurationCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(DeleteUserConfigurationCommandRequest request) {
        // only service account should have access for rollback on on-boarding error
        return "";
    }

    @Override
    protected TreeReferenceResponse handle(DeleteUserConfigurationCommandRequest request) {
        if(request.getUserReference().equalsIgnoreCase(request.getAuthenticatedUser().getUserReference())) {
            throw CommandException.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .errorCode(ILLEGAL_SELF_DELETE_ERROR)
                    .message(ILLEGAL_SELF_DELETE_ERROR.getDefaultMessage())
                    .build();
        }

        UserConfiguration found = getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
        this.mongoAdapter.deleteRequest(found);
        return TreeReferenceResponse.builder().build();
    }

    @Override
    protected List<ErrorObject> validate(DeleteUserConfigurationCommandRequest request) {
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
