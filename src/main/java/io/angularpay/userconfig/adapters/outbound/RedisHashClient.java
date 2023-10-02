package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisHashClient {

    private final AngularPayConfiguration configuration;

    private Jedis jedisInstance() {
        return new Jedis(
                configuration.getRedis().getHost(),
                configuration.getRedis().getPort(),
                configuration.getRedis().getTimeout()
        );
    }

    public Map<String, String> getPlatformConfigurations(String hashName) {
        try (Jedis jedis = jedisInstance()) {
            return jedis.hgetAll(hashName);
        }
    }
}
