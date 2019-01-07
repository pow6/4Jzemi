package net.pow6.kairo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    //センサ用
    SensorManager mSensorManager;
    Sensor mTempSensor;

    //バッテリ用
    IntentFilter intentfilter;
    Intent batteryStatus;
    float batteryTemperature;
    float ambientTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mTempSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE); //外気温センサ

        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatus = this.registerReceiver(null,intentfilter);

        String str;
        batteryTemperature = (float)1/10*batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
        str = "バッテリー温度：" + String.valueOf(batteryTemperature) + "\n外気温：";
        ((TextView)findViewById(R.id.text_temperature)).setText(str);

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mTempSensor,SensorManager.SENSOR_DELAY_GAME);
    }


    @Override
    public void onSensorChanged(SensorEvent event){
        switch(event.sensor.getType()){
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                ambientTemperature = event.values[0];
                break;
        }
        String str;
        str = ((TextView)findViewById(R.id.text_temperature)).getText().toString() +"\n外気温："+String.valueOf(ambientTemperature);
        ((TextView)findViewById(R.id.text_temperature)).setText(str);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

}
