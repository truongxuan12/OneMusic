package teamthat.com.onemusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.activity.LoginActivity;
import teamthat.com.onemusic.model.Artist;

/**
 * Created by thietit on 11/1/2016.
 */

public class ArtistBaseAdapter  extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<Artist> arrayArtist;

    public ArtistBaseAdapter(Context context, int layout, List<Artist> videoList){
        myContext = context;
        myLayout = layout;
        arrayArtist = videoList;
    }

    @Override
    public int getCount() {
        return arrayArtist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout, null);

        TextView tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
        tvArtist.setText(arrayArtist.get(position).getName());

        ImageView ivArtist = (ImageView) convertView.findViewById(R.id.iv_artist);
        Picasso.with(myContext).load(LoginActivity.LOGIN_API+arrayArtist.get(position).getImage()).into(ivArtist);

        return convertView;
    }
}