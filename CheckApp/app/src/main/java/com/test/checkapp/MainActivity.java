package com.test.checkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * アプリ起動時に呼び出されるActivity
 * スタートボタンを押すことで、PlayActivityを呼び出し、画面を遷移させる
 * finishしていないので、戻るボタンで、PlayActivityから戻ることも可能
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 画面切り替え   /////////////////////////////////////
    public void onStart(View v){
        Intent intent = new Intent(this,PlayActivity.class);
        startActivity(intent);
    }
}