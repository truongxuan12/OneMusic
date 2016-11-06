package teamthat.com.onemusic.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.activity.AddplaylistActivity;
import teamthat.com.onemusic.activity.PlaylistActivity;
import teamthat.com.onemusic.model.HotSong;

/**
 * Created by Think-PC on 11/5/2016.
 */

public class HotSongAdapter extends ArrayAdapter<HotSong> {

    public HotSongAdapter(Context context, int resource, List<HotSong> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.itemlistsong, null);

        }
        final HotSong p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView tv1 = (TextView) view.findViewById(R.id.tv1);


            tv1.setText(p.name);

        }

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent1 = new Intent(getContext(), PlaylistActivity.class);

                intent1.putExtra("id",(String.valueOf(p.ID)));
                getContext().startActivity(intent1);
                Intent intent = new Intent(getContext(), AddplaylistActivity.class);

                intent.putExtra("id",(String.valueOf(p.ID)));

                getContext().startActivity(intent);
                return false;
            }
        });
        return view;


    }


}