package org.jeecg.modules.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GamUserInfo {

    private String userId;
    private String userAccount;
    private String userName;
    private String status;
    private String gender;
    private boolean lockFlag ;
    private String beginDate;
    private String startTime;
    private String endTime;
    private boolean isAllowMasterModifyPwd ;
    private String adcUserId;

    private String adcId;

    private String phone;
    private String email;
    private String title;
    private String description;

    private boolean isAdmin;

    private String lastLoginTime;
    private String lastLogoutTime;
    private String allowLoginStartTime;
    private String allowLoginEndTime;
    private String tenantMfaFlag;
    private String mfaFlag;
    private String tenantId;
    private String createdAt;
    private String userIdentifyType;
    private List <String>role;
    private List <String>group;
    private String userTypeFlag;
    private String[] roleIds;
    private String roleId;
    private String createdTime;
    private String operTime;
    private String operId;
    private String beId;
    private String beCode;
    private String roleCode;
    private String roleName;
    private String roleType;
    private String presetFlag;
    private String scope;
    private String exAttr;
    private String orgId;







}
