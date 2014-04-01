package com.nmil.qc;
import java.io.IOException;
import java.io.InputStream;

import com.nmil.qc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapterKML extends ArrayAdapter<String> {

	private final Context context;
	private final String[] Ids;
	private final int rowResourceId;

	public ItemAdapterKML(Context context, int textViewResourceId, String[] objects) {

		super(context, textViewResourceId, objects);

		this.context = context;
		this.Ids = objects;
		this.rowResourceId = textViewResourceId;

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(rowResourceId, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
		TextView textView = (TextView) rowView.findViewById(R.id.textView);

		int id = Integer.parseInt(Ids[position]);
		String imageFile = ModelKML.GetbyId(id).IconFile;

		textView.setText(ModelKML.GetbyId(id).Name);
		// get input stream
		if(position == 0 ||position == 4 ||position == 8){
			rowView.setBackgroundColor(-16777216);
			rowView.setClickable(false);
			textView.setGravity(Gravity.CENTER);
		}
		InputStream ims = null;
		try {
			ims = context.getAssets().open(imageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// load image as Drawable
		Drawable d = Drawable.createFromStream(ims, null);
		// set image to ImageView
		imageView.setImageDrawable(d);
		return rowView;

	}

}