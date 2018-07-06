package com.example.android.postest.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.R;

import java.io.ByteArrayInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Leonardo on 7/5/2018.
 */

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.holder>{

    Context cntx;
    ArrayList<DetailTransaksi> detTransaksi;
    SQLite database;
    int idBarang, hargaBarang;
    ArrayList<Barang> arrBarang = new ArrayList<Barang>();
    Barang barang = new Barang();
    String namaBarang;

    public ReceiptAdapter(Context cntx, ArrayList<DetailTransaksi> detTransaksi){
        this.cntx = cntx;
        this.detTransaksi = detTransaksi;
    }

    class holder extends RecyclerView.ViewHolder{
        TextView mBarang, mJumlah, mTotalHarga, mHarga, a, b, x;

        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            mBarang = itemView.findViewById(R.id.textNamaBarang);
            mJumlah = itemView.findViewById(R.id.textJumlah);
            mTotalHarga = itemView.findViewById(R.id.textTotalHarga);
            mHarga = itemView.findViewById(R.id.textHarga);
            a = itemView.findViewById(R.id.a);
            b = itemView.findViewById(R.id.b);
            x = itemView.findViewById(R.id.txtX);
        }

        public void bindTo(DetailTransaksi detTransaksi){
            database = new SQLite(cntx);
            idBarang = detTransaksi.getIdBarang();
            arrBarang = database.getAllBarang();
            for (int i = 0; i < arrBarang.size(); i++){
                barang = arrBarang.get(i);
                if(idBarang == barang.getId()){
                    namaBarang = barang.getNama();

                    hargaBarang = barang.getHarga();
                }
            }
            if (detTransaksi.getJumlah() != 1){
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);
                mHarga.setVisibility(View.VISIBLE);
                x.setVisibility(View.VISIBLE);
                mJumlah.setVisibility(View.VISIBLE);
            }
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            mTotalHarga.setText(formatRupiah.format(hargaBarang*detTransaksi.getJumlah()));
            mBarang.setText(namaBarang);
            mJumlah.setText(String.valueOf(detTransaksi.getJumlah()));
            mHarga.setText(formatRupiah.format((double)hargaBarang));
        }

    }
    @NonNull
    @Override
    public ReceiptAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cntx).inflate(R.layout.receipt_layout, parent, false);
        ReceiptAdapter.holder hldr = new ReceiptAdapter.holder(view);
        return hldr;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptAdapter.holder holder, int position) {
        final DetailTransaksi data = detTransaksi.get(position);
        holder.bindTo(data);
    }

    @Override
    public int getItemCount() {
        return detTransaksi.size();
    }
}
