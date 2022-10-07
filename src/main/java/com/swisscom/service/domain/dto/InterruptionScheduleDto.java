package com.swisscom.service.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class InterruptionScheduleDto {
    private Long interruptionId;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updatedUser;
    private LocalDateTime updatedDate;
}
