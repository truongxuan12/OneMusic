package teamthat.com.onemusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import teamthat.com.onemusic.R;


public class  EmotionActivity extends AppCompatActivity {

    ImageView ivHappy, ivSad, ivAngel;
    TextView tvSkipEmotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);
        addControls();

        // Happy
        ivHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmotionActivity.this, HappyActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        // Sad
        ivSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmotionActivity.this, SadActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        // Angel
        ivAngel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmotionActivity.this, AngelActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        // Skip
        tvSkipEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    private void addControls() {
        ivHappy = (ImageView) findViewById(R.id.ivHappy);
        ivSad = (ImageView) findViewById(R.id.ivSad);
        ivAngel = (ImageView) findViewById(R.id.ivAngel);
        tvSkipEmotion = (TextView) findViewById(R.id.skipEmotion);
    }


}
