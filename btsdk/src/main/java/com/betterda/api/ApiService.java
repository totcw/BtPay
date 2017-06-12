package com.betterda.api;



import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    @POST("form05_6_2_AppConsume")
    Observable<String> getTn(@Field("merId") String merId,
                             @Field("txnAmt") String txnAmt,
                             @Field("orderId") String orderId,
                             @Field("txnTime") String txnTime);

}
