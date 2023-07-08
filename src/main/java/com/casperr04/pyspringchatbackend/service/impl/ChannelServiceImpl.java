package com.casperr04.pyspringchatbackend.service.impl;

import com.casperr04.pyspringchatbackend.exception.MissingEntityException;
import com.casperr04.pyspringchatbackend.model.dto.ChannelCreationResponse;
import com.casperr04.pyspringchatbackend.model.dto.MessageInfoDto;
import com.casperr04.pyspringchatbackend.model.entity.FriendsEntity;
import com.casperr04.pyspringchatbackend.model.entity.PrivateMessageChannelEntity;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;
import com.casperr04.pyspringchatbackend.repository.FriendRepository;
import com.casperr04.pyspringchatbackend.repository.PrivateChannelRepository;
import com.casperr04.pyspringchatbackend.repository.PrivateMessageEntityRepository;
import com.casperr04.pyspringchatbackend.repository.UserRepository;
import com.casperr04.pyspringchatbackend.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {

    private UserRepository userRepository;
    private FriendRepository friendRepository;
    private PrivateChannelRepository privateChannelRepository;
    private PrivateMessageEntityRepository privateMessageEntityRepository;

    /**
     * Creates a private channel given an username if users are friended.
     * @param username Friended user to create a channel with
     * @return ChannelCreationResponse or null if channel already exists
     * @throws MissingEntityException if user isn't found or users aren't friended.
     */
    @Override
    public ChannelCreationResponse createPrivateChannel(String username) throws MissingEntityException {
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new MissingEntityException("No user found"));

        UserEntity currentUser = userRepository
                .findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .get();

        if(currentUser.getUsername().equals(username)){
            return null;
        }

        FriendsEntity friendsEntity = friendRepository.getIfUsersAreFriended(user, currentUser)
                .orElseThrow(() -> new MissingEntityException("Users are not friended"));

        if(privateChannelRepository.findPrivateChannelByUsers(user, currentUser).isPresent()){
            return null;
        }

        PrivateMessageChannelEntity privateMessageChannelEntity = PrivateMessageChannelEntity.builder()
                .recipient(user)
                .sender(currentUser)
                .build();

        var savedChannelEntity = privateChannelRepository.save(privateMessageChannelEntity);

        return ChannelCreationResponse.builder()
                .dateOfCreation(Instant.now())
                .channelId(String.valueOf(savedChannelEntity.getId()))
                .build();
    }

    @Override
    public MessageInfoDto retrieveMessageInfo(String channelid, String messageid) {
        UserEntity user = userRepository.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if(privateChannelRepository.findIfUserIsInChannel(user.getUsername(), Long.valueOf(channelid)).isEmpty()){
            throw new AccessDeniedException("User is not in the channel");
        }

        var privateMessage = privateMessageEntityRepository.findById(Long.valueOf(channelid))
                .orElseThrow(() -> new MissingEntityException("Message not found"));

        return MessageInfoDto.builder()
                .id(String.valueOf(privateMessage.getId()))
                .username(privateMessage.getUser().getUsername())
                .message(privateMessage.getMessage())
                .dateOfCreation(privateMessage.getDateOfCreation())
                .build();
    }


}
