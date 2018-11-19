package com.example.jyl.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.jyl.dao.User;
import com.example.jyl.dao.UserMapper;
@Service 
public class UserService extends ServiceImpl<UserMapper, User>{
    public Page<User> selectUserPage(Page<User> page, String state) {
        page.setRecords(baseMapper.selectUserList(page,state));
        return page;
    }
}
