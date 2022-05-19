package com.example.personallibrary.room.manage;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personallibrary.R;
import com.example.personallibrary.activity.BookListActivity;
import com.example.personallibrary.recyclerview.MyAdapter;
import com.example.personallibrary.room.Book;
import com.example.personallibrary.room.BookDao;
import com.example.personallibrary.room.BookDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DBEngine {
    private BookDao bookDao;

    public DBEngine(Context context) {
        BookDatabase bookDatabase = BookDatabase.getInstance(context);
        bookDao = bookDatabase.getBookDao();
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void insertBooks(Book...books) {
        new InsertAsyncTask(bookDao).execute(books);
    }

    static class InsertAsyncTask extends AsyncTask<Book,Void,Void> {
        private BookDao dao;
        public InsertAsyncTask(BookDao bookDao) {
            dao = bookDao;
        }
        @Override
        protected Void doInBackground(Book ...books) {
            dao.insertStudents(books);
            return null;
        }
    }

    public void updateBooks(Book ...books) {
        new UpdateAsyncTask(bookDao).execute(books);
    }

    static class UpdateAsyncTask extends AsyncTask<Book,Void,Void> {
        private BookDao dao;
        public UpdateAsyncTask(BookDao bookDao) {
            dao = bookDao;
        }
        @Override
        protected Void doInBackground(Book ...books) {
            dao.updateBooks(books);
            return null;
        }
    }

    public void deleteBooks(Book ...books) {
        new DeleteAsyncTask(bookDao).execute(books);
    }

    static class DeleteAsyncTask extends AsyncTask<Book,Void,Void> {
        private BookDao dao;
        public DeleteAsyncTask(BookDao bookDao) {
            dao = bookDao;
        }
        @Override
        protected Void doInBackground(Book ...books) {
            dao.deleteBooks(books);
            return null;
        }
    }

    public void queryBooks(BookListActivity context,int order) {
        new QueryAsyncTask(bookDao,context).execute(order);
    }

    static class QueryAsyncTask extends AsyncTask<Integer, Void, List<Book>> {

        private BookDao dao;
        private final WeakReference<BookListActivity> activityReference;

        public QueryAsyncTask(BookDao bookDao, BookListActivity context) {
            dao = bookDao;
            activityReference = new WeakReference<>(context);
        }
        @Override
        protected List<Book> doInBackground(Integer ...integers) {
            List<Book> books = new ArrayList<>();
            switch (integers[0]) {
                case 1:
                    books = dao.selectBooks();
                    break;
                case 2:
                    books = dao.selectBooksInYearOrder();
                    break;
                case 3:
                    books = dao.selectBooksInAuthorOrder();
                    break;
            }
            System.out.println(books);
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            BookListActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                Log.e("PersonalLibrary","Activity弱引用创建失败或Activity已经结束");
            }
            else {
                RecyclerView recyclerView = activity.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(new MyAdapter(books, activity));
            }
        }
    }
}
