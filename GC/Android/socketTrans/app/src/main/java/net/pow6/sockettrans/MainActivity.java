package net.pow6.sockettrans;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, SurfaceHolder.Callback {



    SensorManager mSensorManager;
    Sensor mAccSensor;
    float mVX;
    float mVY;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if(sensors.size() > 0){
            Sensor s = sensors.get(0);
            mSensorManager.registerListener(this,s,SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void connect(final String str){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids){
                try{
                    Socket socket = new Socket("192.168.1.6", 5000);
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
        if(event.sensor.getType()== Sensor.TYPE_GYROSCOPE){

            float y = event.values[1];
            float x = event.values[2];

            mVX = mVX + x;
            mVY = mVY + y;

            String str = "X:" + mVX + "\n" + "Y:" + mVY + "\n";
            connect(str);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        registerSensor();
    }

    public void registerSensor(){
        mSensorManager.registerListener(this,mAccSensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format, int width,int height){
        mVX = 0;
        mVY = 0;
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
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
        //もとに戻れるようにfinish()はしない
        switch (item.getItemId()){
            //メニュー【設定】を押したとき
            case R.id.item_setting:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

