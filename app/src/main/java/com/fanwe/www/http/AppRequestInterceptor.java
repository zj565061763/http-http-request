package com.fanwe.www.http;

import android.util.Log;

import com.fanwe.lib.http.IRequest;
import com.fanwe.lib.http.IResponse;
import com.fanwe.lib.http.interceptor.IRequestInterceptor;

/**
 * http请求拦截
 */
public class AppRequestInterceptor implements IRequestInterceptor
{
    public static final String TAG = "AppRequestInterceptor";

    @Override
    public void beforeExecute(IRequest request)
    {
        //请求发起之前回调
        Log.i(TAG, "beforeExecute:" + request);
    }

    @Override
    public void afterExecute(IRequest request, IResponse response)
    {
        //请求发起之后回调
        Log.i(TAG, "afterExecute:" + request);
    }
}
