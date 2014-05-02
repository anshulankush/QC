package com.nmil.qc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.nmil.qc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PostcardActivity extends Activity {

	protected static final int SELECT_IMAGE = 0;
	Bundle b;
	Camera camera;
	SurfaceView surfaceView;
	boolean previewing = false;
	Context context;
	Intent cameraIntent;
	Intent galleryIntent;

	String name = "";
	Uri mImageUri;
	int CAMERA_PIC_REQUEST = 1332;
	Button send;
	EditText editTextEmail, editTextSubject, editTextMessage;
	Button buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	Drawable drawable;
	//TouchImageView imageview;
	Button save, retake;
	Bitmap thumbnail;
	int position = 0;
	boolean flag=true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AlertDialog.Builder alertbox = new AlertDialog.Builder(
				PostcardActivity.this);

		alertbox.setMessage("Choose Image");

		alertbox.setNegativeButton("Gallery",
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				setContentView(R.layout.activity_postcard);
				b = getIntent().getExtras();
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);//
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_IMAGE);
				flag=false;

			}
		});

		alertbox.setPositiveButton("Camera",
				new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				setContentView(R.layout.activity_postcard);
				b = getIntent().getExtras();

				try {
					cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
					// request code
					File file = new File(Functions.getInstance().getCachePath()
							+ "/QcPostcard.jpg");
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					Uri outputFileUri = Uri.fromFile(file);
					cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
					cameraIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, 1);
					startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
				} catch (Exception e) {
					//System.out.println(e.toString());
				}
			}
		});
		alertbox.show();
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(flag==false){
			String mSelectedImagePath = getPath(data.getData());
			System.out.println(mSelectedImagePath);
			String srFile = mSelectedImagePath;
			String dtFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QcPostcard.jpg";
			try{
				  File f1 = new File(srFile);
				  File f2 = new File(dtFile);
				  InputStream in = new FileInputStream(f1);

				  //For Append the file.
				  //OutputStream out = new FileOutputStream(f2,true);

				  //For Overwrite the file.
				  OutputStream out = new FileOutputStream(f2);

				  byte[] buf = new byte[1024];
				  int len;
				  while ((len = in.read(buf)) > 0){
				    out.write(buf, 0, len);
				  }
				  in.close();
				  out.close();
				  System.out.println("File copied.");
				}
				catch(FileNotFoundException ex){
				  System.out.println(ex.getMessage() + " in the specified directory.");
				  System.exit(0);
				}
				catch(IOException e){
				  System.out.println(e.getMessage());      
				}
		}
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		options.outHeight = 600;
		options.outWidth = 600;
		options.inJustDecodeBounds = true;
		thumbnail = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/QcPostcard.jpg"),640,384, false);
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(Environment.getExternalStorageDirectory().getAbsolutePath() + "/QcPostcard.jpg");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1) != 1 && exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,1) != 0 ){
			// Do something for froyo and above versions
			Matrix matrix = new Matrix();
			// resize the bit map
			// rotate the Bitmap
			matrix.postRotate(90);

			// recreate the new Bitmap

			thumbnail = Bitmap.createBitmap(thumbnail, 0, 0, 
					thumbnail.getWidth(), thumbnail.getHeight(), 
					matrix, true);
		}
		//		else{
		//			// do something for phones running an SDK before froyo
		//		}		

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

		//canvas.drawBitmap(Bitmap.createScaledBitmap(stamp,
		//			thumbnail.getWidth() / 4, thumbnail.getHeight() / 4, false),
		//			canvas.getWidth()-thumbnail.getWidth()/4-10, canvas.getHeight()-thumbnail.getHeight()/4-10, new Paint());

		canvas.drawBitmap(
				Bitmap.createScaledBitmap(greetings, thumbnail.getWidth(), thumbnail.getHeight(), false), 0,
				0, new Paint());

		//System.out.println("get me");

		StaticLayout layout = new StaticLayout(name, paint,
				thumbnail.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.3f,
				0, false);
		canvas.translate(0, canvas.getHeight() - 200);

		//layout.draw(canvas);

		drawable = new BitmapDrawable(getResources(), thumbnail);
		drawable.draw(canvas);
		//System.out.println("get me");

		Bitmap result = Bitmap.createBitmap(canvas.getWidth(),
				layout.getHeight(), Config.ARGB_8888).copy(
						Bitmap.Config.ARGB_8888, true);

		Canvas ncanvas = new Canvas(result);
		ncanvas.drawRGB(135, 105, 49);
		paint.setAlpha(80);

		//canvas.drawBitmap(result, 0, 10, paint);
		// canvas.drawBitmap(greetings, 10,10, new Paint());

		drawable = new BitmapDrawable(getResources(), thumbnail);
		Options opts = new BitmapFactory.Options ();
		opts.inSampleSize = 2;   // for 1/2 the image to be loaded
		Bitmap thumb=thumbnail.copy(thumbnail.getConfig(),false);
		thumb = Bitmap.createScaledBitmap (thumb, 384, 640, false);
		drawable = new BitmapDrawable(getResources(), thumb);

		//imageview.setImageDrawable(drawable);



		ImageView myImage = (ImageView) findViewById(R.id.imageViewFinal);
		//System.out.println("99");
		myImage.setImageBitmap(thumb);
		try {
			storeImage(thumb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("100");
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textTo = (EditText) findViewById(R.id.editTextTo);
		textSubject = (EditText) findViewById(R.id.editTextSubject);
		textMessage = (EditText) findViewById(R.id.editTextMessage);

		buttonSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String to = textTo.getText().toString();
				String subject = textSubject.getText().toString();
				String message = textMessage.getText().toString();

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
				//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
				//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				email.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/QCGreeting.jpg")));

				//need this to prompts email client only
				email.setType("message/rfc822");

				startActivity(Intent.createChooser(email, "Choose an Email client :"));

			}
		});
	}
	//		try {
	//			File file = new File(Functions.getInstance().getCachePath()
	//					+ "/QcPostcard.jpg");
	//			if (file.exists()) {
	//				if (requestCode == CAMERA_PIC_REQUEST) {
	//					Bitmap myBitmap = BitmapFactory.decodeFile(Functions.getInstance().getCachePath()
	//							+ "/QcPostcard.jpg");
	//					Bitmap myBitmap1 = BitmapFactory.decodeStream(getAssets().open("greetings_from.png"));
	//					Bitmap finalImage=overlay(myBitmap, myBitmap1);
	//					Bitmap myBitmap2 = BitmapFactory.decodeStream(getAssets().open("postcardstamp.png"));
	//					Bitmap finalImagewithBadge=overlay(finalImage, myBitmap2);
	//
	//					//	savebitmap(finalImage);
	//					storeImage(finalImagewithBadge);
	//					//System.out.println("88");
	//
	//					ImageView myImage = (ImageView) findViewById(R.id.imageViewFinal);
	//					//System.out.println("99");
	//					myImage.setImageBitmap(finalImagewithBadge);
	//					//System.out.println("100");
	//					buttonSend = (Button) findViewById(R.id.buttonSend);
	//					textTo = (EditText) findViewById(R.id.editTextTo);
	//					textSubject = (EditText) findViewById(R.id.editTextSubject);
	//					textMessage = (EditText) findViewById(R.id.editTextMessage);
	//
	//					buttonSend.setOnClickListener(new OnClickListener() {
	//
	//						@Override
	//						public void onClick(View v) {
	//
	//							String to = textTo.getText().toString();
	//							String subject = textSubject.getText().toString();
	//							String message = textMessage.getText().toString();
	//
	//							Intent email = new Intent(Intent.ACTION_SEND);
	//							email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
	//							//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
	//							//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
	//							email.putExtra(Intent.EXTRA_SUBJECT, subject);
	//							email.putExtra(Intent.EXTRA_TEXT, message);
	//							email.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+"/QCGreeting.jpg")));
	//
	//							//need this to prompts email client only
	//							email.setType("message/rfc822");
	//
	//							startActivity(Intent.createChooser(email, "Choose an Email client :"));
	//
	//						}
	//					});
	//
	//				} else {
	//							//System.out.println(4.8);
	//
	//				}
	//			}
	//			System.err.println("4.77");
	//		} catch (Exception e) {
	//
	//		}
	//	}

	private boolean storeImage(Bitmap imageData) throws IOException {
		try{
			OutputStream stream = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/QCGreeting.jpg");
			imageData.compress(CompressFormat.JPEG, 100, stream);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, 0, 0, null);
		return bmOverlay;
	}

	public static Bitmap overlayBadge(Bitmap bmp1, Bitmap bmp2) {
		Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
		Canvas canvas = new Canvas(bmOverlay);
		canvas.drawBitmap(bmp1, new Matrix(), null);
		canvas.drawBitmap(bmp2, 0, 0, null);
		return bmOverlay;
	}
	private Bitmap decodeFile(File f){
		try {
			//Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f),null,o);

			//The new size we want to scale to
			final int REQUIRED_SIZE=70;

			//Find the correct scale value. It should be the power of 2.
			int scale=1;
			while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
				scale*=2;

			//Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize=scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {}
		return null;
	}

}
