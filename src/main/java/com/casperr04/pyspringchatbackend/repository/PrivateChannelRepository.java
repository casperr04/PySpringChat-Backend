package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageChannelEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PrivateChannelRepository extends JpaRepository<PrivateMessageChannelEntity, Long> {

    @Query("select p from PrivateMessageChannelEntity p where p.id = ?1")
    Optional<PrivateMessageChannelEntity> findChannelEntityByIdOptional(Long id);

    @Query("select p from PrivateMessageChannelEntity p where p.id = ?1")
    PrivateMessageChannelEntity findChannelEntityById(Long id);

    @Query("select p from PrivateMessageChannelEntity p where (p.sender = ?1 and p.recipient = ?2) or (p.sender = ?2 and p.recipient = ?1)")
    Optional<PrivateMessageChannelEntity> findPrivateChannelByUsers(UserEntity user, UserEntity user2);

    @Query("select p from PrivateMessageChannelEntity p where (p.sender.username = ?1 or p.recipient.username = ?1) and p.id = ?2")
    Optional<PrivateMessageChannelEntity> findIfUserIsInChannel(String username, Long id);

    @Query("select p from PrivateMessageChannelEntity p where p.recipient.username = ?1 or p.sender.username = ?1")
    ArrayList<PrivateMessageChannelEntity> findChannelsByUsername(String username);
}
