package br.com.shelterhubmanagementapi.controller

import br.com.shelterhubmanagementapi.service.StatusService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping(value = ["v1/status"], produces = [MediaType.APPLICATION_JSON_VALUE])
class StatusController (private val statusService: StatusService) {
@GetMapping("/{id}")
@ResponseBody
suspend fun getById(
    @PathVariable id : UUID,
): SatusResponse {
    return statusService.getStatusById(id).await()
}


















}




