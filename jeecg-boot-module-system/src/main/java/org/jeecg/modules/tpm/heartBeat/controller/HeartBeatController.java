package org.jeecg.modules.tpm.heartBeat.controller;


import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 心跳检测
 * @Author: liyang
 * @Date:   2021-09-28
 * @Version: V1.0
 */

@RestController
@RequestMapping("/heartBeat/heartBeat")
@Slf4j
public class HeartBeatController {

    @GetMapping(value = "/query")
    public Result<?> getHeartBeat(HttpServletRequest req) {
        return Result.OK("还有心跳");
    }
}
