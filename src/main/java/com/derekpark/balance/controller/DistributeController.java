package com.derekpark.balance.controller;

import com.derekpark.balance.dto.DistributeDTO;
import com.derekpark.balance.dto.DistributeResponse;
import com.derekpark.balance.dto.ResponseCode;
import com.derekpark.balance.exception.DataNotFoundException;
import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.exception.ExpiredPeriodException;
import com.derekpark.balance.exception.InvalidToken;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.service.DistributeService;
import com.derekpark.balance.util.AuthTokenManager;
import com.derekpark.balance.util.Distributable;
import com.derekpark.balance.util.DistributeConverter;
import com.derekpark.balance.util.RandomDistributeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pay")
public class DistributeController {


    private final DistributeService distributeService;
    private final AuthTokenManager autoTokenManager;
    private final DistributeConverter distributeConverter;


    @Autowired
    public DistributeController(DistributeService distributeService,
            AuthTokenManager autoTokenManager, DistributeConverter distributeConverter) {
        this.distributeService = distributeService;
        this.autoTokenManager = autoTokenManager;
        this.distributeConverter = distributeConverter;
    }


    @PostMapping(value = "/distribute")
    public ResponseEntity<DistributeResponse<String>> create(
            @RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestHeader(value = "X-USER-ID") Integer userId,
            @RequestBody DistributeDTO distributeDTO) throws DistributeException {

        Distributable distributeStrategy = new RandomDistributeStrategy(distributeDTO);

        Distribute distribute = distributeService.create(roomId, userId, distributeStrategy);

        String authToken = autoTokenManager.generate(distribute.getId());

        DistributeResponse<String> response = new DistributeResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setBody(authToken);

        return ResponseEntity.ok(response);

    }


    @PutMapping(value = "/distribute/{token}")
    public ResponseEntity<DistributeResponse<Integer>> updateDistribute(
            @RequestHeader(value = "X-ROOM-ID") String roomId,
            @RequestHeader(value = "X-USER-ID") Integer requestUserId,
            @PathVariable("token") String token) {

        int distributeId;

        try {
            distributeId = autoTokenManager.getTokenIndex(token);
        } catch(InvalidToken e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            int amount = distributeService.updateDistribute(requestUserId, roomId, distributeId);
            DistributeResponse<Integer> distributeResponse = new DistributeResponse<>();
            distributeResponse.setCode(ResponseCode.SUCCESS.getCode());
            distributeResponse.setBody(amount);
            return ResponseEntity.ok(distributeResponse);

        } catch(DataNotFoundException | ExpiredPeriodException | DistributeException e) {

            DistributeResponse<Integer> distributeResponse = new DistributeResponse<>();
            distributeResponse.setCode(ResponseCode.FAIL.getCode());
            distributeResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(distributeResponse);

        }

    }


    @GetMapping(value = "/distribute/{token}")
    public ResponseEntity<DistributeResponse<DistributeDTO>> getDistribute(
            @RequestHeader(value = "X-USER-ID") Integer requestUserId,
            @PathVariable("token") String token) {

        int distributeId;

        try {
            distributeId = autoTokenManager.getTokenIndex(token);
        } catch(InvalidToken e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DistributeDTO distributeDTO;

        try {
            Distribute distribute = distributeService.getDistribute(requestUserId, distributeId);
            distributeDTO = distributeConverter.convert(distribute);
            DistributeResponse<DistributeDTO> distributeResponse = new DistributeResponse<>();
            distributeResponse.setCode(ResponseCode.SUCCESS.getCode());
            distributeResponse.setBody(distributeDTO);
            return ResponseEntity.ok(distributeResponse);

        } catch(DataNotFoundException | ExpiredPeriodException | DistributeException e) {

            DistributeResponse<DistributeDTO> distributeResponse = new DistributeResponse<>();
            distributeResponse.setCode(ResponseCode.FAIL.getCode());
            distributeResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(distributeResponse);

        }

    }


}
