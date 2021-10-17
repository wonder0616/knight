package com.knight.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件相关工具类
 *
 * @since 2020-07-16
 */
public abstract class ResourceUtils {
    private static final ResourcePatternResolver RESOLVER = new PathMatchingResourcePatternResolver();

    /**
     * 根据模式查找文件
     *
     * @param locationPattern 文件路径
     * @return {@link Resource}对象列表
     * @throws IOException IO操作异常
     */
    public static List<Resource> locateFile(String locationPattern) throws IOException {
        if (!locationPattern.startsWith("classpath")) {
            locationPattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + locationPattern;
        }
        // 通过模式查找
        List<Resource> list = new ArrayList<>();
        for (Resource resource : RESOLVER.getResources(locationPattern)) {
            if (resource.exists()) {
                list.add(resource);
            }
        }
        return list;
    }

    /**
     * 根据模式查找文件
     *
     * @param locationPattern 文件路径
     * @return InputStream
     * @throws IOException IO操作异常
     */
    public static InputStream getInputStreamFromResource(String locationPattern) throws IOException {
        return RESOLVER.getResource(locationPattern).getInputStream();
    }

    /**
     * 读取文件内容
     *
     * @param locationPattern 文件路径
     * @return 文件内容
     * @throws IOException 异常
     */
    public static String readContentFromResource(String locationPattern) throws IOException {
        if (StringUtils.isEmpty(locationPattern)) {
            return StringUtils.EMPTY;
        }
        if (locationPattern.startsWith("classpath:")) {
            try (InputStream inputStream = ResourceUtils.getInputStreamFromResource(locationPattern);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return br.readLine();
            }
        } else {
            byte[] encoded = Files.readAllBytes(Paths.get(locationPattern));
            return new String(encoded, StandardCharsets.UTF_8);
        }
    }
}
