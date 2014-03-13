//package com.example.qc;
//
//import java.io.IOException;
//import java.util.List;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.app.Activity;
//import android.app.Dialog;
//import android.view.Menu;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//
///**
// *
// * @author VIJAYAKUMAR M
// *http://www.android-ever.com/
// */
//public class SaxParserClass extends Activity {
//     
//        public static List<ParsingStructure> parsingStr = null;
//        ListAdap listAdpter;
//        ListView listview  = null;
//      @Override
//      protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            listview = (ListView)findViewById(R.id.listView1);
//            ParsingBigFileAsync parsingAsync = new ParsingBigFileAsync();
//            parsingAsync.execute();
//                       
//      }
//
//      @Override
//      public boolean onCreateOptionsMenu(Menu menu) {
//     
//            getMenuInflater().inflate(R.menu.activity_main, menu);
//            return true;
//      }
//
//     
//      public class ParsingBigFileAsync extends AsyncTask<String, Void , String>{
//     String  result;
//     Dialog dialog;
//     ProgressBar pBar ;
//            @Override
//            public void onPreExecute(){
//                   dialog = new Dialog(MainActivity.this);
//                   dialog.setTitle("Loading......");
//                   dialog.show();
//            }
//            @Override
//            protected String doInBackground(String... params) {
//                   try {
//                         parsingStr = SAXXMLParser.parse(getAssets().open("values.kml"));
//                            result = "in";
//                        } catch (IOException e) {
//                              e.printStackTrace();
//                              result = "out";
//                        }
//                       
//                  return result;
//            }
//           
//            @Override
//            public void onPostExecute(String result){
//                  if(result.equalsIgnoreCase("in")){
//                      listAdpter = new ListAdap(MainActivity.this, R.layout.listitemview, parsingStr);
//                        listview.setAdapter(listAdpter);
//                        dialog.dismiss();
//                  }
//            }
//           
//      }
//
//}
