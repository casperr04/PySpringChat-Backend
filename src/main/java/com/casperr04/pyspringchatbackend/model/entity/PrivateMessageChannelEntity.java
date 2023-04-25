package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.ChannelEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
    @Entity
    @Table(name = "private_message_channel")
    @Getter
    @Setter
    @ToString
    public class PrivateMessageChannelEntity extends ChannelEntity {
        @ManyToOne
        @NotNull
        private UserEntity sender;

        @ManyToOne
        @NotNull
        private UserEntity recipient;
    }
