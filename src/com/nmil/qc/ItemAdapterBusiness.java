package com.nmil.qc;
import java.io.IOException;
import java.io.InputStream;

import com.nmil.qc.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemAdapterBusiness extends ArrayAdapter<String> {

	private final Context context;
	private final String[] Ids;
	private final int rowResourceId;

	public ItemAdapterBusiness(Context context, int textViewResourceId, String[] objects) {

		super(context, textViewResourceId, objects);

		this.context = context;
		this.Ids = objects;
		this.rowResourceId = textViewResourceId;

	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(rowResourceId, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);
		TextView textView = (TextView) rowView.findViewById(R.id.textView);
		int id = Integer.parseInt(Ids[position]);
		String imageFile = ModelBusiness.GetbyId(id).IconFile;
		textView.setText(ModelBusiness.GetbyId(id).Name);
		// get input stream
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
		//imageView.setImageDrawable(d);
//		String uri = "@drawable/"+;
//
//		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
//
//		imageview= (ImageView)findViewById(R.id.imageView);
//		Drawable res = getResources().getDrawable(imageResource);
//		imageView.setImageDrawable(res);
//		
//		
//		imageView.setImageResource(R.drawable.sun);
		return rowView;

	}

}