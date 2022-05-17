package com.example.personallibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.personallibrary.R;
import com.google.android.material.textfield.TextInputEditText;


public class AddBookActivity extends AppCompatActivity {
    TextView textName = null;
    TextView textAuthor = null;
    TextView textPublisher = null;
    TextView textYear = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }
    public void queryBook(View view) {
        TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        String ISBNNumber = textInputEditText.getText().toString();
        if (ISBNNumber.equals("")) {
//            Toast.makeText(this, "shabi", Toast.LENGTH_SHORT).show();
//        } else {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String tmp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
//                URL url = new URL("https://api.jike.xyz/situ/book/isbn/" + ISBNNumber + "?apikey=12665.9188ff54accb26e3b0bc8efe55703cea.3a26bdeba8ac2b6977fb6b348b837402");
                URL url = new URL("https://www.baidu.com");
                URLConnection connection = url.openConnection();
                connection.connect();
//                inputStream = connection.getInputStream();
//                inputStreamReader = new InputStreamReader(inputStream, "GBK");
//                bufferedReader = new BufferedReader(inputStreamReader);
//                while ((tmp = bufferedReader.readLine()) != null) {
//                    stringBuilder.append(tmp);
//                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//            Toast.makeText(this, stringBuilder, Toast.LENGTH_SHORT).show();
//            JSONObject jsonObject = JSON.parseObject(stringBuilder.toString());
//            JSONObject data = jsonObject.getJSONObject("data");
//            System.out.println(data.getString("name"));
//            textName = findViewById(R.id.name);
//            textAuthor = findViewById(R.id.author);
//            textPublisher = findViewById(R.id.publisher);
//            textYear = findViewById(R.id.year);
//            textName.setText(data.getString("name"));
//            textAuthor.setText(data.getString("author"));
//            textPublisher.setText(data.getString("publishing"));
//            textYear.setText(data.getInteger("year"));
        }
    }

    public void addBook(View view) {
        Toast.makeText(this, "你是个傻逼",Toast.LENGTH_SHORT).show();
    }
}