package com.zhu.springbootmybatis.service.impl;

import com.zhu.springbootmybatis.mapper.UserMapper;
import com.zhu.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhu.springbootmybatis.entity.User;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}
