package io.angularpay.userconfig.adapters.inbound;

import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.domain.commands.CreatePeerLookupCommand;
import io.angularpay.userconfig.domain.commands.GetPeerLookupByReferenceCommand;
import io.angularpay.userconfig.models.AuthenticatedUser;
import io.angularpay.userconfig.models.GenericReferenceResponse;
import io.angularpay.userconfig.models.GetPeerLookupByReferenceCommandRequest;
import io.angularpay.userconfig.models.PeerLookupCommandRequest;
import io.angularpay.userconfig.ports.inbound.PeerLookupApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static io.angularpay.userconfig.helpers.Helper.fromHeaders;

@RestController
@RequestMapping("/user-configuration/peer-lookup")
@RequiredArgsConstructor
public class PeerLookupRestApiAdapter implements PeerLookupApiPort {

    private final CreatePeerLookupCommand createPeerLookupCommand;
    private final GetPeerLookupByReferenceCommand getPeerLookupByReferenceCommand;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public GenericReferenceResponse createPeerLookup(
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        PeerLookupCommandRequest peerLookupCommandRequest = PeerLookupCommandRequest.builder()
                .authenticatedUser(authenticatedUser)
                .build();
        return this.createPeerLookupCommand.execute(peerLookupCommandRequest);
    }


    @GetMapping("/{peerLookupReference}")
    @ResponseBody
    @Override
    public PeerLookup findPeerLookupByReference(
            @PathVariable String peerLookupReference,
            @RequestHeader Map<String, String> headers) {
        AuthenticatedUser authenticatedUser = fromHeaders(headers);
        GetPeerLookupByReferenceCommandRequest getPeerLookupByReferenceCommandRequest = GetPeerLookupByReferenceCommandRequest.builder()
                .reference(peerLookupReference)
                .authenticatedUser(authenticatedUser)
                .build();
        return this.getPeerLookupByReferenceCommand.execute(getPeerLookupByReferenceCommandRequest);
    }

}
