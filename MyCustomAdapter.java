package com.example.marlen.appjedi;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by marlen on 02/02/2016.
 */
public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder> {

    ArrayList<User> users;

    Uri pic;

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
        //TODO aqui estaba intentando mostrar las fotos de perfil de cada user en el ranking
        //TODO pero no me deja hacerlo asi por el getContentResolver, i nose otra manera de hacerlo..
        //TODO aunque si esto funcionara me petaria igual por los malditos permisos esos raros,
        //TODO el mismo error que sale cuando quiero mantener la foto del perfil de usuario.
        /*pic = Uri.parse(users.get(position).getImage().toString());
        try{
            holder.foto.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver, pic));
        }catch (IOException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public int getItemCount() {
        if(users!=null) return users.size();
        else return 0;
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder{
        public TextView name, points;
        //public ImageView foto;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.name = (TextView)itemView.findViewById(R.id.txt_name);
            this.points = (TextView)itemView.findViewById(R.id.txt_points);
          //  this.foto = (ImageView)itemView.findViewById(R.id.fotoPerfil);
        }
    }

    public void setData (ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }
}
