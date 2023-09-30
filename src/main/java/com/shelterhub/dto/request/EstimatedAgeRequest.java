package com.shelterhub.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EstimatedAgeRequest {
    private int years, months, days;

    public LocalDate toEstimatedAge() {
        return LocalDate.now()
                .minusYears(years)
                .minusMonths(months)
                .minusDays(days);
    }
}