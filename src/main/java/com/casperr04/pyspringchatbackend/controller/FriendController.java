package com.casperr04.pyspringchatbackend.controller;

import com.casperr04.pyspringchatbackend.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/friend/")
public class FriendController {
    private FriendService friendService;
    @PostMapping("/request/send/{username}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable String username) {
        friendService.sendFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request/accept/{username}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable String username) {
        friendService.acceptFriendRequest(username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/{username}")
    public ResponseEntity<String> removeFriend(@PathVariable String username) {
        friendService.removeFriend(username);
        return ResponseEntity.ok().build();
    }
}
