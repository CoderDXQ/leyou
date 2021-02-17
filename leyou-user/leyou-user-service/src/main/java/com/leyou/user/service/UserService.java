package com.leyou.user.service;

import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/17 8:49 下午
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //    校验数据是否可用
    public Boolean checkUser(String data, Integer type) {
        User record = new User();

        if (type == 1) {
            record.setUsername(data);
        } else if (type == 2) {
            record.setPhone(data);
        } else {
            return null;
        }

//        个数查出来是0则说明数据可用，大于0说明已经被使用（不再可用）
        return this.userMapper.selectCount(record) == 0;
    }

}
