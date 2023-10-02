
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.models.IntegrationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("bank_reference")
    private String bankReference;
    private String iban;
    @JsonProperty("integration_reference")
    private String integrationReference;
    @JsonProperty("integration_type")
    private IntegrationType integrationType;
    private String reference;
}
