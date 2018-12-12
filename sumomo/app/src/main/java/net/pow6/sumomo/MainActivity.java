package net.pow6.sumomo;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private List<String> text;
    private TextView textView;
    private Toast toast;
    TextToSpeech textToSpeech;
    String contents;

    // リストtextの内容をつなげる
// API26以降なら結合処理は String.join("",text)でよいがAPIが古いとできない
    private String getJoinText(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String string:text){    //拡張for文
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    // トーストを使いメッセージを表示する
    private void showToast(String message){
        if(toast !=null) {
            toast.cancel(); // トーストを連続で表示するために表示したトーストを一度消す
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    //音声でトースト内容を読み上げする
    private void speechText() {
        if(0 < contents.length()){
            if(textToSpeech.isSpeaking() ||true){
                textToSpeech.stop();
            }
            textToSpeech.speak(contents, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    // textViewの内容を更新する
    private void textUpdate(){
        StringBuilder stringBuilder = new StringBuilder();
        for(String string:text){
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }
        textView.setText(stringBuilder);
    }

    public void onClickSu(View v){
        // 【加筆部分】リストに「す」を追加しtextViewを更新し、現在のリストの内容のトーストを表示する
        text.add("す");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }

    public void onClickMo(View v){
        // 【加筆部分】リストに「も」を追加しtextViewを更新し、現在のリストの内容のトーストを表示する
        text.add("も");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }

    public void onClickNouti(View v){
        // 【加筆部分】リストに「のうち」を追加しtextViewを更新し、現在のリストの内容のトーストを表示する
        text.add("のうち");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }

    public void onClickReset(View v){
        text.clear();
        textUpdate();
        showToast("リセットしました");
        contents="リセットですううううううううううううううううううううううう";
        speechText();
    }

    // 文章があっていれば正解、違えば不正解と表示
    public void onClickEnter(View v) {
        if (text!=null&&getJoinText().equals("すもももももももものうち")) {
            showToast("正解！");
            contents="せいかい！せいかい！やったね！";
        } else {
            showToast("不正解！");
            contents="ふせいかい！でなおしてこい！";
        }
        speechText();
    }

    public void onClickAtt(View v){
        text.add("あっ！");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }
    public void onClickSuumo(View v){
        text.add("スーモ");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }
    public void onClickYokobo(View v){
        text.add("ー");
        textUpdate();
        showToast(getJoinText());
        contents=getJoinText();
        speechText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        text =new ArrayList<>();
        textUpdate();
        textToSpeech = new TextToSpeech(this, (TextToSpeech.OnInitListener) this);
    }

    @Override   //TextToSpeech を使えるかどうか
    public void onInit(int status){ //TextToSpeech.OnInitListenerをimplementsして使う
        if(status == TextToSpeech.SUCCESS){
            //読み上げ可能
            //言語設定などをしておかないと、読み上げが間に合わない
            int result = textToSpeech.setLanguage(Locale.JAPAN);
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                showToast("日本語TTSに対応していません");
            }
        }else{
            showToast("読み上げ機能が使えません");
        }
    }

    @Override
    public void onDestroy(){
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}