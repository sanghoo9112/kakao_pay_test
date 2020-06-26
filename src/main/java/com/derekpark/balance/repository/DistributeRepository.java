package com.derekpark.balance.repository;

import com.derekpark.balance.model.Distribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DistributeRepository extends JpaRepository<Distribute, Long> {

}
