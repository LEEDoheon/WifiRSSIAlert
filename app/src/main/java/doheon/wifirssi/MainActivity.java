package doheon.wifirssi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvRSSI;
    SoundPool sp;
    int soundID;
    int steamID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundID = sp.load(this,R.raw.alert,1);

        tvRSSI = (TextView) findViewById(R.id.tvRSSI);
        registerReceiver(rssiReceiver, new IntentFilter((WifiManager.RSSI_CHANGED_ACTION)));
    }

    @Override
    public void onDestroy(){
        unregisterReceiver(rssiReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver rssiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifimanger = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifimanger.getConnectionInfo();

            int _rssi = info.getRssi();

            tvRSSI.setText(_rssi+"");
            if(_rssi<-62){
                steamID = sp.play(soundID,1,1,0,-1,1.0f);
            }
            else{
                sp.stop(steamID);
            }

        }
    };
}
