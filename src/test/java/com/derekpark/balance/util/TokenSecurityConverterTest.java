package com.derekpark.balance.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenSecurityConverterTest {

    @Autowired
    private TokenSecurityConverter tokenSecurityConverter;


    @Test
    public void 인코드_디코드_테스트() {

        String token = "PY";
        String encryptToken = tokenSecurityConverter.encrypt(token);
        System.out.println(encryptToken);
        String decryptToken = tokenSecurityConverter.decrypt(encryptToken);

        Assert.assertEquals(token, decryptToken);

        String token2 = "Y";
        String encryptToken2 = tokenSecurityConverter.encrypt(token2);
        System.out.println(encryptToken2);
        String decryptToken2 = tokenSecurityConverter.decrypt(encryptToken2);

        Assert.assertEquals(token2, decryptToken2);

    }


}