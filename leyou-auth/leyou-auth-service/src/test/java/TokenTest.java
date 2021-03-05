import com.leyou.LeyouAuthApplication;
import com.leyou.auth.service.AuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/3/1 3:35 下午
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeyouAuthApplication.class)
public class TokenTest {

    @Autowired
    private AuthService authService;

    @Test
    public void getTokenCSV() throws IOException {


        BufferedWriter bw = null;
        try {
//            ？？？这个地址需要视情况而变化
            File csv = new File("/Users/duanxiangqing/Desktop/leyoucode/leyou/others/Token.csv");
            bw = new BufferedWriter(new FileWriter(csv, true));
            for (int i = 0; i < 500; i++) {
                //            两个参数分别是用户名和密码
                String token = this.authService.accredit("username" + i, "afdgdfg");
                System.out.println("username" + i + "经过鉴权");
                bw.write("username" + i + "," + token);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bw.close();
        }


    }
}
