
package io.angularpay.userconfig.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class TreeReferenceResponse extends GenericReferenceResponse {

    private final String userReference;
    private final String itemReference;
}
