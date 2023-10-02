
package io.angularpay.userconfig.domain;

import jdk.jfr.Frequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    private Amount amount;
    private SalaryFrequency frequency;
}
