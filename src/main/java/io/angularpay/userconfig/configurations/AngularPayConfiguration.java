package io.angularpay.userconfig.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("angularpay")
@Data
public class AngularPayConfiguration {

    private int pageSize;
    private int codecSizeInMB;
    private int maxUpdateRetry;
    private Redis redis;
    private PeerLookupConfiguration peerLookup;

    @Data
    public static class Redis {
        private String host;
        private int port;
        private int timeout;
    }

    @Data
    public static class PeerLookupConfiguration {
        private int ttlInMinutes;
        private int maxSize;
        private int minSize;
    }
}
