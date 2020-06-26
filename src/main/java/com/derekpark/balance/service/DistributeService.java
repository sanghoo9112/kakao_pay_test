package com.derekpark.balance.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import com.derekpark.balance.exception.DataNotFoundException;
import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.exception.ExpiredPeriodException;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.repository.DistributeRepository;
import com.derekpark.balance.util.Distributable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DistributeService {

    private static final int VALIDATE_DAY = 7;
    private static final ZoneId DISTRIBUTE_ZONE_ID = ZoneId.of("Asia/Seoul");
    private final DistributeRepository distributeRepository;


    DistributeService(DistributeRepository distributeRepository) {
        this.distributeRepository = distributeRepository;
    }


    @Transactional
    public Distribute create(String roomId, Integer userId, Distributable distributeStrategy)
            throws DistributeException {

        if(!isValidateRoom(roomId)) {
            throw new DistributeException("invalid room number");
        }

        if(!isValidateUser(userId)) {
            throw new DistributeException("invalid user");
        }

        Distribute distribute = new Distribute();
        distribute.setRoomId(roomId);
        distribute.setUserId(userId);
        distributeStrategy.distribute(distribute);
        distribute = distributeRepository.save(distribute);

        return distribute;
    }


    // 유효하다고 가정
    private boolean isValidateUser(Integer userId) {
        return true;
    }


    // 유효하다고 가정
    private boolean isValidateRoom(String roomId) {
        return true;
    }


    public Distribute getDistribute(Integer requestUserId, Integer distributeId)
            throws DistributeException, DataNotFoundException, ExpiredPeriodException {

        if(!isValidateUser(requestUserId)) {
            throw new DistributeException("invalid user");
        }

        Distribute distribute = distributeRepository.findById(distributeId)
                .orElseThrow(() -> new DataNotFoundException("해당 뿌리기가 존재하지 않습니다."));

        if(!distribute.getUserId().equals(requestUserId)) {
            throw new DistributeException("뿌린 사람 자신만 요청할 수 있습니다.");
        }

        if(isExpiredDate(distribute.getRegDate())) {
            throw new ExpiredPeriodException("만료된 기간입니다");
        }

        return distribute;


    }


    private boolean isExpiredDate(LocalDateTime regDate) {

        LocalDateTime localDateTime = LocalDateTime.now(DISTRIBUTE_ZONE_ID);
        return regDate.isBefore(localDateTime.minusDays(VALIDATE_DAY));

    }
}
