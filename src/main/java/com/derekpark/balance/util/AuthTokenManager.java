package com.derekpark.balance.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenManager {

    private static final int CHAR_MAX_VALUE = 65535;
    private final TokenSecurityConverter tokenSecurityConverter;


    @Autowired
    AuthTokenManager(TokenSecurityConverter tokenSecurityConverter) {
        this.tokenSecurityConverter = tokenSecurityConverter;
    }


    public String generate(Integer id) {

        String token;

        if(id > CHAR_MAX_VALUE) {
            int upperNum = id / CHAR_MAX_VALUE;
            int downNum = id % CHAR_MAX_VALUE;
            token = Character.toString(upperNum) + Character.toString(downNum);
        } else {
            token = Character.toString(id);
        }

        return tokenSecurityConverter.encrypt(token);
    }


    public int getTokenIndex(String token) {

        int value = 0;
        String decodeToken = tokenSecurityConverter.decode(token);
        if(decodeToken.length() == 2) {
            value = (int) decodeToken.charAt(0) * CHAR_MAX_VALUE + (int) decodeToken.charAt(1);
        } else if(decodeToken.length() == 1) {
            value = decodeToken.charAt(0);
        }

        return value;
    }

}
