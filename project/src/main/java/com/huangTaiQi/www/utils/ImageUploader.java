package com.huangTaiQi.www.utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;


/**
 * @author _qqiu
 */
public class ImageUploader {

    /**
     * 文件上传目录
     */

    private static final String UPLOAD_DIRECTORY = "img"+File.separator;

    /**
     * 处理文件上传请求
     */
    public static String processFileUpload(HttpServletRequest request) throws IOException, ServletException {
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            // 只处理文件类型的part
            if (part.getContentType() != null && part.getSize() > 0) {
                // 获取文件名及其保存的目录
                String fileName = part.getSubmittedFileName();
                String saveDir = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                File saveDirFile = new File(saveDir);
                // 如果保存目录不存在，则创建
                if (!saveDirFile.exists()) {
                    saveDirFile.mkdirs();
                }
                // 处理文件名冲突的情况
                File file = new File(saveDir, fileName);
                int count = 1;
                while (file.exists()) {
                    String newName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + count++
                            + fileName.substring(fileName.lastIndexOf("."));
                    file = new File(saveDir, newName);
                }
                try (InputStream in = part.getInputStream()) {
                    // 将文件保存到本地
                    Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    // 返回保存后的文件路径
                    return ".." + File.separator + UPLOAD_DIRECTORY + file.getName();
                }
            }
        }
        return null;
    }

//    public static String processFileUpload(HttpServletRequest request)  {
        // 检查请求是否包含文件
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            // 如果不包含文件，则返回错误信息
//            return "Error: Form must have enctype=multipart/form-data.";
//        }
//
//        // 创建文件上传工厂和文件上传对象
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // 设置最大文件大小限制 10MB
//        upload.setFileSizeMax(1024 * 1024 * 10);
//        upload.setHeaderEncoding("UTF-8");
//
//        // 配置上传目录
//        String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        // 处理文件上传
//        String fileName = "";
//        try {
//            List<FileItem> fileItems = upload.parseRequest(request);
//            for (FileItem item : fileItems) {
//                // 处理普通表单字段
//                if (item.isFormField()) {
//
//                } else {
//                    // 处理上传文件
//                    fileName = new File(item.getName()).getName();
//                    String filePath = uploadPath + File.separator + fileName;
//                    File storeFile = new File(filePath);
//                    // 保存文件到服务器
//                    item.write(storeFile);
//                }
//            }
//        } catch (Exception e) {
//            return "Error: " + e.getMessage();
//        }
//
//        return ".."+File.separator +UPLOAD_DIRECTORY+File.separator +fileName;
//    }

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
