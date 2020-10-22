package com.cc.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class HttpUtils {

    private static HttpUtils httpUtils;
    /**
     * 发送POST请求
     *
     * @param url
     *            目的地址
     * @param par
     *            请求参数，json字符串。
     * @return 远程响应结果
     */
    public static String httpPostWithJSON(String url,String par,String token) throws IOException {

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        if(token != null){
            httpPost.addHeader("token", token);
        }
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
                5000
        ).setConnectTimeout(
                5000
        ).build();
        httpPost.setConfig(requestConfig);
        String respContent = null;
        //解决中文乱码问题
        StringEntity entity = new StringEntity(par,"utf-8");
        System.out.println(entity.toString());
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
//        entity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(entity);

        HttpResponse resp = client.execute(httpPost);
        Integer httpStatusCode = resp.getStatusLine().getStatusCode();
        System.out.println(httpStatusCode);
        if(httpStatusCode == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }

        return respContent;

    }

    public static String httpGet(String url,String token) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        if(token != null){
            httpGet.addHeader("token", token);
        }
        CloseableHttpClient  client = HttpClients.createDefault();
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(
                5000
        ).setConnectTimeout(
                5000
        ).build();
        httpGet.setConfig(requestConfig);
        String respContent = null;
        HttpResponse resp = client.execute(httpGet);
        if(resp.getStatusLine().getStatusCode() == 200) {
            HttpEntity he = resp.getEntity();
            respContent = EntityUtils.toString(he,"UTF-8");
        }
        return respContent;

    }

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) throws Exception {
        if (request == null) {
            throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
        }
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }

        return ipString;
    }

}
