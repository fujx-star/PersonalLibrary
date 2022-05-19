package com.example.personallibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.personallibrary.R;
import com.example.personallibrary.recyclerview.MyAdapter;
import com.example.personallibrary.room.Book;
import com.example.personallibrary.room.BookDao;
import com.example.personallibrary.room.BookDatabase;
import com.example.personallibrary.room.manage.DBEngine;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    DBEngine dbEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        dbEngine = new DBEngine(this);
        dbEngine.queryBooks(this,1);
    }


    public void year_order(View view) {
        dbEngine.queryBooks(this,2);
    }

    public void author_order(View view) {
        dbEngine.queryBooks(this,3);
    }
}