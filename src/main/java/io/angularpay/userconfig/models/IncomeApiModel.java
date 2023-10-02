
package io.angularpay.userconfig.models;

import io.angularpay.userconfig.domain.Amount;
import io.angularpay.userconfig.domain.SalaryFrequency;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class IncomeApiModel {

    @NotNull
    @Valid
    private Amount amount;

    @NotNull
    private SalaryFrequency frequency;
}
