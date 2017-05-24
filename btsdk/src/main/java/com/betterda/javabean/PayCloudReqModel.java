package com.betterda.javabean;

/**
 * 支付云平台请求参数类
 */
public class PayCloudReqModel  {
    private String version;
    private String orderId;
    private String txnAmt;
    private String txnTime;
    private String accNo;
    private String toAccNo;
    private String phoneNo;
    private String identityNo;
    private String realName;
    private String token;
    private String frontUrl;
    private String backUrl;
    private String smsCode;
    private String channeltype;
    private String appid;

    public String getChanneltype() {
        return channeltype;
    }

    public void setChanneltype(String channeltype) {
        this.channeltype = channeltype;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getTxnAmt() {
        return txnAmt;
    }
    
    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }
    
    public String getTxnTime() {
        return txnTime;
    }
    
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    
    public String getAccNo() {
        return accNo;
    }
    
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
    
    public String getToAccNo() {
        return toAccNo;
    }
    
    public void setToAccNo(String toAccNo) {
        this.toAccNo = toAccNo;
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getIdentityNo() {
        return identityNo;
    }
    
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getFrontUrl() {
        return frontUrl;
    }
    
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }
    
    public String getBackUrl() {
        return backUrl;
    }
    
    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
    
}
