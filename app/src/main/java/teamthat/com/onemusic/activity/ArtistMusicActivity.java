package teamthat.com.onemusic.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.model.ArtistMusic;

import static teamthat.com.onemusic.activity.LoginActivity.LOGIN_API;

/**
 * Created by thietit on 11/1/2016.
 */

public class ArtistMusicActivity extends AppCompatActivity {

    public final String GET_MUSIC_ARTIST_API = "http://nghiahoang.net/api/appmusic/?function=songofartist";

    ImageView imgArtist;
    ListView lvMusic;
   static ArrayList<ArtistMusic> listMusic;
    ArrayAdapter adapter;
    static int index;
    static int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_music);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgArtist = (ImageView) findViewById(R.id.iv_artist_music);
        lvMusic = (ListView) findViewById(R.id.lv_artist_music);

        Intent myIntent = getIntent();
        Bundle bundle = myIntent.getBundleExtra("bundle");
        String imgArtist1 = bundle.getString("image");
        String id = bundle.getString("id");
        String name= bundle.getString("name");
        getSupportActionBar().setTitle(name);
        Picasso.with(this).load(LOGIN_API+imgArtist1).into(imgArtist);

        listMusic = new ArrayList<>();
        adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, listMusic);
        lvMusic.setAdapter(adapter);
        adapter.notifyDataSetChanged();

       String url = makeGetSongOfArtist(id);
        Log.d("mydebug",url);
        new getMusicOfArtist().execute(url);

        goToPlayerMusic();
    }

    public String makeGetSongOfArtist(String id){
        StringBuilder builder = new StringBuilder(GET_MUSIC_ARTIST_API);
        builder.append("&artistid=").append(id);

        return builder.toString();
    }
    // Connecto To Server
    public class getMusicOfArtist extends AsyncTask<String,String,String> {
        public void parseJsonResponse(String json){
            try{

                JSONArray array = new JSONArray(json);

                for(int i=0; i< array.length();i++ ){
                    JSONObject cm = array.optJSONObject(i);
                    String nameMusic = cm.optString("name");
                    String pathMusic = cm.optString("musicpath");
                    Log.d("mydebug",nameMusic);
                    ArtistMusic music = new ArtistMusic(nameMusic, pathMusic);
                    listMusic.add(music);

                }
                max = listMusic.size();
                adapter.notifyDataSetChanged();

            }catch (JSONException e){
                e.printStackTrace();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                URL url = new URL(params[0]);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.d("mydebug",forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return forecastJsonStr;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("mydebug",json);
            parseJsonResponse(json);
        }
    }


    public void goToPlayerMusic() {
        lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                index = i;
                intent.putExtra("position",i);
                startActivity(intent);
            }
        });
    }

}
