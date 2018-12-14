package com.test.checkapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    // 問題データ    ////////////////////////
    String[] question = {
            "ガンガン行こうぜ！",
        "慎重に！！",
        "支援は大事だね",
        "元気があれば何でも出来る！",
    };

    // YES選択時のスコア（評価点）
    int[] yes = {1, -1, -1, 1};

    int r = 0;  // 乱数の保管
    int count = 5;  // 問題数
    int score = 0;   // スコアの合計（評価点）

    private boolean mIsClickEvent;

    @Override
    protected void onResume(){
        super.onResume();
        mIsClickEvent = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // 出題   /////////////////////////////
        r = new Random().nextInt(question.length);
        ((TextView) findViewById(R.id.tvQuestion)).setText(question[r]);
    }

    public boolean flagCheck(){
        if(mIsClickEvent)return false;
        mIsClickEvent = true;
        return true;
    }

    // Yesボタン   /////////////////////////////////////
    public void onYes(View v) {
        if(!flagCheck())return;
        count--;
        score += yes[r]; // スコアの加算
        if (count > 0) {
            ((TextView) findViewById(R.id.tvCount)).setText("後" + count + "問");
            // 出題   /////////////////////////////
            r = new Random().nextInt(question.length);
            ((TextView) findViewById(R.id.tvQuestion)).setText(question[r]);
            mIsClickEvent = false;
        } else {
            mIsClickEvent = true;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score" , score);
            startActivity(intent);
            finish();
        }
    }


    // Noボタン   /////////////////////////////////////
    public void onNo(View v) {
        if(!flagCheck())return;
        count--;
        score -= yes[r]; // スコアの減算
        if (count > 0) {
            ((TextView) findViewById(R.id.tvCount)).setText("後" + count + "問");
            // 出題   /////////////////////////////
            r = new Random().nextInt(question.length);
            ((TextView) findViewById(R.id.tvQuestion)).setText(question[r]);
            mIsClickEvent = false;
        } else {
            mIsClickEvent = true;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("score" , score);
            startActivity(intent);
            finish();
        }
    }
}

