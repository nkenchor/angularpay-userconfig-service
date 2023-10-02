
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

import static io.angularpay.userconfig.common.Constants.REGEX_EMAIL_ADDRESS;
import static io.angularpay.userconfig.common.Constants.REGEX_INTERNATIONAL_PHONE_NUMBER;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @NotEmpty
    private String handle;
    @NotEmpty
    private String username;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;
    @NotEmpty
    @Pattern(regexp = REGEX_EMAIL_ADDRESS, message = "Invalid email address", flags = {Pattern.Flag.CASE_INSENSITIVE})
    private String email;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    @NotEmpty
    @Pattern(regexp = REGEX_INTERNATIONAL_PHONE_NUMBER, message = "Invalid phone number")
    private String phone;
    private List<Address> addresses;
}
