package com.dong.freemarkerweb.utils;

import java.io.*;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author LD
 */
public class ZipUtil {

    public static void main(String[] args) {
    }

    /**
     * 压缩单个文件
     *
     * @param srcFilePath  源文件文件夹
     * @param destFolder   源文件文件名
     * @param destFileName 压缩之后文件全路径
     * @throws IOException
     */
    public static void zipSingleFile(String srcFilePath, String destFolder, String destFileName) throws IOException {
        File destFile = new File(destFolder);
        if (!destFile.exists()) {
            destFile.mkdir();//创建文件夹
        }
        if (destFile.exists()) {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFileName)));
            compressedFile(srcFilePath, zos);
            zos.close();
        }
    }

    /**
     * 压缩多个文件
     *
     * @param srcFiles
     * @param destFolder
     * @param destFileName
     * @throws IOException
     */
    public static void zipMultipleFiles(String[] srcFiles, String destFolder, String destFileName) throws IOException {
        File destFile = new File(destFolder);
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        if (destFile.exists()) {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFolder + destFileName)));
            for (String srcFile : srcFiles) {
                compressedFile(srcFile, zos);
            }
            zos.close();
        }
    }

    /**
     * 压缩某个目录
     *
     * @param srcFolder    输入的目录字符串最后不带  /
     * @param destFolder
     * @param destFileName
     * @throws IOException
     */
    public static void zipDir(String srcFolder, String destFolder, String destFileName) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println("ZipDir begin : ");
        File destFile = new File(destFolder);
        if (!destFile.exists()) {
            destFile.mkdir();
        }
        if (destFile.exists()) {
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(destFolder + destFileName)));
            ZipDirFunction(srcFolder, zos);
            zos.close();
        }
        System.out.println("ZipDir end : " + (System.currentTimeMillis() - start) / 1000 + " s");
    }

    /**
     * 不能出现重名的文件
     *
     * @param srcFilePath
     * @param zos
     * @throws IOException
     */
    public static void ZipDirFunction(String srcFilePath, ZipOutputStream zos) throws IOException {
        File srcFile = new File(srcFilePath);
        if (srcFile.isDirectory()) {
            String[] children = srcFile.list();
            for (int i = 0; i < children.length; i++) {
                ZipDirFunction(srcFilePath + File.separator + children[i], zos);
            }
            return;
        }
        System.out.println("ZipDirFunction srcFilePath = " + srcFilePath);
        compressedFile(srcFilePath, zos);
    }

    /**
     * 通用压缩类
     *
     * @param filePath 待压缩文件的路径
     * @param zos
     * @throws IOException
     */
    public static void compressedFile(String filePath, ZipOutputStream zos) throws IOException {
        zos.setLevel(Deflater.BEST_SPEED);//压缩级别-0-9，默认-6
        File entryFile = new File(filePath);
        ZipEntry zipEntry = new ZipEntry(entryFile.getName());
        zos.putNextEntry(zipEntry);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(entryFile));

        byte[] buffer = new byte[1024];
        int count = -1;
        bis.read();

        while ((count = bis.read(buffer)) != -1) {
            zos.write(buffer, 0, count);
        }

        bis.close();
        zos.closeEntry();
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
