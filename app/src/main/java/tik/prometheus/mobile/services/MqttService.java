package tik.prometheus.mobile.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class MqttService extends Service {

    String publisherId = UUID.randomUUID().toString();
    MqttClient client;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("----", "start service");
        try {
            client = new MqttClient("tcp://192.168.1.3:1883", publisherId);
            client.connect();
            client.subscribe("newSensor");
            Log.d("", "connected");
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("", "lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.d("", topic);
                    Log.d("", new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("", "complete");
                }
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
