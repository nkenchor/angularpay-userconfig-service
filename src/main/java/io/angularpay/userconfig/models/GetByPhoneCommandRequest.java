package io.angularpay.userconfig.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetByPhoneCommandRequest extends AccessControl {

    @NotEmpty
    private String phone;

    GetByPhoneCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
