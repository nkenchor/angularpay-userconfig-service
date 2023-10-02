
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document("peer_lookup")
public class PeerLookup {

    @Id
    private String id;
    @Version
    private int version;
    private String reference;
    @JsonProperty("user_reference")
    private String userReference;
    @JsonProperty("created_on")
    private String createdOn;
    @JsonProperty("last_modified")
    private String lastModified;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
}
