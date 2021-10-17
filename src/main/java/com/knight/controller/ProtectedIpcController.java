package com.knight.controller;

import com.knight.bean.Response;
import com.knight.constant.ReturnConst;
import com.knight.db.dto.ProtectedIpc;
import com.knight.domain.connect.ProtectedIpcRequest;
import com.knight.service.ProtectedIpcService;
import com.knight.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 * 重保镜头相关 接口
 *
 * @author knight
 * @since 2021-10-13
 */
@RestController
//@RequestMapping(path = "/ipc/protected")
@RequestMapping(path = { "/ipc/protected" }, produces = { "application/json;charset=UTF-8" })
@Slf4j
public class ProtectedIpcController {
    private final ProtectedIpcService protectedIpcService;

    /**
     * 构造函数
     *
     * @param protectedIpcService ProtectedIpcService
     */
    public ProtectedIpcController(ProtectedIpcService protectedIpcService) {
        this.protectedIpcService = protectedIpcService;
    }

    /**
     * 添加重保镜头
     *
     * @param platCameraIds 重保镜头
     * @param bindingResult bindingResult
     * @return Response
     */
//    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response addProtectedIpc(@RequestBody @NotBlank String platCameraIds, BindingResult bindingResult) {
//        if (StringUtils.isEmpty(platCameraIds)) {
//            return new Response(ReturnConst.PARAM_ERROR, "platCameraId is empty");
//        }
//
//        if (ValidateUtil.paramError(bindingResult)) {
//            return new Response(ReturnConst.PARAM_ERROR, ValidateUtil.getErrorMsg(bindingResult));
//        }
//
//        protectedIpcService.addProtectedIpc(platCameraIds);
//
//        return Response.SUCCESS;
//    }

    /**
     * 删除重保镜头
     *
     * @param platCameraId  待删除的摄像头id
     * @param bindingResult bindingResult
     * @return Response
     */
//    @PostMapping("/delete")
//    public Response deleteProtectedIpc(@RequestParam @NotBlank String platCameraId, BindingResult bindingResult) {
//        if (StringUtils.isEmpty(platCameraId)) {
//            return new Response(ReturnConst.PARAM_ERROR, "ipcCodes is empty");
//        }
//        protectedIpcService.deleteProtectedIpcById(platCameraId);
//        return Response.SUCCESS;
//    }

//    /**
//     * 查询重保镜头列表
//     *
//     * @param request 查询参数
//     * @return Response
//     */
//    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response queryProtectedIpcList(@RequestParam ProtectedIpcRequest request) throws ParseException {
//
//        Response<ProtectedIpc> response = new Response();
//        List<ProtectedIpc> protectedIpcList = protectedIpcService.queryProtectedIpcList(request);
//        response.setDataList(protectedIpcList);
//        response.setTotal(BigInteger.valueOf(protectedIpcList.size()));
//        return response;
//    }

    /**
     * 根据名称查询镜头信息列表
     *
     * @param ipcInfo 查询信息
     * @return Response
     */
//    @PostMapping(value = "/queryByBame", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping({ "/queryByBame" })
    public Response queryProtectedIpcByName(@RequestBody String ipcInfo) {
//    public Response queryProtectedIpcByName(@RequestParam(value = "ipcInfo",required = false) String ipcInfo) {

        Response<ProtectedIpc> response = new Response();
//        log.info("zettttttttttttttttttt :{} ", ipcInfo.getAddTime());
        log.info("zettttttttttttttttttt :{} ", ipcInfo);
        log.info("mmmmmmmmmmmm :{} ", protectedIpcService.queryProtectedIpcByName(ipcInfo));
//        response.setDataList(protectedIpcService.queryProtectedIpcByName(ipcInfo));
//        response.setDataList(protectedIpcService.queryProtectedIpcByName(ipcInfo));

        return response;
    }

}
