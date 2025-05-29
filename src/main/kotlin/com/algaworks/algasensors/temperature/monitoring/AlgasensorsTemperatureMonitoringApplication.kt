package com.algaworks.algasensors.temperature.monitoring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlgasensorsTemperatureMonitoringApplication

fun main(args: Array<String>) {
	runApplication<AlgasensorsTemperatureMonitoringApplication>(*args)
}
