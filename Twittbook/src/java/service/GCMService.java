/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import static bo.Helper.getUser;
import bo.User;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class GCMService {

    private static final String API_KEY = Constants.getAPIKey();
    private static final String GCM_URL = "https://android.googleapis.com/gcm/send";

    public static String sendPushNotice(int rId, int sId, String messageid) {

        User user = getUser(rId);
        if (user.getDeviceid() != null) {
            String url = GCM_URL;

            try {
                HttpClient client = HttpClientBuilder.create().build();

                HttpPost httppost = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList();

                httppost.addHeader("Authorization", "key=" + API_KEY);

                nameValuePairs.add(new BasicNameValuePair("registration_id", user.getDeviceid()));
                nameValuePairs.add(new BasicNameValuePair("data", "Message from " + getUser(sId).getUsername()));
                nameValuePairs.add(new BasicNameValuePair("messageid", messageid));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                System.out.println("Chosen content type: " + entity.getContentType());
                httppost.setEntity(entity);

                HttpResponse response = null;
                try {
                    response = client.execute(httppost);

                    System.out.println("***This is response from google: " + response);
                } catch (IOException ex) {
                    System.out.println("IOEXCEPTION");
                    System.out.println("Exception message: " + ex.getMessage());
                }
                HttpEntity resEntity = response.getEntity();
                return response.toString();
            } catch (UnsupportedEncodingException ex) {
                return "failure";
            }
        }
        return null;
    }

    static String sendPushNotice(String receivername, String sendername, String messageid) {

        User user = getUser(receivername);
        if (user.getDeviceid() != null) {
            String url = GCM_URL;

            try {
                HttpClient client = HttpClientBuilder.create().build();

                HttpPost httppost = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList();

                httppost.addHeader("Authorization", "key=" + API_KEY);

                nameValuePairs.add(new BasicNameValuePair("registration_id", user.getDeviceid()));
                nameValuePairs.add(new BasicNameValuePair("data", "Message from " + sendername));
                nameValuePairs.add(new BasicNameValuePair("messageid", messageid));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                System.out.println("Chosen content type: " + entity.getContentType());
                httppost.setEntity(entity);

                HttpResponse response = null;
                try {
                    response = client.execute(httppost);

                    System.out.println("This is response from google: " + response);
                    HttpEntity resEntity = response.getEntity();
                    return response.toString();
                } catch (IOException ex) {
                    System.out.println("IOEXCEPTION");
                    System.out.println("Exception message: " + ex.getMessage());
                }

            } catch (UnsupportedEncodingException ex) {
                return "failure";
            }
        }
        return null;
    }

}
