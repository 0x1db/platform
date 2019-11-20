package com.platform.common.utils;

import java.io.*;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author wangyu
 * @date 2019/11/19 22:54
 */
public class FileUploadUtils {

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileSuffix(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    /**
     * 获取随机名称
     *
     * @param realName
     * @return
     */
    public static String getUUIDName(String realName) {
        //获取后缀名
        int index = realName.lastIndexOf(".");
        if (index == -1) {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase();
        } else {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase() + realName.substring(index);
        }
    }

    /**
     * 上传头像图片
     *
     * @param inputStream 文件
     * @param filePath    文件保存路径
     * @param fileName    文件名
     * @return
     */
    public static String uploadImg(FileInputStream inputStream, String filePath, String fileName) {
        //图片保存地址
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        //获取随机名称
        String uuidName = getUUIDName(fileName);
        String path = filePath + uuidName;
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            byte[] bs = new byte[1024];
            int len;
            while ((len = inputStream.read(bs)) != -1) {
                bos.write(bs, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return uuidName;
    }
}
