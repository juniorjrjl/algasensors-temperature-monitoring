package com.algaworks.algasensors.temperature.monitoring.domain.model;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class SensorId implements Serializable {

    private TSID value;

    public SensorId(@NonNull final TSID value){
        this.value = value;
    }

    public SensorId(@NonNull final Long value){
        this.value = TSID.from(value);
    }

    public SensorId(@NonNull final String value){
        this.value = TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof SensorId sensorId)) return false;
        return Objects.equals(value, sensorId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
