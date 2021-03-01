package com.leyou.gateway.config;

import com.leyou.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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

