package com.example.app;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import coding.JSONParser;

public class Work extends Activity {

	Spinner spinKepentingan;
	EditText txtBiaya, txtTanggal, txtKeterangan;
	Button btnSubmit, btnCancel;
	String[] arrBulan;
	int tanggal, bulan, tahun;
	ArrayAdapter<String> dataAdapter;
	ArrayList<HashMap<String, String>> listModel = new ArrayList<HashMap<String, String>>();
	TextView lblCount;
	SharedPreferences share;
	boolean cekSubmit = true;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work);

		spinKepentingan = (Spinner) findViewById(R.id.spinKepentingan);
		txtBiaya = (EditText) findViewById(R.id.txtBiaya);
		txtTanggal = (EditText) findViewById(R.id.txtTanggal);
		txtKeterangan = (EditText) findViewById(R.id.txtKeterangan);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		lblCount = (TextView) findViewById(R.id.lblCount);
		share = this.getSharedPreferences("aino", MODE_PRIVATE);
		
		txtKeterangan.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				lblCount.setText(txtKeterangan.getText().toString().length() + " / 500");
			}
		
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				startActivity(new Intent(Work.this, Pilihform.class));
			}
		});
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (cekSubmit) {
					ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("username", share.getString("username", "")));
					param.add(new BasicNameValuePair("idkepentingan", listModel.get(spinKepentingan.getSelectedItemPosition()).get("idkepentingan")));
					param.add(new BasicNameValuePair("biaya", txtBiaya.getText().toString()));
					param.add(new BasicNameValuePair("tanggal", tahun+"-"+bulan+"-"+tanggal));
					param.add(new BasicNameValuePair("keterangan", txtKeterangan.getText().toString()));
					new JSONParser().makeHttpRequest(
							"http://datakaryawan.esy.es/tambahKepentingan.php", "POST", param);
					Toast.makeText(Work.this, "Sukses", Toast.LENGTH_LONG).show();
					finish();
					startActivity(new Intent(Work.this, Workmenu.class));
					cekSubmit = false;
				}
			}
		});

		//menampilkan dialog tanggal saat mengisi tanggal
		arrBulan = new String[] { "Januari", "Februari", "Maret", "April",
				"Mei", "Juni", "Juli", "Agustus", "September", "Oktober",
				"November", "Desember" };
		tanggal = new Date().getDate();
		bulan = new Date().getMonth();
		tahun = new Date().getYear() + 1900;
		txtTanggal.setText(tanggal + " " + arrBulan[bulan] + " " + tahun);

		txtTanggal.setKeyListener(null); //biar tanggal tidak bisa diketik
		txtTanggal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new DatePickerDialog(Work.this, chooseDate, tahun, bulan, tanggal)
				.show();
			}
		});

		// set adapter pada spinner kepentingan
		// data adapter adalah data yang ditampilin di kepentingan
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new ArrayList<String>());
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinKepentingan.setAdapter(dataAdapter);
		new setSpinnerKepentingan().execute();
	}

	class setSpinnerKepentingan extends AsyncTask<String, String, String> {

		ProgressDialog dialog = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(Work.this, "", "Loading", true);
		}

		protected String doInBackground(String... arg0) {
			ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			JSONObject json = new JSONParser().makeHttpRequest(
					"http://datakaryawan.esy.es/ambilKepentingan.php", "POST",
					param);
			try {
				JSONArray jsonArray = json.getJSONArray("satuKepentingan");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject c = jsonArray.getJSONObject(i);
					HashMap<String, String> model = new HashMap<String, String>();
					model.put("idkepentingan", c.optString("idkepentingan"));
					model.put("kepentingan", c.optString("kepentingan"));
					listModel.add(model);
				}
			} catch (JSONException e) {
			}
			return null;
		}

		protected void onPostExecute(String hasil) {
			for (int i = 0; i < listModel.size(); i++) {
				dataAdapter.add(listModel.get(i).get("kepentingan"));
			}
			dataAdapter.notifyDataSetChanged();
			dialog.dismiss();
		}
	}

	private DatePickerDialog.OnDateSetListener chooseDate = new DatePickerDialog.OnDateSetListener() {

		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Calendar tanggalPilih = Calendar.getInstance();
			tanggalPilih.set(year, month, day);
			boolean lebihDariTanggalSekarang = Calendar.getInstance().before(tanggalPilih);
			if (lebihDariTanggalSekarang) {
				tanggal = new Date().getDate();
				bulan = new Date().getMonth();
				tahun = new Date().getYear() + 1900;
			} else {
				tanggal = day;
				bulan = month;
				tahun = year;
			}
			txtTanggal.setText(tanggal + " " + arrBulan[bulan] + " "
					+ tahun);
		}

	};
	
	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(Work.this, Pilihform.class));
	}
}
