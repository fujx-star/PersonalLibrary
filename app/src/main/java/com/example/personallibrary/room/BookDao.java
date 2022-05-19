package com.example.personallibrary.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insertStudents(Book ...books);

    @Update
    void updateBooks(Book ...books);

    @Delete
    void deleteBooks(Book ...books);

    @Query("select * from book order by id")
    List<Book> selectBooks();

    @Query("select * from book order by year")
    List<Book> selectBooksInYearOrder();

    @Query("select * from book order by author")
    List<Book> selectBooksInAuthorOrder();
}
