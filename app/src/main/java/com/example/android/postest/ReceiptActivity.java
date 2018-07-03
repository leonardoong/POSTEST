package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {
    TextView tvKembalian, tvTotalHarga, tvTotalCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        tvKembalian = findViewById(R.id.tvKembalian);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvTotalCash = findViewById(R.id.tvTotalCash);

        Intent i = getIntent();
        String totalHarga = i.getStringExtra("totalHarga");
        String totalCash = i.getStringExtra("totalCash");

        tvTotalHarga.setText(totalHarga);
        tvTotalCash.setText(totalCash);
        int harga = Integer.parseInt(totalHarga);
        int cash = Integer.parseInt(totalCash);
        int kembali = cash - harga;
        String totalKembali = String.valueOf(kembali);

        tvKembalian.setText(totalKembali);
    }
}
