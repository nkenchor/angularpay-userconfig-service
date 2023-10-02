
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document("user_configurations")
public class UserConfiguration {

    @Id
    private String id;
    @Version
    private int version;
    private String reference;
    @JsonProperty("bank_accounts")
    private List<BankAccount> bankAccounts;
    @JsonProperty("created_on")
    private String createdOn;
    private List<Device> devices;
    private boolean enabled;
    private Income income;
    @JsonProperty("kyc_details")
    private KycDetails kycDetails;
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonProperty("newsfeed_preferences")
    private List<NewsfeedPreference> newsfeedPreferences;
    @JsonProperty("notification_options")
    private NotificationOptions notificationOptions;
    @JsonProperty("onboarding_reference")
    private String onboardingReference;
    private Security security;
    @JsonProperty("service_configurations")
    private ServiceConfiguration serviceConfiguration;
    @JsonProperty("user_profile")
    private UserProfile userProfile;
}
