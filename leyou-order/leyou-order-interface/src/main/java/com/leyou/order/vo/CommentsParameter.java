package com.leyou.order.vo;

import com.leyou.comments.pojo.Review;
import lombok.Data;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/1 11:20 下午
 */
@Data
public class CommentsParameter {

    private Long orderId;

    private Review review;

}
