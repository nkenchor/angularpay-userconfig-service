package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GetByPhoneCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByPhoneOrThrow;

@Service
public class GetUserConfigurationByPhoneCommand extends AbstractCommand<GetByPhoneCommandRequest, UserConfiguration> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public GetUserConfigurationByPhoneCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("GetUserConfigurationByPhoneCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(GetByPhoneCommandRequest request) {
        return "";
    }

    @Override
    protected UserConfiguration handle(GetByPhoneCommandRequest request) {
        return getRequestByPhoneOrThrow(this.mongoAdapter, request.getPhone());
    }

    @Override
    protected List<ErrorObject> validate(GetByPhoneCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(Role.ROLE_USERCONFIG_ADMIN, Role.ROLE_SUPER_ADMIN);
    }
}
