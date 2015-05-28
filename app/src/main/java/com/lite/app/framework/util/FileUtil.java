package com.lite.app.framework.util;

import com.lite.app.framework.bean.File;
import com.lite.app.framework.exception.DaoException;
import sun.misc.BASE64Decoder;
import java.io.FileOutputStream;
import java.util.UUID;

public class FileUtil {

    public static void base64ToFile(String base64, String path){
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64);
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
        }
        catch (Exception ex){
            throw new DaoException("file-io","文件写入失败");
        }
    }

    public static void bufferToFile(byte[] buffer, String path){
        try {
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
        }
        catch (Exception ex){
            throw new DaoException("file-io","文件写入失败");
        }
    }

    public static void binaryToFile(String binary, String path){
        try {
            byte[] buffer = binary.getBytes();
            FileOutputStream out = new FileOutputStream(path);
            out.write(buffer);
            out.close();
        }
        catch (Exception ex){
            throw new DaoException("file-io","文件写入失败");
        }
    }

    public static void write(File file,String folder){
        String name = file.getName();
        int index = name.lastIndexOf(".");
        String extension = name.substring(index + 1);
        String reName = UUID.randomUUID().toString();
        String fullName = folder + "/" + reName + "." + extension;
        if(file.getBase64Content() != null){
            FileUtil.base64ToFile(file.getBase64Content(),fullName);
        }
        else if(file.getBinaryContent() != null){
            FileUtil.binaryToFile(file.getBinaryContent(),fullName);
        }
        else if(file.getBufferContent() != null){
            FileUtil.bufferToFile(file.getBufferContent(),fullName);
        }
        else{
            throw new DaoException("file-io","文件写入失败");
        }

    }

}
