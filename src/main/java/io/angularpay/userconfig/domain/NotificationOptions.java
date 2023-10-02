
package io.angularpay.userconfig.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.models.NotificationType;
import io.angularpay.userconfig.models.OtpType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationOptions {

    @JsonProperty("notification_type")
    private NotificationType notificationType;
    @JsonProperty("otp_type")
    private OtpType otpType;
    @JsonProperty("push_notification_enabled")
    private boolean pushNotificationEnabled;
}
