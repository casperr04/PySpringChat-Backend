package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateMessageEntityRepository extends JpaRepository<PrivateMessageEntity, Long> {
}
