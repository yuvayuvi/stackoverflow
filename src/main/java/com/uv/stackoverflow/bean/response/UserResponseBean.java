package com.uv.stackoverflow.bean.response;

import lombok.Data;

import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 3:55 PM.
 */
@Data
public class UserResponseBean {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
