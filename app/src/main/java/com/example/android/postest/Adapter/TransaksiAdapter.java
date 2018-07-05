package com.example.android.postest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.postest.DetailTransaksiActivity;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.Transaksi;
import com.example.android.postest.R;
import com.example.android.postest.SetOnItemRecycleListener;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Aulia Ramadhan on 03/07/2018.
 */

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.holder> {

    private Context cntx;
    private List<Transaksi> list;
    //private SetOnItemRecycleListener mItemClickListener;

    class holder extends RecyclerView.ViewHolder{
        //deklarasi variable yang akan digunakan
        public TextView mCustomer, mTanggal, mTotal, mUser;
        public CardView cardViewTransaksi;
        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            mCustomer = itemView.findViewById(R.id.txtCustomer);
            mTanggal = itemView.findViewById(R.id.txtTanggalPembelian);
            mTotal = itemView.findViewById(R.id.txttotalpenjualan);
            mUser = itemView.findViewById(R.id.txtUser);
            cardViewTransaksi = itemView.findViewById(R.id.cvRiwayatTransaksi);
        }

        public void bindTo(Transaksi transaksi){
            mCustomer.setText(transaksi.getCustomer());
            mTotal.setText(String.valueOf(transaksi.getTotalPenjualan()));
            mTanggal.setText(transaksi.getTanggal());
            mUser.setText(transaksi.getUser());
        }

    }

    public TransaksiAdapter(Context cntx, List<Transaksi> list) {
        this.cntx = cntx;
        this.list = list;
    }

//    public TransaksiAdapter(Context cntx, List<Transaksi> list, SetOnItemRecycleListener mItemClickListener){
//        this.cntx=cntx;
//        this.list=list;
//        this.mItemClickListener = mItemClickListener;
//    }

    @Override
    public TransaksiAdapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.transaksiriwayat_layout, parent, false);
        final TransaksiAdapter.holder hldr = new TransaksiAdapter.holder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mItemClickListener.onItemClick(v, hldr.getAdapterPosition());
//            }
//        });
        return hldr;
    }

    @Override
    public void onBindViewHolder(TransaksiAdapter.holder holder, int position) {
        final Transaksi data = list.get(position);
        holder.bindTo(data);

        holder.cardViewTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(cntx, DetailTransaksiActivity.class);
                i.putExtra("id", data.getId());
                i.putExtra("tanggal", data.getTanggal());
                i.putExtra("customer", data.getCustomer());
                i.putExtra("user", data.getUser());
                i.putExtra("total", data.getTotalPenjualan());
                cntx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Transaksi getData(int position){
        return list.get(position);
    }

}
