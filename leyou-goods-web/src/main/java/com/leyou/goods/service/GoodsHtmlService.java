package com.leyou.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
//


/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/16 6:38 下午
 */

@Service
public class GoodsHtmlService {

    @Autowired
    private TemplateEngine engine;

    @Autowired
    private GoodsService goodsService;

    public void createHtml(Long spuId) {

//        初始化运行上下文
        Context context = new Context();
//        设置数据模型
        context.setVariables(this.goodsService.loadData(spuId));


//        找到staticpage所在的位置 默认地址是项目地址下的staticpage文件夹 这里加个打印需要看一下
        File checkpath = new File(".");
        String path = "";
        try {
            path = checkpath.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        path = path.substring(0, path.lastIndexOf("/") + 1) + "staticpage/";
        System.out.println(path);


        PrintWriter printWriter = null;
        try {
//            把静态文件生成到服务器本地
            File file = new File(path + spuId + ".html");
//            System.out.println(file.getAbsolutePath());
            printWriter = new PrintWriter(file);

//            使用模板引擎把静态页面写入硬盘 已实现静态访问
            this.engine.process("item", context, printWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

    }

    public void deleteHtml(Long id) {

        //        找到staticpage所在的位置 默认地址是项目地址下的staticpage文件夹 这里加个打印需要看一下
        File checkpath = new File(".");
        String path = "";
        try {
            path = checkpath.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(path);
        path = path.substring(0, path.lastIndexOf("/") + 1) + "staticpage/";
        System.out.println(path);

        File file = new File(path + id + ".html");

        file.deleteOnExit();
    }
}
