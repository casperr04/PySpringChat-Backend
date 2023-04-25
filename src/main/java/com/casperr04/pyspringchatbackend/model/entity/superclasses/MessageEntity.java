package com.casperr04.pyspringchatbackend.model.entity.superclasses;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;

@MappedSuperclass
public class MessageEntity extends BaseEntity {

    @NotBlank
    @Length(max = 1000)
    public String message;

    @PastOrPresent
    @CreationTimestamp
    public Instant dateOfCreation;
}
