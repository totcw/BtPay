package com.betterda;

import android.app.Activity;
import android.content.Context;

import com.betterda.callback.BtPayCallBack;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/5/24
 * 功能说明：支付查询类
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class BtQuery {


    private static final String TAG = "BtQuery";


    private static BtQuery instance;

    private BtQuery() {
    }
    /**
     * 唯一获取BCPay实例的入口
     * @param context   留存context
     * @return BCPay实例
     */
    public synchronized static BtQuery getInstance(Context context) {

        if (instance == null) {
            instance = new BtQuery();

        }


        return instance;
    }



}
