package com.derekpark.balance.service;

import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.exception.DataNotFoundException;
import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.exception.ExpiredPeriodException;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import com.derekpark.balance.util.DistributeStrategy;
import com.derekpark.balance.util.RandomDistributeStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeServiceTest {

    @Autowired
    private DistributeService distributeService;

    private DistributeStrategy distributeStrategy;

    private DistributeDTO distributeDTO;


    @Before
    public void setUp() {

        // given
        DistributeDTO distributeDTO = new DistributeDTO();
        distributeDTO.setUserCount(3);
        distributeDTO.setAmount(12000);
        this.distributeDTO = distributeDTO;
        this.distributeStrategy = new RandomDistributeStrategy(distributeDTO);
    }


    @Transactional
    @Test
    public void distribute_create_test() throws DistributeException {

        // when
        Distribute distribute = distributeService.create("secret_room", 948824, distributeStrategy);

        // then
        Assert.assertEquals(distribute.getRecipients().size(), distributeDTO.getUserCount());

        for(Recipient recipient : distribute.getRecipients()) {
            System.out.println(recipient.getUserId());
        }

    }


    @Transactional
    @Test
    public void distribute_get_test()
            throws DistributeException, ExpiredPeriodException, DataNotFoundException {

        // when
        Distribute distribute = distributeService.create("secret_room", 948824, distributeStrategy);
        Distribute result = distributeService.getDistribute(948824, distribute.getId());

        // then
        Assert.assertEquals(distribute.getId(), result.getId());


    }


    @Test(expected = DistributeException.class)
    public void distribute_get_test_invalidUser()
            throws ExpiredPeriodException, DataNotFoundException, DistributeException {

        // when
        Distribute distribute = distributeService.create("secret_room", 948824, distributeStrategy);
        Distribute result = distributeService.getDistribute(2, distribute.getId());

        // then
        Assert.assertEquals(distribute.getId(), result.getId());


    }

}