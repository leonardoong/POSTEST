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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.Objek.Transaksi;
import com.example.android.postest.Validation.NumberTextWatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CashActivity extends AppCompatActivity {

    TextView mharga;
    EditText mCash, mCustomer;
    Button mCharge;
    String harga, cash, customer, tanggal;
    SQLite database;
    ArrayList<Barang> arrBarang, listBarang;
    ArrayList<Integer> arrId = new ArrayList<Integer>();
    ArrayList<Transaksi> arrTransaksi;
    Barang barang;
    Transaksi transaksi;
    int idBarang, idTransaksi, jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        mharga = findViewById(R.id.txtTotalHarga);
        mCash = findViewById(R.id.txtTotalCash);
        mCharge = findViewById(R.id.btnCharge);
        mCustomer = findViewById(R.id.etNamaCustomer);

        database = new SQLite(this);

        final Intent i = getIntent();
        harga = i.getStringExtra("totalHarga");
        arrBarang = (ArrayList<Barang>)i.getSerializableExtra("arrBarang");

        mharga.setText(harga);

        mCash.addTextChangedListener(new NumberTextWatcher(mCash, "#,###"));



        mCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DecimalFormat df;
                df = new DecimalFormat("#,###");
                df.setDecimalSeparatorAlwaysShown(true);
                tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                cash = mCash.getText().toString();
                cash = cash.replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "").replace("Rp","");
                customer = mCustomer.getText().toString();
                int intCash = (int)Float.parseFloat(cash);
                int intHarga = Integer.parseInt(harga);
                if (TextUtils.isEmpty(cash) || TextUtils.isEmpty(customer)){
                    Toast.makeText(CashActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                }else if (intCash < intHarga && TextUtils.isEmpty(customer)){
                    Toast.makeText(CashActivity.this, "Uang yang dimasukkan kurang dan lengkapi data", Toast.LENGTH_SHORT).show();
                }else if (intCash < intHarga){
                    Toast.makeText(CashActivity.this, "Uang yang dimasukkan kurang", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
                    String user = prefs.getString("username", "");
                    database.createTransaksi(new Transaksi((tanggal),(customer),(user),(intHarga)));
                    //String namaBarang;
                    listBarang = database.getAllBarang();

//                    for (int i = 0; i < arrBarang.size(); i ++){
//                        barang = arrBarang.get(i);
//                        Barang barang1 = listBarang.get(i);
//                        idBarang = barang.getId();
//                        if(idBarang == barang1.getId()){
//                            jumlah++;
//                        }else if(idBarang != barang1.getId()){
//                            database.createDetailTransaksi(new DetailTransaksi((2),(idBarang),(jumlah)));
//                            jumlah = 0;
//                        }
//                    }

                    arrTransaksi = database.getAllTransaksi();
                    transaksi = arrTransaksi.get(arrTransaksi.size() - 1);
                    idTransaksi = transaksi.getId();

                    for (int i = 0; i < arrBarang.size(); i++){
                        barang = arrBarang.get(i);
                        idBarang = barang.getId();
                        arrId.add(idBarang);
                    }
                    Set<Integer> unique = new HashSet<Integer>(arrId);
                    for (Integer key : unique) {
                        database.createDetailTransaksi(new DetailTransaksi((idTransaksi),(key),(Collections.frequency(arrId,key))));
                        database.updateBarang(new Barang(key, getStock(key) - Collections.frequency(arrId,key)));
                    }

                    Intent i = new Intent( CashActivity.this, ReceiptActivity.class);
                    i.putExtra("totalHarga",harga);
                    i.putExtra("totalCash",intCash);

                    startActivity(i);
                }
            }
        });
    }
    public int getStock( int id){
        // Goes through the List of schools.

        int stok = database.getBarang(String.valueOf(id)).getStock();
        return stok;
    }
}
