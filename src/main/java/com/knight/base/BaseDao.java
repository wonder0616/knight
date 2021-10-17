package com.knight.base;

import com.knight.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * JDBC工具类 约束：只处理numeric和varchar类型字段
 *
 * @author knight
 * @since 2020-08-31
 */
@Slf4j
public class BaseDao {
    /**
     * JDBC处理类
     */
    protected JdbcTemplate jdbcTemplate;

    /**
     * 构造函数
     *
     * @param jdbcTemplate JdbcTemplate
     */
    public BaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询结果集jdbcUtils.query(Tool.getSql("can_dialTest_ar"))
     *
     * @param sql       查询SQL
     * @param typeClass bean类型
     * @param <T>       T
     * @return List
     */
    public <T> List<T> queryBeans(String sql, Class typeClass) {
        return queryBeans(sql, null, typeClass);
    }

    /**
     * 查询结果集jdbcUtils.query(Tool.getSql("can_dialTest_ar"))
     *
     * @param sql       查询SQL
     * @param params    占位参数
     * @param typeClass bean类型
     * @param <T>       T
     * @return List
     */
    public <T> List<T> queryBeans(String sql, Object[] params, Class typeClass) {
        try {
            Pair<String, Object[]> pair = SqlUtil.processInConditions(sql, params);
            pair = SqlUtil.processNullWhereParam(pair);
            return jdbcTemplate.query(pair.getLeft(), pair.getRight(), new BeanPropertyRowMapper(typeClass));
        } catch (DataAccessException e) {
            log.error("query failed, Message:{}", e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 查询结果集jdbcUtils.query(Tool.getSql("can_dialTest_ar"))
     *
     * @param sql 查询SQL
     * @return List
     */
    public List<Map<String, Object>> queryMappings(String sql) {
        return queryMappings(sql, null);
    }

    /**
     * 查询结果集jdbcUtils.query(Tool.getSql("can_dialTest_ar"))
     *
     * @param sql    查询SQL
     * @param params 占位参数
     * @return List
     */
    public List<Map<String, Object>> queryMappings(String sql, Object[] params) {
        try {
            Pair<String, Object[]> pair = SqlUtil.processInConditions(sql, params);
            pair = SqlUtil.processNullWhereParam(pair);
            return jdbcTemplate.query(pair.getLeft(), pair.getRight(), new ColumnMapRowMapper());
        } catch (DataAccessException e) {
            log.error("query failed, Message:{}", e.getMessage());
        }
        return Collections.emptyList();
    }

    /**
     * 查询单条记录
     *
     * @param sql    查询SQL
     * @param params 占位参数(可以为空)
     * @return Map < String, Object>
     */
    public Map<String, Object> load(String sql, Object[] params) {
        try {
            Pair<String, Object[]> pair = SqlUtil.processInConditions(sql, params);
            pair = SqlUtil.processNullWhereParam(pair);
            return jdbcTemplate.queryForObject(pair.getLeft(), pair.getRight(), new ColumnMapRowMapper());
        } catch (DataAccessException e) {
            log.error("load failed,  Message:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 增删改
     *
     * @param sql    SQL语句
     * @param params 参数列表
     */
    public void executeUpdate(String sql, Object[] params) {
        try {
            Pair<String, Object[]> pair = SqlUtil.processInConditions(sql, params);
            log.info("getLeft {}", pair.getLeft());
            log.info("getRight {}", pair.getRight());
            pair = SqlUtil.processNullWhereParam(pair);
            jdbcTemplate.update(pair.getLeft(), pair.getRight());
        } catch (DataAccessException e) {
            log.error("executeUpdate failed,  Message:{}", e.getMessage());
        }
    }

    /**
     * 增删改
     *
     * @param sql SQL语句
     */
    public void executeUpdate(String sql) {
        executeUpdate(sql, null);
    }

    /**
     * 查询单个值
     *
     * @param sql   查询SQL
     * @param args  查询入参
     * @param typeClass bean类型
     * @param <T>       T
     * @return T
     */
    public <T> T queryBean(String sql, Object[] args, Class<T> typeClass) {
        return jdbcTemplate.queryForObject(sql, args, typeClass);
    }
}