package com.leyou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 载荷对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Long id;

    private String username;

}
