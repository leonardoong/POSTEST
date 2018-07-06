package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.android.postest.Adapter.DetailTransaksiAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.DetailTransaksi;

import java.util.ArrayList;
import java.util.List;

public class DetailTransaksiActivity extends AppCompatActivity {
    int idTransaksi, totalPenjualan;
    String tanggal, customer, user;
    DetailTransaksiAdapter adapter;
    TextView mTanggal, mCustomer, mUser, mTotal;
    RecyclerView rv;
    ArrayList<DetailTransaksi> arrDetailTransaksi = new ArrayList<DetailTransaksi>();
    ArrayList<DetailTransaksi> arrDetail = new ArrayList<DetailTransaksi>();
    DetailTransaksi detail = new DetailTransaksi();
    SQLite database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);

        Intent i = getIntent();
        idTransaksi = i.getIntExtra("id", 0);
        tanggal = i.getStringExtra("tanggal");
        customer = i.getStringExtra("customer");
        user = i.getStringExtra("user");
        totalPenjualan = i.getIntExtra("total",0);

        mTanggal = findViewById(R.id.tvTanggal);
        mCustomer = findViewById(R.id.tvNamaCustomer);
        mUser = findViewById(R.id.tvUser);
        mTotal = findViewById(R.id.tvTotal);


        mTanggal.setText(tanggal);
        mCustomer.setText(customer);
        mUser.setText(user);
        mTotal.setText(String.valueOf(totalPenjualan));

        rv = findViewById(R.id.listViewDetail);
        database = new SQLite(this);

        arrDetailTransaksi = database.getAllDetailTransaksi();
        for (int j = 0 ; j< arrDetailTransaksi.size(); j++){
            detail = arrDetailTransaksi.get(j);
            if (detail.getIdTransaksi() == idTransaksi){
                arrDetail.add(detail);
            }
        }

        adapter = new DetailTransaksiAdapter(this, arrDetail);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);


    }
}
