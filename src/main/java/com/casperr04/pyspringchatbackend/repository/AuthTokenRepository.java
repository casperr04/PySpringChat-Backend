package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    @Query("select a from AuthToken a where a.token = ?1")
    Optional<AuthToken> findAuthTokenByToken(String token);
}