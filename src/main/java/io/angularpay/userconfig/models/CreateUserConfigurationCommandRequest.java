package io.angularpay.userconfig.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateUserConfigurationCommandRequest extends AccessControl {

    @NotNull
    @Valid
    private CreateUserConfigurationRequest createUserConfigurationRequest;

    CreateUserConfigurationCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
