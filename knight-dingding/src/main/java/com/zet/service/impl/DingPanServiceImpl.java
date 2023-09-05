package com.zet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkdrive_1_0.models.*;
import com.aliyun.dingtalkstorage_1_0.Client;
import com.aliyun.dingtalkstorage_1_0.models.CommitFileResponse;
import com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCspaceInfoRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;

import com.dingtalk.api.response.OapiProcessinstanceCspaceInfoResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;

import com.zet.service.DingPanService;
import com.zet.common.utils.AccessTokenUtil;
import com.taobao.api.ApiException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zet.common.constant.UrlConstant.SUCCEED_CODE;


/**
 * @Description
 * @Author
 * @Date  2023/8/7 16:14
 * @Version 1.0
 **/
@Slf4j
@Data
@Service
@ConfigurationProperties(prefix = "ding-pan")
public class DingPanServiceImpl implements DingPanService {


    @Value("${ding-pan.urlConstant.GET_SPACE_ID_URL}")
    private  String getSpaceIdUrl;

    @Value("${ding-pan.urlConstant.USER_GET_URL}")
    private  String userGetUrl;

    @Value("${ding-pan-groups.preferRegion}")
    private String preferRegion;

    @Override
    public String getUnionId(String access_token, String userid) {

        try {
            DingTalkClient client = new DefaultDingTalkClient(userGetUrl);


            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userid);
            req.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            log.info("OapiV2UserGetResponse,{}", rsp.getBody());
            JSONObject json = JSONObject.parseObject(rsp.getBody());

            String unionId = json.getJSONObject("result").getString("unionid");

            return unionId;

        } catch (ApiException e) {
            log.error("spaceId,{}", e.getErrMsg());

        }


        return null;
    }

    @Override
    public List<String> createSpace(String accessToken, String unionId)  {
        List<String> list  = new ArrayList<>(2);
        Config config = new Config();
        config.protocol = "https";
//        config.endpoint = "172.18.28.36:18182";
        config.regionId = "central";
        try {
            com.aliyun.dingtalkdrive_1_0.Client client = new com.aliyun.dingtalkdrive_1_0.Client(config);
            AddSpaceHeaders addSpaceHeaders = new AddSpaceHeaders();
            addSpaceHeaders.xAcsDingtalkAccessToken = accessToken;
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
            String dirName = simpleDateFormat.format(date);

            ListSpacesHeaders listSpacesHeaders = new ListSpacesHeaders();
            listSpacesHeaders.xAcsDingtalkAccessToken = accessToken;
            ListSpacesRequest listSpacesRequest = new ListSpacesRequest()
                    .setUnionId(unionId)
                    .setSpaceType("org")
                    .setMaxResults(50);
            try {
                ListSpacesResponse listSpacesResponse =  client.listSpacesWithOptions(listSpacesRequest, listSpacesHeaders, new RuntimeOptions());

                List<ListSpacesResponseBody.ListSpacesResponseBodySpaces> abc = listSpacesResponse.getBody().getSpaces();

                for (ListSpacesResponseBody.ListSpacesResponseBodySpaces listSpacesResponseBodySpaces : abc) {
                    //组织命中内存中的corpId

                    if (dirName.equals(listSpacesResponseBodySpaces.getSpaceName())){
                        list.add(listSpacesResponseBodySpaces.getSpaceId());
                        log.info("spaceId,{}", listSpacesResponseBodySpaces.getSpaceId());
                        //  钉盘文件夹名
                        list.add(dirName);
                        return list;
                    }
                }

            } catch (TeaException err) {
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                }

            } catch (Exception _err) {
                TeaException err = new TeaException(_err.getMessage(), _err);
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                }
            }

            //  钉盘文件夹名
            list.add(dirName);
            AddSpaceRequest addSpaceRequest = new AddSpaceRequest()
                    .setName(dirName)
                    .setUnionId(unionId);

            AddSpaceResponse addSpaceResponse = client.addSpaceWithOptions(addSpaceRequest, addSpaceHeaders, new RuntimeOptions());
