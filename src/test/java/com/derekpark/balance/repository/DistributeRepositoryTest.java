package com.derekpark.balance.repository;


import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeRepositoryTest {

    @Autowired
    private DistributeRepository distributeRepository;


    @Test
    public void distribute_save_test() {

        //given
        Distribute distribute = new Distribute();
        distribute.setAmount(1000);
        distribute.setRoomId("a");
        distribute.setUserId(948824L);

        Recipient recipient1 = new Recipient();
        recipient1.setAmount(200);
        recipient1.setUserId(1L);
        distribute.addRecipients(recipient1);

        Recipient recipient2 = new Recipient();
        recipient2.setAmount(800);
        recipient2.setUserId(2L);
        distribute.addRecipients(recipient2);

        // when
        Distribute result = distributeRepository.save(distribute);

        // then
        Assert.assertEquals(result.getId(), distribute.getId());
        Assert.assertEquals(result.getRecipients().size(), distribute.getRecipients().size());
    }


    @Test
    public void distribute_find_test() {


    }

}