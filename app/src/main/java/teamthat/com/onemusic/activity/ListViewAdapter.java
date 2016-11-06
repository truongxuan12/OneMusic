package teamthat.com.onemusic.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import teamthat.com.onemusic.R;

public class ListViewAdapter extends BaseAdapter {

	// Khai báo biến

	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;

	HashMap<String, String> resultp = new HashMap<String, String>();
	private ImageButton mImageButton;

	public ListViewAdapter(Context context,
						   ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;

	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		//Khai báo biến

		TextView rank;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		final View itemView = inflater.inflate(R.layout.itemsong, parent, false);

		resultp = data.get(position);

		rank = (TextView) itemView.findViewById(R.id.tv1);



		rank.setText(resultp.get(PlaylistActivity.DUONGDAN));

		itemView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {


				resultp = data.get(position);
				Intent intent = new Intent(context, DeletePlayList.class);

				intent.putExtra("ids",resultp.get(PlaylistActivity.ID));
				intent.putExtra("name",resultp.get(PlaylistActivity.DUONGDAN));


				context.startActivity(intent);

				return false;
			}


		});

		ImageView de = (ImageView)itemView.findViewById(R.id.delete);
		de.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				runOnUiThread();
				Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, PlaylistActivity.class);
				context.startActivity(intent);
			}

			private void runOnUiThread() {
				new docJSON().execute("http://appmusic.890m.com/delete.php");
			}
		});


		return itemView;

	}

	class docJSON extends AsyncTask<String, Integer , String>
	{


		@Override
		protected String doInBackground(String... params) {
			return  makePostRequest(params[0]);

		}

		@Override
		protected void onPostExecute(String s) {
			Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
			super.onPostExecute(s);
		}
	}

	private String makePostRequest(String param) {
		HttpClient httpClient = new DefaultHttpClient();

		// URL của trang web nhận request
		HttpPost httpPost = new HttpPost(param);

		// Các tham số truyền
		List nameValuePair = new ArrayList(2);
		nameValuePair.add(new BasicNameValuePair("action", "delete"));
		nameValuePair.add(new BasicNameValuePair("id", PlaylistActivity.ID));

		//Encoding POST data
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String kq = "";
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			kq = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return kq;
	}



}
