package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken extends BaseEntity {
    @ManyToOne
    @NotNull
    private UserEntity user;

    @NotBlank
    private String token;

    @CreationTimestamp
    @PastOrPresent
    private Instant creationDate;

    @FutureOrPresent
    private Instant expirationDate;

    private boolean isValid = true;

    public boolean isExpired(){
        return this.expirationDate.isBefore(Instant.now());
    }
}
