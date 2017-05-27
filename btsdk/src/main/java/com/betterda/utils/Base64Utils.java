package com.betterda.utils;

import android.util.Base64;

/**
 * 版权：版权所有 (厦门北特达软件有限公司) 2017
 * author : lyf
 * version : 1.0.0
 * email:totcw@qq.com
 * see:
 * 创建日期： 2017/5/27
 * 功能说明：
 * begin
 * 修改记录:
 * 修改后版本:
 * 修改人:
 * 修改内容:
 * end
 */

public class Base64Utils {

    public static String encode(byte[] privateKey) {

        return new String(Base64.encode(privateKey, Base64.NO_WRAP));

    }

    public static byte[] decode(String privateKey) {
        return Base64.decode(privateKey, Base64.NO_WRAP);
    }

}
