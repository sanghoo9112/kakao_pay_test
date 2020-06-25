package com.derekpark.balance.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "distribute")
class Distribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "amount_received")
    private Integer amountReceived;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Recipient> recipients = new HashSet<>();

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
