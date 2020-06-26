package com.derekpark.balance.util;

import java.util.HashSet;
import java.util.Set;
import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.dto.RecipientDTO;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DistributeConverter implements Converter<Distribute, DistributeDTO> {

    @Override
    public DistributeDTO convert(Distribute source) {

        DistributeDTO distributeDTO = new DistributeDTO();
        distributeDTO.setRegDate(source.getRegDate());
        distributeDTO.setAmount(source.getAmount());

        int amountReceive = 0;
        int userCount = 0;

        Set<RecipientDTO> recipientSet = new HashSet<>();

        for(Recipient recipient : source.getRecipients()) {

            if(recipient.getUserId() != null) {

                amountReceive += recipient.getAmount();
                userCount++;
                RecipientDTO recipientDTO = new RecipientDTO();
                recipientDTO.setAmount(recipient.getAmount());
                recipientDTO.setUserId(recipient.getUserId());
                recipientSet.add(recipientDTO);
            }

        }

        distributeDTO.setAmountReceive(amountReceive);
        distributeDTO.setUserCount(userCount);
        distributeDTO.setRecipients(recipientSet);

        return distributeDTO;
    }
}
