package com.fanwe.lib.http.impl.httprequest;

import com.sd.lib.http.IPostRequest;
import com.sd.lib.http.IResponse;
import com.sd.lib.http.body.FileRequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostRequest extends HttpRequestImpl implements IPostRequest
{
    private List<FileRequestBody> mListFile;

    private List<FileRequestBody> getListFile()
    {
        if (mListFile == null)
            mListFile = new ArrayList<>();
        return mListFile;
    }

    @Override
    public PostRequest addFile(String name, File file)
    {
        addFile(name, null, null, file);
        return this;
    }

    @Override
    public PostRequest addFile(String name, String filename, String contentType, File file)
    {
        final FileRequestBody body = new FileRequestBody(file);
        body.setName(name);
        body.setContentType(contentType);
        getListFile().add(body);
        return this;
    }

    @Override
    protected IResponse doExecute() throws Exception
    {
        final HttpRequest request = newHttpRequest(getUrl(), HttpRequest.METHOD_POST);
        final Map<String, Object> params = getParams().toMap();

        if (mListFile != null && !mListFile.isEmpty())
        {
            for (Map.Entry<String, Object> item : params.entrySet())
            {
                request.part(item.getKey(), String.valueOf(item.getValue()));
            }

            for (FileRequestBody item : mListFile)
            {
                request.part(item.getName(), item.getFilename(), item.getContentType(), item.getFile());
            }
        } else
        {
            request.form(params);
        }

        return new Response(request);
    }
}
