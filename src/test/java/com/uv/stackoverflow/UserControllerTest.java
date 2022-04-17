package com.uv.stackoverflow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uv.stackoverflow.bean.request.LoginRequestBean;
import com.uv.stackoverflow.bean.request.SignUpRequestBean;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Yuvaraj S on 17/04/22 10:27 PM.
 */
@RunWith(JUnitPlatform.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("+ Should signup user")
    void signup() throws Exception {
        SignUpRequestBean signUpRequestBean = new SignUpRequestBean();
        signUpRequestBean.setFirstName("Yuvaraj");
        signUpRequestBean.setLastName("Subramani");
        signUpRequestBean.setEmail("yuvamca2793@gmail.com");
        signUpRequestBean.setPassword("Secret");

        ObjectMapper om = new ObjectMapper();
        String inputJson = om.writeValueAsString(signUpRequestBean);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/stackoverflow/signup")
                                .content(inputJson)
                                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Order(2)
    @DisplayName(
            "- Should throw exception when trying to signup user with same email that already exist.")
    void throwException_signup() throws Exception {
        SignUpRequestBean signUpRequestBean = new SignUpRequestBean();
        signUpRequestBean.setFirstName("Yuvaraj");
        signUpRequestBean.setLastName("Subramani");
        signUpRequestBean.setEmail("yuvamca2793@gmail.com");
        signUpRequestBean.setPassword("Secret");

        ObjectMapper om = new ObjectMapper();
        String inputJson = om.writeValueAsString(signUpRequestBean);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/stackoverflow/signup")
                                .content(inputJson)
                                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(
                        jsonPath(
                                "$.message",
                                Matchers.containsString("Email already exist, Please verify the Email Id")));
    }

    @Test
    @Order(3)
    @DisplayName(
            "- Should login and return user details")
    void login() throws Exception {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setEmail("yuvamca2793@gmail.com");
        loginRequestBean.setPassword("Secret");

        ObjectMapper om = new ObjectMapper();
        String inputJson = om.writeValueAsString(loginRequestBean);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/stackoverflow/login")
                                .content(inputJson)
                                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Order(4)
    @DisplayName(
            "- Should throw exception when trying to login with invalid credentials.")
    void Invalid_Credentials() throws Exception {
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setEmail("yuvamca2793@gmail.com");
        loginRequestBean.setPassword("Test");

        ObjectMapper om = new ObjectMapper();
        String inputJson = om.writeValueAsString(loginRequestBean);
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/stackoverflow/login")
                                .content(inputJson)
                                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(
                        jsonPath(
                                "$.message",
                                Matchers.containsString("Invalid Credentials")));
    }

}
