package com.example.lxchild.httpclientpost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lxchild.netmanager.NetManager;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {

    private EditText et_id, et_pwd, et_cd;
    private TextView tv;
    private ImageView iv;

    private NetManager netManager;

    private String get_login_url = "http://cityjw.dlut.edu.cn:7001/ACTIONLOGON.APPPROCESS";

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initData() {

        netManager = NetManager.getNetManager();
        netManager.getcode(iv);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String code = et_cd.getText().toString().trim();

                Map<String, String> map = new HashMap<>();
                map.put("Agnomen", code);
                map.put("Password", pwd);
                map.put("WebUserNO", id);

                netManager.getWebWithPost(get_login_url, map, null);

                showSchedule();
            }
        });
    }

    private void initView() {
        et_id = (EditText) findViewById(R.id.et_id);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_cd = (EditText) findViewById(R.id.et_cd);
        tv = (TextView) findViewById(R.id.textView);
        iv = (ImageView) findViewById(R.id.iv);
    }

    /**
     * 打开课表界面
     * */
    private void showSchedule() {
        Intent intent = new Intent();
        intent.setClass(this, ScheduleActiviy.class);
        startActivity(intent);

     //   Toast.makeText(getApplicationContext(), "Failed to fetch the web!", Toast.LENGTH_SHORT).show();
    }
}
