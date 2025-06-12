package com.algaworks.algasensors.temperature.monitoring.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TSIDToLongJPAConverter implements AttributeConverter<TSID, Long> {

    @Override
    public Long convertToDatabaseColumn(final TSID attribute) {
        return attribute.toLong();
    }

    @Override
    public TSID convertToEntityAttribute(final Long dbData) {
        return TSID.from(dbData);
    }

}
