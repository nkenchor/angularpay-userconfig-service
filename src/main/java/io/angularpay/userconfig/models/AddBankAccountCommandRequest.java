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
public class AddBankAccountCommandRequest extends AccessControl {

    @NotEmpty
    private String userReference;

    @NotNull
    @Valid
    private BankAccountApiModel bankAccountApiModel;

    AddBankAccountCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
