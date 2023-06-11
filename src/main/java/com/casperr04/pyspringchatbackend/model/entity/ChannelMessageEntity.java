package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Entity
@Table(name = "channel_message")
@Getter
@Setter
@ToString
public class ChannelMessageEntity extends BaseEntity {

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    private ServerChannelEntity channel;

    @NotBlank
    @Length(max = 1000)
    public String message;

    @PastOrPresent
    @CreationTimestamp
    public Instant dateOfCreation;

}
