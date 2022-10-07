package com.swisscom.service.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "interruption_Schedules")
@ToString
public class InterruptionSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interruptionId;
    private String description;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    private String createdUser;
    private LocalDateTime createdDate;
    private String updatedUser;
    private LocalDateTime updatedDate;
}
