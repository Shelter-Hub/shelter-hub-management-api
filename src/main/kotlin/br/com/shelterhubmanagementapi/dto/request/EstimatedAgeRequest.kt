package br.com.shelterhubmanagementapi.dto.request

import kotlinx.datetime.LocalDate
// import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class EstimatedAgeRequest(
    val years: Int,
    val months: Int,
    val days: Int,
)

fun EstimatedAgeRequest.toEstimatedAge(): LocalDate = LocalDate(this.years, this.months, this.days)
