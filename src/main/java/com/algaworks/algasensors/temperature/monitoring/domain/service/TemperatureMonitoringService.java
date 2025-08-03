package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogResponse;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository repository;

    @Transactional
    public void process(final TemperatureLogResponse data){
        sensorMonitoringRepository.findById(new SensorId(data.getSensorId()))
                .ifPresentOrElse(sensor -> {
                    if (Objects.nonNull(sensor.getEnabled()) && sensor.getEnabled()){
                        sensor.setLastTemperature(data.getValue());
                        sensor.setUpdatedAt(OffsetDateTime.now());
                        sensorMonitoringRepository.save(sensor);

                        var temperatureLog = new TemperatureLogEntity();
                        temperatureLog.setId(new TemperatureLogId(data.getId()));
                        temperatureLog.setRegisteredAt(data.getRegisteredAt());
                        temperatureLog.setValue(data.getValue());
                        repository.save(temperatureLog);
                    } else {
                        log.info("Ignoring event {}, sensor is disabled", data);
                    }
                }, () -> log.info("Ignoring event {}, sensor not found", data));
    }

}
