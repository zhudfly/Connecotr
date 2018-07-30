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
 * 文件操作相关的辅助类
 * Created by zhudf on 2018/5/4.
 */

public class FileUtil {

    /**
     * 检查SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 检测路径是否存在，不存在就创建
     */
    public static void checkExist(String path) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
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
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/test1.txt
     * @param newPath String 复制后路径 如：f:/test2.txt
     * @return String 返回拷贝后的文件路径
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
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(dest);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    byteSum += byteRead; //字节数 文件大小
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
     * 移动文件
     *
     * @param srcFilePath 源文件完整路径
     * @param destDirPath 目的目录完整路径
     * @return 文件移动成功返回最后的路径，否则返回空
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
     * 将图片添加到系统图库，并通知图库刷新
     *
     * @param imagePath 图片路径
     * @param fileName 文件名
     */
    public static boolean insertPicToSysMedia(
        @NonNull Context context, String imagePath, String fileName) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imagePath, fileName, null);
        } catch (FileNotFoundException e) {
            LogUtil.printStackTrace(e);
            return false;
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));
        return true;
    }

    /**
     * 删除文件
     *
     * @param path 要删除的文件的路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 递归删除文件/文件夹
     *
     * @param file 要删除的根目录
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
     * 从assets下读取文件
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
     * 获取文件MIME TYPE
     */
    public static String getMimeType(@NonNull File file) {
        String extension = getExtension(file);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    /**
     * 获取文件后缀
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
     * 获取文件夹大小
     *
     * @param file File实例
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
     * 格式化文件大小
     * @param size 文件的实际大小（byte）
     * @return 格式化以后的大小 $TB$GB$MB
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
