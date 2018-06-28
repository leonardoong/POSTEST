package com.example.android.postest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.CardView;


import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.holder> {
    private Context cntx;
    private List<Barang> list;

    public BarangAdapter(Context cntx, List<Barang> list){
        this.cntx=cntx;
        this.list=list;
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.barang_layout, parent, false);
        holder hldr = new holder(view);
        return hldr;
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {
        Barang data = list.get(position);
        holder.namaBarang.setText(data.getNama());
        byte[] test = data.getGambar();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(test);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        holder.gambarBarang.setImageBitmap(theImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Barang getData(int position){
        return list.get(position);
    }

    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable yang akan digunakan
        public TextView namaBarang;
        public ImageView gambarBarang;
        public CardView cardv;
        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            namaBarang = itemView.findViewById(R.id.txtNamaBarang);
            gambarBarang = itemView.findViewById(R.id.ivImgBarang);
            cardv = itemView.findViewById(R.id.cardlist);
        }
    }
}

