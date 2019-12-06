package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class onClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click);

        String query;
        Intent i = getIntent();
        query = i.getStringExtra("picList");


        final OkHttpClient client = new OkHttpClient();

        String url = "https://www.reddit.com/r/" + query + "/.json";

        final Request req = new Request
                            .Builder()
                            .url(url)
                            .build();
        ArrayList<String> pictures = new ArrayList<String>();

        ListView lv = findViewById(R.id.lv);
        ArrayList<String> sendPics = new ArrayList<String>();

        final ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sendPics);
        lv.setAdapter(ad);




        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(req).execute();
                    String text = response.body().string();
                    JSONObject object = (JSONObject) new JSONTokener(text).nextValue();
                    JSONArray listings = object.getJSONObject("data").getJSONArray("children");
                    ArrayList<String> sendPics = new ArrayList<String>();

                    for (int j = 0; j < listings.length(); j++) {
                        JSONObject item = listings.getJSONObject(j);
                        pictures.add(item.getJSONObject("data").getString("title"));
                    }
                    runOnUiThread(() -> {
                        String result = pictures.stream().reduce("", (a, b) -> a += "\n" + b);
                        sendPics.add(result);
                        ad.notifyDataSetChanged();

                    });

                } catch (IOException | JSONException e) {
                    runOnUiThread(() -> {
                        Toast.makeText(onClick.this, e.toString(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        };


        t.start();



    }
}
