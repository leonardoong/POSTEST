package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class BarangActivity extends AppCompatActivity {

    private Button mTambahBarang;
    private RecyclerView rv;
    BarangAdapter adapter;
    SQLite dbBarang;
    ArrayList<Barang> listBarang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        rv = (RecyclerView)findViewById(R.id.recview);
        mTambahBarang = (Button)findViewById(R.id.tambahBarang);
        mTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambahBarang = new Intent(BarangActivity.this, TambahBarangActivity.class);
                startActivity(tambahBarang);
            }
        });

        listBarang = new ArrayList<>();
        //membuat database baru
        dbBarang = new SQLite(this);
        //memanggil method readdata
        dbBarang.ReadData(listBarang);


        adapter = new BarangAdapter(this, listBarang);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }
}
