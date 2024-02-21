package com.vince.service;

import com.vince.entity.User;

import java.util.List;

public interface IUserService {

    List<User> getAllUsers();
    int addUser( User user );
    int deleteUser( User user );
}