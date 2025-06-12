package com.algaworks.algasensors.temperature.monitoring.domain.repository;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoringEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureLogRepository extends JpaRepository<TemperatureLogEntity, TemperatureLogId> {

    Page<TemperatureLogEntity> findAllBySensorId(final SensorId sensorId, final Pageable pageable);

}
