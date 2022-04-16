package com.uv.stackoverflow.service.user;

import com.uv.stackoverflow.bean.request.LoginRequestBean;
import com.uv.stackoverflow.bean.request.SignUpRequestBean;
import com.uv.stackoverflow.bean.response.UserResponseBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Yuvaraj S on 15/04/22 1:09 PM.
 */
@Service
public class UserServiceImpl {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> saveUser(SignUpRequestBean signUpRequestBean){
        UserEntity userEntity = UserEntity.builder()
                .firstName(signUpRequestBean.getFirstName())
                .lastName(signUpRequestBean.getLastName())
                .email(signUpRequestBean.getEmail())
                .password(Base64.getEncoder().encodeToString(signUpRequestBean.getPassword().getBytes()))
                .build();
        try{
            userEntity = userRepository.save(userEntity);
            return getUserResponseBean(userEntity);
        }catch (DataIntegrityViolationException violationException){
            return  new ResponseEntity<Object>("Email already exist, Please verify the Email Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateUser(UUID id, SignUpRequestBean signUpRequestBean){
        Optional<UserEntity> optionalUserEntity =  userRepository.findById(id);

        if(optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            if(!Objects.equals(signUpRequestBean.getEmail(), userEntity.getEmail()))
                return  new ResponseEntity<Object>("Not allowed to update Email Id", HttpStatus.INTERNAL_SERVER_ERROR);

            userEntity.setFirstName(signUpRequestBean.getFirstName());
            userEntity.setLastName(signUpRequestBean.getLastName());
            userEntity.setEmail(signUpRequestBean.getEmail());
            userEntity.setPassword(Base64.getEncoder().encodeToString(signUpRequestBean.getPassword().getBytes()));

            userRepository.save(userEntity);
            return getUserResponseBean(userEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid User Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getUserDetails(UUID id){
        Optional<UserEntity> optionalUserEntity =  userRepository.findById(id);
        if(optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            return getUserResponseBean(userEntity);
        }else{
            return  new ResponseEntity<Object>("Invalid User Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> login(LoginRequestBean loginRequestBean){
        Optional<UserEntity> optionalUserEntity =  userRepository.findByEmail(loginRequestBean.getEmail());
        if(optionalUserEntity.isPresent()){

            UserEntity userEntity = optionalUserEntity.get();
            if(userEntity.getPassword().equals(Base64.getEncoder().encodeToString(loginRequestBean.getPassword().getBytes())))
                return getUserResponseBean(userEntity);
            else
                return  new ResponseEntity<Object>("Invalid Credentials", HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return  new ResponseEntity<Object>("Invalid Credentials", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private ResponseEntity<Object> getUserResponseBean(UserEntity userEntity) {
        UserResponseBean userResponseBean = new UserResponseBean();
        userResponseBean.setId(userEntity.getId());
        userResponseBean.setFirstName(userEntity.getFirstName());
        userResponseBean.setLastName(userEntity.getLastName());
        userResponseBean.setEmail(userEntity.getEmail());
        return new ResponseEntity<>(userResponseBean, HttpStatus.OK);
    }
}
