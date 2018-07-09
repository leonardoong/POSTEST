package com.example.android.postest.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.postest.AddJumlahActivity;
import com.example.android.postest.CheckoutActivity;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.MainActivity;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Leonardo on 7/2/2018.
 */

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.holder> {

    public static final String ID_BARANG = "idbarang";
    public static final String JUMLAH = "jumlah";
    public static final int REQUEST_CODE = 1;
    public static final String POSISI = "posisi";
    private Context cntx;
    private ArrayList<Barang> list;
    SQLite database;

    public CheckoutAdapter(Context cntx, ArrayList<Barang> list){
        this.cntx=cntx;
        this.list=list;
        this.database = new SQLite(cntx);
    }

    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //membuat view baru
        View view = LayoutInflater.from(cntx).inflate(R.layout.checkout_layout, parent, false);
        holder hldr = new holder(view);
        return hldr;
    }



    @Override
    public void onBindViewHolder(holder holder, final int position) {
        final Barang data = list.get(position);
        holder.bindTo(data);
        holder.cardViewDetailTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cntx, AddJumlahActivity.class);
                i.putExtra(ID_BARANG, data.getId());
                i.putExtra(JUMLAH, data.getJumlah());
                i.putExtra(POSISI, position);
                ((Activity) cntx).startActivityForResult(i, REQUEST_CODE);
            }
        });

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
        public TextView mNamaBarang, mHarga, mJumlah, mTotalHarga;
        public ImageView mGambarBarang;
        public CardView cardViewDetailTransaksi;
        int idBarang, hargaBarang, totalHarga;
        String namaBarang;
        public holder(View itemView){
            super(itemView);

            //mengakses id text view pada layout dan juga cardview
            mNamaBarang = itemView.findViewById(R.id.textNamaBarang);
            mJumlah = itemView.findViewById(R.id.textJumlah);
            mHarga = itemView.findViewById(R.id.textHarga);
            mGambarBarang = itemView.findViewById(R.id.gambarBarang);
            cardViewDetailTransaksi = itemView.findViewById(R.id.cardViewDetailTransaksi);
            mTotalHarga = itemView.findViewById(R.id.textTotalHarga);
        }

        public void bindTo(Barang barang){

            if (barang.getJumlah() != 1){
                itemView.findViewById(R.id.a).setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.b).setVisibility(View.VISIBLE);
                mHarga.setVisibility(View.VISIBLE);
                itemView.findViewById(R.id.txtX).setVisibility(View.VISIBLE);
                mJumlah.setVisibility(View.VISIBLE);
            }

            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

            idBarang = barang.getId();
            Barang arrBarang = database.getBarang(String.valueOf(idBarang));
            namaBarang = arrBarang.getNama();
            hargaBarang = arrBarang.getHarga();
            mTotalHarga.setText(formatRupiah.format((double)hargaBarang*barang.getJumlah()));
            mNamaBarang.setText(namaBarang);
            mJumlah.setText(String.valueOf(barang.getJumlah()));
            mHarga.setText(formatRupiah.format((double)hargaBarang));
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
