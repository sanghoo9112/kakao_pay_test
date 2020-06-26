package com.derekpark.balance.util;

import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.model.Distribute;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DistributeConverter implements Converter<Distribute, DistributeDTO> {

    @Override
    public DistributeDTO convert(Distribute source) {
        return null;
    }
}
