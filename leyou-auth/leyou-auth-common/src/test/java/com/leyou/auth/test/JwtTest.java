package com.leyou.auth.test;

import com.leyou.common.pojo.UserInfo;
import com.leyou.common.utils.JwtUtils;
import com.leyou.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/18 3:29 下午
 */

public class JwtTest {

    //公钥存放地址
    private String pubKeyPath = getrsapath() + "rsa.pub";
    //私钥存放地址
    private String priKeyPath = getrsapath() + "rsa.pri";
    //公钥
    private PublicKey publicKey;
    //私钥
    private PrivateKey privateKey;


    //    生成RSA使用的公钥和私钥并存储
    @Test
    public void testRsa() throws Exception {
        System.out.println(pubKeyPath);
        System.out.println(priKeyPath);
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }


    //    查看是否存在RSA的公钥和私钥
    @Before
    public void testGetRsa() throws Exception {
        System.out.println();
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);

//        显示公钥和私钥内容
//        System.out.println(this.publicKey);
//        System.out.println(this.privateKey);
//        System.out.println();
    }

    //    生成token
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token 授权中心使用私钥生成token  私钥token使用私钥和公钥都可以解密 公钥token只有授权中心使用私钥才能解密 公钥所有参与方都有 私钥只有授权中心有
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    //    解密token
    @Test
    public void testParseToken() throws Exception {

//        生成token以用来解密
        String token = JwtUtils.generateToken(new UserInfo(20L, "jackma"), privateKey, 5);
        System.out.println("token = " + token);
        System.out.println();

        // 解析token 使用公钥解密token  授权中心使用私钥生成token  私钥token使用私钥和公钥都可以解密 公钥token只有授权中心使用私钥才能解密 公钥所有参与方都有 私钥只有授权中心有
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }

    //    创建存放RSA的文件夹并返回路径
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
