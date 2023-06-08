package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.ChannelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

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

        @ManyToMany
        @JoinTable(
                name = "private_channel_messages",
                joinColumns = @JoinColumn(name = "channel_id"),
                inverseJoinColumns = @JoinColumn(name = "message_id")
        )
        @ToString.Exclude
        private Set<PrivateMessageEntity> messages;
    }
