package com.fanwe.www.http.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fanwe.lib.http.impl.httprequest.PostRequest;
import com.fanwe.www.http.R;
import com.fanwe.www.http.model.InitActModel;
import com.google.gson.Gson;
import com.sd.lib.http.IRequest;
import com.sd.lib.http.IResponse;
import com.sd.lib.http.RequestManager;
import com.sd.lib.http.callback.ModelRequestCallback;

/**
 * 异步请求demo
 */
public class AsyncRequestActivity extends AppCompatActivity
{
    public static final String TAG = AsyncRequestActivity.class.getSimpleName();

    public static final String URL = "http://ilvbt3.fanwe.net/mapi/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_request);
    }

    public void onClickRequest(View view)
    {
        IRequest request = new PostRequest();
        request.setBaseUrl(URL); //设置请求地址
        request.getParams().put("ctl", "app").put("act", "init"); //设置请求参数
        request.setTag(TAG); //设置该请求的tag，可用于取消请求

        request.execute(mModelRequestCallback_0);
//        request.execute(RequestCallbackProxy.get(mModelRequestCallback_0, mModelRequestCallback_1)); //设置多个回调
    }

    private ModelRequestCallback mModelRequestCallback_0 = new ModelRequestCallback<InitActModel>()
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
        protected InitActModel parseToModel(String content, Class<InitActModel> clazz)
        {
            return new Gson().fromJson(content, clazz);
        }

        @Override
        public void onSuccess()
        {
            IResponse response = getResponse(); //获得返回结果对象
            InitActModel model = getActModel(); // 获得接口对应的实体
            Log.i(TAG, "onSuccess_0:" + model.getCity());
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

    private ModelRequestCallback mModelRequestCallback_1 = new ModelRequestCallback<InitActModel>()
    {
        @Override
        public void onPrepare(IRequest request)
        {
            super.onPrepare(request);
            Log.i(TAG, "onPrepare_1");
        }

        @Override
        public void onStart()
        {
            super.onStart();
            Log.i(TAG, "onStart_1");
        }

        @Override
        public void onSuccessBackground() throws Exception
        {
            super.onSuccessBackground();
            Log.i(TAG, "onSuccessBackground_1");
        }

        @Override
        protected InitActModel parseToModel(String content, Class<InitActModel> clazz)
        {
            return new Gson().fromJson(content, clazz);
        }

        @Override
        public void onSuccess()
        {
            InitActModel model = getActModel();
            Log.i(TAG, "onSuccess_1:" + model.getCity());
        }

        @Override
        public void onError(Exception e)
        {
            super.onError(e);
            Log.i(TAG, "onError_1:" + e);
        }

        @Override
        public void onCancel()
        {
            super.onCancel();
            Log.i(TAG, "onCancel_1");
        }

        @Override
        public void onFinish()
        {
            super.onFinish();
            Log.i(TAG, "onFinish_1");
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
        Log.i(TAG, "onDestroy");
    }
}
