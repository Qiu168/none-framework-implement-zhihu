package com.huangTaiQi.www.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 14629
 */
public class ImageUploader {

    /**
     * 文件上传目录
     */

    private static final String UPLOAD_DIRECTORY = "src/main/webapp/img";

    /**
     * 处理文件上传请求
     */

    public static String processFileUpload(HttpServletRequest request)  {
        // 检查请求是否包含文件
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不包含文件，则返回错误信息
            return "Error: Form must have enctype=multipart/form-data.";
        }

        // 创建文件上传工厂和文件上传对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件大小限制 10MB
        upload.setFileSizeMax(1024 * 1024 * 10);

        // 配置上传目录
        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 处理文件上传
        String fileName = "";
        try {
            for (FileItem item : upload.parseRequest(request)) {
                // 处理普通表单字段
                if (item.isFormField()) {
                    // 表单字段
                } else {
                    // 处理上传文件
                    fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // 保存文件到服务器
                    item.write(storeFile);
                }
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }

        return fileName;
    }

    /**
     * 下载图片到服务器
     */

    public static void downloadImage(String imageUrl, String saveDirectory, String fileName) throws IOException {
        InputStream inputStream = null;
        try {
            // 从URL中获取图片的二进制流
            inputStream = new URL(imageUrl).openStream();
            // 将图片保存到服务器
            FileUtils.copyInputStreamToFile(inputStream, new File(saveDirectory + File.separator + fileName));
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

}
