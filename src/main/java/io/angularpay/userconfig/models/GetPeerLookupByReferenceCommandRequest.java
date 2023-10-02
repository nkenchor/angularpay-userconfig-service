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
public class GetPeerLookupByReferenceCommandRequest extends AccessControl {

    @NotEmpty
    private String reference;

    GetPeerLookupByReferenceCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
