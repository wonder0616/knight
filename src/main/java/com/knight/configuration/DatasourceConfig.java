package com.knight.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.knight.util.SubstitutorUtils.replacePlaceHolder;

import javax.sql.DataSource;

/**
 * 系统管理数据源
 *
 * @author knighht
 * @since 2020-10-17
 */
@Configuration
@Slf4j
public class DatasourceConfig {
    private static String clickHouseDriver;

    private static String clickhouseAddress;

    private static String clickhouseUsername;

    private static String clickhousePassword;

    @Value("${spring.datasource.clickhouse.address}")
    public void setClickHouseDriver(String driver) { DatasourceConfig.clickHouseDriver = driver;
    }

    @Value("${spring.datasource.clickhouse.address}")
    public void setClickhouseAddress(String address) { DatasourceConfig.clickhouseAddress = address;
    }

    @Value("${spring.datasource.clickhouse.username}")
    public void setClickhouseUsername(String username) {
        DatasourceConfig.clickhouseUsername = username;
    }

    @Value("${spring.datasource.clickhouse.password}")
    public void setClickhousePassword(String password) {
        DatasourceConfig.clickhousePassword = password;
    }

    /**
     * 自定义数据源，解密配置密码
     *
     * @return 数据源
     */
    @Bean("clickHouseDatasource")
    public DataSource getClickHouseDataSource() {
        DataSourceBuilder<DruidDataSource> builder = DataSourceBuilder.create().type(DruidDataSource.class);
        log.debug("clickHouseUrl:{}, clickHouseDriver:{}, clickHouseUsername:{}", clickhouseAddress, clickHouseDriver,
                clickhouseUsername);
        builder.url(replacePlaceHolder(clickhouseAddress))
                .driverClassName(replacePlaceHolder(clickHouseDriver))
                .username(replacePlaceHolder(clickhouseUsername))
                .password(clickhousePassword);
        DruidDataSource dataSource = builder.build();
        dataSource.setTestWhileIdle(false);
        return dataSource;
    }
}