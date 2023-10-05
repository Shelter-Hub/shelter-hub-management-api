package com.shelterhub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelterhub.dto.request.MedicalRecordRequest;
import com.shelterhub.dto.response.MedicalRecordResponse;
import com.shelterhub.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.shelterhub.utils.MedicalRecordUtils.buildMedicalRecord;
import static com.shelterhub.utils.MedicalRecordUtils.buildMedicalRecordRequest;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    private final String PATH_URL = "/v1/medical-record";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void shouldReturnAllMedicalRecords() throws Exception {
        MedicalRecordResponse firstMedicalRecord = buildMedicalRecordRequest().toMedicalRecord().toResponse();
        MedicalRecordResponse secondMedicalRecord = buildMedicalRecordRequest().toMedicalRecord().toResponse();

        when(medicalRecordService.getAll()).thenReturn(List.of(firstMedicalRecord, secondMedicalRecord));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(firstMedicalRecord.getId().toString()))
                .andExpect(jsonPath("$[0].animalId").value(firstMedicalRecord.getAnimalId().toString()))
                .andExpect(jsonPath("$[1].id").value(secondMedicalRecord.getId().toString()))
                .andExpect(jsonPath("$[1].animalId").value(secondMedicalRecord.getAnimalId().toString()));

        verify(medicalRecordService, times(1)).getAll();
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void shouldReturnNotFoundWhenAllMedicalRecordsDoesNotExists() throws Exception {
        when(medicalRecordService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).getAll();
        verifyNoMoreInteractions(medicalRecordService);
    }


    @Test
    public void shouldReturnMedicalRecordById() throws Exception {
        var medicalRecordRequest = buildMedicalRecordRequest();
        var medicalRecordId = medicalRecordRequest.getId();

        var medicalResponse = medicalRecordRequest
                .toMedicalRecord()
                .toResponse();

        when(medicalRecordService.getById(medicalRecordId))
                .thenReturn(Optional.of(medicalResponse));

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL + "/{id}", medicalRecordId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(medicalResponse.getId().toString()))
                .andExpect(jsonPath("$.animalId").value(medicalResponse.getAnimalId().toString()));


        verify(medicalRecordService, times(1)).getById(medicalRecordId);
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void shouldReturnNotFoundWhenMedicalRecordDoesNotExist() throws Exception {
        var uuid = UUID.randomUUID();
        when(medicalRecordService.getById(uuid))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(PATH_URL + "/{id}", uuid.toString()))
                .andExpect(status().isNotFound());

        verify(medicalRecordService, times(1)).getById(uuid);
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void shouldCreateMedicalRecordSuccessfully() throws Exception {
        MedicalRecordRequest medicalRecordRequest = buildMedicalRecordRequest();
        var medicalRecord = medicalRecordRequest.toMedicalRecord();
        var medicalRecordResponse = medicalRecord.toResponse();

        when(medicalRecordService.create(medicalRecordRequest)).thenReturn(medicalRecordResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(PATH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(medicalRecordResponse.getId().toString()))
                .andExpect(jsonPath("$.animalId").value(medicalRecordResponse.getAnimalId().toString()));

        verify(medicalRecordService, times(1)).create(any());
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void shouldUpdateMedicalRecordSuccessfully() throws Exception {
        var medicalRecordRequest = buildMedicalRecordRequest();
        var medicalRecordId = medicalRecordRequest.getId();
        var medicalRecord = medicalRecordRequest.toMedicalRecord();
        var medicalRecordResponse = medicalRecord.toResponse();

        when(medicalRecordService.update(medicalRecordRequest, medicalRecordId))
                .thenReturn(medicalRecordResponse);

        mockMvc.perform(MockMvcRequestBuilders.put(PATH_URL + "/{id}", medicalRecordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalRecordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(medicalRecordId.toString()))
                .andExpect(jsonPath("$.animalId").value(medicalRecordResponse.getAnimalId().toString()));

        verify(medicalRecordService, times(1)).update(medicalRecordRequest, medicalRecordId);
        verifyNoMoreInteractions(medicalRecordService);
    }

    @Test
    public void shouldDeleteMedicalRecordSuccessfully() throws Exception {
        var medicalRecord = buildMedicalRecord();
        var medicalRecordId = buildMedicalRecord().getId();

        when(medicalRecordService.deleteById(medicalRecordId))
                .thenReturn(Optional.of(medicalRecord));

        mockMvc.perform(MockMvcRequestBuilders.delete(PATH_URL + "/{id}", medicalRecordId))
                .andExpect(status().isNoContent());

        verify(medicalRecordService, times(1)).deleteById(any());
        verifyNoMoreInteractions(medicalRecordService);
    }
}
