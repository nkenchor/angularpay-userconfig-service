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
public class UpdateAddressCommandRequest extends AccessControl {

    @NotEmpty
    private String userReference;

    @NotEmpty
    private String addressReference;

    @NotNull
    @Valid
    private AddressApiModel addressApiModel;

    UpdateAddressCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
