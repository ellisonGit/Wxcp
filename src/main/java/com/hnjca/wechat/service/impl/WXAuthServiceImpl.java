package com.hnjca.wechat.service.impl;

import com.hnjca.wechat.constant.WXConfig;
import com.hnjca.wechat.pojo.WXWebToken;
import com.hnjca.wechat.service.WxAuthService;
import com.hnjca.wechat.util.GsonUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-25
 * Time: 20:39
 * Modified:
 */
@Service
public class WXAuthServiceImpl implements WxAuthService {

    @Override
    public WXWebToken getWXAccessToken(String appId, String secret, String code) {

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;

        try {
            URL localURL = new URL(String.format(WXConfig.getWebTokenUrl, appId, secret, code));
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);

                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                }

                WXWebToken wxAccessToken = (WXWebToken) GsonUtil.jsonToObject(resultBuffer.toString(), WXWebToken.class);
                return wxAccessToken;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
