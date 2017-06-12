package com.betterda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.betterda.api.Api;
import com.betterda.callback.BtPayCallBack;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.paycloud.sdk.model.PayCloudRespModel;
import com.betterda.paycloud.sdk.util.ReqHandlerFactory;
import com.betterda.paycloud.sdk.util.ReqObjectFactory;
import com.unionpay.UPPayAssistEx;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Bt支付类
 */
public class BtPay {


    public static final String PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHEnS5dLtlXREkSVtUN8OGqcRyU/Qxn46DI18eJ/M4KLowosYQUCj3s06DUfaGDUrZgAv+xl90keLuBsLboxad8Lw1Bf0+I9ILakKs4nlasGNEG2l9jaxeJru5jpV4mTxUl3V2LiokWQAKJpGmupesMqarVk5n4u1lwTe4gOi0wwIDAQAB";
    public static final String APP_ID = "ff8080815b1538be015b153988260001";
    public static final String APP_CODE = "ff8080815b1538be015b153988260002";
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
                    jsonMessage = ReqHandlerFactory.getInstance().createUnionpayAppConsumeHandler().doConsume(ReqObjectFactory.getInstance().createUnionPayAppReqData(APP_ID, "20", "1000", "orderid", "20170531113027", VERSION), PUB_KEY);
                    Toast.makeText(mContextActivity,jsonMessage.getMsg(),0).show();
                    requestPayForUnion("");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void requestPay() {
      /*  Api.getNetService().getTn("827791049000001","100","20170612105107","20170612105107")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("s:"+s);
                    }
                });*/

        requestPayForUnion("872188537906328986905");

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
