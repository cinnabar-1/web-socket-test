package com.cinnabar.client.common.util;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotNull;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName HttpUtil.java
 * @Description based on java.net.url && javax.net.ssl
 * @createTime 2020-11-20  16:38:00
 */
public class HttpUtilsImpl implements HttpUtils {
    private final String USER_AGENT = "";

    public static void main(String[] args) throws Exception {

        Map map = (Map)JSONObject.parse("{\"projectId\":\"f68df7ca353e4f978ef202d6fe58cbdb\",\"filePath\":\"code\"}");
        System.out.println(map);
        HttpUtilsImpl http = new HttpUtilsImpl();

/*        System.out.println("Testing 1 - Send Http GET request");
        http.sendGet("http://localhost:8080/welcome");

        System.out.println("\nTesting 2 - Send Http POST request");
        JSONObject parm = new JSONObject();
        parm.put("age", 0);
        parm.put("gender", "man");
        parm.put("name", "zhao");
        parm.put("nickName", "");
        parm.put("password", "1");
        parm.put("surname", "zhuzzz");
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "6053b2f2bac106ee06521cec55c67098");
        map.put("Content-Type", "application/json");
        http.sendPost("http://localhost:8080/user/zhuzhao11111?age=11", parm, map);*/
    }

    // HTTP GET请求
    public void sendGet(@NotNull String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //默认值GET
        con.setRequestMethod("GET");

        //添加请求头
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        Scanner scanner = new Scanner(con.getInputStream());
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
        JSONObject jsonObject = new JSONObject();

    }

    // HTTP POST请求
    public void sendPost(@NotNull String url, @NotNull JSONObject jsonObject) throws Exception {
        URL obj = new URL(url);
//        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //添加请求头
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", "6053b2f2bac106ee06521cec55c67098");
        //发送Post请求
        con.setDoOutput(true);
        /* 数据输出流允许应用程序以与机器无关方式将Java基本数据类型写到底层输出流。*/
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonObject.toJSONString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Scanner scanner = new Scanner(con.getInputStream());
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
    }

    @Override
    public void sendPost(@NotNull String url, JSONObject jsonObject, Map<String, String> map) throws Exception {
        URL obj = new URL(url);
//        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        //添加请求头
        for (String s : map.keySet()) {
            con.setRequestProperty(s, map.get(s));
        }
        //发送Post请求
        con.setDoOutput(true);
        /* 数据输出流允许应用程序以与机器无关方式将Java基本数据类型写到底层输出流。*/
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonObject.toJSONString());
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        Scanner scanner = new Scanner(con.getInputStream());
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
    }
}
