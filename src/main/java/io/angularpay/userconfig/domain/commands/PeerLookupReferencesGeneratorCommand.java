package io.angularpay.userconfig.domain.commands;

import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import io.angularpay.userconfig.ports.outbound.OutboundMessagingPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Slf4j
@Service
public class PeerLookupReferencesGeneratorCommand {

    private final AngularPayConfiguration configuration;
    private final OutboundMessagingPort outboundMessagingPort;

    public PeerLookupReferencesGeneratorCommand(
            AngularPayConfiguration configuration,
            OutboundMessagingPort outboundMessagingPort) {
        this.configuration = configuration;
        this.outboundMessagingPort = outboundMessagingPort;
        log.info("checking peer-lookup-queue size...");
        long size = outboundMessagingPort.getPeerLookupQueueSize();
        if (size < configuration.getPeerLookup().getMinSize()) {
            log.info("peer-lookup-queue is less than min size of {} - generating {} based on configuration...",
                    configuration.getPeerLookup().getMinSize(),
                    configuration.getPeerLookup().getMaxSize()
            );
            this.generatePeerLookupReference();
        } else {
            log.info("peer-lookup-queue size is OK. Moving on...");
        }
    }

    public void generatePeerLookupReference() {
        Random random = new Random();
        int min = 1000000, max = 9999999;
        Set<String> set = new HashSet<>();
        while (set.size() < configuration.getPeerLookup().getMaxSize()) {
            int result = random.nextInt(max - min) + min;
            set.add(String.format("%07d", result));
        }
        set.stream().parallel().forEach(outboundMessagingPort::pushPeerLookupReference);
    }
}
