package com.swisscom.service.repository;

import com.swisscom.service.domain.entity.InterruptionSchedule;
import org.springframework.data.repository.CrudRepository;


public interface InterruptionScheduleRepository extends CrudRepository<InterruptionSchedule, Long> {
}
