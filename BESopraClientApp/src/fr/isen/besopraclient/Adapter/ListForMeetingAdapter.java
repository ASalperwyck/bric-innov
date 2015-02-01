package fr.isen.besopraclient.Adapter;

import java.util.List;

import fr.isen.besopraclient.R;
import fr.isen.besopraclient.model.Meeting;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListForMeetingAdapter extends ArrayAdapter<Meeting> {

	public ListForMeetingAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ListForMeetingAdapter(Context context, int resource, List<Meeting> meetings) {
		super(context, resource, meetings);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.list_meeting_row_item, null);
		}

		Meeting m = getItem(position);

		if (m != null) {
			TextView tt = (TextView) v.findViewById(R.id.adviserName);
			TextView tt2 = (TextView) v.findViewById(R.id.reason);

			if (tt != null) {
				tt.setText(m.getAdviser());
			}
			if (tt2 != null) {
				if(m.getReason().equals("null")){
					tt2.setText("Aucun motif");
				}
				else{
					tt2.setText(m.getReason());
				}
			}
		}
		return v;
	}
}
