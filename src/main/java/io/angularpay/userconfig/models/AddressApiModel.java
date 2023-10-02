
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class AddressApiModel {

    @NotEmpty
    private String country;
    @NotEmpty
    private String state;
    @NotEmpty
    private String city;
    @JsonProperty("address_lines")
    private List<String> addressLines;
}
