package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GenericGetByReferenceCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;

@Service
public class GetUserConfigurationByReferenceCommand extends AbstractCommand<GenericGetByReferenceCommandRequest, UserConfiguration> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public GetUserConfigurationByReferenceCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("GetUserConfigurationByReferenceCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(GenericGetByReferenceCommandRequest request) {
        return request.getAuthenticatedUser().getUserReference();
    }

    @Override
    protected UserConfiguration handle(GenericGetByReferenceCommandRequest request) {
        return getRequestByReferenceOrThrow(this.mongoAdapter, request.getUserReference());
    }

    @Override
    protected List<ErrorObject> validate(GenericGetByReferenceCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(Role.ROLE_USERCONFIG_ADMIN, Role.ROLE_SUPER_ADMIN);
    }
}
