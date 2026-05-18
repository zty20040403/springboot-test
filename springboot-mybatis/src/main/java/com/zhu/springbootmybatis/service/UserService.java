package com.zhu.springbootmybatis.service;

import com.zhu.springbootmybatis.entity.User;
import com.zhu.springbootmybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

   public User findById(Long id);
}
