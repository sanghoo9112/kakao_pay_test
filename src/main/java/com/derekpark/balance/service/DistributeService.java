package com.derekpark.balance.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import com.derekpark.balance.exception.DataNotFoundException;
import com.derekpark.balance.exception.DistributeException;
import com.derekpark.balance.exception.ExpiredPeriodException;
import com.derekpark.balance.model.Distribute;
import com.derekpark.balance.model.Recipient;
import com.derekpark.balance.repository.DistributeRepository;
import com.derekpark.balance.util.Distributable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DistributeService {

    private static final int VALIDATE_DAY = 7;
    private static final int VALIDATE_MINUTE = 10;
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

    @Transactional
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


    @Transactional
    public int updateDistribute(Integer requestUserId, String roomId, Integer distributeId)
            throws DistributeException, DataNotFoundException, ExpiredPeriodException {

        if(!isValidateUser(requestUserId)) {
            throw new DistributeException("invalid user");
        }

        Distribute distribute = distributeRepository.findByIdWithRocking(distributeId);

        if(distribute == null) {
            throw new DataNotFoundException("해당 뿌리기가 존재하지 않습니다.");
        }

        if(distribute.getUserId().equals(requestUserId)) {
            throw new DistributeException("자신이 뿌리기한 건은 자신이 받을 수 없습니다.");
        }

        if(!distribute.getRoomId().equals(roomId)) {
            throw new DistributeException("뿌린기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.");
        }

        if(!isValidatePeriod(distribute.getRegDate())) {
            throw new ExpiredPeriodException("뿌린 건은 10분간만 유효합니다.");
        }

        if(isAlreadyReceivedUser(distribute, requestUserId)) {
            throw new DistributeException("이미 참여하셨습니다.");
        }

        Recipient recipient =
                distribute.getRecipients().stream().filter(x -> x.getUserId() == null).findAny()
                        .orElseThrow(() -> new DataNotFoundException("모두 가져갔습니다. 아쉽지만 다음 기회에"));

        recipient.setUserId(requestUserId);

        return recipient.getAmount();

    }


    private boolean isAlreadyReceivedUser(Distribute distribute, Integer requestUserId) {

        for(Recipient recipient : distribute.getRecipients()) {
            if(recipient.getUserId() != null
                    && recipient.getUserId().compareTo(requestUserId) == 0) {
                return true;
            }
        }
        return false;
    }


    private boolean isValidatePeriod(LocalDateTime regDate) {

        LocalDateTime localDateTime = LocalDateTime.now(DISTRIBUTE_ZONE_ID);
        return !regDate.isBefore(localDateTime.minusMinutes(VALIDATE_MINUTE));
    }
}
