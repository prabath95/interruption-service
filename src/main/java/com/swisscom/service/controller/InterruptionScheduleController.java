package com.swisscom.service.controller;

import com.swisscom.service.domain.dto.InterruptionScheduleDto;
import com.swisscom.service.service.InterruptionScheduleService;
import com.swisscom.service.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interruption-schedule")
public class InterruptionScheduleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterruptionScheduleController.class);
    private InterruptionScheduleService interruptionScheduleService;

    public InterruptionScheduleController(InterruptionScheduleService interruptionScheduleService) {
        this.interruptionScheduleService = interruptionScheduleService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createInterruptionSchedule(
            @RequestBody InterruptionScheduleDto interruptionScheduleDto) {

        LOGGER.info("Create interruption schedule task request {} ", interruptionScheduleDto);

        interruptionScheduleService.createInterruptionSchedule(interruptionScheduleDto);

        LOGGER.info("Create interruption schedule task created request {} ", interruptionScheduleDto);

        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.CREATED)
                .withMessage("Schedule created successfully")
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateInterruptionSchedule(
            @PathVariable Long id,
            @RequestBody InterruptionScheduleDto interruptionScheduleDto) {

        LOGGER.info("Update interruption schedule task id {} request {} ", id, interruptionScheduleDto);

        interruptionScheduleService.updateInterruptionSchedule(interruptionScheduleDto,id);

        LOGGER.info("Create interruption schedule task id {} updated request {} ", id, interruptionScheduleDto);

        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withMessage("Schedule updated successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getInterruptionSchedule(@PathVariable Long id) {

        LOGGER.info("Fetch interruption schedule task  request ID  {} ", id);

        InterruptionScheduleDto interruptionScheduleDto = interruptionScheduleService.getInterruptionSchedule(id);

        LOGGER.info("Fetch interruption schedule task  request ID  {} | response  ", id, interruptionScheduleDto);

        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withData(interruptionScheduleDto)
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getInterruptionSchedule() {

        LOGGER.info("Fetch all interruption schedules.");

        List<InterruptionScheduleDto> interruptionSchedules = interruptionScheduleService.getAllInterruptionSchedules();

        LOGGER.info("Fetch all interruption schedules response {} ", interruptionSchedules);

        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withData(interruptionSchedules)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteInterruptionSchedule(@PathVariable Long id) {

        LOGGER.info("Delete interruption schedule request ID {}.", id);

        interruptionScheduleService.deleteInterruptionSchedule(id);

        LOGGER.info("Interruption schedule request deleted ID {}.", id);

        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withMessage("Schedule deleted successfully")
                .build();
    }
}
