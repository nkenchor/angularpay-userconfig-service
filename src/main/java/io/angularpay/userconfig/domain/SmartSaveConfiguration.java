
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
public class SmartSaveConfiguration {

    @JsonProperty("bank_debit_notification_phone")
    private String bankDebitNotificationPhone;
    @JsonProperty("smart_save_goals")
    private List<SmartSaveGoal> smartSaveGoals;
}
