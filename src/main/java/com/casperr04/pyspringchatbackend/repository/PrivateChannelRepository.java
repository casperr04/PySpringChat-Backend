package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageChannelEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivateChannelRepository extends JpaRepository<PrivateMessageChannelEntity, Long> {

    @Query("select p from PrivateMessageChannelEntity p where p.id = ?1")
    Optional<PrivateMessageChannelEntity> findChannelEntityById(Long id);

    @Query("select p from PrivateMessageChannelEntity p where p.sender = ?1 and p.recipient = ?2 or p.sender = ?2 and p.recipient = ?1")
    Optional<PrivateMessageChannelEntity> findPrivateChannelByUsers(UserEntity user, UserEntity user2);
}
