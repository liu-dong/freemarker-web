package com.dong.freemarkerweb.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author LD
 * @date 2020/8/4 17:34
 */
public class ZipFileWithTier {
    private static final String srcFiles = "E:\\MyUploadFile\\桥洞";
    private static final String zipPath = "E:\\MyUploadFile\\桥洞3.zip";
    private static final String unzipPath = "C:\\temp\\Lemur\\";

    public static void main(String[] args) {
            new ZipFileWithTier().zipFile();
    }

    public void zipFile() {
        File file = new File(zipPath);
        if (file.exists())
            file.delete();
        zipFileWithTier(srcFiles, zipPath);
    }

    public void upZipFile() {
        try {
            unzipFilesWithTier(readFileByte(zipPath), unzipPath + File.separator);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /*
     * Compress the specify file that contains sub-folders and store them to a zipfile.
     * @param srcFiles: the file will be compressed
     * @param zipPath: the location which stores the zipfile.
     */
    public static void zipFileWithTier(String srcFiles, String zipPath) {
        try {
            FileOutputStream zipFile = new FileOutputStream(zipPath);
            BufferedOutputStream buffer = new BufferedOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(buffer);
            zipFiles(srcFiles, out, "");
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Recursive the specify file also contains the folder which may don't include any file.
     * @param filePath: compress file
     * @param ZipOutputStream: the zipfile's outputStream.
     * @param prefix: the prefix indicates the parent folder name of the file that makes the tier relation.
     */
    public static void zipFiles(String filePath, ZipOutputStream out, String prefix)
            throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            if (file.listFiles().length == 0) {
                ZipEntry zipEntry = new ZipEntry(prefix + file.getName() + "/");
                out.putNextEntry(zipEntry);
                out.closeEntry();
            } else {
                prefix += file.getName() + File.separator;
                for (File f : file.listFiles())
                    zipFiles(f.getAbsolutePath(), out, prefix);
            }
        } else {
            FileInputStream in = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(prefix + file.getName());
            out.putNextEntry(zipEntry);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }

    }

    /*
     * Unzip the file also contains the folder which the listFiles's length is 0.
     * @param bytes: the content of the zipfile by byte array.     *
     * @param prefix: the prefix is the root of the store path.
     * @IOExcetion: the ioexception during unzipFiles.
     */
    public static void unzipFilesWithTier(byte[] bytes, String prefix) throws IOException {

        InputStream bais = new ByteArrayInputStream(bytes);
        ZipInputStream zin = new ZipInputStream(bais);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                File file = new File(prefix + ze.getName());
                if (!file.exists())
                    file.mkdirs();
                continue;
            }
            File file = new File(prefix + ze.getName());
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            ByteArrayOutputStream toScan = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = zin.read(buf)) > 0) {
                toScan.write(buf, 0, len);
            }
            byte[] fileOut = toScan.toByteArray();
            toScan.close();
            writeByteFile(fileOut, new File(prefix + ze.getName()));
        }
        zin.close();
        bais.close();
    }

    public static byte[] readFileByte(String filename) throws IOException {

        if (filename == null || filename.equals("")) {
            throw new NullPointerException("File is not exist!");
        }
        File file = new File(filename);
        long len = file.length();
        byte[] bytes = new byte[(int) len];

        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(file));
        int r = bufferedInputStream.read(bytes);
        if (r != len)
            throw new IOException("Read file failure!");
        bufferedInputStream.close();

        return bytes;

    }

    public static String writeByteFile(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    public static void zipFolder1(String folderPath, String zipFolderPath) throws IOException {
        FileOutputStream zipFile = new FileOutputStream(zipFolderPath);
        BufferedOutputStream buffer = new BufferedOutputStream(zipFile);
        ZipOutputStream out = new ZipOutputStream(buffer);
        File file = new File(folderPath);
        if (file.isDirectory()) {
            if (file.listFiles().length == 0) {
                System.out.println("" + file.getName() + "/");
                ZipEntry zipEntry = new ZipEntry("" + file.getName() + "/");
                out.putNextEntry(zipEntry);
                out.closeEntry();
            }
        }
        out.close();
        System.out.println("文件夹压缩成功");
    }
}
