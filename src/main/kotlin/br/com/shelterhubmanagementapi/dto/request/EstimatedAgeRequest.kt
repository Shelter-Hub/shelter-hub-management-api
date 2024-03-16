package br.com.shelterhubmanagementapi.dto.request

import java.time.LocalDate

data class EstimatedAgeRequest(
    val years: Int,
    val months: Int,
    val days: Int
)

fun EstimatedAgeRequest.toEstimatedAge(): LocalDate = LocalDate.of(this.years, this.months, this.days)
