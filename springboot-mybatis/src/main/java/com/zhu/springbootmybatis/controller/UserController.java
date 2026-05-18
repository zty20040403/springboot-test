package com.zhu.springbootmybatis.controller;

import com.zhu.springbootmybatis.entity.User;
import com.zhu.springbootmybatis.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/findById")
    public User findById(Long id) {
        return userService.findById(id);
    }
}
