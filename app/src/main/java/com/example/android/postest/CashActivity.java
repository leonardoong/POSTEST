package com.example.android.postest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.Transaksi;

import java.util.Date;
import java.util.Locale;

public class CashActivity extends AppCompatActivity {

    TextView mharga;
    EditText mCash, mCustomer;
    Button mCharge;
    String harga, cash, customer, tanggal;
    SQLite database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        mharga = findViewById(R.id.txtTotalHarga);
        mCash = findViewById(R.id.txtTotalCash);
        mCharge = findViewById(R.id.btnCharge);
        mCustomer = findViewById(R.id.etNamaCustomer);

        database = new SQLite(this);

        Intent i = getIntent();
        harga = i.getStringExtra("totalHarga");

        mharga.setText(harga);

        mCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                cash = mCash.getText().toString();
                customer = mCustomer.getText().toString();
                int intCash = Integer.parseInt(cash);
                int intHarga = Integer.parseInt(harga);
                if (TextUtils.isEmpty(cash) || TextUtils.isEmpty(customer)){
                    Toast.makeText(CashActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                }else if (intCash < intHarga || TextUtils.isEmpty(customer)){
                    Toast.makeText(CashActivity.this, "Uang yang dimasukkan kurang dan lengkapi data", Toast.LENGTH_SHORT).show();
                }else if (intCash < intHarga){
                    Toast.makeText(CashActivity.this, "Uang yang dimasukkan kurang", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
                    String user = prefs.getString("username", "");
                    database.createTransaksi(new Transaksi((tanggal),(customer),(user),(intHarga)));

                    Intent i = new Intent( CashActivity.this, ReceiptActivity.class);
                    i.putExtra("totalHarga",harga);
                    i.putExtra("totalCash",cash);
                    startActivity(i);
                }
            }
        });
    }
}
