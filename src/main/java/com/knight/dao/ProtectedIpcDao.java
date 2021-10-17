package com.knight.dao;

import com.knight.configuration.SqlConfig;
import com.knight.base.BaseDao;
import com.knight.db.dto.ProtectedIpc;
import com.knight.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 重保镜头处理
 *
 * @author knight
 * @since 2021-10-13
 */
@Component
@Slf4j
public class ProtectedIpcDao extends BaseDao {
    /**
     * 构造函数
     *
     * @param jdbcTemplate JdbcTemplate
     */
    public ProtectedIpcDao(@Qualifier("clickHouseJdbcTemplate") JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * 添加重保镜头
     *
     * @param fileList List
     */
    public void addProtectedIpc(List<Object[]> fileList) {
        if (CollectionUtils.isEmpty(fileList)) {
            return;
        }
        String sql = SqlConfig.getSql("insert_protected_ipc");
        try {
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @SneakyThrows
                @Override
                public void setValues(PreparedStatement ps, int position) throws SQLException {
                    setParam(ps, fileList);
                }

                @Override
                public int getBatchSize() {
                    return fileList.size();
                }
            });
        } catch (DataAccessException e) {
            log.error("batch AddProtectedIpc failed,  Message:{}", e.getMessage());
        }
    }

    /**
     * 根据摄像机的ID删除重保镜头信息
     *
     * @param platCameraId 待删除的id
     */
    public void deleteProtectedIpcById(String platCameraId) {
        String deleteSql = SqlConfig.getSql("delete_protected_ipc_byId");
        deleteSql = deleteSql.replaceAll("@platCameraId", platCameraId);
        executeUpdate(deleteSql);
    }

    private void setParam(PreparedStatement ps, List<Object[]> platCameraIDs) throws SQLException, ParseException {
        for (int i = 0; i < platCameraIDs.size(); i++) {
            Object[] tempObj = platCameraIDs.get(i);
            ps.setString(1, (String) tempObj[i]);
            ps.setString(2, String.valueOf(DateUtil.formatTimeInMillis(new Date())));
        }
    }

    public ProtectedIpc queryProtectedIpcByName(String aa, String name) {
        // 没有运营Id默认获取所有标签值
        String sql = SqlConfig.getSql("query_protected_icp_by_name");
        return (ProtectedIpc) jdbcTemplate.query(sql, new Object[]{aa, name}, new BeanPropertyRowMapper(ProtectedIpc.class))
                .get(0);
    }
}
