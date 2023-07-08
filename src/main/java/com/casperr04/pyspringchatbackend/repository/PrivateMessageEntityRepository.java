package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageEntity;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@NonNullApi
public interface PrivateMessageEntityRepository extends JpaRepository<PrivateMessageEntity, Long> {
    @Override
    Optional<PrivateMessageEntity> findById(Long id);
}
