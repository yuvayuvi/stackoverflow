package com.uv.stackoverflow.bean.request;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Yuvaraj S on 14/04/22 3:55 PM.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestBean implements Serializable {
    String firstName;
    String lastName;
    String email;
    String password;
}
