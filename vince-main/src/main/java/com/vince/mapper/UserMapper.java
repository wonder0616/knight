package com.vince.mapper;

import com.vince.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    List<User> getAllUsers();
    int addUser( User user );
    int deleteUser( User user );
}