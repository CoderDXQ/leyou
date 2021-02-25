package com.leyou.comments.pojo;

import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/26 2:37 上午
 */

@NoArgsConstructor
public class Review implements Serializable {
    @Id
    private String _id;

    /**
     * 订单id
     */
    private String orderid;
    /**
     * 商品id
     */
    private String spuid;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论时间
     */
    private Date publishtime;
    /**
     * 评论用户id
     */
    private String userid;
    /**
     * 评论用户昵称
     */
    private String nickname;
    /**
     * 评论的浏览量
     */
    private Integer visits;
    /**
     * 评论的点赞数
     */
    private Integer thumbup;
    /**
     * 评论中的图片
     */
    private List<String> images;
    /**
     * 评论的回复数
     */
    private Integer comment;
    /**
     * 该评论是否可以被回复
     */
    private Boolean iscomment;
    /**
     * 该评论的上一级id
     */
    private String parentid;
    /**
     * 是否是顶级评论
     */
    private Boolean isparent;
    /**
     * 评论的类型
     */
    private Integer type;

    public Review(String orderid, String spuid, String content, String userid, String nickname, List<String> images, Boolean iscomment, String parentid, Boolean isparent, Integer type) {
        this.orderid = orderid;
        this.spuid = spuid;
        this.content = content;
        this.userid = userid;
        this.nickname = nickname;
        this.images = images;
        this.iscomment = iscomment;
        this.parentid = parentid;
        this.isparent = isparent;
        this.type = type;
    }


}
