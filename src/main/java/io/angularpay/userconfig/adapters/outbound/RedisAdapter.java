package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.ports.outbound.OutboundMessagingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisAdapter implements OutboundMessagingPort {

    private final RedisHashClient redisHashClient;
    private final RedisQueueClient redisQueueClient;

    @Override
    public Map<String, String> getPlatformConfigurations(String hashName) {
        return this.redisHashClient.getPlatformConfigurations(hashName);
    }

    @Override
    public void pushPeerLookupReference(String message) {
        this.redisQueueClient.push(message);
    }

    @Override
    public String popPeerLookupReference() {
        return this.redisQueueClient.pop();
    }

    @Override
    public Long getPeerLookupQueueSize() {
        return this.redisQueueClient.size();
    }
}
