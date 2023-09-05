package com.zet.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author knight
 */
@Data
@Component
@PropertySource(value = "classpath:ding-pan-groups.yaml",factory = YmlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "ding-pan-groups")
public class DingPanConfig {
    private List<Map<String,String>> groupConstants;

    @Data
    public  class GroupConstant {
        private String groupTAG;
        private String corpId;
        private String APP_KEY;
        private String APP_SECRET;
    }

}
