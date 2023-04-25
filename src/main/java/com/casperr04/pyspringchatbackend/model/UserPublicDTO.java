package com.casperr04.pyspringchatbackend.model;

import lombok.Data;

@Data
public class UserPublicDTO {
    private String publicId;
    private String name;
    private long dateOfCreation;
}
