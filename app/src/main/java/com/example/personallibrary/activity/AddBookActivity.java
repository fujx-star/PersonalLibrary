package com.example.personallibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.textfield.TextInputEditText;


public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void queryBook(View view) {
        TextInputEditText textInputEditText = findViewById(R.id.textInputEditText);
        String ISBNNumber = textInputEditText.getText().toString();
        if (ISBNNumber.equals("")) {
            Toast.makeText(this,"ISBN号码不能为空！",Toast.LENGTH_SHORT).show();
        }
        else {
            GetBookTask bookTask = new GetBookTask(this);
            bookTask.execute("9787020024760");
        }
    }

    public void addBook(View view) {
        TextView textName = findViewById(R.id.name);
        TextView textAuthor = findViewById(R.id.author);
        TextView textPublisher = findViewById(R.id.publisher);
        TextView textYear = findViewById(R.id.year);
    }

//    通过静态内部类加弱引用的方式更新UI
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
            AddBookActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            JSONObject jsonObject = JSON.parseObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            TextView textName = activity.findViewById(R.id.name);
            TextView textAuthor = activity.findViewById(R.id.author);
            TextView textPublisher = activity.findViewById(R.id.publisher);
            TextView textYear = activity.findViewById(R.id.year);
            textName.setText(data.getString("name"));
            textAuthor.setText(data.getString("author"));
            textPublisher.setText(data.getString("publishing"));
            textYear.setText(data.getString("published"));
        }
    }
}