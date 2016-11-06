package teamthat.com.onemusic.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.adapter.HotSongAdapter;
import teamthat.com.onemusic.model.HotSong;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicHotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicHotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicHotFragment extends Fragment {

    public final String GET_ALL_ARTIST_API = "http://mp3online.freevnn.com/listsong.php";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView lvsong;
    static int index;
    ArrayList<HotSong> mang;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MusicHotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicHotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicHotFragment newInstance(String param1, String param2) {
        MusicHotFragment fragment = new MusicHotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_music_hot, container, false);
        lvsong = (ListView) rootview.findViewById(R.id.song);
        runOnUiThread();

        mang = new ArrayList<HotSong>();
        return rootview;
    }

    private void runOnUiThread() {
        new docJSON().execute("http://nghiahoang.net/api/appmusic/?function=getallsongs");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class docJSON extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {
            try {

                JSONArray jsonArray = new JSONArray(s);

                for (int i=0; i<jsonArray.length();i++)
                {
                    JSONObject son = jsonArray.getJSONObject(i);
                    mang.add(new HotSong(
                            son.getInt("id"),
                            son.getString("name"),
                            son.getString("musicpath")

                    ));

                    HotSongAdapter listAdapter = new HotSongAdapter(getContext(), R.layout.itemlistsong,mang);
                    lvsong.setAdapter(listAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // tao url
            URL url = new URL(theUrl);

            // tao ủlconnection
            URLConnection urlConnection = url.openConnection();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

}
