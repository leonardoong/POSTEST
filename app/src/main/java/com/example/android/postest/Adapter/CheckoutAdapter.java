package com.example.android.postest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.postest.Objek.Barang;
import com.example.android.postest.R;

import java.util.ArrayList;

/**
 * Created by Leonardo on 7/2/2018.
 */

public class CheckoutAdapter extends ArrayAdapter<Barang> {
    private Context mContext;
    int mResource;
    ArrayList<Barang> listBarang;

    public CheckoutAdapter(Context context, int resource, ArrayList<Barang> object) {
        super(context, R.layout.checkout_layout, object);
        this.mContext = context;
        this.mResource= resource;
        this.listBarang = object;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        String namaBarang = getItem(position).getNama();
        int hargaBarang = getItem(position).getHarga();
        String strHarga = String.valueOf(hargaBarang);

        Barang barang = new Barang(namaBarang, hargaBarang);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View listViewItem = inflater.inflate(R.layout.checkout_layout, null, true);

        TextView tvNamaBarang = (TextView)listViewItem.findViewById(R.id.tvNamaBarang);
        TextView tvHargaBarang = (TextView)listViewItem.findViewById(R.id.tvHargaBarang);

        tvNamaBarang.setText(namaBarang);
        tvHargaBarang.setText(strHarga);
        return listViewItem;
    }
}
