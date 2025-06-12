package com.algaworks.algasensors.temperature.monitoring.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SensorAlertUpdateRequest {

    private Double minTemperature;
    private Double maxTemperature;
}
