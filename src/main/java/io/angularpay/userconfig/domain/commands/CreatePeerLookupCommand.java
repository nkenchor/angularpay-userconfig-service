package io.angularpay.userconfig.domain.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.angularpay.userconfig.adapters.outbound.MongoAdapter;
import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.Role;
import io.angularpay.userconfig.domain.UserConfiguration;
import io.angularpay.userconfig.exceptions.CommandException;
import io.angularpay.userconfig.exceptions.ErrorObject;
import io.angularpay.userconfig.models.PeerLookupCommandRequest;
import io.angularpay.userconfig.models.ResourceReferenceResponse;
import io.angularpay.userconfig.ports.outbound.OutboundMessagingPort;
import io.angularpay.userconfig.validation.DefaultConstraintValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static io.angularpay.userconfig.exceptions.ErrorCode.PEER_LOOKUP_QUEUE_ERROR;
import static io.angularpay.userconfig.helpers.CommandHelper.getPeerLookupByReferenceOrThrow;
import static io.angularpay.userconfig.helpers.CommandHelper.getRequestByReferenceOrThrow;

@Slf4j
@Service
public class CreatePeerLookupCommand extends AbstractCommand<PeerLookupCommandRequest, ResourceReferenceResponse> {

    private final MongoAdapter mongoAdapter;
    private final DefaultConstraintValidator validator;
    private final AngularPayConfiguration configuration;
    private final OutboundMessagingPort outboundMessagingPort;

    public CreatePeerLookupCommand(
            ObjectMapper mapper,
            MongoAdapter mongoAdapter,
            DefaultConstraintValidator validator,
            AngularPayConfiguration configuration,
            OutboundMessagingPort outboundMessagingPort) {
        super("CreatePeerLookupCommand", mapper);
        this.mongoAdapter = mongoAdapter;
        this.validator = validator;
        this.configuration = configuration;
        this.outboundMessagingPort = outboundMessagingPort;
    }

    @Override
    protected String getResourceOwner(PeerLookupCommandRequest request) {
        return request.getAuthenticatedUser().getUserReference();
    }

    @Override
    protected ResourceReferenceResponse handle(PeerLookupCommandRequest request) {
        UserConfiguration foundUserConfiguration = getRequestByReferenceOrThrow(this.mongoAdapter, request.getAuthenticatedUser().getUserReference());

        this.mongoAdapter.findPeerLookupByUserReference(foundUserConfiguration.getReference())
                .ifPresent(found-> {
                    outboundMessagingPort.pushPeerLookupReference(found.getReference());
                    mongoAdapter.deletePeerLookup(found);
                });

        String reference = outboundMessagingPort.popPeerLookupReference();
        if (!StringUtils.hasText(reference)) {
            throw CommandException.builder()
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .errorCode(PEER_LOOKUP_QUEUE_ERROR)
                    .message(PEER_LOOKUP_QUEUE_ERROR.getDefaultMessage())
                    .build();
        }

        PeerLookup peerLookup = PeerLookup.builder()
                .reference(reference)
                .userReference(foundUserConfiguration.getReference())
                .firstname(foundUserConfiguration.getUserProfile().getFirstname())
                .lastname(foundUserConfiguration.getUserProfile().getLastname())
                .build();

        PeerLookup response = this.mongoAdapter.createPeerLookup(peerLookup);

        Executors.newScheduledThreadPool(1).schedule(
                () -> {
                    PeerLookup foundPeerLookup = getPeerLookupByReferenceOrThrow(this.mongoAdapter, response.getReference());
                    mongoAdapter.deletePeerLookup(foundPeerLookup);
                },
                configuration.getPeerLookup().getTtlInMinutes(),
                TimeUnit.MINUTES
        );

        return new ResourceReferenceResponse(response.getReference());
    }

    @Override
    protected List<ErrorObject> validate(PeerLookupCommandRequest request) {
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
