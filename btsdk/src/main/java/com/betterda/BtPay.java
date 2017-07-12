package com.betterda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.betterda.callback.BtPayCallBack;
import com.betterda.javabean.BtPayResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.paycloud.sdk.model.PayCloudRespModel;
import com.betterda.paycloud.sdk.util.ReqHandlerFactory;
import com.betterda.paycloud.sdk.util.ReqObjectFactory;

import java.io.ObjectStreamException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Bt支付类
 */
public class BtPay {


    public static final String VERSION = "1.0.1";


    private static final String TAG = "BtPay";

    /**
     * 线程池
     */
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 保留callback实例
     */
    static BtPayCallBack mBtPayCallBack;

    static Activity mContextActivity;


    private static BtPay instance;

    private BtPay() {

    }

    /**
     * 唯一获取BCPay实例的入口
     *
     * @param context 留存context
     * @return BtPay实例
     */
    public synchronized static BtPay getInstance(Context context) {

        if (instance == null){
            synchronized(BtPay.class){
                if(instance == null){
                    instance = new BtPay();
                    mBtPayCallBack = null;
                }
            }
        }

        mContextActivity = (Activity) context;

        return instance;
    }
    //防止反序列化的时候生成新的对象

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    /**
     * 释放BtPay占据的context, callback
     */
    public static void clear() {
        mContextActivity = null;
        mBtPayCallBack = null;

    }

    /**
     * 支付调用总接口
     */
    public void requestPay(final PayCloudReqModel payCloudReqModel, BtPayCallBack btPayCallBack) {

        if (payCloudReqModel == null) {
            return;
        }

        if (btPayCallBack == null) {

            return;
        }
        //设置回调
        this.mBtPayCallBack = btPayCallBack;
        //获取tn
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                PayCloudRespModel jsonMessage ;
                try {

                    jsonMessage = ReqHandlerFactory.getInstance().createUPAppCtrlConsumeHandler().doConsume(ReqObjectFactory.getInstance().createUPAppCtrlReqData(payCloudReqModel.getAppid(), "31",payCloudReqModel.getAccNo(),payCloudReqModel.getTxnAmt(),
                            payCloudReqModel.getOrderId(), payCloudReqModel.getTxnTime(),payCloudReqModel.getBackUrl(), VERSION), payCloudReqModel.getPublicKey(),payCloudReqModel.getServiceUrl());
                    if (jsonMessage != null) {
                        String tn = jsonMessage.getMsg();
                        if (tn != null) {
                            System.out.println("tn:" + tn);
                            requestPayForUnion(tn);
                        } else {
                            mContextActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (BtPay.mBtPayCallBack != null) {
                                        BtPay.mBtPayCallBack.done(new BtPayResult(BtPayResult.RESULT_FAIL, BtPayResult.APP_INTERNAL_PARAMS_ERR_CODE, BtPayResult.FAIL_INVALID_PARAMS,
                                                "获取银联支付流水号失败"));
                                    }
                                }
                            });
                        }

                    }else {
                        mContextActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (BtPay.mBtPayCallBack != null) {
                                    BtPay.mBtPayCallBack.done(new BtPayResult(BtPayResult.RESULT_FAIL, BtPayResult.APP_INTERNAL_PARAMS_ERR_CODE, BtPayResult.FAIL_INVALID_PARAMS,
                                            "获取银联支付流水号失败"));
                                }
                            }
                        });

                    }

                } catch (Exception e) {
                    mContextActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (BtPay.mBtPayCallBack != null) {
                                BtPay.mBtPayCallBack.done(new BtPayResult(BtPayResult.RESULT_FAIL, BtPayResult.APP_INTERNAL_PARAMS_ERR_CODE, BtPayResult.FAIL_INVALID_PARAMS,
                                        "获取银联支付流水号失败"));
                            }
                        }
                    });
                    e.printStackTrace();
                }
            }
        });

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
