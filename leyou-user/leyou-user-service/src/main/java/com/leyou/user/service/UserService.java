package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.utils.CodecUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/17 8:49 下午
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:";

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

    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)) {
            return;
        }
//        生成验证码
        String code = NumberUtils.generateCode(6);
        System.out.println("phone: " + phone + " code: " + code);
//        发送消息到RabbitMQ 用于给用户发送
        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);
        this.amqpTemplate.convertAndSend("leyou.sms.exchange", "verifycode.sms", msg);
//        把验证码保存到Redis中 用于验证用户填写的验证码是否正确
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
    }

    public void register(User user, String code) {
//        查询Redis中的验证码
        String redisCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
//        校验验证码
        if (!StringUtils.equals(code, redisCode)) {
            return;
        }
//        生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
//        加盐加密 对密码进行加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
//        新增用户 最终要写入数据库
        user.setId(null);
        user.setCreated(new Date());
        this.userMapper.insertSelective(user);
    }

    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
//        查询数据库
        User user = this.userMapper.selectOne(record);

//        判断user是否为空
        if (user == null) {
            return null;
        }

//        获取盐，对用户输入的密码加盐加密
        password = CodecUtils.md5Hex(password, user.getSalt());

//        和数据库中的密码进行比较
        if (StringUtils.equals(password, user.getPassword())) {
            return user;
        }
        return null;
    }


}
