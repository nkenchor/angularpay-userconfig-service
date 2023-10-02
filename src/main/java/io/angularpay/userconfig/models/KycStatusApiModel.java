
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.domain.VerificationMethod;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class KycStatusApiModel {

    @NotNull
    @JsonProperty("is_verified")
    private Boolean verified;
    @JsonProperty("verification_method")
    @NotNull
    private VerificationMethod verificationMethod;
}
