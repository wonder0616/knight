package com.knight.db.dto;

import lombok.Data;

/**
 * IPC 详情
 *
 * @author knight
 * @since 2021-10-14
 */
@Data
public class IpcDetail {
    /**
     * 摄像机国标编码
     */
    private String deviceId;

    /**
     * 摄像机xxx id
     */
    private String cameraId;

    /**
     * 摄像机名称
     */
    private String cameraName;

    /**
     * 摄像机domain
     */
    private String domainId;

    /**
     * 区域ID
     */
    private String areaId;

    /**
     * 应用类型
     */
    private String applicationType;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 维修类型
     */
    private String maintainType;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 摄像机在运营平台的ID
     */
    private String platDeviceId;

    /**
     * 摄像机在运营平台的ID
     */
    private String platCameraId;

    /**
     * 状态：1 在线 0离线
     */
    private int online;

    /**
     * 状态：1 正常 0异常
     */
    private int state;
}