package com.example.lxchild.netmanager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lxchild.asynctask.MyAsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LXChild on 2015/1/20.
 */
public class NetManager {

    private HttpClient client;
    public static String COOKIE = "";
    private String get_randomcode_url = "http://cityjw.dlut.edu.cn:7001/ACTIONVALIDATERANDOMPICTURE.APPPROCESS";

    public static NetManager netManager = new NetManager();

    private ImageView iv;

    private NetManager(){
        client = new DefaultHttpClient();
    }

    public static NetManager getNetManager() {
        return netManager;
    }

    public void getWebWithPost(String url, Map<String, String> map, TextView tv) {
        MyAsyncTask webTask = new MyAsyncTask(map, tv);
        webTask.execute(url);
    }
/**
 * 获取验证码
 * */
    public void getcode(ImageView iv_in) {
        this.iv = iv_in;
        new AsyncTask<String, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(String... params) {

                String urlString = params[0];

                HttpPost post = new HttpPost(urlString);

                byte[] bytes = new byte[0];
                try {
                    HttpResponse response = client.execute(post);
                    bytes = EntityUtils.toByteArray(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                COOKIE = ((AbstractHttpClient) client).getCookieStore().getCookies().get(0).getValue();
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                iv.setImageBitmap(bitmap);
                super.onPostExecute(bitmap);
            }
        }.execute(get_randomcode_url);
    }
}