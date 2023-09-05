package com.zet.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description
 * @Author 
 * @Date  2023/8/7 16:36
 * @Version 1.0
**/
@Slf4j
@Component
public class FileUtils {

    private static final int BUFFER_SIZE = 1024;

    public static String getFileMd5(File file) {
        if (file == null || !file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (IOException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    public static String fileToMD5(String path){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                md5.update(buffer, 0, len);
            }
            fis.close();

            byte[] byteArray = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : byteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
}


