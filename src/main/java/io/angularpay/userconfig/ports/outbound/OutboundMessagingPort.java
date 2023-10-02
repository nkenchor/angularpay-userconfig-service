package io.angularpay.userconfig.ports.outbound;

import java.util.Map;

public interface OutboundMessagingPort {
    Map<String, String> getPlatformConfigurations(String hashName);
    void pushPeerLookupReference(String reference);
    String popPeerLookupReference();
    Long getPeerLookupQueueSize();
}
