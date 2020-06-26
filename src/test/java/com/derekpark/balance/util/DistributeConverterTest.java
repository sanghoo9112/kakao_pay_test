package com.derekpark.balance.util;

import java.time.LocalDateTime;
import com.derekpark.balance.dto.DistributeDTO;
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
public class DistributeConverterTest {

    @Autowired
    private DistributeConverter distributeConverter;


    @Test
    public void convert_test() {

        //given
        Distribute distribute = new Distribute();
        distribute.setId(1);
        distribute.setAmount(1000);
        distribute.setRoomId("a");
        distribute.setUserId(948824);
        distribute.setRegDate(LocalDateTime.now());

        Recipient recipient1 = new Recipient();
        recipient1.setAmount(200);
        recipient1.setUserId(1);
        distribute.addRecipients(recipient1);

        Recipient recipient2 = new Recipient();
        recipient2.setAmount(800);
        distribute.addRecipients(recipient2);

        // when
        DistributeDTO distributeDTO = distributeConverter.convert(distribute);

        // then
        Assert.assertEquals(distributeDTO.getAmountReceive(), 200);


    }
}