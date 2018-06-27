package com.example.android.postest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.IllegalFormatCodePointException;

public class TambahBarangActivity extends AppCompatActivity {

    EditText mNamaBarang, mHargaBarang,
            mStok, mDeskripsi;
    Button mSimpanBarang;
    SQLite database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        mNamaBarang = (EditText)findViewById(R.id.namaBarang);
        mHargaBarang = (EditText)findViewById(R.id.hargaBarang);
        mStok = (EditText)findViewById(R.id.stok);
        mDeskripsi = (EditText)findViewById(R.id.deskripsi);

        database = new SQLite(this);

        mSimpanBarang = (Button)findViewById(R.id.simpanBarang);
        mSimpanBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = 1;
                String namaBarang = mNamaBarang.getText().toString();
                String hargaBarang = mHargaBarang.getText().toString();
                String stokBarang = mStok.getText().toString();
                String deskripsi = mDeskripsi.getText().toString();


                if(!hargaBarang.isEmpty() || !stokBarang.isEmpty())
                {
                    try
                    {
                        if(TextUtils.isEmpty(namaBarang) || TextUtils.isEmpty(hargaBarang) || TextUtils.isEmpty(stokBarang)
                                || TextUtils.isEmpty(deskripsi)){
                            Toast.makeText(TambahBarangActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();

                            mNamaBarang.setText(null);
                            mHargaBarang.setText(null);
                            mStok.setText(null);
                            mDeskripsi.setText(null);
                        }else {
                            int intHarga = Integer.parseInt(hargaBarang);
                            int intStok = Integer.parseInt(stokBarang);

                            database.createBarang(new Barang((id), (namaBarang), (intHarga), (intStok), (deskripsi)));

                            Toast.makeText(TambahBarangActivity.this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        }
                    }catch (NumberFormatException e)
                    {
                        Log.e("Parse Int:",e.getMessage());
                    }
                }


            }
        });

    }
}
