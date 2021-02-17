package com.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/17 10:07 下午
 */

//配置读取类
@ConfigurationProperties(prefix = "leyou.sms")
@Data
public class SmsProperties {

    String accessKeyId;
    String accessKeySecret;
    String signName;
    String verifyCodeTemplate;

}
