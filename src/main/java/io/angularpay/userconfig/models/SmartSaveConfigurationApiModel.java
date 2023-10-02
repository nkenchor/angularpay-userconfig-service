
package io.angularpay.userconfig.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.angularpay.userconfig.domain.SmartSaveGoal;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SmartSaveConfigurationApiModel {

    @NotEmpty
    @JsonProperty("bank_debit_notification_phone")
    private String bankDebitNotificationPhone;
    @JsonProperty("smart_save_goals")
    private List<SmartSaveGoal> smartSaveGoals;
}
