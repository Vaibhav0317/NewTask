package com.newTask.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserLogingDetails {

    public String details() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String login = authentication.getName();
        System.out.println("login user>=="+login);
        return login;
    }
}
