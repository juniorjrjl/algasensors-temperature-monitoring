package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertDetailResponse;
import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertUpdateRequest;
import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertUpdatedResponse;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlertEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/sensors/{sensorAlertId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {

    private final SensorAlertRepository repository;

    @GetMapping
    SensorAlertDetailResponse findBySensorId(@PathVariable final TSID sensorAlertId){
        var response = repository.findById(new SensorAlertId(sensorAlertId))
                .map(e -> new SensorAlertDetailResponse(e.getId().getValue(), e.getMinTemperature(), e.getMaxTemperature()));
        return response.orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PutMapping
    SensorAlertUpdatedResponse update(@PathVariable final TSID sensorAlertId,
                                      @RequestBody final SensorAlertUpdateRequest request){
        var id = new SensorAlertId(sensorAlertId);
        var optional = repository.findById(id);
        SensorAlertEntity entity;
        if (optional.isEmpty()){
            entity = new SensorAlertEntity(id, request.getMaxTemperature(), request.getMinTemperature());
        } else {
            entity = optional.get();
            entity.setMaxTemperature(request.getMaxTemperature());
            entity.setMinTemperature(request.getMinTemperature());
        }
        repository.save(entity);
        return new SensorAlertUpdatedResponse(sensorAlertId, request.getMinTemperature(), request.getMaxTemperature());
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    void delete(@PathVariable final TSID sensorAlertId){
        var id = new SensorAlertId(sensorAlertId);
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        repository.deleteById(id);
    }

}
