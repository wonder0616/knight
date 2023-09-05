package com.zet.service;

import com.aliyun.dingtalkstorage_1_0.Client;
import com.aliyun.dingtalkstorage_1_0.models.CommitFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @Description 服务端调用的钉盘API接口
 * @Author
 * @Date  2023/8/7 16:03
 * @Version 1.0
**/
public interface DingPanService {

    /**
     * @Description 根据userid获取unionId
     * @param accessToken accessToken 的有效期为7200秒（2小时），有效期内重复获取会返回相同结果并自动续期，过期后获取会返回新的accessToken
     * @param userid  userid 员工的userId
     * @return UnionId 员工在当前开发者企业账号范围内的唯一标识。
     * @Author
     * @Date  2023/8/7 11:12
     * @Version 1.0
    **/
    String getUnionId(String accessToken, String userid);

    /**
     * @Description 根据操作人的unionId在企业存储内添加新空间
     * @param accessToken  accessToken 的有效期为7200秒（2小时），有效期内重复获取会返回相同结果并自动续期，过期后获取会返回新的accessToken
     * @param unionId 员工在当前开发者企业账号范围内的唯一标识。
     * @return SpaceId 空间id
     * @Author
     * @Date  2023/8/7 11:13
     * @Version 1.0
    **/
    List<String> createSpace(String accessToken, String unionId);
    
    /**
     * @Description 获取用户userid下的钉盘空间id
     * @Author 
     * @Date  2023/8/7 11:20
     * @Version 1.0
    **/
    Long getSpaceId(String userId, String corpId);


    /**
     * @Description 获取文件上传信息
     * @Author
     * @Date  2023/8/7 11:15
     * @Version 1.0
    **/
    CommitFileResponse fileUploadInfo(String spaceId, String accessToken, String fileName, MultipartFile multipartFile, String unionId);

    /**
     * @Description 提交文件到钉盘
     * @Author
     * @Date  2023/8/7 14:17
     * @Version 1.0
    **/
    CommitFileResponse commitFile(String accessToken, String unionId, String uploadKey, String fileName, String spaceId, Client client);

}
