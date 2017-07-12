package com.betterda.javabean;

import com.betterda.callback.BtResult;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/5/24
 * 功能说明：支付结果返回类
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class BtPayResult implements BtResult{
    //result包含支付成功、取消支付、支付失败
    private String result;
    //针对支付失败的情况，提供失败代码
    private Integer errCode;
    //针对支付失败的情况，提供失败原因
    private String errMsg;
    //提供详细的支付信息，比如原生的支付宝返回信息
    private String detailInfo;

    private Throwable mThrowable;
    //成功的代码
    public static final int APP_PAY_SUCC_CODE = 0;
    //取消的代码
    public static final int APP_PAY_CANCEL_CODE = -1;
    //参数错误
    public static final int APP_INTERNAL_PARAMS_ERR_CODE = -10;
    //三方错误导致取消支付的代码
    public static final int APP_INTERNAL_THIRD_CHANNEL_ERR_CODE = -12;


    /**
     * 表示支付成功
     */
    public static final String RESULT_SUCCESS = "SUCCESS";

    /**
     * 表示用户取消支付
     */
    public static final String RESULT_CANCEL = "CANCEL";

    /**
     * 表示支付失败
     */
    public static final String RESULT_FAIL = "FAIL";


    /**
     * 参数不合法造成的支付失败
     */
    public static final String FAIL_INVALID_PARAMS = "FAIL_INVALID_PARAMS";

    /**
     * 从第三方app支付渠道返回的错误信息
     */
    public static final String FAIL_ERR_FROM_CHANNEL = "FAIL_ERR_FROM_CHANNEL";




    public BtPayResult(String result, Integer errCode, String errMsg, String detailInfo) {
        this.result = result;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.detailInfo = detailInfo;
    }

    public BtPayResult(String result, Integer errCode, String errMsg, String detailInfo, Throwable throwable) {
        this.result = result;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.detailInfo = detailInfo;
        mThrowable = throwable;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "BtPayResult{" +
                "result='" + result + '\'' +
                ", errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                ", detailInfo='" + detailInfo + '\'' +
                '}';
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public void setThrowable(Throwable throwable) {
        mThrowable = throwable;
    }
}
