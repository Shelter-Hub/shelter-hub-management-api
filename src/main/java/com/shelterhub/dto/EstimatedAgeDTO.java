package com.shelterhub.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class EstimatedAgeDTO {
    private int years, months, days;
    public LocalDate toEstimatedAge() {
        return LocalDate.now()
                .minusYears(years)
                .minusMonths(months)
                .minusDays(days);
    }
}