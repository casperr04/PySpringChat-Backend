package com.casperr04.pyspringchatbackend.repository;

import com.casperr04.pyspringchatbackend.model.entity.FriendsEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<FriendsEntity, Long> {

    @Query("select f from FriendsEntity f where f.user = ?1 or f.friendedUser = ?1 and f.isConfirmed")
    List<FriendsEntity> findFriendedUsers(UserEntity user);

    @Query("select f from FriendsEntity f where f.friendedUser = ?1 and f.isConfirmed = false")
    List<FriendsEntity> getAllReceivedFriendRequests(UserEntity user);

    @Query("select f from FriendsEntity f where f.user = ?1 and f.isConfirmed = false")
    List<FriendsEntity> getAllSentFriendRequests(UserEntity user);

    @Query("select f from FriendsEntity f where f.user = ?1 and f.friendedUser = ?2 or f.user = ?2 and f.friendedUser = ?1")
    Optional<FriendsEntity> getIfUsersAreFriended(UserEntity user1, UserEntity user2);

    @Query("select f from FriendsEntity f where f.user = ?1 and f.friendedUser = ?2")
    Optional<FriendsEntity> getSpecificFriendRequest(UserEntity user1, UserEntity user2);
}
