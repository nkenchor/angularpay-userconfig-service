package io.angularpay.userconfig.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateServiceConfigurationCommandRequest extends AccessControl {

    @NotEmpty
    private String userReference;

    @NotEmpty
    private String serviceReference;

    @NotNull
    @Valid
    private UpdateServiceConfigurationApiModel updateServiceConfigurationApiModel;

    UpdateServiceConfigurationCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
