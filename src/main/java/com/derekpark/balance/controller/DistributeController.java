package com.derekpark.balance.controller;

import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.service.DistributeService;
import com.derekpark.balance.util.AuthTokenManager;
import com.derekpark.balance.util.Distributable;
import com.derekpark.balance.util.RandomDistributeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pay")
public class DistributeController {

    private final DistributeService distributeService;
    private final AuthTokenManager autoTokenManager;


    @Autowired
    public DistributeController(DistributeService distributeService,
            AuthTokenManager autoTokenManager) {
        this.distributeService = distributeService;
        this.autoTokenManager = autoTokenManager;
    }


    @PostMapping(value = "/distribute")
    public ResponseEntity<String> create(@RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestHeader(value = "X-USER-ID") Long userId,
            @RequestBody DistributeDTO distributeDTO) throws DistributeException {

        Distributable distributeStrategy = new RandomDistributeStrategy(distributeDTO);

        Distribute distribute = distributeService.create(roomId, userId, distributeStrategy);

        String authToken = autoTokenManager.generate(distribute.getId());

        return ResponseEntity.ok(authToken);

    }


}
