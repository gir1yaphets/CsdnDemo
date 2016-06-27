package com.example.pengxiaolve.csdndemo.httpUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pengxiaolve on 16/6/11.
 */
public class HttpConnect {

    public static String doGet(String urlString) {
        StringBuilder sb = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            //连接成功
            if (conn.getResponseCode() == 200) {
                //下载指定页面的内容
                InputStream is = conn.getInputStream();

                sb = new StringBuilder();
                int len;
                byte bytes[] = new byte[1024];
                String line = null;
//                读取的信息有重复tag数据 待解决。。。
//                while ((len = is.read(bytes)) != -1) {
//                    sb.append(new String(bytes, 0, len, "UTF-8"));
//                    sb.append(new String(bytes, 0, len));
//                }
//
//                is.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            }
        } catch (Exception e) {

        }

        return sb.toString();
    }
}
