
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddServiceConfigurationApiModel {

    @NotEmpty
    @JsonProperty("service_reference")
    private String serviceReference;
    @NotNull
    private Boolean subscribe;
}
