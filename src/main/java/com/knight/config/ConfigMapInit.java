package com.knight.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * configmap 配置读取到系统属性
 *
 * @author knight
 * @since 2021-10-17
 */
@Slf4j
public class ConfigMapInit {
    private static Set<String> propertyNames = new HashSet<>();

    private static Set<String> needChangeCaches = new HashSet<>();
    static {
        propertyNames.add("PT_DB_IP");
        propertyNames.add("PT_DB_PORT");
        propertyNames.add("DBUS_WEBUI_URL");
        needChangeCaches.add("ACCESS_HOME");
        needChangeCaches.add("IPC_DOWNLOAD_HOME");
    }

    private static void replaceValFromFile(String propertyName) {
        // 获取configmap挂载路径
        String mountPath = System.getenv(propertyName);
        if (StringUtils.isBlank(mountPath)) {
            log.warn("{} mount path is blank", propertyName);
            return;
        }
        try {
            String val = ResourceUtils.readContentFromResource(mountPath);
            log.info("{} value:{}", propertyName, val);
            System.setProperty(propertyName, val);
        } catch (IOException e) {
            log.error("load {} failed:{}", propertyName, e.getMessage());
        }
    }

    private static void changeCacheLocation(String propertyName) {
        String valueInEnv = System.getenv(propertyName);
        if (StringUtils.isBlank(valueInEnv)) {
            log.warn("{} is not exist in env", propertyName);
            return;
        }
        System.setProperty(propertyName, valueInEnv);
    }

    /**
     * 读取configmap值更新到system
     */
    public static void readConfigMaps() {
        propertyNames.stream().forEach(item -> replaceValFromFile(item));
        needChangeCaches.stream().forEach(item -> changeCacheLocation(item));
    }
}
