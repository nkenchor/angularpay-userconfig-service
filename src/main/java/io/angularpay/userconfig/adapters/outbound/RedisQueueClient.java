package io.angularpay.userconfig.adapters.outbound;

import io.angularpay.userconfig.configurations.AngularPayConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

import static io.angularpay.userconfig.common.Constants.PEER_LOOKUP_QUEUE;

@Service
@RequiredArgsConstructor
public class RedisQueueClient {

    private final AngularPayConfiguration configuration;

    public void push(String message) {
        try (Jedis jedis = jedisInstance()) {
            jedis.rpush(PEER_LOOKUP_QUEUE, message);
        }
    }

    public String pop() {
        try (Jedis jedis = jedisInstance()) {
            List<String> messages = jedis.blpop(0, PEER_LOOKUP_QUEUE);
            return messages.get(1);
        }
    }

    public Long size() {
        try (Jedis jedis = jedisInstance()) {
            return jedis.llen(PEER_LOOKUP_QUEUE);
        }
    }

    private Jedis jedisInstance() {
        return new Jedis(
                configuration.getRedis().getHost(),
                configuration.getRedis().getPort(),
                configuration.getRedis().getTimeout()
        );
    }
}
