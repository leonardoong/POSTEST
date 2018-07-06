package com.example.android.postest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.DetailBarangActivity;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 7/2/2018.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.holder> {

    private Context cntx;
    private ArrayList<Barang> list;
    SQLite database;

    public CheckoutAdapter(Context cntx, ArrayList<Barang> list){
        this.cntx=cntx;
        this.list=list;
        database = new SQLite(cntx);
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.checkout_layout, parent, false);
        holder hldr = new holder(view);
        return hldr;
    }



    @Override
    public void onBindViewHolder(holder holder, int position) {
        final Barang data = list.get(position);
        holder.bindTo(data);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Barang getData(int position){
        return list.get(position);
    }

    class holder extends RecyclerView.ViewHolder {
        //deklarasi variable yang akan digunakan
        public TextView mBarang, mHarga, mJumlah, mTotalHarga;
        public ImageView mGambarBarang;
        public CardView cardViewDetailTransaksi;
        int idBarang, hargaBarang, totalHarga;
        String namaBarang;
        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            mBarang = itemView.findViewById(R.id.txtNamaBarang);
            mJumlah = itemView.findViewById(R.id.tvJumlah);
            mHarga = itemView.findViewById(R.id.txtHargaBarang);
            mGambarBarang = itemView.findViewById(R.id.gambarBarang);
            cardViewDetailTransaksi = itemView.findViewById(R.id.cardViewDetailTransaksi);
            mTotalHarga = itemView.findViewById(R.id.tvHarga);
        }

        public void bindTo(Barang barang){

            idBarang = barang.getId();
            Barang arrBarang = database.getBarang(String.valueOf(idBarang));
            namaBarang = arrBarang.getNama();
            hargaBarang = arrBarang.getHarga();
            mTotalHarga.setText(String.valueOf(hargaBarang*barang.getJumlah()));
            mBarang.setText(namaBarang);
            mJumlah.setText(String.valueOf(barang.getJumlah()));
            mHarga.setText(String.valueOf(hargaBarang));
        }
    }

//    private Context mContext;
//    int mResource;
//    ArrayList<Barang> listBarang;
//
//    public CheckoutAdapter(Context context, int resource, ArrayList<Barang> object) {
//        super(context, R.layout.checkout_layout, object);
//        this.mContext = context;
//        this.mResource= resource;
//        this.listBarang = object;
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position,  View convertView,  ViewGroup parent) {
//        String namaBarang = getItem(position).getNama();
//        int hargaBarang = getItem(position).getHarga();
//        String strHarga = String.valueOf(hargaBarang);
//
//        Barang barang = new Barang(namaBarang, hargaBarang);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View listViewItem = inflater.inflate(R.layout.checkout_layout, null, true);
//
//        TextView tvNamaBarang = (TextView)listViewItem.findViewById(R.id.tvNamaBarang);
//        TextView tvHargaBarang = (TextView)listViewItem.findViewById(R.id.tvHargaBarang);
//
//        tvNamaBarang.setText(namaBarang);
//        tvHargaBarang.setText(strHarga);
//        return listViewItem;
//    }
}
