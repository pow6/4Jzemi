package net.pow6.sockettrans;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //wifi on/off の取得
        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        if(wifiManager.isWifiEnabled() == false){
            wifiManager.setWifiEnabled(true);
        }

    }

    //オプションメニューの作成
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return true;
    }


    //メニュー選択時の処理
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //もとに戻れるようにfinish()はしない
        Intent intent;
        switch (item.getItemId()){
            //メニュー【設定】を押したとき
            case R.id.item_setting:
                intent = new Intent(InitialActivity.this,SettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_info:
                intent = new Intent(InitialActivity.this,InfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
