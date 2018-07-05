package com.example.android.postest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.Objek.Transaksi;
import com.example.android.postest.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 7/4/2018.
 */

public class DetailTransaksiAdapter extends RecyclerView.Adapter<DetailTransaksiAdapter.holder> {
    private Context cntx;
    private List<DetailTransaksi> list;
    SQLite database;
    ArrayList<Barang> arrBarang = new ArrayList<Barang>();
    Barang barang = new Barang();
    int idBarang, hargaBarang, totalHarga;
    String namaBarang;
    byte[] byteGambar;

    public DetailTransaksiAdapter(Context cntx, ArrayList<DetailTransaksi> list) {
        this.cntx = cntx;
        this.list = list;
    }

    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable yang akan digunakan
        public TextView mBarang, mHarga, mJumlah, mTotalHarga;
        public ImageView mGambarBarang;
        public CardView cardViewDetailTransaksi;
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

        public void bindTo(DetailTransaksi detTransaksi){
            database = new SQLite(cntx);
            idBarang = detTransaksi.getIdBarang();
            arrBarang = database.getAllBarang();
            for (int i = 0; i < arrBarang.size(); i++){
                barang = arrBarang.get(i);
                if(idBarang == barang.getId()){
                    namaBarang = barang.getNama();
                    byteGambar = barang.getGambar();
                    hargaBarang = barang.getHarga();
                }
            }
            mTotalHarga.setText(String.valueOf(hargaBarang*detTransaksi.getJumlah()));
            mBarang.setText(namaBarang);
            mJumlah.setText(String.valueOf(detTransaksi.getJumlah()));
            mHarga.setText(String.valueOf(hargaBarang));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(byteGambar);
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            mGambarBarang.setImageBitmap(theImage);
        }

    }

    @Override
    public DetailTransaksiAdapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.detail_transaksi_layout, parent, false);
        DetailTransaksiAdapter.holder hldr = new DetailTransaksiAdapter.holder(view);
        return hldr;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailTransaksiAdapter.holder holder, int position) {
        final DetailTransaksi data = list.get(position);
        holder.bindTo(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
