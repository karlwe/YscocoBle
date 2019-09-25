package com.yscoco.blue.utils;

import android.util.Log;

import com.yscoco.blue.BleManage;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者：karl.wei
 * 创建日期： 2018/6/5 0006 16:54
 * 邮箱：karl.wei@yscoco.com
 * QQ:2736764338
 * 类介绍：处理notify返回的数据结果
 */

public class FileWriteUtils {
    public static String  filePaths = "/sdcard/yscoco/";
    /**
     * 删除七天前的文件
     */
    public static void deleteFile(){
        File fileLocal = new File(filePaths);
        if(fileLocal.exists()){
            File[] files = fileLocal.listFiles();
            if(files!=null){
                for(File file:files) {
                    String fileName = file.getName();
                    if((!fileName.contains((getDate(0)+".txt")))
                            &&(!fileName.contains((getDate(-1)+".txt")))
                            &&(!fileName.contains((getDate(-2)+".txt")))
                            ){
                        file.delete();
                    }
                }
            }
        }
    }
    public synchronized static  void initWrite(String value,String fileNameStart) {
        String filePath = filePaths;
        String fileName = fileNameStart+getDate(0)+".txt";

        writeTxtToFile(value, filePath, fileName);
    }
    public synchronized static  void initWrite(String value) {
        String filePath = filePaths;
        String fileName = BleManage.getInstance().getBleConfig().getPROJECT_NAME()+getDate(0)+".txt";
        writeTxtToFile(value, filePath, fileName);
    }
    // 将字符串写入到文本文件中
    public synchronized static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        /*判断是否开启了本地文件写入*/
        if(BleManage.getInstance().getBleConfig().isCloseFile()) {
            return;
        }
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = getDate()+":"+strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
//            LogBlueUtils.d("文件名称" + strcontent);
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
//            LogBlueUtils.d("文件名称写入错误");
        }
    }

    // 生成文件
    public synchronized static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public synchronized static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
    /**
     * 删除全部文件
     */
    public synchronized static void del(){
        String filePath = filePaths;
        File file = null;
        file = new File(filePath);
        DeleteFile(file);
    }
    /**
     * 删除特定路径下的文件
     *
    */
    public synchronized static void del(String path){
        String filePath = path;
        File file = null;
        file = new File(filePath);
        DeleteFile(file);
    }
    /**
     * 递归删除文件和文件夹
     *
     * @param file
     * 要删除的根目录
     */
    public synchronized static void DeleteFile(File file) {
        if (file.exists() == false) {
            return;
        } else {
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
                    DeleteFile(f);
                }
                file.delete();
            }
        }
    }

    public static String getDate(int day){
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.add(Calendar.DAY_OF_MONTH,day);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        LogBlueUtils.d("前"+day+"天==" + dft.format(endDate));
        return dft.format(endDate);
    }
    /*获取当前日期yyyy-MM-dd*/
    public static String getDate() {
        Date now = new Date();
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String date = dateFormat.format(now);
//        LogUtils.e("date" + date);
        return date;
    }

}
