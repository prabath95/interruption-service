package com.swisscom.service.service;

import com.swisscom.service.domain.dto.InterruptionScheduleDto;

import java.util.List;

public interface InterruptionScheduleService {
    void createInterruptionSchedule(InterruptionScheduleDto interruptionSchedule,String username);

    void deleteInterruptionSchedule(Long scheduleId);

    void updateInterruptionSchedule(InterruptionScheduleDto interruptionSchedule,Long id);

    InterruptionScheduleDto getInterruptionSchedule(Long scheduleId);

    List<InterruptionScheduleDto> getAllInterruptionSchedules();
}
