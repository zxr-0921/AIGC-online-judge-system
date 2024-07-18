package com.zxr.aiojbakcend.mapper;

import com.zxr.aiojbakcend.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserMapperTest {

//    测试数据库是否链接成功
    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelect() {
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }
}