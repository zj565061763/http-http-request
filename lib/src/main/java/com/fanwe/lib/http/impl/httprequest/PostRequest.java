package com.fanwe.lib.http.impl.httprequest;

import com.fanwe.lib.http.IPostRequest;
import com.fanwe.lib.http.IResponse;
import com.fanwe.lib.http.body.FileRequestBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengjun on 2017/10/11.
 */
public class PostRequest extends HttpRequestImpl implements IPostRequest
{
    private List<FileRequestBody> mListFile;

    private List<FileRequestBody> getListFile()
    {
        if (mListFile == null)
        {
            mListFile = new ArrayList<>();
        }
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
        FileRequestBody body = new FileRequestBody();
        body.setName(name);
        body.setFilename(filename);
        body.setContentType(contentType);
        body.setFile(file);

        getListFile().add(body);
        return this;
    }

    @Override
    protected IResponse doExecute() throws Exception
    {
        HttpRequest request = newHttpRequest(getUrl(), HttpRequest.METHOD_POST);
        final Map<String, String> params = getParams().toMap();

        if (mListFile != null && !mListFile.isEmpty())
        {
            for (Map.Entry<String, String> item : params.entrySet())
            {
                request.part(item.getKey(), item.getValue());
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
