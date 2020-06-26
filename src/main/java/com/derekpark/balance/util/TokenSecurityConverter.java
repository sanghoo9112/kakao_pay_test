package com.derekpark.balance.util;

import com.derekpark.balance.exception.InvalidToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenSecurityConverter {

    @Value("${pay.token.privateKey}")
    private String tokenPrivateKey;


    public String encrypt(String token) {
        //DO 암호화 로직 들어가야 한다. 3자리로 리턴되게 그리고 프라이빗 키로 예측불가능하게
        return token;
    }


    public String decode(String token) throws InvalidToken {
        // decode
        return token;
    }
}
