package com.zet.model;


import org.apache.commons.lang3.StringUtils;

/**
 * RPC服务返回结果
 *
 * @author knight
 */
public class RpcLinkServiceResult<T> {

    private int success;

    private String resultCode;

    private String resultMessage;

    private T result;

    public int isSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T data) {
        this.result = data;
    }

    public static <T> RpcLinkServiceResult<T> getSuccessResult(T t) {
        RpcLinkServiceResult<T> result = new RpcLinkServiceResult<>();
        result.setResultCode("0");
        result.setResultMessage("success");
        result.setResult(t);

        return result;
    }

    public static <T> RpcLinkServiceResult<T> getFailureResult(String code, String msg) {
        RpcLinkServiceResult<T> result = new RpcLinkServiceResult<>();
        if (StringUtils.isNotBlank(code)) {
            result.setResultCode(code);
            result.setResultMessage(msg);
        } else {
            result.setResultCode("1");
            result.setResultMessage("失败");
        }

        return result;
    }
}
