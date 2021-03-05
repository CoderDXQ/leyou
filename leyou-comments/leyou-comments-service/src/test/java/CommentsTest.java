import com.leyou.comments.LeyouCommentsApplication;
import com.leyou.comments.dao.CommentDao;
import com.leyou.comments.pojo.Review;
import com.leyou.comments.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/28 10:44 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouCommentsApplication.class)
public class CommentsTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentDao commentDao;

    /**
     * spuId为2的商品添加100条顶级评论数据
     * 评论数据都是存在mongoDB里
     */
    @Test
    public void LoadData() {
        for (int i = 0; i < 100; i++) {
            String spuId = "2";
            String content = "手机不错" + i;
            String userId = (35 + i) + "";
            String nickname = "username" + i;
            List<String> images = new ArrayList<>();
            boolean iscomment = i % 2 == 0;
            String parentId = 0 + "";
            boolean isparent = true;
            int type = i % 5;

            Review review = new Review(spuId, content, userId, nickname, images, iscomment, parentId, isparent, type);
//            ？？？创建一个订单弄进来
            String orderId = "???";
//            下面两句分别试试
            commentDao.insert(review);
//            会同时更新MongoDB和MySQL数据库
//            commentService.add(orderId, review);
        }


    }

    @Test
    public void LoadOneData() {
        String spuId = "2";
        String content = "苹果手机不错";
        String userId = 36 + "";
        String nickname = "username1";
        List<String> images = new ArrayList<>();
        boolean iscomment = true;

//        String parentId = "1071767095416725504";
        String parentId = "？？？";
        boolean isparent = false;
        int type = 4;
        Review review = new Review(spuId, content, userId, nickname, images, iscomment, parentId, isparent, type);

//        缺个orderId
//        commentService.add(review);

    }

    @Test
    public void delete() {
        commentDao.deleteAll();
    }


}
