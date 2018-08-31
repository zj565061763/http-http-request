package com.sd.www.http.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.sd.lib.http.IRequest;
import com.sd.lib.http.IResponse;
import com.sd.lib.http.RequestManager;
import com.sd.lib.http.callback.ModelRequestCallback;
import com.sd.lib.http.impl.httprequest.GetRequest;
import com.sd.www.http.R;
import com.sd.www.http.model.WeatherModel;

/**
 * 异步请求demo
 */
public class AsyncRequestActivity extends AppCompatActivity
{
    public static final String TAG = AsyncRequestActivity.class.getSimpleName();
    public static final String URL = "http://www.weather.com.cn/data/cityinfo/101010100.html";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_request);
    }

    public void onClickRequest(View view)
    {
        IRequest request = new GetRequest();
        request.setBaseUrl(URL); //设置请求地址
        request.getParams().put("aaa", "aaa").put("bbb", "bbb"); //设置请求参数
        request.setTag(TAG); //设置该请求的tag，可用于取消请求

        request.execute(mModelRequestCallback);
    }

    private final ModelRequestCallback mModelRequestCallback = new ModelRequestCallback<WeatherModel>()
    {
        @Override
        public void onPrepare(IRequest request)
        {
            super.onPrepare(request);
            Log.i(TAG, "onPrepare_0");
        }

        @Override
        public void onStart()
        {
            super.onStart();
            Log.i(TAG, "onStart_0");
        }

        @Override
        public void onSuccessBackground() throws Exception
        {
            super.onSuccessBackground();
            Log.i(TAG, "onSuccessBackground_0");
        }

        @Override
        protected WeatherModel parseToModel(String content, Class<WeatherModel> clazz)
        {
            return new Gson().fromJson(content, clazz);
        }

        @Override
        public void onSuccess()
        {
            IResponse response = getResponse(); //获得返回结果对象
            WeatherModel model = getActModel(); // 获得接口对应的实体
            Log.i(TAG, "onSuccess_0:" + model.weatherinfo.city);
        }

        @Override
        public void onError(Exception e)
        {
            super.onError(e);
            Log.i(TAG, "onError_0:" + e);
        }

        @Override
        public void onCancel()
        {
            super.onCancel();
            Log.i(TAG, "onCancel_0");
        }

        @Override
        public void onFinish()
        {
            super.onFinish();
            Log.i(TAG, "onFinish_0");
        }
    };

    public void onClickCancelRequest(View view)
    {
        RequestManager.getInstance().cancelTag(TAG);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        RequestManager.getInstance().cancelTag(TAG);
        Log.i(TAG, "onDestroy");
    }
}
