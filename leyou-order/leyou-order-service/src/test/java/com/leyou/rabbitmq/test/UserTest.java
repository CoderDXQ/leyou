package com.leyou.rabbitmq.test;

import com.leyou.order.LyOrderApplication;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/1 3:23 下午
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyOrderApplication.class)
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    //    注册5000个用户
    @Test
    public void addUser() {
        User user = new User();
        for (int i = 0; i < 5000; i++) {
            user.setId(null);
            user.setCreated(new Date());
            user.setPhone("1883482" + String.format("%04d", i));
            user.setUsername("username" + i);
            user.setPassword("abcdefg" + i);
            String encodePassword = CodecUtils.passwordBcryptEncode(user.getUsername().trim(), user.getPassword().trim());
            user.setPassword(encodePassword);
            this.userMapper.insertSelective(user);
        }
    }

    /**
     * 添加后台管理人员
     */
    @Test
    public void addAdmin() {
        User user = new User();
        user.setCreated(new Date());
        user.setPhone("88888888");
        user.setUsername("admin");
        user.setPassword("admin");
        String encodePassword = CodecUtils.passwordBcryptEncode(user.getUsername().trim(), user.getPassword().trim());
        user.setPassword(encodePassword);
        this.userMapper.insertSelective(user);
    }


}
