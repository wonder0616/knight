package com.knight.exception;

/**
 * 功能描述
 *
 * @author knight
 * @since 2021-07-02
 */
public class ParamInvalidException extends RuntimeException {
    /**
     * 构造函数
     */
    public ParamInvalidException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public ParamInvalidException(String message) {
        super(message);
    }
}
