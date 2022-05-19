package com.example.personallibrary.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//单例模式数据库对象
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao getBookDao();
    private static BookDatabase INSTANCE;
    public static synchronized BookDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Log.e("fjx","是个傻逼");
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),BookDatabase.class,"book_database").build();
        }
        return INSTANCE;
    }
}
