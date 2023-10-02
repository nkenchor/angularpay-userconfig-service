
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BankAccountApiModel {

    @NotEmpty
    @JsonProperty("account_number")
    private String accountNumber;
    @NotEmpty
    @JsonProperty("account_name")
    private String accountName;
    @NotEmpty
    @JsonProperty("bank_reference")
    private String bankReference;
    @NotEmpty
    private String iban;
    @NotEmpty
    @JsonProperty("integration_reference")
    private String integrationReference;
    @NotNull
    @JsonProperty("integration_type")
    private IntegrationType integrationType;
}
