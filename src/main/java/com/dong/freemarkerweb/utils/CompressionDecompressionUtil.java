package com.dong.freemarkerweb.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压
 *
 * @author LD
 */
public class CompressionDecompressionUtil {

    public static void main(String[] args) {
        try {
//            CompressionDecompressionUtil.zipFile("F:\\MyUploadFile\\瀑布.jpg", "F:\\MyUploadFile\\瀑布1.zip");
            CompressionDecompressionUtil.zipFolder("F:\\MyUploadFile\\瀑布1", "F:\\MyUploadFile\\瀑布2.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     *
     * @param filePath    待压缩文件的路径
     * @param zipFilePath 压缩后文件的路径
     * @throws IOException
     */
    public static void zipFile(String filePath, String zipFilePath) throws IOException {
        File file = new File(filePath);//待压缩文件
        if (!file.exists()) {
            System.out.println("没有该文件");
            return;
        }
        File zipFile = new File(zipFilePath);//压缩后文件
        InputStream inputStream = new FileInputStream(file);//定义文件的输入流
        ZipOutputStream zipOutputStream;// 声明压缩流对象
        zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
        zipOutputStream.putNextEntry(new ZipEntry(file.getName() + File.separator));//设置ZipEntry对象
        zipOutputStream.setComment("com.dong.zipFile");//设置注释
        int temp;
        while ((temp = inputStream.read()) != -1) {//读取内容
            zipOutputStream.write(temp);//压缩输出
        }
        inputStream.close();//关闭输入流
        zipOutputStream.close();//关闭输出流
        System.out.println("文件压缩成功");
    }

    /**
     * 压缩文件夹
     *
     * @param folderPath    待压缩文件夹的路径
     * @param zipFolderPath 压缩后文件夹的路径
     * @throws IOException
     */
    public static void zipFolder(String folderPath, String zipFolderPath) throws IOException {
        File folder = new File(folderPath);//待压缩文件夹
        if (!folder.exists()) {
            System.out.println("没有该文件夹");
            return;
        }
        File zipFolder = new File(zipFolderPath);//压缩后文件
        ZipOutputStream zipOutputStream;// 声明压缩流对象
        zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFolder));
        zipOutputStream.setComment("com.dong.zipFolder");//设置注释
        File[] files = folder.listFiles();
        if (files != null && files.length == 0) {
            zipOutputStream.putNextEntry(new ZipEntry(folder.getName() + File.separator));//设置ZipEntry对象
            zipOutputStream.closeEntry();
        }else {
            System.out.println("该文件夹没有文件");
            return;
        }
//        zipOutputStream.close();//关闭输出流
        System.out.println("文件夹压缩成功");
    }

    /**
     * 判断是文件还是文件夹
     *
     * @param fileFolder
     * @param zipOutputStream
     * @throws IOException
     */
    public static void isFileFolder(File fileFolder, ZipOutputStream zipOutputStream) throws IOException {
        InputStream inputStream;//定义文件的输入流
        if (fileFolder.isDirectory()) {//判断是否是文件夹
            File[] files = fileFolder.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    System.out.println(fileFolder.getName() + File.separator + file.getName());
                    if (file.isFile()) {
                        int temp;
                        zipOutputStream.putNextEntry(new ZipEntry(fileFolder.getName() + File.separator + file.getName()));//设置ZipEntry对象
                        inputStream = new FileInputStream(file);//定义文件输入流
                        while ((temp = inputStream.read()) != -1) {//读取内容
                            zipOutputStream.write(temp);//压缩输出
                        }
                        inputStream.close();//关闭输入流
                    } else {
                        zipOutputStream.putNextEntry(new ZipEntry(fileFolder.getName() + File.separator + file.getName()));//设置ZipEntry对象
                        isFileFolder(file, zipOutputStream);
                    }
                }
            }else {
                System.out.println("该文件夹没有文件");
                zipOutputStream.putNextEntry(new ZipEntry(fileFolder.getName() + File.separator));//设置ZipEntry对象
            }

        }
    }
}
