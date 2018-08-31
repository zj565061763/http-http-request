package com.fanwe.lib.http.impl.httprequest;

import android.text.TextUtils;

import com.sd.lib.http.RequestManager;
import com.sd.lib.http.utils.HttpLog;

import java.net.HttpCookie;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengjun on 2017/10/10.
 */
class FHttpRequest extends HttpRequest
{
    public FHttpRequest(CharSequence url, String method) throws HttpRequestException
    {
        super(url, method);
        loadCookieForRequest();
    }

    public FHttpRequest(URL url, String method) throws HttpRequestException
    {
        super(url, method);
        loadCookieForRequest();
    }

    /**
     * 'Set-Cookie' header name
     */
    public static final String HEADER_SET_COOKIE = "Set-Cookie";
    /**
     * 'Cookie' header name
     */
    public static final String HEADER_COOKIE = "Cookie";

    private int mCode = Integer.MAX_VALUE;

    private List<HttpCookie> getResponseCookie()
    {
        final Map<String, List<String>> headers = headers();
        if (headers == null || headers.isEmpty())
            return null;

        final List<String> listCookie = headers.get(HEADER_SET_COOKIE);
        if (listCookie == null || listCookie.isEmpty())
            return null;

        HttpLog.i("cookie ---------->saveCookieFromResponse " + url() + "\r\n" + TextUtils.join("\r\n", listCookie));
        final List<HttpCookie> listResult = new ArrayList<>();
        for (String item : listCookie)
        {
            listResult.addAll(HttpCookie.parse(item));
        }
        return listResult;
    }

    private void loadCookieForRequest()
    {
        try
        {
            final List<HttpCookie> listCookie = RequestManager.getInstance().getCookieStore().get(url().toURI());

            if (listCookie != null && !listCookie.isEmpty())
            {
                final String cookie = TextUtils.join(";", listCookie);
                header(HEADER_COOKIE, cookie);
                HttpLog.i("cookie loadCookieForRequest " + url() + "\r\n" + cookie);
            }
        } catch (Exception e)
        {
            HttpLog.e("cookie loadCookieForRequest error:" + e);
        }
    }

    private void saveCookieFromResponse()
    {
        try
        {
            final List<HttpCookie> listCookie = getResponseCookie();

            RequestManager.getInstance().getCookieStore().add(url().toURI(), listCookie);
        } catch (Exception e)
        {
            HttpLog.e("cookie saveCookieFromResponse error:" + e);
        }
    }

    //---------- Override start ----------

    @Override
    public int code() throws HttpRequestException
    {
        final int code = super.code();

        if (mCode == Integer.MAX_VALUE)
        {
            mCode = code;
            saveCookieFromResponse();
        }

        return code;
    }

    //---------- Override end ----------
}
