package com.derekpark.balance.dto;

import java.time.LocalDateTime;
import java.util.Set;

public class DistributeDTO {

    private int amount;
    private int userCount;
    private LocalDateTime regDate;
    private int amountReceive;
    private Set<RecipientDTO> recipients;


    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }


    public int getUserCount() {
        return userCount;
    }


    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }


    public LocalDateTime getRegDate() {
        return regDate;
    }


    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }


    public int getAmountReceive() {
        return amountReceive;
    }


    public void setAmountReceive(int amountReceive) {
        this.amountReceive = amountReceive;
    }


    public Set<RecipientDTO> getRecipients() {
        return recipients;
    }


    public void setRecipients(Set<RecipientDTO> recipients) {
        this.recipients = recipients;
    }
}
