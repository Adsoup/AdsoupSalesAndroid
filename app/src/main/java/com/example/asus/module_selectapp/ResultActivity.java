package com.example.asus.module_selectapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.asus.module_selectapp.MainActivity.list_app_choose;


/**
 * Created by asus on 5/4/2561.
 */

public class ResultActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ListView view = (ListView) findViewById(R.id.app_choose_list);

        ResultAdapter installedAppAdapter = new ResultAdapter(ResultActivity.this,list_app_choose);
        view.setAdapter(installedAppAdapter);
    }
}
