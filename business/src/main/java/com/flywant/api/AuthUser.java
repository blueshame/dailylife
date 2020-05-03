package com.flywant.api;

import android.util.Log;

import com.flywant.base.WebInfo;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class AuthUser {
    private static final String TAG = "AuthUser";
    private static final String URL_LOGIN = WebInfo.SERVER_ADDR + "/login";

    public class LoginResult {
        @SerializedName("code")
        private int code;

        @SerializedName("message")
        private String message;

        public LoginResult(int code, String message) {
            this.code = code;
            this.message = message;
        }
        public int getCode() {
            return this.code;
        }
        public String getMessage() {
            return this.message;
        }
    }

    public interface ICallback {
        void OnLoginSuccess(LoginResult loginResult);
        void OnLoginFail(LoginResult loginResult);
    }

    public void login(String username, String password, ICallback callback) {
        try {
            Log.e(TAG, "login");
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL_LOGIN);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair("password", password));
            httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");
                if (callback != null) {
                    Gson gson = new Gson();
                    LoginResult loginResult = gson.fromJson(response, LoginResult.class);
                    callback.OnLoginSuccess(loginResult);
                }
                Log.e(TAG, response);
            } else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity, "utf-8");
                if (callback != null) {
                    Gson gson = new Gson();
                    LoginResult loginResult = gson.fromJson(response, LoginResult.class);
                    callback.OnLoginFail(loginResult);
                }
                Log.e(TAG, response);
            } else {
                if (callback != null) {
                    LoginResult loginResult = new LoginResult(-1, "fail");
                    callback.OnLoginFail(loginResult);
                }
                Log.e(TAG, "error");
            }
            Log.e(TAG, "login successful");
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
