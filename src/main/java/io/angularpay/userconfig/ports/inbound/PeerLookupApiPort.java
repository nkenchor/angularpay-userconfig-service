package io.angularpay.userconfig.ports.inbound;

import io.angularpay.userconfig.domain.PeerLookup;
import io.angularpay.userconfig.models.GenericReferenceResponse;

import java.util.Map;

public interface PeerLookupApiPort {
    GenericReferenceResponse createPeerLookup(Map<String, String> headers);
    PeerLookup findPeerLookupByReference(String peerLookupReference, Map<String, String> headers);
}
