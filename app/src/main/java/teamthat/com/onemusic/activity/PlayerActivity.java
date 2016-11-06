package teamthat.com.onemusic.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.Service.BoundService;
import teamthat.com.onemusic.model.ArtistMusic;

/**
 * Created by thietit on 11/2/2016.
 */

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    ImageButton ibPlay, ibPrevious, ibNext, ibFavourite, ibDowload;
    static  SeekBar seekBar;
    static TextView tvMinTime,tvMaxTime;
    ArtistMusic song;
    boolean isPlayed = false;
    String path;
    Intent intent;
    BoundService boundService;
    Bundle bundle;
    int k =0;
    int index;
    long currentTime;
    int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        addCtrols();
        clickImgButton();
        ibPlay.setMaxWidth(16);
        index = ArtistMusicActivity.index;
        max = ArtistMusicActivity.max;
        intent = getIntent();
        int position = intent.getIntExtra("position",0);
        song = ArtistMusicActivity.listMusic.get(position);
        Log.d("mydebug","duong dan bai hat "+song.getMusicPath());
        boundService = new BoundService();
        path = song.getMusicPath();
        intent = new Intent(PlayerActivity.this,BoundService.class);
        bundle = new Bundle();
        bundle.putString("path",path);
        intent.putExtra("bundle",bundle);
        if(isServiceRunning()){
            stopService(intent);
        }
        if(!isPlayed){
            ibPlay.setImageResource(R.drawable.ic_pause);
            bundle.putString("path",path);
            bundle.putInt("position",0);
            intent.putExtra("bundle",bundle);
            startService(intent);
            isPlayed=true;
        }
        setMaxseekbar();
        seekBar.setOnSeekBarChangeListener(this);



    }
    public void setCurrentTime(long time){
        this.currentTime=time;
    }
    public long getCurrentTime(){
        return this.currentTime;
    }
    public void addCtrols() {
        ibPlay = (ImageButton) findViewById(R.id.ib_play);
        ibPrevious = (ImageButton) findViewById(R.id.ib_previous);
        ibNext = (ImageButton) findViewById(R.id.ib_next);
        ibFavourite = (ImageButton) findViewById(R.id.ib_favourite);
        ibDowload = (ImageButton) findViewById(R.id.ib_dowload);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tvMaxTime = (TextView) findViewById(R.id.tv_end);
        tvMinTime = (TextView) findViewById(R.id.tv_start);
    }
    public void setDuration(String duration){
        tvMaxTime.setText(duration);}
    public void setPosition(String time){
        tvMinTime.setText(time);
    }
    public void setMaxSeekBar(int time){
        seekBar.setMax(time);
    }
    public void setMaxseekbar(){
        seekBar.setMax((int)boundService.getDuration());
    }
    public void updateSeekBar(int time){
        seekBar.setProgress(time);
    }

    public void clickImgButton() {
        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlayed){
                    Toast.makeText(getBaseContext(), "Pause", Toast.LENGTH_LONG).show();
                    // ibPlay.setBackgroundResource(R.drawable.ic_play);
                    ibPlay.setImageResource(R.drawable.ic_play);
                    Log.d("mydebug",getCurrentTime()+" time stop");

                    Intent intent1 = new Intent();
                    intent1.setAction("pause");

                    sendBroadcast(intent1);
                    //  stopService(intent);
                    isPlayed=false;
                }else{
                    //  ibPlay.setBackgroundResource(R.drawable.ic_pause);
                    Toast.makeText(getBaseContext(), "Play", Toast.LENGTH_LONG).show();
                    ibPlay.setImageResource(R.drawable.ic_pause);
                    Log.d("mydebug",getCurrentTime()+" time restart");
                    Intent intent1 = new Intent();
                    intent1.setAction("replay");

                    sendBroadcast(intent1);
//                    bundle.putString("path",path);
//                    bundle.putInt("position",1);
//                    intent.putExtra("bundle",bundle);
//                    startService(intent);
                    isPlayed=true;
                }



            }
        });

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Previous", Toast.LENGTH_LONG).show();

                int next;
                if(index==0){
                    k+=1;
                    next = max-k;


                }else{
                    k+=1;
                    next = index-k;
                    if(next ==0){
                        next = max-1;
                        k=0;
                    }
                }
                Log.d("mydebug","index "+index+"max "+max);
                Log.d("mydebug","next "+next);
                ArtistMusic song1 = ArtistMusicActivity.listMusic.get(next);
                String path = song1.getMusicPath();
                Log.d("mydebug","path "+path);
                if(isServiceRunning()){
                    stopService(intent);
                }

                ibPlay.setImageResource(R.drawable.ic_pause);
                bundle.putString("path",path);

                intent.putExtra("bundle",bundle);
                startService(intent);
                isPlayed=true;



            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Next", Toast.LENGTH_LONG).show();
                int next;


                if(index==(max-1)){
                    k+=1;
                    next=k;
                    if(next == (max-1)){
                        k=0;
                        next=k;
                        k+=1;
                    }

                }else{
                    k+=1;
                    next = index+k;

                }
                Log.d("mydebug","index "+index+"max "+max);
                Log.d("mydebug","next "+next);
                ArtistMusic song1 = ArtistMusicActivity.listMusic.get(next);
                String path = song1.getMusicPath();
                Log.d("mydebug","path "+path);
                if(isServiceRunning()){
                    stopService(intent);
                }

                ibPlay.setImageResource(R.drawable.ic_pause);
                bundle.putString("path",path);

                intent.putExtra("bundle",bundle);
                startService(intent);
                isPlayed=true;


            }
        });

        ibFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Favourite", Toast.LENGTH_LONG).show();

            }
        });

        ibDowload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Dowload", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("teamthat.com.onemusic.Service.BoundService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar1, int i, boolean b) {
        if(b) { // Event is triggered only if the seekbar position was modified by the user
            if(seekBar1.equals(seekBar)) {
                //boundService.seekTo(i);
                Log.d("mydebug","seekTo "+i);
                //Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                intent.setAction("updateSeekbar");

                sendBroadcast(intent);



            }
            // updatePosition();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
