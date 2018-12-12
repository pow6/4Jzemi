package com.test.checkapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    String[] result = {
            "魔女タイプ",
            "魔法少女",
            "魔法剣士",
            "剣士タイプ",
            "パワータイプ",
    };

    String[] reTest = {
            "とっても神秘的！！",
            "後方支援が得意です",
            "オールマイティ！！",
            "スタンダードに攻撃だ！",
            "何も気にせずごり押しだ！",
    };

    int[] reImg = {
            R.drawable.p0,
            R.drawable.p1,
            R.drawable.p2,
            R.drawable.p3,
            R.drawable.p4,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // インテントからスコアを取得    /////////////////////
        int score = getIntent().getIntExtra("score" , 0);
        if( score < 0) score = 0;
        if( score >= result.length) score = result.length -1;

        // テキストと画像をセット  //////////////////////////
        ((TextView)findViewById(R.id.tvResult)).setText(result[score]);
        ((TextView)findViewById(R.id.tvReText)).setText(reTest[score]);
        ((ImageView)findViewById(R.id.ivReImg)).setImageResource(reImg[score]);
    }
}
