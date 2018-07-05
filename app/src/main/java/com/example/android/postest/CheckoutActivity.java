package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.postest.Adapter.CheckoutAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.Transaksi;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    TextView totalHarga, namaBarang;
    Button btnCash;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        namaBarang = findViewById(R.id.namaBarang);
        totalHarga = findViewById(R.id.totalHarga);
        btnCash = findViewById(R.id.btnPemabayaranCash);

        lv = findViewById(R.id.listView);


        Intent data = getIntent();
        final String strHarga = data.getStringExtra("totalHarga");
        final ArrayList<Barang> arrBarang = (ArrayList<Barang>)data.getSerializableExtra("arrayList");

        totalHarga.setText(strHarga);
        CheckoutAdapter adapter = new CheckoutAdapter
                (CheckoutActivity.this, R.layout.checkout_layout, arrBarang);
        lv.setAdapter(adapter);

        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(CheckoutActivity.this, CashActivity.class);
                i.putExtra("totalHarga", strHarga);
                i.putExtra("arrBarang", arrBarang);
                startActivity(i);*/
                ///PINDAH CASH ACTIVITY





                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CheckoutActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cash, null);
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });
    }
}
