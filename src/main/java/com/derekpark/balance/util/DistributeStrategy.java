package com.derekpark.balance.util;

import com.derekpark.balance.dto.DistributeDTO;

public abstract class DistributeStrategy implements Distributable {

    public DistributeDTO distributeDTO;


    DistributeStrategy(DistributeDTO distributeDTO) {
        this.distributeDTO = distributeDTO;
    }
}
