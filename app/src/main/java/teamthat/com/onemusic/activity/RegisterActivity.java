package teamthat.com.onemusic.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import teamthat.com.onemusic.R;

public class RegisterActivity extends Activity {

	private InputStream is;
	private JSONObject jObj;
	private String json;
	private ProgressDialog pDialog;
	private EditText user, pass;
	private Button Login, Register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.pass);
		Login = (Button) findViewById(R.id.Login);
		Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		Register = (Button) findViewById(R.id.register);
		Register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(user.getText().toString())
						|| TextUtils.isEmpty(pass.getText().toString())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setTitle("Error");
					builder.setMessage("User and Password empty");
					builder.setCancelable(false);
					builder.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					new CreateUser().execute();
				}
			}
		});
	}

	class CreateUser extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setMessage("Registering...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			int success;
			String userName = user.getText().toString();
			String Password = pass.getText().toString();
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", userName));
				params.add(new BasicNameValuePair("password", Password));
				JSONObject jObject = makeHttpRequest(config.REGISTER_URL,
						params);
				success = jObject.getInt("success");
				if (success == 1) {
					return jObject.getString("message");
				} else {
					return jObject.getString("message");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			if (result != null) {
				Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	public JSONObject makeHttpRequest(String url, List<NameValuePair> name) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			post.setEntity(new UrlEncodedFormEntity(name));//set giá trị cho mảng post của php
			HttpResponse httpResponse = httpClient.execute(post);
			HttpEntity entity = httpResponse.getEntity();
			is = entity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();//đọc giá trị trong json
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj;
	}
}
