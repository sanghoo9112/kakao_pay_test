package com.derekpark.balance.util;

import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.model.Distribute;
import org.junit.Assert;
import org.junit.Test;

public class RandomDistributeStrategyTest {

    @Test
    public void random_distribute_Test() {

        // when
        DistributeDTO distributeDTO = new DistributeDTO();
        distributeDTO.setAmount(12000);
        distributeDTO.setUserCount(3);

        // given
        RandomDistributeStrategy randomDistributeStrategy =
                new RandomDistributeStrategy(distributeDTO);
        Distribute distribute = new Distribute();
        randomDistributeStrategy.distribute(distribute);


        // then
        Assert.assertEquals(distribute.getRecipients().size(), distributeDTO.getUserCount());

    }

}