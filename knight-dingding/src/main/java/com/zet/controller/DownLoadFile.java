package com.zet.controller;


import com.alibaba.fastjson.JSONObject;
import com.zet.config.DingPanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.lf5.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Locale;

import static com.zet.common.constant.UrlConstant.WINDOW_SYS;


/**
 * @Description
 * @Author
 * @Date  2023/8/7 16:10
 * @Version 1.0
**/
@CrossOrigin
@RestController
@Slf4j
public class DownLoadFile {

    @Value("${ding-pan.windowPath}")
    private  String windowPath;

    @Value("${ding-pan.linuxPath}")
    private  String linuxPath;

    @Autowired
    private DingPanConfig dingpanConfig;

    @GetMapping("/download3")
    public void download3(@RequestParam("online") Boolean online, HttpServletResponse response) throws IOException {

        String path;
        String osName = System.getProperties().get("os.name").toString().toLowerCase(Locale.ROOT);
        if (osName.contains(WINDOW_SYS)) {
            path = windowPath;
        } else {
            path = linuxPath;

        }
        File file = new File(path);

        FileInputStream fileInputStream = new FileInputStream(file);

        String fileName = file.getName().substring(file.getName().lastIndexOf("\\") + 1);

        if (online) {
            //下载
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        }
        StreamUtils.copy(fileInputStream, response.getOutputStream());
    }

    @GetMapping("/downloadBlob")
    @ResponseBody
    public JSONObject download2(@RequestParam("online") Boolean online, HttpServletResponse response) throws Exception {


        JSONObject obj = new JSONObject();
        String path = "";
        String osName = System.getProperties().get("os.name").toString().toLowerCase(Locale.ROOT);
        if (osName.contains(WINDOW_SYS)) {
            path = windowPath;
        } else {
            path = linuxPath;

        }
        File file = new File(path);
        String fileName = file.getName().substring(file.getName().lastIndexOf("\\") + 1);


        log.info("fileName,fileName{}", fileName);
        //下载
        if (online) {
            obj.put("name", fileName);
            String base64 = encodeBase64File(file);
            Blob blob = base64ToBlob(base64);
            log.info("Blob,blob{}", blob);
            obj.put("file", blob);
            return obj;

        }

        return obj;
    }


    public Blob base64ToBlob(String base64String) throws SQLException {
        byte[] bytes = Base64.getMimeDecoder().decode(base64String);
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);


        return blob;
    }

    public static String encodeBase64File(File file) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(file);
        // 读取到 byte 里面
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 得到文件 之后转成bytes 然后使用base64转码
        String encode = base64Encoder.encode(bytes);
        return encode;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }

}
