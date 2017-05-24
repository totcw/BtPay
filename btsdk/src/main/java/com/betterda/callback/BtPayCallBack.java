package com.betterda.callback;

/**
 * author : lyf
 * version : 1.0.0
 * 版权：版权所有 (厦门北特达软件有限公司)
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/5/24
 * 功能说明：操作完成后的回调接口
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public interface BtPayCallBack {

    /**
     * 操作完成后的回调接口
     * param result    包含支付或者查询结果信息
     */
    void done(BtResult result);
}
