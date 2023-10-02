package io.angularpay.userconfig.helpers;

import io.angularpay.userconfig.domain.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

import static io.angularpay.userconfig.models.NotificationType.RING_AND_VIBRATE;
import static io.angularpay.userconfig.models.OtpType.SEND_AS_SMS_ONLY;

public class ObjectFactory {

    public static UserConfiguration userConfigurationWithDefaults() {
        return UserConfiguration.builder()
                .reference(UUID.randomUUID().toString())
                .createdOn(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString())
                .lastModified(Instant.now().truncatedTo(ChronoUnit.SECONDS).toString())
                .enabled(true)
                .security(Security.builder()
                        .twoFactorAuthEnabled(true)
                        .transactionPinEnabled(true)
                        .build())
                .notificationOptions(NotificationOptions.builder()
                        .pushNotificationEnabled(true)
                        .notificationType(RING_AND_VIBRATE)
                        .otpType(SEND_AS_SMS_ONLY)
                        .build())
                .bankAccounts(new ArrayList<>())
                .newsfeedPreferences(new ArrayList<>())
                .serviceConfiguration(ServiceConfiguration.builder()
                        .smartSaveConfiguration(SmartSaveConfiguration.builder().build())
                        .subscriptions(new ArrayList<>())
                        .build())
                .devices(new ArrayList<>())
                .kycDetails(KycDetails.builder()
                        .verified(false)
                        .identification(new ArrayList<>())
                        .build())
                .build();
    }
}