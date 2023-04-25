package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.ChannelEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "server_channel")
@Getter
@Setter
@ToString
public class ServerChannelEntity extends ChannelEntity {

    @ManyToOne
    @JoinColumn(name = "server_id")
    @NotNull
    public ServerEntity server;

    @ManyToMany
    @JoinTable(
            name = "server_channel_messages",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    @ToString.Exclude
    private Set<ChannelMessageEntity> messages;

    @ManyToMany
    @JoinTable(
            name = "channel_access",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<RoleEntity> access;
}
