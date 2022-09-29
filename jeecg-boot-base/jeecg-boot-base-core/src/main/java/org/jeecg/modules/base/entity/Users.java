package org.jeecg.modules.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users {

    private static final long serialVersionUID = 1L;
    private Integer	total;
    private String	userId;
    private String	userAccount;
    private String	userName;
    private Integer	status;
    private String	phone;
    private String	email;
    private Integer	gender;
    private String	title;
    private String	description;
    private Boolean	lockFlag;
    private String	beginDate;
    private String	endDate;
    private Boolean	isAllowMasterModifyPwd;
    private String	mfaFlag;
    private String	company;
    private String	department;
    private String	timeZone;
    private String	adcUserId;
    private String	adcId;
}
