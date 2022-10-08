package com.swisscom.service.service.impl;

import com.swisscom.service.domain.dto.InterruptionScheduleDto;
import com.swisscom.service.domain.entity.InterruptionSchedule;
import com.swisscom.service.repository.InterruptionScheduleRepository;
import com.swisscom.service.service.InterruptionScheduleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InterruptionScheduleServiceImpl implements InterruptionScheduleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterruptionScheduleServiceImpl.class);
    private InterruptionScheduleRepository interruptionScheduleRepository;
    private ModelMapper modelMapper;
    private SimpMessagingTemplate simpMessagingTemplate;

    public InterruptionScheduleServiceImpl(InterruptionScheduleRepository interruptionScheduleRepository,
                                           ModelMapper modelMapper,
                                           SimpMessagingTemplate simpMessagingTemplate) {
        this.interruptionScheduleRepository = interruptionScheduleRepository;
        this.modelMapper = modelMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * Save schedule data and send it to the websocket to update clients
     * @param interruptionScheduleDto
     */
    @Override
    public void createInterruptionSchedule(InterruptionScheduleDto interruptionScheduleDto,String username) {
        InterruptionSchedule interruptionSchedule = modelMapper.map(interruptionScheduleDto, InterruptionSchedule.class);
        interruptionSchedule.setCreatedUser(username);
        interruptionSchedule.setCreatedDate(LocalDateTime.now());
        InterruptionSchedule createdSchedule = interruptionScheduleRepository.save(interruptionSchedule);

        // Submit data to websocket for update subscribed clients
        LOGGER.info("Send data to websocket after creating schedule {} ", createdSchedule);
        simpMessagingTemplate.convertAndSend("/topic/updateSchedule", createdSchedule);
    }

    /**
     *  get interruption schedule using schedule id
     * @param scheduleId
     * @return
     */
    @Override
    public InterruptionScheduleDto getInterruptionSchedule(Long scheduleId) {
        InterruptionSchedule interruptionSchedule = interruptionScheduleRepository
                .findById(scheduleId)
                .orElse(null);
        return modelMapper.map(interruptionSchedule, InterruptionScheduleDto.class);
    }

    /**
     * get all interruption schedules
     * @return
     */
    @Override
    public List<InterruptionScheduleDto> getAllInterruptionSchedules() {
        Iterable<InterruptionSchedule> interruptionSchedules = interruptionScheduleRepository.findAll();
        return modelMapper.map(interruptionSchedules, new TypeToken<List<InterruptionScheduleDto>>() {
        }.getType());
    }

    /**
     * Update interruption schedule using schedule id and publish to web socket
     * @param interruptionScheduleDto
     * @param id
     */
    @Override
    public void updateInterruptionSchedule(InterruptionScheduleDto interruptionScheduleDto, Long id) {
        interruptionScheduleDto.setInterruptionId(id);
        InterruptionSchedule interruptionSchedule = modelMapper.map(
                interruptionScheduleDto,
                InterruptionSchedule.class
        );
        interruptionScheduleRepository.save(interruptionSchedule);

        // Submit data to websocket for update subscribed clients
        LOGGER.info("Send data to websocket after updating schedule {} ", interruptionSchedule);
        simpMessagingTemplate.convertAndSend("/topic/updateSchedule", interruptionSchedule);
    }

    /**
     * Delete interruption schedule by id and publish to web socket
     * @param scheduleId
     */
    @Override
    public void deleteInterruptionSchedule(Long scheduleId) {
        interruptionScheduleRepository.deleteById(scheduleId);

        // Submit data to websocket for update subscribed clients
        LOGGER.info("Send data to websocket after deleting schedule id {} ", scheduleId);
        simpMessagingTemplate.convertAndSend("/topic/updateSchedule", scheduleId);
    }

}
