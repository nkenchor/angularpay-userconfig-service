package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GetUserConfigurationListCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GetUserConfigurationListCommand extends AbstractCommand<GetUserConfigurationListCommandRequest, List<UserConfiguration>> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public GetUserConfigurationListCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("GetUserConfigurationListCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(GetUserConfigurationListCommandRequest request) {
        return "";
    }

    @Override
    protected List<UserConfiguration> handle(GetUserConfigurationListCommandRequest request) {
        Pageable pageable = PageRequest.of(request.getPaging().getIndex(), request.getPaging().getSize());
        return this.mongoAdapter.listRequests(pageable).getContent();
    }

    @Override
    protected List<ErrorObject> validate(GetUserConfigurationListCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(Role.ROLE_USERCONFIG_ADMIN, Role.ROLE_SUPER_ADMIN);
    }
}
