package com.uv.stackoverflow.controller;


import com.uv.stackoverflow.bean.request.LoginRequestBean;
import com.uv.stackoverflow.bean.request.SignUpRequestBean;
import com.uv.stackoverflow.service.user.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


/**
 * Created by Yuvaraj S on 14/04/22 3:58 PM.
 */
@Slf4j
@RestController
@RequestMapping(value = "/stackoverflow", produces = "application/hal+json")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequestBean signUpRequestBean) {
        return  userServiceImpl.saveUser(signUpRequestBean);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID id, @RequestBody SignUpRequestBean signUpRequestBean) {
        return  userServiceImpl.updateUser(id, signUpRequestBean);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUser(@PathVariable UUID id) {
        return  userServiceImpl.getUserDetails(id);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestBean loginRequestBean) {
        return  userServiceImpl.login(loginRequestBean);
    }

}
