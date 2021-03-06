package com.example.personallibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.personallibrary.R;
import com.example.personallibrary.room.Book;
import com.example.personallibrary.room.manage.DBEngine;
import com.google.android.material.textfield.TextInputEditText;


public class AddBookActivity extends AppCompatActivity {

    private DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        dbEngine = new DBEngine(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("bookSetting", Context.MODE_PRIVATE);
        String sp1 = sp.getString("checked","false");
        String sp2 = sp.getString("size","15");
        boolean checked = Boolean.parseBoolean(sp1);
        int size = Integer.parseInt(sp2);
        TextView textName = findViewById(R.id.name);
        TextView textAuthor = findViewById(R.id.author);
        TextView textPublisher = findViewById(R.id.publisher);
        TextView textYear = findViewById(R.id.year);
        LinearLayout linearLayout = findViewById(R.id.price_layout);
        textName.setTextSize(size);
        textAuthor.setTextSize(size);
        textPublisher.setTextSize(size);
        textYear.setTextSize(size);
        linearLayout.setVisibility(checked?View.VISIBLE:View.INVISIBLE);
    }

    public void queryBook(View view) {
        TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        String ISBNNumber = textInputEditText.getText().toString();
        if (ISBNNumber.equals("")) {
            Toast.makeText(this,"ISBN?????????????????????",Toast.LENGTH_SHORT).show();
        }
        else {
            GetBookTask bookTask = new GetBookTask(this);
            bookTask.execute(ISBNNumber);
        }
    }

    public void addBook(View view) {
        TextView textName = findViewById(R.id.name);
        TextView textAuthor = findViewById(R.id.author);
        TextView textPublisher = findViewById(R.id.publisher);
        TextView textYear = findViewById(R.id.year);
        TextView textPrice = findViewById(R.id.price);
        if (textName.getText() == null || textAuthor.getText() == null || textPublisher.getText() == null || textYear.getText() == null || textPrice.getText() == null) {
            Toast.makeText(this,"????????????????????????????????????",Toast.LENGTH_SHORT).show();
            return;
        }
        String name = textName.getText().toString();
        String author = textAuthor.getText().toString();
        String publisher = textPublisher.getText().toString();
        String year = textYear.getText().toString();
        String price = textPrice.getText().toString();
        Book book = new Book(name, author, publisher, year, price);
        dbEngine.insertBooks(book);
        Toast.makeText(this,"?????????"+name+"?????????????????????????????????",Toast.LENGTH_SHORT).show();
    }

    public void setting(View view) {
        Intent intent = new Intent(AddBookActivity.this, BookSettingActivity.class);
        startActivity(intent);
    }


    //    ????????????????????????????????????????????????UI
    private static class GetBookTask extends AsyncTask<String, Void, String> {

        public static final String PRE_URL = "https://api.jike.xyz/situ/book/isbn/";
        public static final String API_KEY = "12665.9188ff54accb26e3b0bc8efe55703cea.3a26bdeba8ac2b6977fb6b348b837402";
        private final WeakReference<AddBookActivity> activityReference;

        public GetBookTask(AddBookActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            String tmp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL( PRE_URL + strings[0] + "?apikey=" + API_KEY);
                URLConnection connection = url.openConnection();
                connection.connect();
                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                while ((tmp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tmp);
                }
                return stringBuilder.toString();
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                Log.e("PersonalLibrary","ISBN???????????????????????????????????????????????????apikey????????????");
                return;
            }
            AddBookActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("PersonalLibrary","Activity????????????????????????Activity????????????");
                return;
            }
            JSONObject jsonObject = JSON.parseObject(s);
            //??????ISBN???????????????????????????????????????????????????
            if (jsonObject.getString("ret").equals("1")) {
                Toast.makeText(activity.getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                return;
            }
            //???????????????????????????????????????????????????????????????????????????????????????????????????????????????
            JSONObject data = jsonObject.getJSONObject("data");
            String name = data.getString("name") == null ? "???" : data.getString("name");
            String author = data.getString("author") == null ? "???" : data.getString("author");
            String publisher = data.getString("publishing") == null ? "???" : data.getString("publishing");
            String year = data.getString("published") == null ? "???" : data.getString("published");
            String price = data.getString("price") == null ? "???" : data.getString("price");
            TextView textName = activity.findViewById(R.id.name);
            TextView textAuthor = activity.findViewById(R.id.author);
            TextView textPublisher = activity.findViewById(R.id.publisher);
            TextView textYear = activity.findViewById(R.id.year);
            TextView textPrice = activity.findViewById(R.id.price);
            Button button_add = activity.findViewById(R.id.button_add);
            textName.setText(name);
            textAuthor.setText(author);
            textPublisher.setText(publisher);
            textYear.setText(year);
            textPrice.setText(price);
            button_add.setEnabled(true);
        }
    }
}