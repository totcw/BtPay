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
            if (BtPay.mBtPayCallBack != null) {
                BtPay.mBtPayCallBack.done(new BtPayResult(BtPayResult.RESULT_FAIL, BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE, BtPayResult.FAIL_ERR_FROM_CHANNEL,
                        "获取银联支付流水号失败"));
            }
            finish();
        }
    }

    /**
     * 处理银联手机支付控件返回的支付结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String result = null;
        int errCode = -99;
        String errMsg = null;
        String detailInfo = "银联支付:";

        //银联插件内部升级会出现data为null的情况
        if (data == null) {
            result = BtPayResult.RESULT_FAIL;
            errCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
            errMsg = BtPayResult.FAIL_ERR_FROM_CHANNEL;
            detailInfo += "invalid pay result";
        } else {
            /*
             * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
             */
            String str = data.getExtras().getString("pay_result");

            if (str == null) {
                result = BtPayResult.RESULT_FAIL;
                errCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
                errMsg = BtPayResult.FAIL_ERR_FROM_CHANNEL;
                detailInfo += "invalid pay result";
            } else if (str.equalsIgnoreCase("success")) {
                result = BtPayResult.RESULT_SUCCESS;
                errCode = BtPayResult.APP_PAY_SUCC_CODE;
                errMsg = BtPayResult.RESULT_SUCCESS;
                detailInfo += "支付成功！";
            } else if (str.equalsIgnoreCase("fail")) {
                result = BtPayResult.RESULT_FAIL;
                errCode = BtPayResult.APP_INTERNAL_THIRD_CHANNEL_ERR_CODE;
                errMsg = BtPayResult.RESULT_FAIL;
                detailInfo += "支付失败！";
            } else if (str.equalsIgnoreCase("cancel")) {
                result = BtPayResult.RESULT_CANCEL;
                errCode = BtPayResult.APP_PAY_CANCEL_CODE;
                errMsg = BtPayResult.RESULT_CANCEL;
                detailInfo += "用户取消了支付";
            }
        }

        if (BtPay.mBtPayCallBack != null) {
            BtPay.mBtPayCallBack.done(new BtPayResult(result, errCode, errMsg,
                    detailInfo));
        }

        this.finish();
    }

}
