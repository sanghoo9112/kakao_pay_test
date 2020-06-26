package com.derekpark.balance.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenManagerTest {

    @Autowired
    private AuthTokenManager authTokenManager;


    @Test
    public void Token_generator_test() {

        //given
        Integer id = (int) Character.MAX_VALUE - 100;
        Integer id2 = (int) Character.MAX_VALUE + 1000;

        // when
        String token = authTokenManager.generate(id);
        String token2 = authTokenManager.generate(id2);
        Integer resultId = authTokenManager.getTokenIndex(token);
        Integer resultId2 = authTokenManager.getTokenIndex(token2);

        //then
        Assert.assertEquals(id, resultId);
        Assert.assertEquals(id2, resultId2);


    }

}