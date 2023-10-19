package com.korit.board.repository;

import com.korit.board.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper // mybatis 어노테이션
public interface UserMapper { // mapper는 interface로 받아야 한다.

    public int saveUser(User user);
    public int checkDuplicate(User user);
    public User findUserByEmail(String email);
    public int updateEnabledToEmail(String email);
}
