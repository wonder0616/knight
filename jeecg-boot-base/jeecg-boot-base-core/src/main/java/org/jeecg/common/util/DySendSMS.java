/*
package org.jeecg.common.util;

import cn.com.flaginfo.sdk.cmc.api.ApiProvider;
import cn.com.flaginfo.sdk.cmc.api.request.ApiConfig;
import cn.com.flaginfo.sdk.cmc.api.result.ComResult;
import cn.com.flaginfo.sdk.cmc.api.sms.dynsend.DynSMSAPI;
import cn.com.flaginfo.sdk.cmc.api.sms.dynsend.DynSMSSendDataResult;
import cn.com.flaginfo.sdk.cmc.api.sms.dynsend.DynSMSSendRequest;
import cn.com.flaginfo.sdk.cmc.api.sms.reply.SMSReplyAPI;
import cn.com.flaginfo.sdk.cmc.api.sms.reply.SMSReplyDataResult;
import cn.com.flaginfo.sdk.cmc.api.sms.reply.SMSReplyRequest;
import cn.com.flaginfo.sdk.cmc.api.sms.replyconfirm.SMSReplyConfirmAPI;
import cn.com.flaginfo.sdk.cmc.api.sms.replyconfirm.SMSReplyConfirmRequest;
import cn.com.flaginfo.sdk.cmc.api.sms.report.SMSReportAPI;
import cn.com.flaginfo.sdk.cmc.api.sms.report.SMSReportRequest;
import cn.com.flaginfo.sdk.cmc.api.sms.report.SMSReportResult;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSApi;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSSendDataResult;
import cn.com.flaginfo.sdk.cmc.api.sms.send.SMSSendRequest;
import cn.com.flaginfo.sdk.cmc.common.ApiEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

*/
/**
 * @Description: sms管理
 * @Author: ls
 * @Date: 2021-11-11
 * @Version:V1.0
 *//*

@Slf4j
@Component
public class DySendSMS
{

    private static ApiProvider provider;

    @Value("${sms.service.sendServer}")
    private String sendServer = "";
    @Value("${sms.service.replyServer}")
    private String infoServer = "";
    @Value("${sms.service.spCode}")
    private String spCode = "";
    @Value("${sms.service.appKey}")
    private String appKey = "";
    @Value("${sms.service.appSecret}")
    private String appSecret = "";

    */
/**
     * 初始化短信服务器
     * 帮助文档：https://zx.ums86.com/web/page/inteface/docs-v2/index.html#/com
     *//*

    @PostConstruct
    public void init()
    {
        log.info(String.format("初始化短信服务spCode=%s  appKey=%s  appSecret=%s",spCode, appKey, appSecret));
        ApiConfig apiConfig = new ApiConfig(spCode, appKey, appSecret);
        provider = ApiProvider.getInstance(apiConfig);
    }

    */
/**
     * 发送固定短信不处理回执
     *@param  userNumber	接受号码以英文逗号隔开
     *@param  templateId	模板编号
     *@param  msgContent	发送内容
     *//*

    public ComResult sendMethod(String msgContent,String templateId,String userNumber) {
        return sendMethod(msgContent,templateId,userNumber,null,null);
    }
    */
/**
     * 固定内容发送
     *@param  userNumber	接受号码以英文逗号隔开
     *@param  templateId	模板编号
     *@param  msgContent	发送内容
     *@param  serialNumber	流水号，20 位数字，唯一 （规则自定义,建议时间格式精确到毫秒），与回执接口中的流水号一一对应，不传后面回执接口无法查询数据。
     *//*

    public ComResult sendMethod(String msgContent,String templateId,String userNumber,String serialNumber) {
        return sendMethod(msgContent,templateId,userNumber,serialNumber,null);
    }
    */
