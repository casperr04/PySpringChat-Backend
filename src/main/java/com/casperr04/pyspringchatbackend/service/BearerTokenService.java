package com.casperr04.pyspringchatbackend.service;

import com.casperr04.pyspringchatbackend.model.entity.AuthToken;
import com.casperr04.pyspringchatbackend.model.entity.UserEntity;

public interface BearerTokenService {
    AuthToken generateToken(UserEntity user);
}
