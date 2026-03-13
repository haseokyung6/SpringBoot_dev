package com.basic.dev.security.controller;

import com.basic.dev.security.model.UserInfo;
import com.basic.dev.security.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userinfos")
public class UserInfoController {

    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoController(UserInfoRepository repository,
                              PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/new")
    public UserInfo addUser(@RequestBody UserInfo userInfo) {

        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

        return repository.save(userInfo);
    }
}