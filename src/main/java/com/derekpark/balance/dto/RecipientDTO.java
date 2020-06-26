package com.derekpark.balance.dto;

public class RecipientDTO {

    private Integer userId;
    private Integer amount;


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getAmount() {
        return amount;
    }


    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
