package com.knight.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 功能描述
 *
 * @author knight
 * @since 2021-10-17
 */
@Configuration
public class JdbcTemplateConfig {

    /**
     * 创建clickHouse数据库的操作模板
     *
     * @param dataSource DataSource
     * @return JdbcTemplate
     */
    @Bean("clickHouseJdbcTemplate")
    public JdbcTemplate getClickHouseJdbcTemplate(@Qualifier("clickHouseDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
