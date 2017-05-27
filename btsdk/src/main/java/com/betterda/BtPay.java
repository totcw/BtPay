package com.betterda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.betterda.api.Api;
import com.betterda.callback.BtPayCallBack;
import com.betterda.javabean.BtPayResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.utils.KeyGenerator;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.betterda.utils.KeyGenerator.genKeyPair;
import static com.betterda.utils.KeyGenerator.getPrivateKey;
import static com.betterda.utils.KeyGenerator.getPublicKey;

/**
 * Bt支付类
 */
public class BtPay {

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
     * @param context   留存context
     * @return BCPay实例
     */
    public synchronized static BtPay getInstance(Context context) {

        if (instance == null) {
            instance = new BtPay();
            mBtPayCallBack=null;
        }

        if (context != null){
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
    public  void  requestPay(PayCloudReqModel payCloudReqModel, BtPayCallBack btPayCallBack) {
        if (payCloudReqModel == null) {
            return;
        }

        if (btPayCallBack == null) {
            return;
        }
        //设置回调
        this.mBtPayCallBack = btPayCallBack;

        //校验参数  TODO

        //转成json  TODO

        //用rsa加密 TODO

        //往服务器提交数据
        Api.getNetService().getTn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BtPay.mBtPayCallBack != null) {
                            BtPay.mBtPayCallBack.done(new BtPayResult(e.toString(), BtPayResult.APP_INTERNAL_NETWORK_ERR_CODE, BtPayResult.FAIL_NETWORK_ISSUE,
                                    "服务器异常",e));
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        // 根据通道类型 解析自己服务器的数据

                     /*   try {
                            Map<String,Object> keyMap =  KeyGenerator.genKeyPair();
                            byte[] b = new byte[]{1, 0};
                            byte[] bytes = KeyGenerator.encryptByPrivateKey(b, KeyGenerator.getPrivateKey(keyMap));
                            System.out.println(bytes.toString());
                            String st = new String(bytes);
                            System.out.println("st:"+st);
                            byte[] bytes1 = KeyGenerator.decryptByPrivateKey(bytes, KeyGenerator.getPrivateKey(keyMap));
                            System.out.println("st2:"+new String(bytes1));
                            if (s.equals(new String(bytes1))) {
                                System.out.println("1");
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/


                         requestPayForUnion(s);
                    }
                });
    }


    /**
     * 进入银联支付
     * @param s
     */
    private void requestPayForUnion(String s) {
        Intent intent = new Intent(mContextActivity, BtUnionPayActivity.class);
        intent.putExtra("tn",s);
        intent.putExtra("mode","01");
        mContextActivity.startActivity(intent);
    }
}
