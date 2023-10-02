
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.domain.Device;
import io.angularpay.userconfig.domain.UserProfile;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserConfigurationRequest {

    @NotEmpty
    @JsonProperty("onboarding_reference")
    private String onboardingReference;

    @NotNull
    @Valid
    @JsonProperty("user_profile")
    private UserProfile userProfile;

    @NotNull
    @Valid
    private Device device;
}
