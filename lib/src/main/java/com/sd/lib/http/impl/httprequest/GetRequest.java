package com.sd.lib.http.impl.httprequest;

import com.sd.lib.http.IGetRequest;
import com.sd.lib.http.IResponse;

public class GetRequest extends HttpRequestImpl implements IGetRequest
{
    @Override
    protected IResponse doExecute() throws Exception
    {
        HttpRequest request = newHttpRequest(HttpRequest.append(getUrl(), getParams().toMap()), HttpRequest.METHOD_GET);
        return new Response(request);
    }
}
