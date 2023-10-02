
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.domain.RequestStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewsfeedPreferenceApiModel {

    @NotEmpty
    @JsonProperty("service_reference")
    private String serviceReference;
    @NotNull
    @JsonProperty("show_in_newsfeed")
    private Boolean showInNewsfeed;
    @NotEmpty
    @JsonProperty("only_show_status_types")
    private List<RequestStatus> onlyShowStatusTypes;
}
