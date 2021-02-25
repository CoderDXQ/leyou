package com.leyou.comments.pojo;

import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/26 2:37 上午
 */

@Data
public class Spit {
    private String _id;

    private String content;

    private String userid;

    private String nickname;

    private Integer visits;
}
