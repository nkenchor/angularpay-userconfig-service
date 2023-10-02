
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedPreference {

    @JsonProperty("service_reference")
    private String serviceReference;
    @JsonProperty("show_in_newsfeed")
    private boolean showInNewsfeed;
    @JsonProperty("only_show_status_types")
    private List<RequestStatus> onlyShowStatusTypes;
}
