package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.ChannelEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "private_message_channel")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageChannelEntity extends ChannelEntity {

        @ManyToOne
        @NotNull
        private UserEntity sender;

        @ManyToOne
        @NotNull
        private UserEntity recipient;
    }
