package fr.isen.besopraclient.Adapter;

import java.util.List;

import fr.isen.besopraclient.R;
import fr.isen.besopraclient.model.Product;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListForProductAdapter extends ArrayAdapter<Product> {

	public ListForProductAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ListForProductAdapter(Context context, int resource, List<Product> products) {
		super(context, resource, products);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.row_layout, null);
		}

		Product p = getItem(position);

		if (p != null) {
			TextView nameTv = (TextView) v.findViewById(R.id.nameItem);
			TextView quantityTv = (TextView) v.findViewById(R.id.quantityItem);
			TextView priceTv = (TextView) v.findViewById(R.id.priceItem);
			ImageView icon = (ImageView) v.findViewById(R.id.icon);
			
			if (nameTv != null) {
				nameTv.setText(p.getTitle());
			}
			if (quantityTv != null) {
				quantityTv.setText("1");
			}
			if(priceTv != null){
				priceTv.setText(String.valueOf(p.getPrice()));
			}
			if(icon != null){
				System.out.println("Number of byte = " + p.getImage().length);
				System.out.println("Byte = " + p.getImage().toString());
				Bitmap bmp=BitmapFactory.decodeByteArray(p.getImage(),0,p.getImage().length);		
				if(bmp != null){
					icon.setImageBitmap(bmp);
				}
			}
		}
		return v;
	}
}