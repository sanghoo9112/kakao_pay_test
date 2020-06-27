package com.derekpark.balance.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import com.derekpark.balance.model.Distribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;


@Repository
public interface DistributeRepository extends JpaRepository<Distribute, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    @Query("SELECT distribute FROM Distribute distribute join fetch distribute.recipients WHERE distribute.id = ?1")
    Distribute findByIdWithRocking(int distributeId);
}
