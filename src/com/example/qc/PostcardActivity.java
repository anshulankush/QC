package com.example.qc;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PostcardActivity extends Activity {

	Bundle b;
	Camera camera;
	SurfaceView surfaceView;
	boolean previewing = false;
	Context context;
	Intent cameraIntent;
	String name = "";
	Uri mImageUri;
	int CAMERA_PIC_REQUEST = 1332;
	Button send;
	EditText editTextEmail, editTextSubject, editTextMessage;
	Button buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
	


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
			cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			File file = new File(Functions.getInstance().getCachePath()
					+ "/QcPostcard.jpg");
			if (file.exists()) {
				if (requestCode == CAMERA_PIC_REQUEST) {
					Bitmap myBitmap = BitmapFactory.decodeFile(Functions.getInstance().getCachePath()
							+ "/QcPostcard.jpg");
					Bitmap myBitmap1 = BitmapFactory.decodeStream(getAssets().open("greetings_from.png"));
					Bitmap finalImage=overlay(myBitmap, myBitmap1);
					//	savebitmap(finalImage);
					storeImage(finalImage);
					System.out.println("88");

					ImageView myImage = (ImageView) findViewById(R.id.imageViewFinal);
					System.out.println("99");
					myImage.setImageBitmap(finalImage);
					System.out.println("100");
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

				} else {
							System.out.println(4.8);

				}
			}
			System.err.println("4.77");
		} catch (Exception e) {

		}
	}
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
