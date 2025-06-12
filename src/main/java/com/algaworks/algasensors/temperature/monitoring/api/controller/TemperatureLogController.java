package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogResponse;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogRepository repository;

    @GetMapping
    Page<TemperatureLogResponse> search(@PathVariable final TSID sensorId,
                                        final Pageable pageable){
        var entities = repository.findAllBySensorId(new SensorId(sensorId), pageable);
        return entities.map(t -> new TemperatureLogResponse(
                t.getId().getValue(),
                t.getSensorId().getValue(),
                t.getRegisteredAt(),
                t.getValue()));
    }

}
