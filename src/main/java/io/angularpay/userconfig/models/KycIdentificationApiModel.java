
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.domain.IdentificationType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class KycIdentificationApiModel {

    @NotEmpty
    @JsonProperty("document_reference")
    private String documentReference;
    @NotEmpty
    private String expiry;
    @JsonProperty("passport_number")
    @NotEmpty
    private String passportNumber;
    @NotNull
    private IdentificationType type;
}
