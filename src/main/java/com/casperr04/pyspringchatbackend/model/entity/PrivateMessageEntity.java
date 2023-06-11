package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@Entity
@Table(name = "private_message")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessageEntity extends BaseEntity {

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    private PrivateMessageChannelEntity channel;

    @NotBlank
    @Length(max = 1000)
    public String message;

    @PastOrPresent
    @CreationTimestamp
    public Instant dateOfCreation;
}
