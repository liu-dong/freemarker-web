package com.dong.freemarkerweb.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩/解压文件
 *
 * @author LD
 */
public class ZIPUtils {


    public static void main(String[] args) throws IOException {
//            ZIPUtils.zip("F:\\MyUploadFile\\瀑布.jpg", "F:\\MyUploadFile\\瀑布.zip");
//            ZIPUtils.zip("F:\\MyUploadFile\\瀑布", "F:\\MyUploadFile\\瀑布文件夹.zip");
//            ZIPUtils.zip("F:\\MyUploadFile\\1", "F:\\MyUploadFile\\11.zip");
        ZIPUtils.zip("F:\\MyUploadFile\\12", "F:\\MyUploadFile\\13.zip");
    }


    /**
     * 压缩文件
     *
     * @param filePath    待压缩文件的路径
     * @param zipFilePath 压缩后文件的路径
     * @throws IOException IO流异常
     */
    public static void zip(String filePath, String zipFilePath) throws IOException {
        File file = new File(filePath);//待压缩文件
        if (!file.exists()) {
            System.out.println("没有该文件");
            return;
        }
        File zipFile = new File(zipFilePath);//压缩文件
        if (zipFile.exists()) {//如果存在则删除
            zipFile.delete();
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));// 声明压缩流对象
        zipOutputStream.setComment("com.dong.zipFile");//设置注释
        isFileFolder(file, zipOutputStream, "");
        zipOutputStream.close();//关闭输出流
        System.out.println("压缩成功");
    }

    /**
     * 判断是文件还是文件夹
     *
     * @param file 文件
     * @param zipOutputStream 压缩输出流
     * @param path 待压缩路径
     * @throws IOException IO流异常
     */
    public static void isFileFolder(File file, ZipOutputStream zipOutputStream, String path) throws IOException {
        if (file.isDirectory()) {
            zipFolder(file, zipOutputStream, path);
        } else {
            zipFile(file, zipOutputStream, path);
        }
    }

    /**
     * 压缩文件
     * @param file 待压缩文件
     * @param zipOutputStream 压缩文件输出流
     * @param path  待压缩路径
     * @throws IOException IO流异常
     */
    public static void zipFile(File file, ZipOutputStream zipOutputStream, String path) throws IOException {
        InputStream inputStream = new FileInputStream(file);//定义文件的输入流
        zipOutputStream.putNextEntry(new ZipEntry(path + file.getName() + File.separator));//设置ZipEntry对象
        int temp;
        while ((temp = inputStream.read()) != -1) {//读取内容
            zipOutputStream.write(temp);//压缩输出
        }
        zipOutputStream.closeEntry();
        inputStream.close();//关闭输入流
    }

    /**
     * 压缩文件夹
     * @param folder 待压缩文件夹
     * @param zipOutputStream 压缩文件输出流
     * @param path 待压缩路径
     * @throws IOException IO流异常
     */
    public static void zipFolder(File folder, ZipOutputStream zipOutputStream, String path) throws IOException {
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            zipOutputStream.putNextEntry(new ZipEntry(path + folder.getName() + "/"));//设置ZipEntry对象
            zipOutputStream.closeEntry();
        } else {
            path += folder.getName() + File.separator;
            for (File file : files) {
                isFileFolder(file, zipOutputStream, path);
            }
        }
    }
}
