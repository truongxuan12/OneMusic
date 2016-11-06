package teamthat.com.onemusic.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import teamthat.com.onemusic.activity.LoginActivity;
import teamthat.com.onemusic.activity.PlayerActivity;

public class BoundService extends Service {
    BroadcastReceiver pause=null;

    BroadcastReceiver replay=null;
    BroadcastReceiver udseekbar=null;

    IntentFilter filter=new IntentFilter("pause");
    IntentFilter filter1=new IntentFilter("replay");
    IntentFilter filter2=new IntentFilter("updateSeekbar");

    MediaPlayer media;
    static PlayerActivity playerActivity;
    final int HOUR = 60*60*1000;
    final int MINUTE = 60*1000;
    final int SECOND = 1000;
    CountDownTimer countDownTimer;
    long currenttime;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("mydebug","vao oncreate");
        pause=new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("mydebug","dk pause");
                stop();
            }
        };
        registerReceiver(pause, filter);
        udseekbar = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int time = intent.getIntExtra("pos",0);
                seekTo(time);
                Log.d("mydebug","time "+time);
                Log.d("mydebug","udseekbar"+time);
               // start();
            }
        };
        registerReceiver(udseekbar, filter2);

    }


public void seekTo(int position){
    if(media!=null){
        Log.d("mydebug","seek "+position);
        media.seekTo(position);
    }
}

public long getPosition(){
    if(media!=null){
        try {
            currenttime= media.getCurrentPosition();
            return currenttime;
        }catch(IllegalStateException e){
           // media.reset();
            return 0;
        }
    }
    return 0;
}
    public void setInforMedia(){
        media = new MediaPlayer();
        try {
            media.setDataSource(Constant.path);
            media.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start(){


        if(media!=null){
            Log.d("mydebug","start "+media.getDuration());
            media.start();
        }

    }
    public void stop(){
        if(media!=null){
            Log.d("mydebug","stop");
            media.pause();
           // media.stop();
        }
    }
    public long getDuration(){
        if(media!=null){
            Log.d("mydebug","getDuration");
          return media.getDuration();
        }
        return 0;
    }
    public void replay(){
       // setInforMedia();
     //  seekTo((int)Constant.a);
        Log.d("mydebug","replay");
        start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        replay=new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("mydebug","dk replay");
                replay();
            }
        };

        registerReceiver(replay, filter1);

        playerActivity = new PlayerActivity();
        Bundle bundle = intent.getBundleExtra("bundle");
            String path = bundle.getString("path");
            int pos = bundle.getInt("position");
//        if(pos==0){
//            Constant.a =0;
//        }
        Log.d("mydebug","vao command"+path);

            Constant.path=LoginActivity.LOGIN_API+path;
           setInforMedia();
           final long duration = getDuration();
            Intent intent1 = new Intent().putExtra("duration",duration);
            intent1.setAction("sendMaxTime");

            sendBroadcast(intent1);
//            long position = media.getCurrentPosition();
//            Intent intent2 = new Intent().putExtra("position",position);
//            intent2.setAction("sendPosition");
//
//            sendBroadcast(intent2);

//                seekTo((int)Constant.a);


          start();
//--------------------------------


            //---------------------
         countDownTimer =  new CountDownTimer(duration, 900) {

                public void onTick(long time) {
                   // Log.d("mydebug",duration-media.getCurrentPosition()+"");
                    if(media!=null){
                        long position = getPosition();
                        final long hour = position/HOUR;
                        final long _hour = position%HOUR;
                        final  long minute = _hour/MINUTE;
                        final  long _minute = _hour%MINUTE;
                        final  long second = _minute/SECOND;

                        playerActivity.updateSeekBar((int)position);
                        if(hour==0){
                            playerActivity.setPosition(minute+":"+second);
                            Log.d("mydebug",second+"");

                        }else{
                            playerActivity.setPosition(hour+":"+minute+":"+second);
                        }
                    }

                    Constant.a = getPosition();
                    Log.d("mydebug",getPosition()+" service time");
                    //playerActivity.setCurrentTime(getPosition());

                }

                public void onFinish() {

                }
            };
            countDownTimer.start();










        return START_NOT_STICKY;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        countDownTimer.cancel();
        playerActivity.setCurrentTime(getPosition());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        media.release();
        countDownTimer.cancel();
        playerActivity.setCurrentTime(getPosition());
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");

    }

}
