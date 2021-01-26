package com.example.androidnangcao5_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
//    String link = "https://vtc.vn/rss/kinh-te.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTex);
    }

    public void Load(View view) {
        GetDataTask getDataTask = new GetDataTask();
        getDataTask.execute(editText.getText().toString());
    }

    class GetDataTask extends AsyncTask<String, Long, List<TinTuc>> {

        @Override
        protected List<TinTuc> doInBackground(String... strings) {
            List<TinTuc> tinTucs = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                Log.e("0: ", "vao try1");
                InputStream inputStream = httpURLConnection.getInputStream();
                // khoi tao xmlpullparser
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(false);
                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                Log.e("1: ", "" + inputStream);
                // truyen du lieu vao xmlpullparser roi boc tach
                xmlPullParser.setInput(inputStream, "utf-8");
                Log.e("58: ", "58");
                int eventType = xmlPullParser.getEventType();
                Log.e("60: ", "60");
                TinTuc tinTuc = null;
                String text = "";
//                Log.e("eventType", "" + eventType);
                Log.e("64: ", "eventType :"+eventType);
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = xmlPullParser.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            Log.e("case1: ", "hello1");
                            if (name.equalsIgnoreCase("item")) {
                                tinTuc = new TinTuc();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            Log.e("case2: ", "hello2");
                            text = xmlPullParser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            Log.e("case3: ", "hello3");
                            if (tinTuc != null && name.equalsIgnoreCase("title")) {
                                tinTuc.title = text;
                                Log.e("title", "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+tinTuc.title);
                            }else
                            if (tinTuc != null &&name.equalsIgnoreCase("description")){
                                tinTuc.des= text;
                            }else
                            if (tinTuc != null &&name.equalsIgnoreCase("link")){
                                tinTuc.id= text;
                            }else
                            if (tinTuc != null &&name.equalsIgnoreCase("pubDate")){
                                tinTuc.pubDate= text;
                            }else
                            if (name.equalsIgnoreCase("item")){
                                tinTucs.add(tinTuc);
                            }

                            break;
                    }
                    eventType =xmlPullParser.next();
                }
            } catch (Exception e) {
                Log.e("Loi", "roi: ");
            }
            Log.e("size", ""+tinTucs.size());
            return tinTucs;
        }

        @Override
        protected void onPostExecute(List<TinTuc> tinTucs) {
            super.onPostExecute(tinTucs);
            ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,getTitle(tinTucs));
            listView = findViewById(R.id.listView);
            listView.setAdapter(arrayAdapter);
            Toast.makeText(MainActivity.this, "size  "+tinTucs.size(), Toast.LENGTH_SHORT).show();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(MainActivity.this, "id : "+tinTucs.get(position).getId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                    intent.putExtra("link",tinTucs.get(position).getId());
                    startActivity(intent);
                }
            });
        }
    }
    List<String> getTitle(List<TinTuc> tinTucs){
        List list = new ArrayList();
        for (int i =0;i<tinTucs.size();i++){
           String a = tinTucs.get(i).getTitle()+"\n"+tinTucs.get(i).getPubDate();
            list.add(a);
        }
        return list;
    }
}