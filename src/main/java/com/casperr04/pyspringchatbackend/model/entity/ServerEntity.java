package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "server")
@Getter
@Setter
@ToString
public class ServerEntity extends BaseEntity {

    @OneToMany(mappedBy = "server")
    @ToString.Exclude
    private Set<ServerChannelEntity> channels;

    @Length(min = 3, max = 32)
    @NotBlank
    private String serverName;

    @Length(max = 255)
    private String serverDescription;

    @ManyToMany
    @JoinTable(
            name = "server_users",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<UserEntity> users;

    @Length(min = 4, max = 9)
    @NotBlank
    @Column(unique = true)
    private String inviteId;

    @PastOrPresent
    @CreationTimestamp
    private Instant creationDate;

    @OneToMany
    @JoinColumn(name = "server_id")
    @ToString.Exclude
    private Set<RoleEntity> roles;

    @ManyToMany
    @JoinTable(
            name = "server_ban_list",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<UserEntity> banList;
}
