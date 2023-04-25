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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class UserEntity extends BaseEntity implements UserDetails {

    @NotBlank
    @Length(min = 4, max = 32)
    private String username;

    @NotBlank
    private String encryptedPassword;

    @Length(min = 12, max = 12)
    @NotBlank
    @Column(unique = true)
    private String publicUserId;

    @ManyToMany
    @JoinTable(
            name = "pm_channels_to_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "private_message_channel_id")
    )
    @ToString.Exclude
    private Set<PrivateMessageChannelEntity> privateMessageChannels;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<FriendsEntity> friendedBy;

    @OneToMany(mappedBy = "friendedUser")
    @ToString.Exclude
    private Set<FriendsEntity> sentFriends;


    @CreationTimestamp
    @PastOrPresent
    @Column(columnDefinition = "TIMESTAMP")
    private Instant creationDate;

    private boolean isBanned = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isBanned();
    }
}
