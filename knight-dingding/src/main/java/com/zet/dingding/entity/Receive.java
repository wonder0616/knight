package com.zet.dingding.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取datalink的钉钉配置
 * @author knight
 */
@Data
public class Receive implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
     * 描述key
     */
    private String describeKey;

    /**
     * 描述值
     */
    private String describeValue;

    /**
     * 描述key
     */
    private List<Receives> keyValue;
    @Data
    public  class Receives{
        private String key;
        private String value;
    }

}
