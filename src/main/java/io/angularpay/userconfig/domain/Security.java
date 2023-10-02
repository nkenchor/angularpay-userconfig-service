
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Security {

    @JsonProperty("two_factor_auth_enabled")
    private Boolean twoFactorAuthEnabled;
    @JsonProperty("transaction_pin_enabled")
    private Boolean transactionPinEnabled;
}
