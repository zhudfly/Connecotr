package com.zhudfly.utilsconnector.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * File operation helper
 * Created by zhudf on 2018/5/4.
 */

public class FileUtil {

    /**
     * check if sdcard exist
     */
    public static boolean checkSDcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * check path, create if not exist
     */
    public static void checkExist(String path) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.e("checkExist", "SD card is not avaiable/writeable right now.");
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
            file.getParentFile().mkdirs();
        }
    }

    /**
     * copy single file
     *
     * @param oldPath
     * @param newPath
     * @return String new file path
     */
    public static String copyFile(String oldPath, String newPath) {
        try {
            File srcFile = new File(oldPath);
            if (!srcFile.exists() || !srcFile.isFile()) return "";

            File destDir = new File(newPath);
            if (!destDir.exists()) destDir.mkdirs();

            File dest = new File(newPath + srcFile.getName());
            int byteSum = 0;
            int byteRead;
            File oldFile = new File(oldPath);
            if (oldFile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(dest);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead;
                    System.out.println(byteSum);
                    fs.write(buffer, 0, byteRead);
                }
                inStream.close();
            }

            return newPath + srcFile.getName();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }

        return "";
    }

    /**
     * move file
     *
     * @param srcFilePath
     * @param destDirPath
     * @return if success return path, else return ""
     */
    public static String moveFile(String srcFilePath, String destDirPath) {
        File srcFile = new File(srcFilePath);
        if (!srcFile.exists() || !srcFile.isFile()) return "";

        File destDir = new File(destDirPath);
        if (!destDir.exists()) destDir.mkdirs();

        File dest = new File(destDirPath + srcFile.getName());
        if (srcFile.renameTo(dest)) {
            return destDirPath + srcFile.getName();
        }
        return "";
    }

    /**
     * insert photo into system photos and fresh it
     *
     * @param imagePath
     * @param fileName
     */
    public static boolean insertPicToSysMedia(
        @NonNull Context context, String imagePath, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, fileName, null);
        } catch (FileNotFoundException e) {
            LogUtil.printStackTrace(e);
            return false;
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
        return true;
    }

    /**
     * delete file
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * delete file recursively
     *
     * @param file root path
     */
    public static void recursionDeleteFile(@NonNull File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * get file from assets
     */
    public static String getFromAssets(@NonNull Context mContext, String fileName) {
        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(mContext.getResources()
                .getAssets()
                .open(fileName), "utf-8");
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuffer Result = new StringBuffer();
            while ((line = bufReader.readLine()) != null) Result.append(line);
            return Result.toString();
        } catch (UnsupportedEncodingException e) {
            LogUtil.printStackTrace(e);
        } catch (IOException e) {
            LogUtil.printStackTrace(e);
        } finally {
            try {
                if (inputReader != null) {
                    inputReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get file`s MIME TYPE
     */
    public static String getMimeType(@NonNull File file) {
        String extension = getExtension(file);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * get file`s suffix
     */
    private static String getExtension(@NonNull File file) {
        String suffix = "";
        String name = file.getName();
        final int idx = name.lastIndexOf(".");
        if (idx > 0) {
            suffix = name.substring(idx + 1);
        }
        return suffix;
    }

    /**
     * get size of file
     *
     * @param file
     * @return long
     */
    public static long getFolderSize(@NonNull File file) {

        long size = 0;
        try {
            java.io.File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
        }
        return size;
    }

    /**
     * format file size
     * @param size (byte)
     * @return $TB$GB$MB
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;

        double megaByte = kiloByte / 1024;

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = BigDecimal.valueOf(megaByte);
            return result2.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = BigDecimal.valueOf(gigaByte);
            return result3.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = BigDecimal.valueOf(teraBytes);
        return result4.setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
