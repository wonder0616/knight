package com.knight.bean;

import com.knight.constant.ReturnConst;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.List;

/**
 * 功能描述
 *
 * @author knight
 * @since 2021-07-02
 */
@Slf4j
@Data
public class Response<T> {
    /**
     * 默认的成功响应
     */
    public static final Response SUCCESS = new Response();

    private String retCode = ReturnConst.SUCCESS;

    private String retMsg;

    private List<T> dataList;

    private List<T> configurations;

    private T data;

    private Long version;

    private BigInteger total = BigInteger.ZERO;

    /**
     * 构造函数
     */
    public Response() {
    }

    /**
     * 构造函数
     *
     * @param retCode 返回码
     * @param retMsg  返回消息
     */
    public Response(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    /**
     * 错误信息
     *
     * @param errorMsg 错误信息
     */
    public void setErrorMessage(String errorMsg) {
        this.retMsg = errorMsg;
    }
}