/**
     * 固定内容发送
     *@param  userNumber	接受号码以英文逗号隔开
     *@param  templateId	模板编号
     *@param  msgContent	发送内容
     *@param  serialNumber	流水号，20 位数字，唯一 （规则自定义,建议时间格式精确到毫秒），与回执接口中的流水号一一对应，不传后面回执接口无法查询数据。
     *@param  extendAccessNum		string	拓展号
     * @return {"code":0,"data":{"failList":"","taskId":"1011182173305494"},"msg":"发送成功","succ":true}
     *//*

    public ComResult sendMethod(String msgContent,String templateId,String userNumber,String serialNumber,String extendAccessNum) {
        //请求接口
        SMSApi api = (SMSApi) provider.getApi(ApiEnum.SENDSMS);
        api.setRequestUrl(sendServer + "/api/sms/send");
        //请求参数
        SMSSendRequest sendRequest = new SMSSendRequest();
        sendRequest.setMessageContent(msgContent);
        sendRequest.setTemplateId(templateId);
        sendRequest.setUserNumber(userNumber);
        if (serialNumber != null) {
            sendRequest.setSerialNumber(serialNumber);
        }
        if (extendAccessNum != null) {
            sendRequest.setExtendAccessNum(extendAccessNum);
        }
        ComResult<SMSSendDataResult> request = api.request(sendRequest);
        log.info(JSON.toJSONString(request));
        return request;

    }

    */
/**
     * 动态短信内容批量发送
     *//*

    public ComResult dynSendMethod(String templateId,String[][] data) {
        return dynSendMethod(templateId,data,null);
    }
    */
/**
     * 动态短信内容批量发送
     *//*

    public ComResult dynSendMethod(String templateId,String[][] data,String serialNumber) {
        //请求接口
        DynSMSAPI api = (DynSMSAPI) provider.getApi(ApiEnum.SENDDYNSMS);
        api.setRequestUrl(sendServer + "/api/sms-var/send");
        //请求参数
        DynSMSSendRequest sendRequest = new DynSMSSendRequest();
        sendRequest.setTemplateId(templateId);
        //new String[][]{{"手机号码", "编号"}, {"13000000000", "456"}}
        sendRequest.setDynData(data);
        if (serialNumber != null) {
            sendRequest.setSerialNumber(serialNumber);
        }
        ComResult<DynSMSSendDataResult> result = api.request(sendRequest);
        log.info(String.format("短信返回结果：%s",JSON.toJSONString(result)));
        return result;
    }

    */
/**
     * 状态报告
     *//*

    public void reportMethod() {
        //请求接口
        SMSReportAPI api = (SMSReportAPI) provider.getApi(ApiEnum.REPORTSMS);
        api.setRequestUrl(infoServer + "/info/sms/report");
        //请求
        ComResult<List<SMSReportResult>> result = api.request(new SMSReportRequest());
        List<SMSReportResult> data = result.getData();
        if (data.size() > 0) {
            System.out.println(data.get(0).getResult());
        }
        System.out.println(JSON.toJSONString(data));
        System.out.println(JSON.toJSONString(result));

    }

    */
/**
     * 上行回复查询
     *//*

    public void replyMethod() {
        //请求接口
        SMSReplyAPI api = (SMSReplyAPI) provider.getApi(ApiEnum.REPLYSMS);
        api.setRequestUrl(infoServer + "/info/sms/reply/query");
        SMSReplyRequest smsReplyRequest = new SMSReplyRequest();
        //请求
        ComResult<SMSReplyDataResult> result = api.request(smsReplyRequest);
        System.out.println(result.getData().getMaxId());
        if (result.getData().getApiSmsReplyBoList() != null && result.getData().getApiSmsReplyBoList().size() > 0) {
            System.out.println(result.getData().getApiSmsReplyBoList().get(0).getContent());
        }
        System.out.println(JSON.toJSONString(result));
    }

    */
/**
     * 上行回复查询数据收到确认
     *//*

    public void replyConfirmMethod() {
        //请求接口
        SMSReplyConfirmAPI api = (SMSReplyConfirmAPI) provider.getApi(ApiEnum.REPLYSMSCONFIRM);
        //请求Url
        SMSReplyConfirmRequest req = new SMSReplyConfirmRequest();
        api.setRequestUrl(infoServer + "/info/sms/reply/confirm");
        req.setMaxId("10110572149");
        ComResult request = api.request(req);
        System.out.println(request.isSucc());
        System.out.println(JSON.toJSONString(request));
    }

}*/
