package com.example.finalexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    EditText et;
    Button btn;
    ListView lv;
    ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.editText);
        btn = findViewById(R.id.addBtn);
        lv = findViewById(R.id.lv);
        list = new ArrayList<String>();


        final ArrayAdapter<String> ad = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(ad);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String input = et.getText().toString();
                list.add(input);
                ad.notifyDataSetChanged();
            }
        });





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedFromList = lv.getItemAtPosition(position).toString();
                Intent intent;
                intent = new Intent(MainActivity.this, onClick.class);
                intent.putExtra("picList", list.get(position));
                startActivity(intent);

            }
        });


    }
}
