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
        int totalHarga = i.getIntExtra("totalHarga", 0);
        int cash = i.getIntExtra("totalCash", 0);

        tvTotalHarga.setText(String.valueOf(totalHarga));
        tvTotalCash.setText(String.valueOf(cash));
        int kembali = cash - totalHarga;
        String totalKembali = String.valueOf(kembali);



        tvKembalian.setText(totalKembali);


    }
}
