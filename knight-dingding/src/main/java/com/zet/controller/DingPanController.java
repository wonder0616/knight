package com.zet.controller;

import com.aliyun.dingtalkstorage_1_0.models.CommitFileResponse;
import com.zet.model.Result;
import com.zet.service.DingPanService;
import com.zet.common.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Description
 * @Author
 * @Date 2023/8/7 16:10
 * @Version 1.0
 **/
@CrossOrigin
@RestController
@Slf4j
@RequestMapping("uploadDing")
public class DingPanController {


    @Autowired
    private DingPanService dingPanService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;


    /**
     * 上传服务器 MultipartFile 上传 带文件的钉盘路径
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("method")
    public Result<?> upload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("userId") String userId,
                            @RequestParam("corpId") String corpId) {
        //获取开始时间
        long startTime = System.currentTimeMillis();
        if (multipartFile.isEmpty() || userId.isEmpty() ||corpId.isEmpty()) {
            return Result.error(500, "file,userId,corpId不能为空");

        } else {
            CommitFileResponse fileUploadInfo = null;
            String fileName = multipartFile.getOriginalFilename();
            log.info("start：userId{}", userId);


            String accessToken = null;
            Map<String, String> corpIdsAndaccessTokens = AccessTokenUtil.corpIdsAndaccessTokens;

            //组织命中内存中的corpId

            accessToken = corpIdsAndaccessTokens.get(corpId);
            if(accessToken == null) {
                return Result.error(500, "内部错误");
            }

            String getUnionId = dingPanService.getUnionId(accessToken, userId);
            List<String> spaceIdanddirName = dingPanService.createSpace(accessToken, getUnionId);
            String DingPandirName = spaceIdanddirName.get(0);
//            String DingPandirName = "2023082419";
            log.info("DingPandirName0,{}",spaceIdanddirName.get(0));
            log.info("DingPandirName1,{}",spaceIdanddirName.get(1));
            log.info("fileName,{}",fileName);
            fileUploadInfo = dingPanService.fileUploadInfo(spaceIdanddirName.get(1), accessToken, fileName, multipartFile, getUnionId);

            Map<String, String> headers = fileUploadInfo.getHeaders();
            headers.put("dingPan-path", DingPandirName);
            fileUploadInfo.setHeaders(headers);
            //获取结束时间
            long endTime = System.currentTimeMillis();
            //输出程序运行时间
            log.info("程序运行时间：{}", (endTime - startTime) / 1000 + "s");
            return Result.OK(fileUploadInfo);
        }

    }
}