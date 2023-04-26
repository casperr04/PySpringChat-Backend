package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(long id);
    Optional<UserEntity> findUserByUsername(String username);
    @Query("select u from UserEntity u inner join u.tokens tokens where tokens.token = ?1")
    Optional<UserEntity> findByTokensToken(@NotBlank String token);
}