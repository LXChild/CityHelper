package com.example.lxchild.httpclientpost;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lxchild.netmanager.NetManager;

import java.util.HashMap;
import java.util.Map;

public class ScheduleActiviy extends ActionBarActivity {

    private TextView tv;

    private NetManager netManager;
    private String get_schedule_url = "http://cityjw.dlut.edu.cn:7001/ACTIONQUERYSTUDENTSCHEDULEBYSELF.APPPROCESS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initView();
        initData();
    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("YearTermNO", "15");

        netManager = NetManager.getNetManager();
        netManager.getWebWithPost(get_schedule_url, map, tv);
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv_schedule);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_performance_activiy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
