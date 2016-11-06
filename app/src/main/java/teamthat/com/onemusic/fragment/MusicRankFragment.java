package teamthat.com.onemusic.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import teamthat.com.onemusic.R;
import teamthat.com.onemusic.activity.ListViewAdapter;
import teamthat.com.onemusic.activity.PlaylistActivity;
import teamthat.com.onemusic.activity.SplashActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicRankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicRankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicRankFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    Button btn1;
    EditText edit;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String ID = "id";
    static String COUNTRY = "UserId";
    static String DUONGDAN = "SongName";
    static String MUSICFILE = "path";
    static String PlayID = "PlaylistId";
    String dl,thumb;
    ImageView img;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView vui, buon,thich;
    private OnFragmentInteractionListener mListener;

    public MusicRankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicRankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicRankFragment newInstance(String param1, String param2) {
        MusicRankFragment fragment = new MusicRankFragment();
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
        View rootview = inflater.inflate(R.layout.fragment_music_rank, container, false);
        vui = (ImageView)rootview.findViewById(R.id.vui);
        vui.setOnClickListener(this);
        buon = (ImageView)rootview.findViewById(R.id.buon);
        buon.setOnClickListener(this);
        thich = (ImageView)rootview.findViewById(R.id.thich);
        thich.setOnClickListener(this);
        return rootview;

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

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.vui:
                if (SplashActivity.user.getUsername() == "")
                {
                    Toast.makeText(getContext(), "Bạn phai dang nhap", Toast.LENGTH_LONG).show();
                }else
                {

                    Intent intent = new Intent(getContext(), PlaylistActivity.class);
                    intent.putExtra("vui",(String.valueOf(1)));
                    startActivity(intent);
                }
                break;
            case R.id.buon:
                if (SplashActivity.user.getUsername() == "")
                {

                    Toast.makeText(getContext(), "Bạn phai dang nhap", Toast.LENGTH_LONG).show();
                }else
                {
                    Intent intent = new Intent(getContext(), PlaylistActivity.class);
                    intent.putExtra("vui",(String.valueOf(2)));
                    startActivity(intent);
                }
                break;
            case R.id.thich:
                if (SplashActivity.user.getUsername() == "")
                {
                    Toast.makeText(getContext(), "Bạn phai dang nhap", Toast.LENGTH_LONG).show();
                }else
                {
                    Intent intent = new Intent(getContext(), PlaylistActivity.class);
                    intent.putExtra("vui",(String.valueOf(3)));
                    startActivity(intent);
                }
                break;

        }

    }

}
