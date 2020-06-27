package com.derekpark.balance.dto;

public enum ResponseCode {

    SUCCESS("00"), FAIL("10");

    private String code;


    ResponseCode(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


}
