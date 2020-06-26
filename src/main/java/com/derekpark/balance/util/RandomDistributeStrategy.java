package com.derekpark.balance.util;

import java.util.Random;
import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import org.springframework.transaction.annotation.Transactional;

public class RandomDistributeStrategy extends DistributeStrategy {

    public RandomDistributeStrategy(DistributeDTO distributeDTO) {
        super(distributeDTO);
    }


    @Transactional
    @Override
    public void distribute(Distribute distribute) {

        Random random = new Random();

        int userCount = distributeDTO.getUserCount();

        distribute.setAmount(distributeDTO.getAmount());

        int initRandomAmount = 0;

        for(int i = 0; i < userCount; i++) {

            int nextAmount;

            if(i == userCount - 1) {
                nextAmount = distributeDTO.getAmount() - initRandomAmount;
            } else {
                nextAmount = (random.nextInt(distributeDTO.getAmount() - initRandomAmount) + 1);

            }
            Recipient recipient = new Recipient();
            recipient.setAmount(nextAmount);
            distribute.addRecipients(recipient);
            initRandomAmount += nextAmount;

        }


    }
}
