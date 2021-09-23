package com.knight.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    /**
     * 错误码
     */
    public String retCode;

    /**
     * 错误信息
     */
    public String retMsg;

    /**
     * 构造函数
     *
     * @param retCode 返回码
     * @param retMsg 返回信息
     */
    public Result(String retCode,String retMsg){
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String toString(){
        final StringBuffer sb  = new StringBuffer("Result{");
        sb.append("retCode'").append(retCode).append('\'');
        sb.append("retMsg'").append(retMsg).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
