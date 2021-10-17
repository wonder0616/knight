package com.knight.configuration;

import com.knight.exception.AppInitialException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * sql语句配置
 *
 * @author knight
 * @since 2021-06-23
 */
@Slf4j
@Component
public class SqlConfig {
    private static final String[] SQL_FILES =
        new String[] {"sql.properties", "create_table.properties", "ipc.properties"};

    private static Properties sqlProperties = null;

    static {
        try {
            for (String file : SQL_FILES) {
                if (sqlProperties == null) {
                    sqlProperties = PropertiesLoaderUtils.loadAllProperties("sql" + File.separator + file);
                } else {
                    sqlProperties.putAll(PropertiesLoaderUtils.loadAllProperties("sql" + File.separator + file));
                }
            }
            log.debug("sql load success, keys:[{}]", sqlProperties.stringPropertyNames());
        } catch (IOException e) {
            log.error("sql config load failed:{}", e.getMessage());
            throw new AppInitialException("sql config load failed");
        }
    }

    /**
     * getSql
     *
     * @param key key
     * @return String
     */
    public static String getSql(String key) {
        String sql = sqlProperties.getProperty(key);
        if (StringUtils.isBlank(sql)) {
            log.error("sql key[{}] not found. please check sql.properties");
        }
        return sql;
    }
}
