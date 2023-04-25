package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Table(name = "friends")
public class FriendsEntity extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user1_id")
    public UserEntity user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user2_id")
    public UserEntity friendedUser;

    public boolean isConfirmed = false;

    @PastOrPresent
    public Instant friendDate;
}
