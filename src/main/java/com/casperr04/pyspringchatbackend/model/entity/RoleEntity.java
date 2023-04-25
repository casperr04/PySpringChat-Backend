package com.casperr04.pyspringchatbackend.model.entity;

import com.casperr04.pyspringchatbackend.model.entity.superclasses.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
public class RoleEntity extends BaseEntity {
    @ManyToMany
    @JoinTable(
            name = "role_authorities",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id"))
    @ToString.Exclude
    private Set<RoleAuthoritiesEntity> authorities;

    @ManyToOne
    private ServerEntity server;

    @Length(min = 1, max = 32)
    @NotEmpty
    private String roleName;
}
