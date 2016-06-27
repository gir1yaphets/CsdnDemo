package com.example.pengxiaolve.csdndemo.httpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pengxiaolve on 16/6/26.
 */
public class HttpGetConnect {

    /**
     * 根据指定的url通过http的get请求获取inputstream
     * @param url
     * @return
     */
    public static InputStream httpDoGet(String url) {
        InputStream inputStream = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;

        try{
            response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity httpEntity = response.getEntity();
                inputStream = httpEntity.getContent();
            }

        }catch (IOException e) {

        }
        return inputStream;
    }

    /**
     * 将inputStream转换成String
     * @param inputStream
     * @return
     */
    public static String getStringFromInputStream (InputStream inputStream) {
        StringBuilder sb = new StringBuilder();

        if (inputStream != null) {
            byte bytes[] = new byte[1024];
            int len;

            try {
                while ((len = inputStream.read(bytes)) != -1) {
                    sb.append(new String(bytes, 0, len, "UTF-8"));
                }
            }catch (IOException e) {

            }finally {
                try {
                    inputStream.close();
                }catch (IOException e){

                }
            }
        }

        return sb.toString();
    }
}
