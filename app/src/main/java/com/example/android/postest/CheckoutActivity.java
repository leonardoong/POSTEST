package com.example.android.postest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.postest.Adapter.BarangAdapter;
import com.example.android.postest.Adapter.CheckoutAdapter;
import com.example.android.postest.Adapter.DetailTransaksiAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.DetailTransaksi;
import com.example.android.postest.Objek.Transaksi;
import com.example.android.postest.Validation.NumberTextWatcher;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CheckoutActivity extends AppCompatActivity {
    TextView totalHarga, namaBarang, mharga;
    Button btnCash, mCharge;
    String harga, cash, customer, tanggal;
    ArrayList<Barang> arrBarang, listBarang;
    ArrayList<Integer> arrId = new ArrayList<Integer>();
    ArrayList<Transaksi> arrTransaksi;
    Barang barang;
    Transaksi transaksi;
    int idBarang, idTransaksi;
    ListView lv;
    RecyclerView rv ;
    SQLite database;
    EditText mCash, mCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        namaBarang = findViewById(R.id.namaBarang);
        totalHarga = findViewById(R.id.totalHarga);
        btnCash = findViewById(R.id.btnPemabayaranCash);

//        lv = findViewById(R.id.listView);
        rv = findViewById(R.id.recview);
        database = new SQLite(this);

        Intent data = getIntent();
        final String strHarga = data.getStringExtra("totalHarga");
        final ArrayList<Barang> arrBarang = (ArrayList<Barang>)data.getSerializableExtra("arrayList");

        totalHarga.setText(strHarga);
        CheckoutAdapter adapter = new CheckoutAdapter
                (CheckoutActivity.this, arrBarang);
//        lv.setAdapter(adapter);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        btnCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(CheckoutActivity.this, CashActivity.class);
//                i.putExtra("totalHarga", strHarga);
//                i.putExtra("arrBarang", arrBarang);
//                startActivity(i);
                ///PINDAH CASH ACTIVITY
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CheckoutActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cash, null);

                mharga = mView.findViewById(R.id.txtTotalHarga);

                mCash = mView.findViewById(R.id.txtTotalCash);
                mCharge = mView.findViewById(R.id.btnCharge);
                mCustomer = mView.findViewById(R.id.etNamaCustomer);

                mharga.setText(strHarga);
                mCash.addTextChangedListener(new NumberTextWatcher(mCash, "#,###"));

                mCharge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DecimalFormat df;
                        df = new DecimalFormat("#,###");
                        df.setDecimalSeparatorAlwaysShown(true);
                        tanggal = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        cash = mCash.getText().toString();
                        cash = cash.replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "").replace("Rp. ","");
                        customer = mCustomer.getText().toString();
                        int intCash = (int)Float.parseFloat(cash);
                        int intHarga = Integer.parseInt(strHarga);
                        if (TextUtils.isEmpty(cash) || TextUtils.isEmpty(customer)){
                            Toast.makeText(CheckoutActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                        }else if (intCash < intHarga && TextUtils.isEmpty(customer)){
                            Toast.makeText(CheckoutActivity.this, "Uang yang dimasukkan kurang dan lengkapi data", Toast.LENGTH_SHORT).show();
                        }else if (intCash < intHarga){
                            Toast.makeText(CheckoutActivity.this, "Uang yang dimasukkan kurang", Toast.LENGTH_SHORT).show();
                        }else{
                            SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
                            String user = prefs.getString("username", "");
                            database.createTransaksi(new Transaksi((tanggal),(customer),(user),(intHarga)));
                            //String namaBarang;
                            listBarang = database.getAllBarang();

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

                            Intent i = new Intent( CheckoutActivity.this, ReceiptActivity.class);
                            i.putExtra("totalHarga",intHarga);
                            i.putExtra("totalCash",intCash);

                            startActivity(i);
                        }
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }});
    }

    public int getStock( int id){
        // Goes through the List of schools.

        int stok = database.getBarang(String.valueOf(id)).getStock();
        return stok;
    }
}
