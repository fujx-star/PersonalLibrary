package com.example.personallibrary.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personallibrary.R;
import com.example.personallibrary.activity.AddBookActivity;
import com.example.personallibrary.activity.BookListActivity;
import com.example.personallibrary.room.Book;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Book> data;
    private Context context;

    public MyAdapter(List<Book> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerview_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.author.setText(data.get(position).getAuthor());
        holder.publisher.setText(data.get(position).getPublisher());
        holder.year.setText(data.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView author;
        private TextView publisher;
        private TextView year;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            author = itemView.findViewById(R.id.item_author);
            publisher = itemView.findViewById(R.id.item_publisher);
            year = itemView.findViewById(R.id.item_year);
        }
    }
}