//            AddSpaceResponse addSpaceResponse = new AddSpaceResponse();
            log.info("addSpaceResponse,{}", addSpaceResponse.getBody().getSpaceId());
            JSONObject json0 = JSONObject.parseObject(JSON.toJSONString(addSpaceResponse));
            log.info("addSpaceResponsejson0,{}", json0);
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(addSpaceResponse.getBody()));
            log.info("addSpaceResponse.getBody(),{}", json);
            String spaceId = json.getString("spaceId");
            list.add(spaceId);
            log.info("spaceId,{}", spaceId);
            return list;
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("AddSpaceResponse,{}", err.code);
                log.error("AddSpaceResponse ,{}", err.message);
            }
        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("AddSpaceResponse Tea,{}", err.code);
                log.error("AddSpaceResponse Tea,{}", err.message);
            }
        }
        return null;
    }

    @Override
    public Long getSpaceId(String userId, String corpId) {
        DingTalkClient client = new DefaultDingTalkClient(getSpaceIdUrl);
        OapiProcessinstanceCspaceInfoRequest req = new OapiProcessinstanceCspaceInfoRequest();
        // 用户的钉钉id
        req.setUserId(userId);
        OapiProcessinstanceCspaceInfoResponse rsp = null;
        try {
            String accessToken = null;
            Map<String,String> corpIdsAndaccessTokens = AccessTokenUtil.corpIdsAndaccessTokens;

            //组织命中内存中的corpId
            accessToken = corpIdsAndaccessTokens.get(corpId);

            rsp = client.execute(req, accessToken);
            return rsp.getResult().getSpaceId();

        } catch (ApiException e) {
            log.error("获取钉盘Id失败！原因：{}", e.getErrMsg());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAccessToken,{}", e.getMessage());
        }
        if (Objects.isNull(rsp)) {
            return null;
        }
        return rsp.getResult().getSpaceId();
    }

    @Override
    public CommitFileResponse fileUploadInfo(String spaceId, String accessToken, String fileName, MultipartFile multipartFile, String unionId) {
        CommitFileResponse commitFile = null;
        log.info("CommitFileResponse commitFile ,{}",commitFile);
        com.aliyun.dingtalkstorage_1_0.Client client = createClient();
        log.info("GetFileUploadInfoHeaders,{}", "start");
        com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoHeaders getFileUploadInfoHeaders = new com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoHeaders();
        log.info("GetFileUploadInfoHeaders,{}", "end");
        getFileUploadInfoHeaders.xAcsDingtalkAccessToken = accessToken;
        log.info("getFileUploadInfoHeaders.xAcsDingtalkAccessToken,{}", accessToken);

        com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoRequest.GetFileUploadInfoRequestOption option = new com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoRequest.GetFileUploadInfoRequestOption()
                .setPreferRegion(preferRegion);
        com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoRequest getFileUploadInfoRequest = new com.aliyun.dingtalkstorage_1_0.models.GetFileUploadInfoRequest()
                .setProtocol("HEADER_SIGNATURE")
                .setMultipart(false)
                .setUnionId(unionId)
                .setOption(option);

        try {
            GetFileUploadInfoResponse getFileUploadInfoResponse = client.getFileUploadInfoWithOptions(spaceId, getFileUploadInfoRequest, getFileUploadInfoHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            List<String> resourceUrls = getFileUploadInfoResponse.getBody().getHeaderSignatureInfo().getResourceUrls();
            // 从接口返回信息中拿到resourceUrls
//            String resourceUrl0 = resourceUrls.get(0);
//            String resourceUrl = resourceUrl0.replace("https://zjk-dualstack.trans.dingtalk.com","http://172.18.28.36:18183");


            String resourceUrl = resourceUrls.get(0);
            log.info("resourceUrl0,{}", resourceUrl);
            // 从接口返回信息中拿到headers
            Map<String, String> headers = getFileUploadInfoResponse.getBody().getHeaderSignatureInfo().getHeaders();
            log.info("headers,{}", headers);
            // uploadKey
            String uploadKey = getFileUploadInfoResponse.getBody().getUploadKey();
            log.info("uploadKey,{}", uploadKey);
            URL url = new URL(resourceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setUseCaches(false);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            OutputStream out = connection.getOutputStream();
            InputStream is = multipartFile.getInputStream();
            byte[] b = new byte[1024];
            int temp;
            while ((temp = is.read(b)) != -1) {
                out.write(b, 0, temp);
            }
            out.flush();
            out.close();
            int responseCode = connection.getResponseCode();
            connection.disconnect();
            if (responseCode == SUCCEED_CODE) {
                log.info("上传成功 fileName:{}",fileName);

                commitFile = commitFile(accessToken, unionId, uploadKey, fileName, spaceId, client);
                return  commitFile;

            } else {
                log.error("上传失败 fileName:{}", fileName);
                return null;

            }
        } catch (TeaException err) {
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("GetFileUploadInfoResponse,{}", err.code);
                log.error("GetFileUploadInfoResponse,{}", err.message);
            }

        } catch (Exception _err) {
            TeaException err = new TeaException(_err.getMessage(), _err);
            if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                // err 中含有 code 和 message 属性，可帮助开发定位问题
                log.error("GetFileUploadInfoResponse Tea,{}", err.code);
                log.error("GetFileUploadInfoResponse Tea,{}", err.message);
            }

        }
        return null;
    }

    @Override
    public CommitFileResponse commitFile(String accessToken, String unionId, String uploadKey, String fileName, String spaceId, Client client) {
        {
            log.info("commitFileStart,{}", fileName);

            com.aliyun.dingtalkstorage_1_0.models.CommitFileHeaders commitFileHeaders = new com.aliyun.dingtalkstorage_1_0.models.CommitFileHeaders();
            commitFileHeaders.xAcsDingtalkAccessToken = accessToken;
            com.aliyun.dingtalkstorage_1_0.models.CommitFileRequest commitFileRequest = new com.aliyun.dingtalkstorage_1_0.models.CommitFileRequest()
                    .setUnionId(unionId)
                    .setUploadKey(uploadKey)
                    .setName(fileName)
                    .setParentId("0");
            try {
                CommitFileResponse commitFileResponse = client.commitFileWithOptions(spaceId, commitFileRequest, commitFileHeaders, new com.aliyun.teautil.models.RuntimeOptions());
                log.info("commitFileEnd,{}", fileName);
                return commitFileResponse;
            } catch (TeaException err) {
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    log.error("CommitFileResponse,{}", err.code);
                    log.error("CommitFileResponse,{}", err.message);

                }
            } catch (Exception _err) {
                TeaException err = new TeaException(_err.getMessage(), _err);
                if (!com.aliyun.teautil.Common.empty(err.code) && !com.aliyun.teautil.Common.empty(err.message)) {
                    // err 中含有 code 和 message 属性，可帮助开发定位问题
                    log.error("CommitFileResponse Tea,{}", err.code);
                    log.error("CommitFileResponse Tea,{}", err.message);
                }

            }

        }
        return null;
    }

    public static com.aliyun.dingtalkstorage_1_0.Client createClient()  {
        try {
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
            config.protocol = "https";
//            config.endpoint = "172.18.28.36:18182";
            config.regionId = "central";
            log.info("com.aliyun.dingtalkstorage_1_0.Client,{}", "successs");
            return new com.aliyun.dingtalkstorage_1_0.Client(config);
        } catch (Exception e) {
            log.error("createClientError,{}", e.getMessage());
        }
        return null;
    }
}
