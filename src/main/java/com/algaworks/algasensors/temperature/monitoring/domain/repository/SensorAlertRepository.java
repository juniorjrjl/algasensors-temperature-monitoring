package com.algaworks.algasensors.temperature.monitoring.domain.repository;

import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlertEntity;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorAlertRepository extends JpaRepository<SensorAlertEntity, SensorAlertId> {
}
