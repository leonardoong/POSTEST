package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.postest.Adapter.ReceiptAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.Objek.Transaksi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ReceiptActivity extends AppCompatActivity {
    TextView tvKembalian, tvTotalHarga, tvTotalCash, tvTanggal, tvUser, tvCustomer;
    RecyclerView rv;
    SQLite database;
    ArrayList<DetailTransaksi> arrDt = new ArrayList<DetailTransaksi>();
    Transaksi transaksi = new Transaksi();
    ReceiptAdapter adapter;
    Button mNewTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        this.setTitle("Receipt");

        tvKembalian = findViewById(R.id.tvKembalian);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvTotalCash = findViewById(R.id.tvTotalCash);
        tvTanggal = findViewById(R.id.tvTanggalBeli);
        tvCustomer = findViewById(R.id.tvCustomer);
        tvUser = findViewById(R.id.tvUsername);
        mNewTransaksi = findViewById(R.id.btnSales);

        rv = findViewById(R.id.rv);

        database = new SQLite(this);

        Intent i = getIntent();
        int totalHarga = i.getIntExtra("totalHarga", 0);
        int cash = i.getIntExtra("totalCash", 0);
        int idTransaksi = i.getIntExtra("idTransaksi",0 );

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        transaksi = database.getTransaksi(String.valueOf(idTransaksi));
        tvUser.setText(transaksi.getUser());
        tvCustomer.setText(transaksi.getCustomer());
        tvTanggal.setText(transaksi.getTanggal());

        tvTotalHarga.setText(formatRupiah.format((double)totalHarga));
        tvTotalCash.setText(formatRupiah.format((double)cash));
        int kembali = cash - totalHarga;
        tvKembalian.setText(formatRupiah.format((double)kembali));

        arrDt = database.getDetailTransaksi(String.valueOf(idTransaksi));

        adapter = new ReceiptAdapter(this, arrDt);

        rv.setHasFixedSize(true);
        rv.setVerticalScrollBarEnabled(false);
        rv.setNestedScrollingEnabled(false);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        mNewTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReceiptActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
