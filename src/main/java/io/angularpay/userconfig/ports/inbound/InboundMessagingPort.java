package io.angularpay.userconfig.ports.inbound;

import io.angularpay.userconfig.models.platform.PlatformConfigurationIdentifier;

public interface InboundMessagingPort {
    void onMessage(String message, PlatformConfigurationIdentifier identifier);
}
