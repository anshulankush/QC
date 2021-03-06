package com.nmil.qc;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.nmil.qc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditPostCard extends Activity {
	String name = "";
	Drawable drawable;
	TouchImageView imageview;
	Button save, retake;
	Bitmap thumbnail;
	int position = 0;
	Bundle b;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			File file = new File(Functions.getInstance().getCachePath() + "/QcPostcard.jpg");

			if (file.exists()) {
				file.delete();
				System.gc();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onCreate(Bundle savedInstanceState) {
		
		
		System.out.println("this is main:");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editpostcard);

		imageview = (TouchImageView) findViewById(R.id.imageView1);

		b = getIntent().getExtras();
		try {
			name = (new JSONObject(b.getString("data"))).getString("name");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle(name);
		//System.out.println(name);

		retake = (Button) findViewById(R.id.retake);
		retake.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				thumbnail=null;
				File file = new File(Functions.getInstance().getCachePath() + "/QcPostcard.jpg");
				if (file.exists()) {
					file.delete();

				}
				System.gc();
				startActivity(new Intent(EditPostCard.this, PostcardActivity.class)
				.putExtras(b));
				EditPostCard.this.finish();
			}
		});
		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaStore.Images.Media.insertImage(getContentResolver(),
						((BitmapDrawable) drawable).getBitmap(), "", "");
				File file = new File(Functions.getInstance().getCachePath() + "/QcPostcard.jpg");
				if (file.exists()) {
					file.delete();
				}
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						EditPostCard.this);

				alertbox.setMessage("Image saved into gallery");

				alertbox.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						File file = new File(Functions.getInstance().getCachePath() + "/QcPostcard.jpg");
						if(file.exists())
						{
							file.delete();
						}
						EditPostCard.this.finish();
					}
				});

				alertbox.show();

			}
		});
		File file = new File(Functions.getInstance().getCachePath() + "/QcPostcard.jpg");

		if (file.exists()) {
			refresh();
		} else {
			EditPostCard.this.finish();
		}
	}

	public void refresh() {
		try {

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			options.outHeight = 640;
			options.outWidth = 384;
			options.inJustDecodeBounds = true;
			
			thumbnail = //Bitmap.createScaledBitmap(
					BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/QcPostCard.jpg",options);
							//Functions.getInstance().getCachePath() + "/QcPostcard.jpg", options);//(
					//Functions.getInstance().getCachePath() + "/QcPostcard.jpg").copy(
						//	Bitmap.Config.ARGB_8888, true),
							//640, 384, false);
			Canvas canvas = new Canvas(thumbnail);
			//System.out.println("hi:");

			TextPaint paint = new TextPaint();
			paint.setStyle(Style.STROKE);

			paint.setTypeface(Typeface.SANS_SERIF);

			paint.setColor(Color.WHITE);
			paint.setTextSize(75);

			Bitmap stamp = BitmapFactory.decodeResource(getResources(),
					R.drawable.postcardstamp);
			Bitmap greetings = BitmapFactory.decodeResource(getResources(),
					R.drawable.greetings_from);

			canvas.drawBitmap(Bitmap.createScaledBitmap(stamp,
					thumbnail.getWidth() / 3, thumbnail.getWidth() / 3, false),
					10, 10, new Paint());
			canvas.drawBitmap(
					Bitmap.createScaledBitmap(greetings, 350, 100, false), 10,
					canvas.getHeight() - 300, new Paint());

			//System.out.println("get me");

			StaticLayout layout = new StaticLayout(name, paint,
					thumbnail.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.3f,
					0, false);
			canvas.translate(0, canvas.getHeight() - 200);

			layout.draw(canvas);

			drawable = new BitmapDrawable(getResources(), thumbnail);
			drawable.draw(canvas);
			//System.out.println("get me");

			Bitmap result = Bitmap.createBitmap(canvas.getWidth(),
					layout.getHeight(), Config.ARGB_8888).copy(
							Bitmap.Config.ARGB_8888, true);

			Canvas ncanvas = new Canvas(result);
			ncanvas.drawRGB(135, 105, 49);
			paint.setAlpha(80);

			canvas.drawBitmap(result, 0, 10, paint);
			// canvas.drawBitmap(greetings, 10,10, new Paint());

			drawable = new BitmapDrawable(getResources(), thumbnail);
			Options opts = new BitmapFactory.Options ();
			opts.inSampleSize = 2;   // for 1/2 the image to be loaded
			Bitmap thumb=thumbnail.copy(thumbnail.getConfig(),false);
			thumb = Bitmap.createScaledBitmap (thumb, 1000, 1000, false);
			drawable = new BitmapDrawable(getResources(), thumb);

			imageview.setImageDrawable(drawable);
		} catch (Exception e) {
			//System.out.println(e.toString());
		}

	}

}
