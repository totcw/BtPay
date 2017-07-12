/**
 * BCUnionPaymentActivity.java
 *
 * Created by xuanzhui on 2015/7/27.
 * Copyright (c) 2015 BeeCloud. All rights reserved.
 */
package com.betterda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.betterda.javabean.BtPayResult;
import com.unionpay.UPPayAssistEx;


/**
 * 银联支付
 */
public class BtUnionPayActivity extends Activity {

    private String mResult;
    private int mErrCode;
    private String mErrMsg;
    private String mDetailInfo;

    @Override
    public void onStart(){
        super.onStart();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //流水号
            String tn= extras.getString("tn");
            //00 正式环境 01测试环境
            String mode= extras.getString("mode");
            //调用银联api支付
            UPPayAssistEx.startPay(this, null, null, tn, mode);

        } else {

            BtPay.mContextActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (BtPay.mBtPayCallBack != null) {
                        BtPay.mBtPayCallBack.done(new BtPayResult(BtPayResult.RESULT_FAIL, BtPayResult.APP_INTERNAL_PARAMS_ERR_CODE, BtPayResult.FAIL_INVALID_PARAMS,
                                "获取银联支付流水号失败"));
                    }
                }
            });

            finish();
        }
    }

    /**
     * 处理银联手机支付控件返回的支付结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mResult = null;
        mErrCode = -99;
        mErrMsg = null;
        mDetailInfo = "银联支付:";

        //银联插件内部升级会出现data为null的情况
        if (data == null) {
            mResult = BtPayResult.RESULT_FAIL;
            mErrCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
            mErrMsg = BtPayResult.FAIL_ERR_FROM_CHANNEL;
            mDetailInfo += "invalid pay result";
        } else {
            /*
             * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
             */
            String str = data.getExtras().getString("pay_result");

            if (str == null) {
                mResult = BtPayResult.RESULT_FAIL;
                mErrCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
                mErrMsg = BtPayResult.FAIL_ERR_FROM_CHANNEL;
                mDetailInfo += "invalid pay result";
            } else if (str.equalsIgnoreCase("success")) {
                mResult = BtPayResult.RESULT_SUCCESS;
                mErrCode = BtPayResult.APP_PAY_SUCC_CODE;
                mErrMsg = BtPayResult.RESULT_SUCCESS;
                mDetailInfo += "支付成功！";
            } else if (str.equalsIgnoreCase("fail")) {
                mResult = BtPayResult.RESULT_FAIL;
                mErrCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
                mErrMsg = BtPayResult.RESULT_FAIL;
                mDetailInfo += "支付失败！";
            } else if (str.equalsIgnoreCase("cancel")) {
                mResult = BtPayResult.RESULT_CANCEL;
                mErrCode = BtPayResult.APP_PAY_CANCEL_CODE;
                mErrMsg = BtPayResult.RESULT_CANCEL;
                mDetailInfo += "用户取消了支付";
            }
        }


        BtPay.mContextActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (BtPay.mBtPayCallBack != null) {
                    BtPay.mBtPayCallBack.done(new BtPayResult(mResult, mErrCode, mErrMsg,
                            mDetailInfo));
                }
            }
        });

        this.finish();
    }

}
