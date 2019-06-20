package com.hnjca.wechat.util;

import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    public static String httpGetMethod(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient();
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("?");
        params.keySet().stream().forEach(key -> {
            paramStr.append(key + "=" + params.get(key) + "&");
        });
        paramStr.setLength(paramStr.length() - 1);
        Request request = new Request.Builder().url(url + paramStr.toString()).get().build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpPostParams(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient();
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("?");
        params.keySet().stream().forEach(key -> {
            paramStr.append(key + "=" + params.get(key) + "&");
        });
        paramStr.setLength(paramStr.length() - 1);
        System.out.println("测试：" + url + paramStr.toString());
        Request request = new Request.Builder().url(url + paramStr.toString()).post(okhttp3.internal.Util.EMPTY_REQUEST).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpPostStringParamsAndlongParams(String url, Map<String, String> Stringparams, Map<String, Long> Longparams) throws IOException {
        OkHttpClient client = new OkHttpClient();
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("?");
        Stringparams.keySet().stream().forEach(key -> {
            paramStr.append(key + "=" + Stringparams.get(key) + "&");
        });
        Longparams.keySet().stream().forEach(key -> {
            paramStr.append(key + "=" + Stringparams.get(key) + "&");
        });
        paramStr.setLength(paramStr.length() - 1);
        System.out.println("测试：" + url + paramStr.toString());
        Request request = new Request.Builder().url(url + paramStr.toString()).post(okhttp3.internal.Util.EMPTY_REQUEST).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpPostMethod(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        FormBody.Builder formBody = new FormBody.Builder();
        params.keySet().stream().forEach(key -> {
            formBody.add(key, params.get(key));
        });
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpPostMethodWithUtf8(String url, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        FormBody.Builder formBody = new FormBody.Builder();
        params.keySet().stream().forEach(key -> {
            try {
                formBody.add(key, URLEncoder.encode(params.get(key), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Request request = new Request.Builder().url(url).post(formBody.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpPostMethod(String url, Map<String, String> headerMap, Map<String, String> params) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        Headers headers = Headers.of(headerMap);
        params.keySet().stream().forEach(key -> {
            formBody.add(key, params.get(key));
        });
        Request request = new Request.Builder().url(url).headers(headers).post(formBody.build()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static Response httpPostResponse(String url, Map<String, String> headers, String requestBodys) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(headers.get("Content-Type") == null ? "application/json" : headers.get("Content-Type"));
        RequestBody requestBody = RequestBody.create(mediaType, requestBodys);
        Headers headerMap = okhttp3.Headers.of(headers);
        Request request = new Request.Builder().url(url).headers(headerMap).post(requestBody).build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static String httpPostMethod(String url, Map<String, String> headers, String requestBodys) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(headers.get("Content-Type") == null ? "application/json" : headers.get("Content-Type"));
        RequestBody requestBody = RequestBody.create(mediaType, requestBodys);
        Headers headerMap = okhttp3.Headers.of(headers);
        Request request = new Request.Builder().url(url).headers(headerMap).post(requestBody).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String postMethod(String url, Map<String, String> headers, InputStream bodyContent) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(headers.get("Content-Type") == null ? "application/json" : headers.get("Content-Type"));
        int total = (bodyContent.available() / 1024 + 1) * 1024;
        byte b[] = new byte[total];
        byte r[] = new byte[1024];
        int len = 0;
        int temp = 0; // 所有读取的内容都使用temp接收
        while ((temp = bodyContent.read(r)) != -1) {
            // 当没有读取完时，继续读取
            System.arraycopy(r, 0, b, 1024 * len, 1024); len++;
        }
        RequestBody requestBody = RequestBody.create(mediaType, b);
        Headers headerMap = okhttp3.Headers.of(headers);
        Request request = new Request.Builder().url(url).headers(headerMap) .post(requestBody).build();
        Response response = client.newCall(request).execute();
        bodyContent.reset(); return response.body().string();
    }
    public static String clientPostMethod(String url, Map<String, String> headers, InputStream bodyContent) throws IOException {
        HttpPost method = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        headers.keySet().stream().forEach(head -> {
            method.addHeader(new BasicHeader(head, headers.get(head)));
        });
        if (bodyContent != null) {
            method.setEntity(new InputStreamEntity(bodyContent, -1));
        }
        HttpResponse postResponse = httpClient.execute(method);
        return getResponsBodyAsString(postResponse.getEntity().getContent()); }
        public static String clientPostMethod(String url, Map<String, String> headers) throws IOException {
        HttpPost method = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();
        headers.keySet().stream().forEach(head -> {
            method.addHeader(new BasicHeader(head, headers.get(head)));
        });
        HttpResponse postResponse = httpClient.execute(method);
        return getResponsBodyAsString(postResponse.getEntity().getContent());
    } private static String getResponsBodyAsString(InputStream input) throws IOException {
        String responsBodyString = null;
        try {
            Reader reader = new InputStreamReader(input, "utf-8");
            StringBuilder b = new StringBuilder(); char[] c = new char[1024];
            int len;
            while (0 < (len = reader.read(c))) {
                b.append(c, 0, len);
            } responsBodyString = b.toString();
        } finally { input.close();
        }
        return responsBodyString;
    }
}
