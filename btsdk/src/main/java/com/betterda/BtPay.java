package com.betterda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.betterda.api.Api;
import com.betterda.callback.BtPayCallBack;
import com.betterda.javabean.BtPayResult;
import com.betterda.javabean.PayCloudReqModel;
import com.betterda.paycloud.sdk.constant.SDKConstant;
import com.betterda.paycloud.sdk.handler.PayCloudReqService;
import com.betterda.paycloud.sdk.model.PayCloudRespModel;
import com.betterda.paycloud.sdk.util.Base64Util;
import com.betterda.paycloud.sdk.util.HttpClient;
import com.betterda.paycloud.sdk.util.PlatService;
import com.betterda.paycloud.sdk.util.ReqHandlerFactory;
import com.betterda.paycloud.sdk.util.ReqObjectFactory;
import com.betterda.utils.Base64Utils;
import com.betterda.utils.KeyGenerator;

import java.util.HashMap;
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

        try {
          /*  PayCloudRespModel jsonMessage = ReqHandlerFactory.getInstance().createUnionpayAppConsumeHandler().doConsume(ReqObjectFactory.getInstance().createUnionPayAppReqData(APP_ID, "20", "1000", "orderid", "2016-5-7", VERSION), PUB_KEY);
            System.out.println("success:"+jsonMessage.isSuccess());
            System.out.println("msg:"+jsonMessage.getMsg());
            System.out.println("code:"+jsonMessage.getErrorCode());
            System.out.println("attributes:"+jsonMessage.getAttributes());*/

          /*  HttpClient hc = new HttpClient(reqUrl, 30000, 30000);
            try {
                int e = hc.send(doAssembleData(ReqObjectFactory.getInstance().createUnionPayAppReqData(APP_ID, "20", "1000", "orderid", "2016-5-7", VERSION), PUB_KEY), SDKConstant.encoding_UTF8);
                if(200 == e) {
                    String resultString = hc.getResult();
                    if(resultString != null && !"".equals(resultString)) {
                        System.out.println("resut:"+resultString);
                    }
                } else {
                    System.out.println("返回http状态码[" + e + "]，请检查请求报文或者请求地址是否正确");
                }
            } catch (Exception var6) {
                System.out.println("var6:"+var6);
            }*/


            final String reqUrl = "http://192.168.0.122:8080/paycloud-openapi/api/unionpay/app/getform/20/" + APP_ID;
            System.out.println("reqUrl:"+reqUrl);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    PayCloudRespModel jsonMessage = null;
                    try {
                        //时间格式:yyyyMMddHHmmss
                        jsonMessage = ReqHandlerFactory.getInstance().createUnionpayAppConsumeHandler().doConsume(ReqObjectFactory.getInstance().createUnionPayAppReqData(APP_ID, "20", "1000", "orderid", "20170531113027", VERSION), PUB_KEY);
                        System.out.println("success:"+jsonMessage.isSuccess());
                        System.out.println("msg:"+jsonMessage.getMsg());
                        System.out.println("code:"+jsonMessage.getErrorCode());
                        System.out.println("attributes:"+jsonMessage.getAttributes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //往服务器提交数据
       /* Api.getNetService().getTn()
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

                     *//*   try {
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
                        }*//*


                         requestPayForUnion(s);
                    }
                });*/
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
