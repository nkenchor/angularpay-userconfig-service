package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GetByEmailCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByEmailOrThrow;

@Service
public class GetUserConfigurationByEmailCommand extends AbstractCommand<GetByEmailCommandRequest, UserConfiguration> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public GetUserConfigurationByEmailCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("GetUserConfigurationByEmailCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(GetByEmailCommandRequest request) {
        return "";
    }

    @Override
    protected UserConfiguration handle(GetByEmailCommandRequest request) {
        return getRequestByEmailOrThrow(this.mongoAdapter, request.getEmail());
    }

    @Override
    protected List<ErrorObject> validate(GetByEmailCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(Role.ROLE_USERCONFIG_ADMIN, Role.ROLE_SUPER_ADMIN);
    }
}
