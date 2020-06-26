package com.derekpark.balance.service;


import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.util.Distributable;
import org.springframework.stereotype.Service;

@Service
public class DistributeService {

    public Distribute create(String roomId, Long userId, Distributable distributeStrategy)
            throws DistributeException {

        if(isValidateRoom(roomId)) {
            throw new DistributeException("invalid room number");
        }

        if(isValidateUser(userId)) {
            throw new DistributeException("invalid user");
        }


        return null;
    }


    // 유효하다고 가정
    private boolean isValidateUser(Long userId) {
        return true;
    }


    // 유효하다고 가정
    private boolean isValidateRoom(String roomId) {
        return true;
    }
}
