package com.example.app;


import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import coding.JSONParser;
import coding.ListAdapterShare;


public class Share extends Activity {

	EditText txtJudulShare, txtShare;
	Button btnpost;
	ListView lvShare;
	SharedPreferences share;
	ListAdapterShare adapter;
	ArrayList<HashMap<String, String>> listModel = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);

		txtShare = (EditText) findViewById(R.id.txtShare);
		btnpost = (Button) findViewById(R.id.btnpost);
		txtJudulShare = (EditText) findViewById(R.id.txtJudulShare);
		lvShare = (ListView) findViewById(R.id.lvShare);
		share = this.getSharedPreferences("aino", MODE_PRIVATE);
		
		btnpost.setOnClickListener(new OnClickListener() {
			
	    @Override
		public void onClick(View arg0) {
	    	if (txtJudulShare.getText().toString().length() == 0) {
	    		txtJudulShare.setError("Judul postingan harus diisi");
	    		txtJudulShare.requestFocus();
			} else if (txtShare.getText().toString().length() == 0) {
				txtShare.setError("Postingan harus diisi");
				txtShare.requestFocus();
			} else {
				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("Isishare", txtShare.getText().toString()));
				param.add(new BasicNameValuePair("Judulshare", txtJudulShare.getText().toString()));
				param.add(new BasicNameValuePair("username", share.getString("username", "")));
				new JSONParser().makeHttpRequest(
						"http://datakaryawan.esy.es/share.php", "POST", param);
				txtJudulShare.setText("");
				txtShare.setText("");
				txtJudulShare.requestFocus();
			}
	    }
		});
		new ambilListShare().execute();
	}	
	
	class ambilListShare extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... arg0) {
	        JSONParser jsonParser = new JSONParser();
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			JSONObject json = jsonParser.makeHttpRequest("http://datakaryawan.esy.es/listShare.php", "POST", param);
			try {
				JSONArray jsonArray = json.getJSONArray("satuShare");
				for(int i = 0;i<jsonArray.length(); i++)
				{
					JSONObject c = jsonArray.getJSONObject(i);
					HashMap<String, String> model = new HashMap<String, String>();
					model.put("nama", c.optString("nama"));
					model.put("tanggal", c.optString("tanggal"));
					model.put("judul", c.optString("judul"));
					model.put("isi", c.optString("isi"));
					listModel.add(model);
				}
				adapter = new ListAdapterShare(Share.this, listModel);
		 		
			} catch (JSONException e) {
			}
			return null;
		}

		protected void onPostExecute(String hasil) {
			lvShare.setAdapter(adapter);
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(Share.this, Pilihform.class));
	}
}
