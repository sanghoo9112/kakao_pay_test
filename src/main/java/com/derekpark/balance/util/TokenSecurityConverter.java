package com.derekpark.balance.util;

import com.derekpark.balance.exception.OutOfIdException;
import org.springframework.stereotype.Service;

@Service
public class TokenSecurityConverter {

    public String encrypt(String token) {
        //DO 암호화 로직 들어가야 한다. 예측불가능하게
        if(token.length() == 1) {
            return "00" + token;
        } else if(token.length() == 2) {
            return "0" + token;
        } else {
            throw new OutOfIdException("token 은 3자리를 넘어갈 수 없습니다.");
        }
    }


    public String decode(String token) {

        return null;
    }
}
