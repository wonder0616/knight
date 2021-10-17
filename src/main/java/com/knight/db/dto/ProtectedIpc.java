package com.knight.db.dto;

import lombok.Data;

/**
 * 重保镜头信息
 *
 * @author knight
 * @since 2021-10-13
 */
@Data
public class ProtectedIpc extends IpcDetail {

    /**
     * 添加时间：yyyy-mm-dd HH:mm:ss
     */
    private String addTime;

}
