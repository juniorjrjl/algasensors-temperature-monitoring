package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.SensorMonitoringDetailResponse;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoringEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

    private final SensorMonitoringRepository repository;

    @GetMapping
    SensorMonitoringDetailResponse getDetail(@PathVariable final TSID sensorId){
        var entity = findByIdOrDefault(sensorId);
        return new SensorMonitoringDetailResponse(
                entity.getId().getValue(),
                entity.getLastTemperature(),
                entity.getUpdatedAt(),
                entity.getEnabled()
        );
    }


    @PutMapping("/enable")
    @ResponseStatus(NO_CONTENT)
    void enable(@PathVariable final TSID sensorId){
        var entity = findByIdOrDefault(sensorId);
        if (nonNull(entity.getEnabled()) && entity.getEnabled()){
            throw new ResponseStatusException(UNPROCESSABLE_ENTITY);
        }
        entity.setEnabled(true);
        repository.save(entity);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(NO_CONTENT)
    @SneakyThrows
    void disable(@PathVariable final TSID sensorId){
        var entity = findByIdOrDefault(sensorId);
        if (nonNull(entity.getEnabled()) && !entity.getEnabled()){
            TimeUnit.SECONDS.sleep(10);
        }
        entity.setEnabled(false);
        repository.save(entity);
    }

    private SensorMonitoringEntity findByIdOrDefault(final TSID sensorId) {
        return repository.findById(new SensorId(sensorId))
                .orElse(new SensorMonitoringEntity(
                        new SensorId(sensorId),
                        null,
                        null,
                        null));
    }

}
