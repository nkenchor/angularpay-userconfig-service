
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
public class Identification {

    @JsonProperty("document_reference")
    private String documentReference;
    private String expiry;
    @JsonProperty("passport_number")
    private String passportNumber;
    private String reference;
    private IdentificationType type;
}
