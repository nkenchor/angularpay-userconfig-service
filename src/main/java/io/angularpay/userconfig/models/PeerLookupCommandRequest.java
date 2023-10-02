package io.angularpay.userconfig.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class PeerLookupCommandRequest extends AccessControl {

    PeerLookupCommandRequest(AuthenticatedUser authenticatedUser) {
        super(authenticatedUser);
    }
}
