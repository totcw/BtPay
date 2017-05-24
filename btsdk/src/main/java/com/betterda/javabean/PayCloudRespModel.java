package com.betterda.javabean;

import java.util.Map;

/**
 * @版权：版权所有 (c) 2017
 * @author:Ivan
 * @version Revision 1.0.0
 * @email:451615966@qq.com
 * @see:
 * @创建日期：2017-4-20
 * @功能说明：支付云平台结果反馈对象
 * @begin
 * @修改记录:
 * @修改后版本          修改人      	修改内容
 * @2017-4-20  	         Ivan        	创建
 * @end
 */
@SuppressWarnings("serial")
public class PayCloudRespModel {
    private boolean success = false;// 是否成功
    private String msg = "";// 提示信息
    private String errorCode;
    private Map<String, String> attributes;// 其他参数
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Map<String, String> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
