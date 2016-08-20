package com.example.lxchild.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.lxchild.datamanager.ParseHtml;
import com.example.lxchild.netmanager.NetManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LXChild on 2015/1/21.
 */
public class MyAsyncTask extends AsyncTask<String, Void, String>{

    private String TAG = "MyAsyncTask";
    private List<NameValuePair> list;
    private TextView tv;

    public MyAsyncTask(Map<String, String> map, TextView tv) {
        list = new ArrayList<>();
        for(String key : map.keySet()) {
            list.add(new BasicNameValuePair(key, map.get(key)));
        }

        this.tv = tv;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        HttpPost post = new HttpPost(urlString);
        post.setHeader("Cookie", "JSESSIONID=" + NetManager.COOKIE);

        try {
            post.setEntity(new UrlEncodedFormEntity(list, "GBK"));
            HttpResponse response = new DefaultHttpClient().execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                return readFromStream(response.getEntity().getContent());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(tv != null) {
            List<Map<String, String>> scheduleList = ParseHtml.getScheduleList(s);
            StringBuilder sb = toShow(scheduleList);
            tv.setText(sb.toString());
        } else {
            Log.d(TAG, s);
        }
        super.onPostExecute(s);
    }

    private String readFromStream(InputStream inputStream)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        String data;
        while ((data = br.readLine()) != null) {
            sb.append(data);
        }
        return sb.toString();
    }

    private StringBuilder toShow(List<Map<String, String>> scheduleList){
        StringBuilder sb = new StringBuilder();
        for(Map<String,String> map:scheduleList){
            for(String key : map.keySet()) {
                if(!"".equals(map.get(key))){
                    sb.append(key).append("----").append(map.get(key)).append("\n");
                }
            }
            sb.append("\n============================\n");
        }
        return sb;
    }
}
