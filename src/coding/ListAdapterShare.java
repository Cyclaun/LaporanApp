package coding;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app.R;

public class ListAdapterShare extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListAdapterShare(Context context,
			ArrayList<HashMap<String, String>> arrayList) {
		this.context = context;
		data = arrayList;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.list_share, parent, false);
		resultp = data.get(position);
		TextView txtNamaList = (TextView) view.findViewById(R.id.txtNamaList);
		TextView txtTanggalList = (TextView) view
				.findViewById(R.id.txtTanggalList);
		TextView txtJudulList = (TextView) view.findViewById(R.id.txtJudulList);
		WebView txtIsiList = (WebView) view.findViewById(R.id.txtIsiList);

		txtNamaList.setText(resultp.get("nama"));
		txtTanggalList.setText(resultp.get("tanggal"));
		txtJudulList.setText(resultp.get("judul"));
		String text = "<html><body>" + "<p align=\"justify\">"
				+ resultp.get("isi") + "</p> " + "</body></html>";
		txtIsiList.loadData(text, "text/html", "utf-8");
		return view;
	}
}
