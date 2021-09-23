package com.knight.base;

public class KnightResponse {
    private Result result;

    /**
     * 设置resultCode参数
     *
     * @param resultCode 返回码
     */
    public void setResultCode(String resultCode) {
        this.result.setRetCode(resultCode);
    }

    /**
     * 设置返回信息
     *
     * @param resultInfo 返回信息
     */
    public void setreulstInfo(String resultInfo) {
        this.result.setRetMsg(resultInfo);
    }

    /**
     * 构造函数
     *
     * @param resultCode 返回码
     * @param resultInfo 返回信息
     */
    public KnightResponse(String resultCode, String resultInfo) {
        this.result = new Result(resultCode, resultInfo);
    }

    @Override
    public java.lang.String toString(){
        final StringBuffer sb = new StringBuffer("KnightResponse");
        sb.append("result=").append(result);
        sb.append('}');
        return sb.toString();
    }

}
