package com.derekpark.balance.model;

import java.time.LocalDateTime;
import java.util.HashSet;
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
public class Distribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(name = "room_id")
    private String roomId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "amount")
    private Integer amount;

    @OneToMany(mappedBy = "distribute", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Recipient> recipients = new HashSet<>();

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public void addRecipients(Recipient recipient) {
        this.recipients.add(recipient);
        recipient.setDistribute(this);
    }


    public String getRoomId() {
        return roomId;
    }


    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getAmount() {
        return amount;
    }


    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public Set<Recipient> getRecipients() {
        return recipients;
    }


    public void setRecipients(Set<Recipient> recipients) {
        this.recipients = recipients;
    }


    public LocalDateTime getRegDate() {
        return regDate;
    }


    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
