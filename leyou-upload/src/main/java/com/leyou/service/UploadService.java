package com.leyou.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author Duan Xiangqing
 * @version 1.0
 * @date 2021/2/6 12:21 下午
 */

@Service
public class UploadService {

    //    支持的文件类型
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif", "image/png", "image/jpeg");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired //注入
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();

//        校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return null;
        }

        try {
//        校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }


//        保存到服务器
            /*
            File getpath = new File("..");

            String savepath = getpath.getCanonicalPath() + "/image/" + originalFilename;
            System.out.println(savepath);
            file.transferTo(new File(savepath));
             */

//            分离出上传文件的格式
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
//            返回文件上传到FastDFS后的存储路径
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);


//        需要配置nginx和hosts  http://image.leyou.com/upload/实际上就是本地的一个文件夹
            //        返回url 进行回显
//            String url = "http://image.leyou.com/" + file.getOriginalFilename();

            String url = "http://image.leyou.com/" + storePath.getFullPath();
            System.out.println(url);

            return url;
        } catch (Exception e) {
            LOGGER.info("服务器内部错误：{}" + originalFilename);
            e.printStackTrace();
            return null;
        }

    }


}
