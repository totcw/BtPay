package com.betterda.api;


import com.betterda.javabean.PlatOutboundMsg;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author : lyf
 * @version : 1.0.0
 * @版权：版权所有 (厦门北特达软件有限公司) 2017
 * @email:totcw@qq.com
 * @see:
 * @创建日期： 2017/5/24
 * @功能说明： 网络请求接口
 * @begin
 * @修改记录:
 * @修改后版本:
 * @修改人:
 * @修改内容:
 * @end
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("ctrl.do?api/v1/mer/getform")
    Observable<PlatOutboundMsg> getTn(@Field("data") String data,
                                      @Field("appId") String appId);

}
