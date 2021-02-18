package com.leyou.auth.test;

import java.io.File;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 3:46 下午
 */
public class GetRSAPath {

//    创建文件夹并返回路径
    public String getrsapath() {
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

        return rsapath;
    }
}
