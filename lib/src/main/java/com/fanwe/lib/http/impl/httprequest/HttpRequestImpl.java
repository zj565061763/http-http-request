package com.fanwe.lib.http.impl.httprequest;

import android.text.TextUtils;

import com.sd.lib.http.IResponse;
import com.sd.lib.http.Request;
import com.sd.lib.http.utils.HttpIOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

abstract class HttpRequestImpl extends Request
{
    protected HttpRequest newHttpRequest(String url, String method)
    {
        final FHttpRequest request = new FHttpRequest(url, method);
        request.headers(getHeaders().toMap());
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
        final String url = HttpRequest.append(getUrl(), getParams().toMap());
        return url + " " + super.toString();
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

        @Override
        public synchronized String getAsString() throws IOException
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
    }
}
