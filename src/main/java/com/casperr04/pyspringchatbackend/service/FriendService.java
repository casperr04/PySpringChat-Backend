package com.casperr04.pyspringchatbackend.service;

public interface FriendService {
    void sendFriendRequest(String username);
    void acceptFriendRequest(String username);
    void removeFriend(String username);
}
