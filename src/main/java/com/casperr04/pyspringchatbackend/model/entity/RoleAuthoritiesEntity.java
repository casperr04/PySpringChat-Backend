package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.enums.RoleAuthorities;
import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "role_authority")
@Getter
@Setter
@ToString
public class RoleAuthoritiesEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleAuthorities authorityType;

    @ManyToMany(mappedBy = "authorities")
    @ToString.Exclude
    private Set<RoleEntity> roles;
}
