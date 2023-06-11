package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.entity.FriendsEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.repository.FriendRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import com.casperr04.pyspringchatbackend.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FriendServiceImpl implements FriendService {
    private FriendRepository friendRepository;
    private UserRepository userRepository;

    @Override
    public void sendFriendRequest(String username) {
        UserEntity userToFriend = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new MissingEntityException("No user found"));

        UserEntity friendingUser = userRepository
                .findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();

        if(Objects.equals(userToFriend.getUsername(), friendingUser.getUsername())){
            return;
        }

        if(friendRepository.getIfUsersAreFriended(userToFriend, friendingUser).isPresent()){
            return;
        }


        FriendsEntity friendsEntity = FriendsEntity.builder()
                .user(friendingUser)
                .friendedUser(userToFriend)
                .isConfirmed(false)
                .build();

        friendRepository.save(friendsEntity);
    }
    @Override
    public void acceptFriendRequest(String username) {
        UserEntity friendingUser = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new MissingEntityException("No user found"));

        UserEntity userReceivingFriendRequest = userRepository
                .findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();

        FriendsEntity friendsEntity = friendRepository.getSpecificFriendRequest(friendingUser, userReceivingFriendRequest)
                .orElseThrow(() -> new MissingEntityException("No friend request found"));

        friendsEntity.setConfirmed(true);
        friendsEntity.setFriendDate(Instant.now());
        friendRepository.save(friendsEntity);
    }

    @Override
    public void removeFriend(String username) {
        UserEntity friendToRemove = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new MissingEntityException("No user found"));

        UserEntity userRemovingFriend = userRepository
                .findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();

        FriendsEntity friendsEntity = friendRepository.getIfUsersAreFriended(friendToRemove, userRemovingFriend)
                .orElseThrow(() -> new MissingEntityException("No friend found"));

        friendRepository.delete(friendsEntity);
    }
}