package org.jeecg.modules.rzg.onlinePreview.controller;


import cn.hutool.core.codec.Base64;
import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import freemarker.template.utility.StringUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeException;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Description: 在线预览
 * @Author: jeecg-boot
 * @Date: 2022-05-19
 * @Version: V1.0
 */
@Api(tags = "在线预览")
@RestController
@RequestMapping("/preview")
@Slf4j
public class previewController
{


    @Value("${jeecg.path.upload}")
    private String upLoadPath;


    /**
     * 默认转换后文件后缀
     */
    private static final String DEFAULT_SUFFIX = "pdf";
    /**
     *  这里的内容是根据你的系统选择不同的端口号，windows系统的端口号是8100
     */
    private static final Integer OPENOFFICE_PORT = 8100;
    /**
     * 文件路径类型  本地C:\\xxx  网络 http://xxx
     */
    private  static final  String  FILE_LOCAL = "local";
    private  static final  String  FILE_URL = "url";

    @Value("${jeecg.openOffice.home}")
    private  String officeHome ; //这里写的是你的openoffice的安装地址，如果你在安装openOffice 的时候选择的是默认安装，那么地址是：C:/Program Files (x86)/OpenOffice 4/。　　　　
    // 　//如果是自定义的安装方式，请填写自定义安装路径。
    @SuppressWarnings("static-access")

    private static OfficeManager officeManager; // 尝试连接已存在的服务器

