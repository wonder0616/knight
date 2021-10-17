package com.knight.service;

import com.knight.configuration.SqlConfig;
import com.knight.dao.ProtectedIpcDao;
import com.knight.db.dto.ProtectedIpc;
import com.knight.util.SqlUtil;
import com.knight.domain.connect.ProtectedIpcRequest;
import com.knight.exception.ParamInvalidException;
import com.knight.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 重保镜头 业务处理类
 *
 * @author knight
 * @since 2021-10-13
 */
@Component
@Slf4j
public class ProtectedIpcService {
    private final ProtectedIpcDao protectedIpcDao;

    /**
     * 构造函数
     *
     * @param protectedIpcDao ProtectedIpcDao
     */
    public ProtectedIpcService(ProtectedIpcDao protectedIpcDao) {
        this.protectedIpcDao = protectedIpcDao;
    }

//    /**
//     * 添加重保镜头
//     *
//     * @param platCameraIds list
//     */
//    public void addProtectedIpc(String platCameraIds) {
//        if (StringUtils.isEmpty(platCameraIds)) {
//            throw new ParamInvalidException("platCameraID is empty");
//        }
//        String[] cameraIds = platCameraIds.split(",");
//        List<Object[]> fileList = new ArrayList<>();
//        fileList.add(cameraIds);
//
//        protectedIpcDao.addProtectedIpc(fileList);
//    }

//    /**
//     * 根据摄像机的id删除
//     *
//     * @param platCameraId 待删除的id
//     */
//    public void deleteProtectedIpcById(String platCameraId) {
//        if (StringUtils.isEmpty(platCameraId)) {
//            throw new ParamInvalidException("platCameraId is empty");
//        }
//        protectedIpcDao.deleteProtectedIpcById(platCameraId);
//
//        // 调用策略配置部分，删除定制数据
//    }
//
//    /**
//     * 根据条件查询重保镜头列表
//     *
//     * @param req 查询条件
//     * @return List 重保镜头列表
//     */
//    public List<ProtectedIpc> queryProtectedIpcList(ProtectedIpcRequest req) throws ParseException {
//        if (req == null) {
//            throw new ParamInvalidException("ProtectedIpcInfo is null");
//        }
//        String areaId = req.getAreaId();
//        // 根据 areaid，查询解析出对应的 省、市、区
//        String applicationType = req.getApplicationType();
//        int state = req.getState();
//        String cameraName = req.getCameraName();
//        // 时间取当前5分钟之内的最新的一条
//        Calendar beforeTime = Calendar.getInstance();
//        beforeTime.add(Calendar.MINUTE, -5);
//        long time = DateUtil.formatTimeInMillis(beforeTime.getTime());
//
//        // 页码，从0开始
//        Long offset = req.getOffset();
//        // 分页大小，默认20
//        Long limit = req.getLimit();
//        Object[] params = new Object[]{areaId, applicationType, cameraName, state, time, offset, limit};
//        String sql = SqlConfig.getSql("query_protected_icp_by_params");
//        sql = SqlUtil.replaceTodayPartition(sql);
//        return protectedIpcDao.queryBeans(sql, params, ProtectedIpc.class);
//    }

    /**
     * 根据名称模糊查询对应镜头列表
     *
     * @param ipcInfo 查询条件
     * @return List
     */
    public ProtectedIpc queryProtectedIpcByName(String ipcInfo) {
//    public List<ProtectedIpc> queryProtectedIpcByName(ProtectedIpc ipcInfo) {
        if (ipcInfo == null) {
            throw new ParamInvalidException("protectedIpc is null");
        }
//        String areaId = ipcInfo.getAreaId();
        // 根据 areaid，查询解析出对应的 省、市、区
//        String cameraName = ipcInfo.getCameraName();

        Object[] params = new Object[]{"300100",ipcInfo};
//        Object[] params = new Object[]{areaId, cameraName};
        String sql = SqlConfig.getSql("query_protected_icp_by_name");
//        sql = SqlUtil.replaceTodayPartition(sql);
//        return protectedIpcDao.queryBeans(sql, params, ProtectedIpc.class);
//        return protectedIpcDao(sql, params,  new BeanPropertyRowMapper(String.class));
        return protectedIpcDao.queryProtectedIpcByName("300100",ipcInfo);
    }

}
