package io.angularpay.userconfig.adapters.inbound;

import io.angularpay.userconfig.domain.commands.PlatformConfigurationsConverterCommand;
import io.angularpay.userconfig.models.platform.PlatformConfigurationIdentifier;
import io.angularpay.userconfig.ports.inbound.InboundMessagingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.angularpay.userconfig.models.platform.PlatformConfigurationSource.TOPIC;

@Service
@RequiredArgsConstructor
public class RedisMessageAdapter implements InboundMessagingPort {

    private final PlatformConfigurationsConverterCommand converterCommand;

    @Override
    public void onMessage(String message, PlatformConfigurationIdentifier identifier) {
        this.converterCommand.execute(message, identifier, TOPIC);
    }
}
