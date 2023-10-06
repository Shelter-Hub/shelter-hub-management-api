package br.com.shelterhubmanagementapi.dto.request

import kotlinx.datetime.LocalDate

data class EstimatedAgeRequest(
    val years: Int,
    val months: Int,
    val days: Int,
)

fun EstimatedAgeRequest.toLocalDate(): LocalDate = LocalDate(this.years, this.months, this.days)
