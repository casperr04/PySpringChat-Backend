package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.enums.RoleAuthorities;
import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role_authority")
@Getter
@Setter
@ToString
public class RoleAuthoritiesEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleAuthorities authorityType;
}
