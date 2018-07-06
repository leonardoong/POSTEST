package com.example.android.postest;

import android.app.Activity;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class SuccessActivity extends AppCompatActivity {

    private TextView mKembalian,mCash;
    private Button mPrint;
    int totalHarga, cash, idTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        this.setTitle("Transaksi Berhasil");

        mKembalian = (TextView)findViewById(R.id.txtKembalian);
        mCash = (TextView)findViewById(R.id.txtCash);
        mPrint = (Button)findViewById(R.id.buttonPrint);

        Intent i = getIntent();
        totalHarga = i.getIntExtra("totalHarga", 0);
        cash = i.getIntExtra("totalCash", 0);
        idTransaksi = i.getIntExtra("idTransaksi",0 );

        int kembali = cash - totalHarga;
        mKembalian.setText(formatRupiah.format((double)kembali));
        mCash.setText(formatRupiah.format((double)cash));

        mPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( SuccessActivity.this, ReceiptActivity.class);
                i.putExtra("totalHarga",totalHarga);
                i.putExtra("totalCash",cash);
                i.putExtra("idTransaksi", idTransaksi);
                startActivity(i);
            }
        });

    }
}
