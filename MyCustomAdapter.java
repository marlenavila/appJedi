package com.example.marlen.appjedi;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marlen on 02/02/2016.
 */
public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder> {

    ArrayList<User> users;

    MyCustomAdapter(){ users = new ArrayList<>();}

    @Override
    public MyCustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rows, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.AdapterViewHolder holder, int position){
        holder.name.setText(users.get(position).getName());
        holder.points.setText(users.get(position).getPoints().toString());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView name, points;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.name = (TextView)itemView.findViewById(R.id.txt_name);
            this.points = (TextView)itemView.findViewById(R.id.txt_points);
        }
    }

    public void setData (ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }
}
