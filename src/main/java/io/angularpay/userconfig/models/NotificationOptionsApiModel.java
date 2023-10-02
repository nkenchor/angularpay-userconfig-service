
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotificationOptionsApiModel {

    @NotNull
    @JsonProperty("notification_type")
    private NotificationType notificationType;
    @NotNull
    @JsonProperty("otp_type")
    private OtpType otpType;
    @NotNull
    @JsonProperty("push_notification_enabled")
    private Boolean pushNotificationEnabled;
}
