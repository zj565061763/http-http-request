package com.fanwe.lib.http.impl.httprequest;

import android.text.TextUtils;

import com.fanwe.lib.http.IResponse;
import com.fanwe.lib.http.Request;
import com.fanwe.lib.http.utils.HttpIOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengjun on 2017/10/11.
 */

abstract class HttpRequestImpl extends Request
{
    protected HttpRequest newHttpRequest(String url, String method)
    {
        FHttpRequest request = new FHttpRequest(url, method);
        request.headers(getHeaders());
        request.readTimeout(getReadTimeout());
        request.connectTimeout(getConnectTimeout());
        request.progress(new HttpRequest.UploadProgress()
        {
            @Override
            public void onUpload(long uploaded, long total)
            {
                notifyProgressUpload(uploaded, total);
            }
        });
        return request;
    }

    @Override
    public String toString()
    {
        String url = HttpRequest.append(getUrl(), getParams());
        return url;
    }

    static class Response implements IResponse
    {
        private HttpRequest mHttpRequest;
        private String mBody;

        public Response(HttpRequest httpRequest)
        {
            mHttpRequest = httpRequest;
            mHttpRequest.code();
        }

        @Override
        public synchronized String getBody() throws IOException
        {
            if (TextUtils.isEmpty(mBody))
            {
                try
                {
                    mBody = HttpIOUtil.readString(getInputStream(), getCharset());
                } finally
                {
                    HttpIOUtil.closeQuietly(getInputStream());
                }
            }
            return mBody;
        }

        @Override
        public int getCode()
        {
            return mHttpRequest.code();
        }

        @Override
        public int getContentLength()
        {
            return mHttpRequest.contentLength();
        }

        @Override
        public Map<String, List<String>> getHeaders()
        {
            return mHttpRequest.headers();
        }

        @Override
        public String getCharset()
        {
            return mHttpRequest.charset();
        }

        @Override
        public InputStream getInputStream()
        {
            return mHttpRequest.stream();
        }
    }
}
