package com.basic.dev.security.service;

import com.basic.dev.security.model.UserInfo;
import com.basic.dev.security.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoRepository repository;

    public UserInfoUserDetailsService(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserInfo user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().split(","))
                .build();
    }
}