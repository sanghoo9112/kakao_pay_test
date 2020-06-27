package com.derekpark.balance.util;

import java.util.Random;
import com.derekpark.balance.exception.InvalidToken;
import org.springframework.stereotype.Service;

@Service
public class TokenSecurityConverter {

    private static final int PRIVATE_NUM = 300;

    public String encrypt(String token) {
        //DO 암호화 로직 들어가야 한다. 3자리로 리턴되게 그리고 프라이빗 키로 예측불가능하게
        int strCount = token.length();
        String prefixToken = null;

        if(strCount == 2) {
            prefixToken = Character.toString(strCount + PRIVATE_NUM);
        } else if(strCount == 1) {

            Random random = new Random();

            prefixToken =
                    Character.toString(PRIVATE_NUM) + Character.toString(random.nextInt(5000));
        } else {
            throw new InvalidToken("허용 값과 맞지 않습니다. ");
        }
        return prefixToken + token;
    }


    public String decrypt(String token) throws InvalidToken {

        if(token.length() != 3) {
            throw new InvalidToken("invalid Token");
        }

        // decode
        int prefixNum = token.charAt(0);

        if(prefixNum > PRIVATE_NUM + 2) {
            throw new InvalidToken("invalid Token");
        }

        int strCount = prefixNum - PRIVATE_NUM;

        if(strCount == 0) {
            return Character.toString(token.charAt(2));
        } else if(strCount == 2) {
            return Character.toString(token.charAt(1)) + Character.toString(token.charAt(2));
        } else {
            throw new InvalidToken("invalid Token");
        }
    }
}
