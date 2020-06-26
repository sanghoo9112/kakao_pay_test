package com.derekpark.balance.controller;

import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.model.AuthToken;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.service.DistributeService;
import com.derekpark.balance.util.Distributable;
import com.derekpark.balance.util.RandomDistributeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pay")
public class DistributeController {

    private final DistributeService distributeService;


    @Autowired
    DistributeController(DistributeService distributeService) {
        this.distributeService = distributeService;
    }


    @PostMapping(value = "/distribute")
    public ResponseEntity<AuthToken> create(@RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestHeader(value = "X-USER-ID") Long userId) throws DistributeException {

        Distributable distributeStrategy = new RandomDistributeStrategy();

        Distribute distribute = distributeService.create(roomId, userId, distributeStrategy);

        AuthToken authToken = AuthTokenGenerator.generate(distribute.getId());

        return ResponseEntity.ok(authToken);

    }


}
