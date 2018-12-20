package net.pow6.sockettrans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback , SharedPreferences.OnSharedPreferenceChangeListener {


    //センサ用
    SensorManager mSensorManager;
    Sensor mAccSensor;
    Sensor mGyroSensor;
    float[] acceleration = new float[3];
    float[] gyroscope = new float[3];
    //センサ補正用
    static final double MOVE_SENSIBILITY=100.0;
    static final double MOVE_CORRECTION=2.0;


    //socket通信用
    String host;
    int port;

    //移動量用
    double theta;    //角度
    double dist;        //移動量の大きさ


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Android 端末が 各センサに対応しているかどうかチェックする
        //対応しているセンサをすべて実装する
        //List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        //if(sensors.size() > 0){
        //    Sensor s = sensors.get(0);
        //    mSensorManager.registerListener(this,s,SensorManager.SENSOR_DELAY_UI);
        //}

        //センサが対応していない場合の例外処理を入れようと思ったが、めんどいので後回し
        mSensorManager.registerListener(this,mAccSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mGyroSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    public void onClickReset(View v){
        Arrays.fill(acceleration,0);
        Arrays.fill(gyroscope,0);
        mSensorManager.registerListener(this,mAccSensor,SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this,mGyroSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    public void onClickA(View v){
        String send;
        send = "***********\n";
        connect(send);
    }


    public void connect(final String str){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids){
                try{
                    Socket socket = new Socket(host, port);
                    OutputStream os = socket.getOutputStream();

                    BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(os));

                    bufwriter.write(str);
                    bufwriter.flush();
                    bufwriter.close();
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        switch(event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                for(int i=0;i<3;i++){
                    acceleration[i] = event.values[i];
                }
               break;
            case Sensor.TYPE_GYROSCOPE:
                for(int i=0;i<3;i++){
                    gyroscope[i] += event.values[i];
                }
                break;
        }
        calcMovements();
        String move = "theta: " + theta + "\n" + "dist: " + dist + "\n";
        String str = "X:" + acceleration[0] + "\n" + "Y:" + acceleration[1]+ "\n" + "Z:" + acceleration[2] + "\n"
                + "X:"+ gyroscope[0] + "\nY:" + gyroscope[1] + "\nZ:" + gyroscope[2] + "\n"
                +"\n\n"
                + move;
        connect(move);
        ((TextView)findViewById(R.id.text_acc)).setText(str);

    }

    /*傾きの方向（角度）と，傾きの大きさを計算する
     *横軸：gyroscope の X軸を利用
     *縦軸：acceleration の Y軸を利用
     *値の変化量が異なるので、調整する必要がある
     *値の最大値をpreference で設定可能にし、最大値以上の値は最大値に丸める。
     * preference  で設定する値は、レベル単位にする
     */
    public void calcMovements(){
        double gyroX = gyroscope[0];
        double gyroZ = gyroscope[2];
        theta = Math.atan2(gyroZ,gyroX);        //atan2 は -πから+πを返す
        dist = Math.sqrt(Math.pow(gyroX,2) + Math.pow(gyroZ,2));

        //dist の値が小さい場合、thetaの値が荒ぶるので、調整をする
        if(dist < MOVE_CORRECTION) {
            theta = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        registerSensor();
    }

    //センサーマネージャーを登録する
    public void registerSensor(){
        mSensorManager.registerListener(this,mAccSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format, int width,int height){
        Arrays.fill(acceleration,0);
        Arrays.fill(gyroscope,0);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        mSensorManager.unregisterListener(this);
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
        switch (item.getItemId()){
            //メニュー【設定】を押したとき
            case R.id.item_setting:
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //preference が変更されたときに呼ばれる
        switch (key){
            case "ipAddress_preference":
                host = sharedPreferences.getString("ipAddress_preference","192.168.1.6");
                ((TextView)findViewById(R.id.text_ip)).setText(host);
                break;
            case "portNumber_preferences":
                port = Integer.parseInt(sharedPreferences.getString("portNumber_preference","5000"));
                ((TextView)findViewById(R.id.text_port)).setText(String.valueOf(port));
                break;
            default:
                break;
        }

    }
}

