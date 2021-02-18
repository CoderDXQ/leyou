package com.leyou.auth.test;

import org.junit.Test;

import java.io.File;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 3:35 下午
 */
public class PathTest {

    @Test
    public void testpath() {

        File file = new File("..");
        String prepath = file.getAbsolutePath();
        prepath = prepath.substring(0, prepath.indexOf("leyou-auth") - 1);
        prepath = prepath.substring(0, prepath.lastIndexOf("/") + 1);

        String rsapath = prepath + "rsa/";
        File rsafile = new File(rsapath);


        if (rsafile.exists()) {
            System.out.println("rsa文件夹已经存在");
        } else {
            rsafile.mkdir();
            if (rsafile.exists()) {
                System.out.println("rsa文件夹已经被创建");
            }
        }

        System.out.println(rsapath);
    }
}
