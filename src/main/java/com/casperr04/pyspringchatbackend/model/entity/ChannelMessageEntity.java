package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.MessageEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "channel_message")
@Getter
@Setter
@ToString
public class ChannelMessageEntity extends MessageEntity {
    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ServerChannelEntity channel;

}
