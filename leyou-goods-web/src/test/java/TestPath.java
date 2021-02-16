import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/16 7:53 下午
 */
public class TestPath {


    //    检查存放静态页面的文件夹是否存在并创建文件夹
    @Test
    public void testpath() {
        File file = new File("..");
        System.out.println(file.getAbsolutePath());

        String path = "";
        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = path.substring(0, path.lastIndexOf("/") + 1) + "staticpage1/";

        File ff = new File(path);
        if (ff.exists()) {
            System.out.println("文件夹存在");
        } else {
            ff.mkdir();
            if (ff.exists()) {
                if (ff.isDirectory()) {
                    System.out.println("文件夹已创建");
                }
                if (ff.isFile()) {
                    System.out.println("文件已创建");
                }
            }
        }
        System.out.println(path);

    }


}
