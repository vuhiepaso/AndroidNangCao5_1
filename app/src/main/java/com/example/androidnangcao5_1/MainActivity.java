package com.example.androidnangcao5_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<News> newsList = new ArrayList<>();

                // khai bao try catch de bat loi~
                try {

                    URL url = new URL("https://vtc.vn/rss/kinh-te.rss");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //phan loi
                    Log.e("loi",""+httpURLConnection.getInputStream() );
                    InputStream inputStream = httpURLConnection.getInputStream();


                    // khoi tao doi tuong xmlpullparser
                    XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                    xmlPullParserFactory.setNamespaceAware(false);


                    XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();

                    // truyen du lieu vao xmlpullparser tien hanh boc tach xml
                    xmlPullParser.setInput(inputStream, "utf-8");

                    int eventType = xmlPullParser.getEventType();
                    News news = null;
                    String text = "";
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String name = xmlPullParser.getName();
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                if (name.equals("item")) {
                                    news = new News();
                                }
                                break;

                            case XmlPullParser.TEXT:
                                text = xmlPullParser.getText();
                                break;

                            case XmlPullParser.END_TAG:
                                if (news != null && name.equalsIgnoreCase("title")) {
                                    news.title = text;
                                    Log.e("title", ""+news.title);
                                } else if (news != null && name.equalsIgnoreCase("description")) {
                                    news.description = text;
                                } else if (news != null && name.equalsIgnoreCase("pubdate")) {
                                    news.pubDate = text;
                                } else if (news != null && name.equalsIgnoreCase("link")) {
                                    news.link = text;
                                } else if (news != null && name.equalsIgnoreCase("guiid")) {
                                    news.guiid = text;
                                } else if (name.equalsIgnoreCase("item")) {
                                    newsList.add(news);
                                }
                                break;

                        }
                        // di chuyen toi tag ke tiep
                        eventType = xmlPullParser.next(); //move to next element
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
//                    Log.e("Exception", e.getMessage());
                }
                Log.e("sixe list", ""+newsList.size());
//        listView.findViewById(R.id.listView);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,newsList);
//        listView.setAdapter(arrayAdapter);
                Toast.makeText(MainActivity.this, ""+newsList.size(), Toast.LENGTH_SHORT).show();
            }

        });

    }

}