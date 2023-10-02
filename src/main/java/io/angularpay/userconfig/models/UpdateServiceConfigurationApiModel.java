
package io.angularpay.userconfig.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateServiceConfigurationApiModel {

    @NotNull
    private Boolean subscribe;
}
