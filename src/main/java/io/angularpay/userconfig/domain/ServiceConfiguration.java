
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceConfiguration {

    private List<ServiceSubscription> subscriptions;
    @JsonProperty("smart_save_configuration")
    private SmartSaveConfiguration smartSaveConfiguration;
}
