package com.cinnabar.client.common.util;

import javax.net.ssl.HttpsURLConnection;
import javax.validation.constraints.NotNull;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.Scanner;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName HttpsUtilsImpl.java
 * @Description
 * @createTime 2020-11-23  10:23:00
 */
public class HttpsUtilsImpl {
    private final String USER_AGENT = "";

    public void sendGet(@NotNull String url) throws Exception {

    }


    public void sendPost(@NotNull String url) throws Exception {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        //添加请求头
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Authorization", "e1df8db31df51e2dd89f683c472313ab");
        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        //发送Post请求
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
        Scanner scanner = new Scanner(con.getInputStream());
        String responseBody = scanner.useDelimiter("\\A").next();
        System.out.println(responseBody);
    }
}
