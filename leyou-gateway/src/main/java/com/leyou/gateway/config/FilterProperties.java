package com.leyou.gateway.config;

import com.leyou.common.utils.RsaUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 4:38 下午
 */

@Data
@ConfigurationProperties(prefix = "leyou.filter")//读取配置文件 前缀描述
public class FilterProperties {
    private List<String> allowPaths;
}