    /**
     * 转换pdf预览
     *
     * @param url
     * @param response
     * @return
     */
    @GetMapping("/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception
    {
        ///获取文件类型
        String s = Base64.decodeStr(url);
        log.info("urldecodeStr："+s);
        String[] str = StringUtil.split(s, '.');
        log.info("openofficeinstalladresss："+officeHome);
        if (str.length == 0)
        {
            throw new Exception("文件格式不正确");
        }
        String suffix = str[str.length - 1];
        suffix =  suffix.indexOf("?") != -1 ? suffix.substring(0,suffix.indexOf("?")):suffix;

        log.info("fileFormat"+suffix);
        if (!suffix.equals("pdf")&&!suffix.equals("txt") && !suffix.equals("doc") && !suffix.equals("docx") && !suffix.equals("xls") && !suffix.equals("xlsx") && !suffix.equals("ppt") && !suffix.equals("pptx")&& !suffix.equals("jpg")&& !suffix.equals("png"))
        {
            throw new Exception("文件格式不支持预览");
        }

        // 本地服存储务实现openOffice调用,
        //  File inFile = convertToPdf(url,FILE_URL);
        //InputStream in = new FileInputStream(inFile);

        // 通过远程服务实现openOffice调用
        InputStream in;
        if (suffix.equals("pdf")||suffix.equals("jpg")||suffix.equals("png")){
            log.info("no convert");
            in= convertNetFile(s);
        }
        else {
            in = covertCommonByStream(convertNetFile(s),suffix);
        }
        if (suffix.equals("pdf")){
            response.setContentType("application/pdf;charset=UTF-8");
        }
        if (suffix.equals("png")){
            response.setContentType("image/png;charset=UTF-8");
        }
        if (suffix.equals("jpg")){
            response.setContentType("image/jpeg;charset=UTF-8");
        }

        OutputStream outputStream = response.getOutputStream();
        //创建存放文件内容的数组
        byte[] buff = new byte[1024];
        //所读取的内容使用n来接收
        int n;
        //当没有读取完时,继续读取,循环
        while ((n = in.read(buff)) != -1)
        {
            //将字节数组的数据全部写入到输出流中
            outputStream.write(buff, 0, n);
        }
        //强制将缓存区的数据进行输出
        outputStream.flush();
        //关流
        outputStream.close();
        in.close();
        //inFile.delete();
    }


    /**
     * 方法描述 office文档转换为PDF(处理本地文件)
     *
     * @param sourcePath 源文件路径
     * @param suffix     源文件后缀
     * @return InputStream 转换后文件输入流
     * @author tarzan
     */
    public  InputStream convertLocaleFile(String sourcePath, String suffix) throws Exception
    {
        File inputFile = new File(sourcePath);
        InputStream inputStream = new FileInputStream(inputFile);
        return covertCommonByStream(inputStream, suffix);
    }

    /**
     * 方法描述 office文档转换为PDF(处理网络文件)
     *
     * @param netFileUrl 网络文件路径
     * @return InputStream 转换后文件输入流
     * @author tarzan
     */
    public  InputStream convertNetFile(String netFileUrl) throws Exception
    {
        // 创建URL
        URL url = new URL(netFileUrl);
        // 试图连接并取得返回状态码
        URLConnection urlconn = url.openConnection();
       // urlconn.setConnectTimeout(50000);
        urlconn.connect();
        HttpURLConnection httpconn = (HttpURLConnection) urlconn;
        int httpResult = httpconn.getResponseCode();
        log.info(" 文件长度={}" ,httpconn.getContentLength());
        log.info("状态码={}",httpResult);
        if (httpResult == HttpURLConnection.HTTP_OK)
        {
            log.info("success reponse");
            InputStream inputStream = urlconn.getInputStream();
            return inputStream;
        }
        return null;
    }

    /**
     * 方法描述 将文件以流的形式转换
     *
     * @param inputStream 源文件输入流
     * @param suffix      源文件后缀
     * @return InputStream 转换后文件输入流
     * @author tarzan
     */
    public  InputStream covertCommonByStream(InputStream inputStream, String suffix) throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPENOFFICE_PORT);
        connection.connect();
        DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
        DocumentFormat targetFormat = formatReg.getFormatByFileExtension(DEFAULT_SUFFIX);
        DocumentFormat sourceFormat = formatReg.getFormatByFileExtension(suffix);
        converter.convert(inputStream, sourceFormat, out, targetFormat);
        connection.disconnect();
        return outputStreamConvertInputStream(out);
    }

    /**
     * 方法描述 outputStream转inputStream
     *
     * @author tarzan
     */
    public  ByteArrayInputStream outputStreamConvertInputStream(final OutputStream out) throws Exception
    {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private  boolean reconnect()
    {
        try
        {
            // 尝试连接openoffice的已存在的服务器
            ExternalOfficeManagerConfiguration externalProcessOfficeManager = new ExternalOfficeManagerConfiguration();
            externalProcessOfficeManager.setConnectOnStart(true);
            externalProcessOfficeManager.setPortNumber(OPENOFFICE_PORT);

            officeManager = externalProcessOfficeManager.buildOfficeManager();
            officeManager.start();
            return true;
        }
        catch (OfficeException e)
        {
            e.printStackTrace();
            log.info("connect error");
            return false;
        }
    }

    /**
     * 开启新的openoffice的进程
     */
    private  void start()
    {
        log.debug("启动OpenOffice服务");
        try
        {
            DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
            // 安装地址
            configuration.setOfficeHome(officeHome);
            // 端口号
            configuration.setPortNumbers(OPENOFFICE_PORT);
            // 设置任务执行超时为5分钟
            configuration.setTaskExecutionTimeout(1000 * 60 * 5);
            // 设置任务队列超时为24小时
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24);
            officeManager = configuration.buildOfficeManager();
            // 启动服务
            officeManager.start();
        }
        catch (Exception e)
        {
            log.error("启动OpenOffice服务出错" + e);
        }
    }

    /**
     * 使用完需要关闭该进程
     */
    private  void stop()
    {
        log.debug("关闭OpenOffice服务");
        try
        {
            if (officeManager != null) {
                officeManager.stop();
            }
        }
        catch (Exception e)
        {
            log.error("关闭OpenOffice服务出错" + e);
        }
    }


    public  File convertToPdf(String input,String pathType)
    {
        File inputFile = null;
        File outFile = null;
        try
        { // 如果已存在的服务不能连接或者不存在服务，那么开启新的服务　　　　
            if (!reconnect())
            {
                log.info("reStart openOffice");
                start();// 开启服务
            }
            if (FILE_URL.equals(pathType))
            {
                InputStream urlFile =  convertNetFile(input);
                String fileName =  FilenameUtils.getName(input);
                fileName =  fileName.indexOf("?") != -1 ? fileName.substring(0,fileName.indexOf("?")):fileName;
                input =  upLoadPath + "/"+ fileName;
                log.info("input file name={}",input);
                inputFile = new File(input);
                inputFile = writeToLocal(inputFile,urlFile);
            }
           else
            {
                inputFile = new File(input);
            }

            // filenameUtils是Apache对java io的封装。　FilenameUtils.separatorsToSystem：转换分隔符为当前系统分隔符　/ FilenameUtils.getFullPath:获取文件的完整目录　　　　　　　　　　　　　　// FilenameUtils.getBaseName:取出文件目录和后缀名的文件名
            String output = FilenameUtils.separatorsToSystem(FilenameUtils.getFullPath(input) + FilenameUtils.getBaseName(input) + ".pdf");

            outFile = new File(output);
            log.info("开始转换文档：" + input + "=>" + output);
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(inputFile, outFile); // 转换文档
        }
        catch (Exception e)
        {
            log.error("transcription errorr");
            e.printStackTrace();
            outFile = null;
        }
        finally
        {
            log.info("结束转换文档");
            stop();
            if (inputFile!=null)
            {
                inputFile.delete();
            }
        }
        return outFile;
    }

    /**
     * 测试工具类是否成功
     * @param args 参数
     */
    public static void main(String[] args)
    {
        new previewController().convertToPdf("C:\\Users\\liusi\\Desktop\\ls.doc",FILE_LOCAL);
        String input = "http://10.100.72.206:60268/buckets/test/ls.doc";

// File sf = new File("E:/test.ppt");
// System.out.println(sf.getPath());
    }
    public  File writeToLocal(File file, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(file);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
        }
        downloadFile.flush();
        input.close();
        downloadFile.close();
        return file;
    }
}