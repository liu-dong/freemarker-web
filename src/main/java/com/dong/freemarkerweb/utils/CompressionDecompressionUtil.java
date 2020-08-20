package com.dong.freemarkerweb.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
            CompressionDecompressionUtil.unZipFile("F:\\MyUploadFile\\123.zip", "F:\\MyUploadFile\\14");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压文件
     *
     * @param zipFilePath    压缩文件的路径
     * @param filePath 解压后文件的路径
     * @throws IOException
     */
    public static void unzipFile(String zipFilePath, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File zipFile = new File(zipFilePath);//待压缩文件
        if (!zipFile.exists()) {
            System.out.println("没有该文件");
            return;
        }
        InputStream inputStream = new FileInputStream(zipFile);//定义文件的输入流
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
     * 解压缩
     *
     * @param srcFilePath 需要解压的文件路径
     * @param destFolder  解压之后存储文件的路径   这个文件路径后面需要加 /
     * @throws IOException
     */
    public static void unZipFile(String srcFilePath, String destFolder) throws IOException {
        File filePath = new File(destFolder);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
        long start = System.currentTimeMillis();
        System.out.println("unZipFile beigin : " + start);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(srcFilePath)));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            File newFile = new File(destFolder + fileName);
            if (zipEntry.isDirectory()) {

            }

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));

            byte[] buffer = new byte[1024];
            int count = -1;
            while ((count = zis.read(buffer)) > 0) {
                bos.write(buffer, 0, count);
            }

            bos.close();
            System.out.println("unZip file name = " + zipEntry.getName());
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

        System.out.println("unZipFile end : " + (System.currentTimeMillis() - start) / 1000 + " s");
    }
}
