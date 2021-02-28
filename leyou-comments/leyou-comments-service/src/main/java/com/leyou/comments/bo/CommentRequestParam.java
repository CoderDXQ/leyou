package com.leyou.comments.bo;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/27 10:26 下午
 */
public class CommentRequestParam {
    private Long spuId;
    private Integer page;
    private static final Integer DEFAULT_SIZE = 20;
    private static final Integer DEFAULT_PAGE = 1;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Integer getPage() {
        if (page == null) {
            return DEFAULT_PAGE;
        }
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDefaultSize() {
        return DEFAULT_SIZE;
    }


}
