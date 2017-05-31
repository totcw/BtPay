package com.betterda.javabean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.betterda.utils.Base64Utils;

import java.util.HashMap;
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


    @Override
    public String toString() {
        return "PayCloudRespModel{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", attributes=" + attributes +
                '}';
    }


    /**
     * 参数校验
     * @param model
     * @param pubKey
     * @return
     */
    private PayCloudRespModel doReqDataValidate(PayCloudReqModel model, String pubKey) {
        PayCloudRespModel jsonMessage = new PayCloudRespModel();
        String appId = model.getAppid();
        String channelType = model.getChanneltype();
        String orderId = model.getOrderId();
        String txnTime = model.getTxnTime();
        if(!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(channelType) && !TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(txnTime)) {
            if(TextUtils.isEmpty(pubKey)) {
                jsonMessage.setMsg("商户公钥 不能为空");
                return jsonMessage;
            } else {
                jsonMessage = this.doExtValidate(model, pubKey);
                if(jsonMessage == null) {
                    jsonMessage = new PayCloudRespModel();
                    jsonMessage.setSuccess(true);
                    return jsonMessage;
                } else {
                    return jsonMessage;
                }
            }
        } else {
            jsonMessage.setMsg("app id|渠道类型|订单号|订单时间 为必填，不能为空");
            return jsonMessage;
        }
    }
    public PayCloudRespModel doExtValidate(PayCloudReqModel model, String pubKey) {
        return null;
    }

    /**
     * 对参数进行rsa和base64加密
     * @param model
     * @param pubKey
     * @return
     */
    protected Map<String, String> doAssembleData(PayCloudReqModel model, String pubKey) {
        HashMap reqData = new HashMap();
        String jsonString = JSON.toJSONString(model);
        String data = "";

        try {
            byte[] e = com.betterda.paycloud.sdk.util.KeyGenerator.encryptByPublicKey(jsonString.getBytes("UTF-8"), pubKey);
            data = Base64Utils.encode(e);
        } catch (Exception var7) {
            System.out.println(var7);
        }

        reqData.put("data", data);
        return reqData;
    }
}
