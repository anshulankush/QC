package com.nmil.qc;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.text.format.Time;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Functions {

	static Functions functions=new Functions();
	
	public static Functions getInstance()
	{
		return functions;
	}
	
	public  boolean isNetworkAvailable(Context c) {
		ConnectivityManager connectivityManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public  String convertToSentence(String input)
	{
		String output="";
		String ttemp[]=input.split(" ");
		for(String temp : ttemp )
		{
			temp=temp.toLowerCase(Locale.US);
			temp=(temp.substring(0, 1)).toUpperCase(Locale.US)+temp.substring(1);
			output=output+temp+" ";
		}
		return output;
	}
	
	public  void writeDistance(double i) {
		double distance = 0;
		
		if ((readFile(getCachePath() + "/Trails_Downloaded/distance")).trim()
				.length() != 0) {
			distance = Double.parseDouble(readFile(getCachePath() + "/Trails_Downloaded/distance"));
		}
		else
		{
			distance=0;
		}
		distance=distance+i;
		writeFile(getCachePath() + "/Trails_Downloaded/distance",
				String.valueOf(distance));
		
	}

	public  String[] getLocations(Context c) {
		LocationManager locationManager;
		locationManager = (LocationManager) c
				.getSystemService(Context.LOCATION_SERVICE);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
				1000, (new MyLocationListener()));

		Location location = locationManager
				.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		String[] output = new String[3];
		output[0] = String.valueOf(location.getLatitude());
		output[1] = String.valueOf(location.getLongitude());
		output[2] = String.valueOf(location.getAltitude());
		return output;
	}

	public  int getHours() {
		Time dtNow = new Time();
		dtNow.setToNow();
		int hours = dtNow.hour;
		int month=dtNow.month;
		return hours;

	}
	
	public  int getMonth() {
		Time dtNow = new Time();
		dtNow.setToNow();
		int month=dtNow.month;
		return month;

	}

	public  String getCachePath() {
		//System.out.println("Path:"+Environment.getExternalStorageDirectory().getAbsolutePath());
		return Environment.getExternalStorageDirectory().getAbsolutePath();
		////System.out.println("Absolute path: "+Environment.getRootDirectory().getAbsolutePath());
		//return Environment.getRootDirectory().getAbsolutePath();
	}

	public  boolean downloadTrail(String data) {
		try {

			JSONObject j = new JSONObject(data);

			String l = GetStaticMap(j.getInt("id"));
			String name = j.getString("name");
			final JSONObject jo = new JSONObject(l);

			String serverUrl = "http://129.219.171.240/~trails/wsgi";
			URL imageURL = new URL(serverUrl
					+ jo.getString("google_static_url"));
			String lon = jo.getString("ll_long");
			String lat = jo.getString("ll_lat");
			
			File sdTargetDirectory = new File(getCachePath()
					+ "/Trails_Downloaded/" + name + "+"
					+ jo.getInt("google_zl") + "+" + lat + "+" + lon);
			if (!sdTargetDirectory.exists())
				sdTargetDirectory.mkdirs();

			writeFile(sdTargetDirectory.toString() + "/Data", data);
			writeFile(sdTargetDirectory.toString() + "/Count", "0");

			String[] weatheroutput = getWeather(j.getString("zip"));
			String weather = weatheroutput[0];

			for (int i = 1; i < weatheroutput.length; i++) {
				weather = weather + "#" + weatheroutput[i];
			}

			writeFile(sdTargetDirectory.toString() + "/Weather",
					weather);

			Calendar c = Calendar.getInstance();
			//System.out.println("Current time => " + c.getTime());

			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			String formattedDate = df.format(c.getTime());

			writeFile(sdTargetDirectory.toString() + "/ddate",
					formattedDate);

			FileOutputStream fos1 = new FileOutputStream(
					sdTargetDirectory.toString() + "/data.jpg");

			BufferedOutputStream bos1 = new BufferedOutputStream(fos1);

			URL url = new URL(imageURL.toString());
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bmap = BitmapFactory.decodeStream(input);

			bmap.compress(CompressFormat.JPEG, 100, bos1);

			return true;

		} catch (Exception e) {
			return false;
		}

	}

	public  String[] getWeatherByGPS(String[] location) {
		InputStream inputXml = null;
		String[] listItems = new String[12];

		try {

			String inputjson = readURL("http://forecast.weather.gov/MapClick.php?lat="
							+ location[0] + "&lon=" + location[2]
							+ "&site=okx&unit=0&lg=en&FcstType=json");

			JSONObject jweather = new JSONObject(inputjson);

			listItems[2] = (jweather.getJSONObject("data")).getJSONArray(
					"temperature").getString(0);
			listItems[2] = (jweather.getJSONObject("data")).getJSONArray(
					"temperature").getString(1);

			if (getHours() > 18) {
				listItems[1] = (jweather.getJSONObject("data")).getJSONArray(
						"weather").getString(1);
			} else {
				listItems[1] = (jweather.getJSONObject("data")).getJSONArray(
						"weather").getString(0);
			}

		} catch (Exception e) {

		}
		return listItems;
	}

	public  String[] getWeather(String zip) {
		InputStream inputXml = null;
		String[] listItems = new String[12];

		try {
			inputXml = new URL("http://weather.yahooapis.com/forecastrss?p="
					+ zip).openConnection().getInputStream();

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputXml);

			NodeList nodi = doc.getElementsByTagName("yweather:forecast");

			if (nodi.getLength() > 0) {

				Element nodo = (Element) nodi.item(0);

				String strLow = nodo.getAttribute("low");
				Element nodo1 = (Element) nodi.item(0);
				String strHigh = nodo1.getAttribute("high");

				listItems[1] = nodo1.getAttribute("text");

				listItems[2] = strHigh;
				listItems[3] = strLow;

				strHigh = nodo1.getAttribute("code");
				listItems[0] = strHigh;
				Time dtNow = new Time();
				dtNow.setToNow();
				int hours = dtNow.hour;
				int min = dtNow.minute;
				String zone = dtNow.timezone;
				listItems[11] = hours + ":" + min + " " + zone;
			}

			nodi = doc.getElementsByTagName("yweather:condition");

			if (nodi.getLength() > 0) {

				Element nodo = (Element) nodi.item(0);
				listItems[10] =nodo.getAttribute("date");
			}
			nodi = doc.getElementsByTagName("yweather:atmosphere");

			if (nodi.getLength() > 0) {

				Element nodo = (Element) nodi.item(0);

				listItems[4] = nodo.getAttribute("humidity") + "%";
				listItems[5] = nodo.getAttribute("visibility") + " meter";
				listItems[6] = nodo.getAttribute("pressure");
			}

			nodi = doc.getElementsByTagName("yweather:astronomy");

			if (nodi.getLength() > 0) {

				Element nodo = (Element) nodi.item(0);

				listItems[7] = nodo.getAttribute("sunrise");
				listItems[8] = nodo.getAttribute("sunset");
			}

			nodi = doc.getElementsByTagName("yweather:wind");

			if (nodi.getLength() > 0) {

				Element nodo = (Element) nodi.item(0);

				listItems[9] = nodo.getAttribute("speed");
			}

		} catch (Exception e) {

		}
		return listItems;
	}

	public  boolean browserInternet() {
		String l = readURL("http://129.219.171.240/~trails/wsgi/trail_details/?id=1");
		try {
			//System.out.println("hi+" + l);
			JSONObject jo = new JSONObject(l);

			return true;
		} catch (JSONException e) {
			return false;

		}

	}

	public  boolean writeFile(String fname, String data) {
		try {

			File myFile = new File(fname);
			if (myFile.exists()) {
				myFile.delete();
			}
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.write(data);
			myOutWriter.close();
			fOut.close();

		} catch (Exception e) {

			e.printStackTrace(System.err);

		}
		return true;
	}

	public  String readFile(String fname) {
		try {
			File myFile = new File(fname);

			FileInputStream fIn = new FileInputStream(myFile);
			BufferedReader myReader = new BufferedReader(new InputStreamReader(
					fIn));
			String aDataRow = "";
			String aBuffer = "";
			while ((aDataRow = myReader.readLine()) != null) {
				aBuffer += aDataRow + "\n";
			}
			myReader.close();
			return aBuffer.trim();

		} catch (Exception e) {
			return e.toString();
		}
	}

	public  String readURL(String Url) {
		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();

			StrictMode.setThreadPolicy(policy);

			URL url = new URL(Url);

			URLConnection tc = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			String line;
			String l = "";
			if ((line = in.readLine()) != null) {
				l = l + line;
			}

			return l;
		} catch (Exception e) {
			return e.toString();
		}

	}

	public  String TrailNearMe(double latitude, double longitude) {
		String l = readURL("http://129.219.171.240/~trails/wsgi/trails_search/?lat="
						+ latitude
						+ "&lon="
						+ longitude
						+ "&distance=25&sort=Nearest");
		return l;
	}

	public  String TrailDetails(int id) {
		String l = readURL("http://129.219.171.240/~trails/wsgi/trail_details/?id="
						+ id);
		return l;
	}

	public  String TrailsSearch(String region) {
		String l = readURL("http://129.219.171.240/~trails/wsgi/trails_search/"
						+ region);
		return l;
	}

	public  String TrailsSearch(String region, String latitude,
			String longitude) {
		String l = readURL("http://129.219.171.240/~trails/wsgi/trails_search/"
						+ region + "?&lat=" + latitude + "&lon=" + longitude);
		return l;
	}

	public  Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			//System.out.println("Exc=" + e);
			return null;
		}
	}

	public  String GetStaticMap(int id) {
		String l;
		try {
			l = readURL("http://129.219.171.240/~trails/wsgi/get_static_map/?id="
							+ id);
		} catch (Exception e) {
			l = e.toString();
		}
		return l;
	}

	public  String GetWeather(String zip) {
		String l;
		try {
			l = readURL("http://weather.yahooapis.com/forecastrss?p=85282");
		} catch (Exception e) {
			l = e.toString();
		}
		return l;
	}
}
