package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.GetPeerLookupByReferenceCommandRequest;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static io.angularpay.userconfig.helpers.CommandHelper.getPeerLookupByReferenceOrThrow;

@Slf4j
@Service
public class GetPeerLookupByReferenceCommand extends AbstractCommand<GetPeerLookupByReferenceCommandRequest, PeerLookup> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;

    public GetPeerLookupByReferenceCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator) {
        super("GetPeerLookupByReferenceCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
    }

    @Override
    protected String getResourceOwner(GetPeerLookupByReferenceCommandRequest request) {
        return request.getAuthenticatedUser().getUserReference();
    }

    @Override
    protected PeerLookup handle(GetPeerLookupByReferenceCommandRequest request) {
        return getPeerLookupByReferenceOrThrow(this.mongoAdapter, request.getReference());
    }

    @Override
    protected List<ErrorObject> validate(GetPeerLookupByReferenceCommandRequest request) {
        return this.validator.validate(request);
    }

    @Override
    protected List<Role> permittedRoles() {
        return Arrays.asList(
                Role.ROLE_UNVERIFIED_USER,
                Role.ROLE_VERIFIED_USER,
                Role.ROLE_USERCONFIG_ADMIN,
                Role.ROLE_SUPER_ADMIN
        );
    }

}
