
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
public class KycDetails {

    private List<Identification> identification;
    @JsonProperty("is_verified")
    private Boolean verified;
    @JsonProperty("verification_method")
    private VerificationMethod verificationMethod;
    @JsonProperty("verified_on")
    private String verifiedOn;
}
