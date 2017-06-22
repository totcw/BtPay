package com.betterda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.betterda.api.Api;
import com.betterda.callback.BtPayCallBack;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.javabean.PlatOutboundMsg;
import com.betterda.paycloud.sdk.model.PayCloudRespModel;
import com.betterda.paycloud.sdk.util.Base64Util;
import com.betterda.paycloud.sdk.util.KeyGenerator;
import com.betterda.paycloud.sdk.util.ReqHandlerFactory;
import com.betterda.paycloud.sdk.util.ReqObjectFactory;
import com.betterda.utils.GsonTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Bt支付类
 */
public class BtPay {


    public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqQ8mG2VN2rRi5pF4drOi9pB2kdIAiO6YR7LQGDWQkP2DkAI19apajGxDt3q1m2kmWdytX5dmI8AhxEgK+Ak+qoaf7qNv/6NRQUesnJ8kB7sACzEG79CNwxeZy0jLP2E0RC69r/vyyqcD5PwkIuaMNc5KIJhapl0pPmsMZ+F85QIDAQAB";
    public static final String APP_ID = "47cb95e8badd4521b3bd17da1516d5db";
    public static final String APP_CODE = "ac3f8f5b72594fac92f42e62a71f35b8";
    public static final String VERSION = "1.0.1";


    private static final String TAG = "BtPay";

    /**
     * 保留callback实例
     */
    static BtPayCallBack mBtPayCallBack;

    static Activity mContextActivity;

    // IWXAPI 是第三方app和微信通信的openapi接口
    //static IWXAPI wxAPI = null;


    private static BtPay instance;

    private BtPay() {

    }

    /**
     * 唯一获取BCPay实例的入口
     *
     * @param context 留存context
     * @return BCPay实例
     */
    public synchronized static BtPay getInstance(Context context) {

        if (instance == null) {
            instance = new BtPay();
            mBtPayCallBack = null;

        }

        if (context != null) {
            mContextActivity = (Activity) context;
        }

        return instance;
    }


    /**
     * 释放BCPay占据的context, callback
     */
    public static void clear() {
        mContextActivity = null;
        mBtPayCallBack = null;

    }

    /**
     * 支付调用总接口
     */
    public void requestPay(PayCloudReqModel payCloudReqModel, BtPayCallBack btPayCallBack) {


        if (payCloudReqModel == null) {
            return;
        }

        if (btPayCallBack == null) {
            return;
        }
        //设置回调
        this.mBtPayCallBack = btPayCallBack;

        //校验参数  TODO

        //转成json rsa加密 base64 处理 TODO

        new Thread(new Runnable() {
            @Override
            public void run() {
                PayCloudRespModel jsonMessage = null;
                try {
                    //时间格式:yyyyMMddHHmmss
                    String orderId = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
                    String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    jsonMessage = ReqHandlerFactory.getInstance().createUPAppCtrlConsumeHandler().doConsume(ReqObjectFactory.getInstance().createUPAppCtrlReqData(APP_ID, "31", "10", orderId, txnTime,"http://www.baidu.com", VERSION), PUB_KEY);
                    System.out.println("jsonmessage:"+jsonMessage);
                 //   if (jsonMessage != null) {
                        String tn = jsonMessage.getMsg();
                        System.out.println("tn:"+tn);
                        System.out.println("errorCode:"+jsonMessage.getErrorCode());
                       // System.out.println(jsonMessage.getErrorCode());
                  //  }
                    requestPayForUnion(tn);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void requestPay() {
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNN6pMibqG6iZI7+XFzkZZUPPRis8vcLW+mHePfLIeX4/sDjFgGnXs9XueccDcIxUWZBTzffOTfRQALKxNPR7fnPWKjbdyCsgHLfMc6uIgX5GXSFpgNBmhVmhaYAAK5aumXDEscOoD5svdv+14RQA1ZuzuMAyoeWT+uKsgJuUWKQIDAQAB";
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String orderId = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
        String appId = "40289edd58f0d7c40158f0d7c4c50000";
        String appCode = "40289edd58f0d7fe0158f0d7fe000000";
        String txnAmt = "10";
        String backUrl = "http://www.baidu.com";
        Map<String, String> param = new HashMap<String, String>();
        param.put("appId", appId);
        param.put("appCode", appCode);
        param.put("txnAmt", txnAmt);
        param.put("orderId", orderId);
        param.put("txnTime", txnTime);
        param.put("backUrl",backUrl);
        String jsonString = GsonTools.getJsonString(param);
        String data = "";
        try {
            byte[] encodeData = KeyGenerator.encryptByPublicKey(jsonString.getBytes("utf-8"),PUBLIC_KEY);
            data = Base64Util.encode(encodeData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Api.getNetService().getTn(data,appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlatOutboundMsg>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PlatOutboundMsg s) {
                        Map<String, Object> attributes = s.getAttributes();
                        String tn = (String) attributes.get("tn");
                        requestPayForUnion(tn);
                    }
                });

        /*RequestParams params = new RequestParams("http://www.meichebang.com.cn:8999/EffersonPay/unionpay/app/ctrl.do?api/v1/mer/getform");
        params.addBodyParameter("data",data);
        params.addBodyParameter("appId",appId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });*/

        //requestPayForUnion("872188537906328986905");

    }


    /**
     * 进入银联支付
     *
     * @param s
     */
    private void requestPayForUnion(String s) {
        Intent intent = new Intent(mContextActivity, BtUnionPayActivity.class);
        intent.putExtra("tn", s);
        intent.putExtra("mode", "00");
        mContextActivity.startActivity(intent);
    }


}
