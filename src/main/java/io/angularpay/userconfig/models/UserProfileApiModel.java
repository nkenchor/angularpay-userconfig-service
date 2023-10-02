
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserProfileApiModel {

    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
}
