package edu.bjtu.lab1_iot;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;

import android.hardware.SensorManager;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SensorReader extends AppCompatActivity {

    private SensorManagerSimulator mSensorManager;

    private TextView temperatureValueText;
    private TextView pressureValueText;
    private TextView lightValueText;

    private SensorEventListener temperatureEventListener;
    private SensorEventListener pressureEventListener;
    private SensorEventListener lightEventListener;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reader);

        temperatureValueText = (TextView) findViewById(R.id.temperature_value);
        pressureValueText = (TextView) findViewById(R.id.pressure_value);
        lightValueText = (TextView) findViewById(R.id.light_value);

    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager = SensorManagerSimulator.getSystemService(this,
                SENSOR_SERVICE);

        // 5) Connect to the sensor simulator, using the settings
        // that have been set previously with SensorSimulatorSettings
        mSensorManager.connectSimulator();

        // The rest of your application can stay unmodified.
        // //////////////////////////////////////////////////////////////

        initListeners();


    }



    private void initListeners() {





        temperatureEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                temperatureValueText.setText(values[0] + " °C");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        lightEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                lightValueText.setText(values[0] + " lx");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        pressureEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                pressureValueText.setText(values[0] + " Pa");
                
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();



        mSensorManager.registerListener(temperatureEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(lightEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(pressureEventListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(temperatureEventListener);
        mSensorManager.unregisterListener(pressureEventListener);
        mSensorManager.unregisterListener(lightEventListener);

        super.onStop();
    }
}
