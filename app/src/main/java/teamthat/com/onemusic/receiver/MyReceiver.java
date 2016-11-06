package teamthat.com.onemusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import teamthat.com.onemusic.activity.PlayerActivity;

public class MyReceiver extends BroadcastReceiver {
    PlayerActivity playerActivity;
    final int HOUR = 60*60*1000;
    final int MINUTE = 60*1000;
    final int SECOND = 1000;

    public MyReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        playerActivity = new PlayerActivity();
        final long duration = intent.getLongExtra("duration",0);
   final   long hour = duration/HOUR;
        final  long _hour = duration%HOUR;
        final   long minute = _hour/MINUTE;
        final    long _minute = _hour%MINUTE;
        final   long second = _minute/SECOND;
       // playerActivity.setMaxSeekBar((int)duration);
        if(hour==0){
            playerActivity.setDuration(minute+":"+second);
        }else{
            playerActivity.setDuration(hour+":"+minute+":"+second);
        }




    }

}
