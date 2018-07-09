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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.example.android.postest.Adapter.CheckoutAdapter.JUMLAH;
import static com.example.android.postest.Adapter.CheckoutAdapter.POSISI;
import static com.example.android.postest.Adapter.CheckoutAdapter.REQUEST_CODE;

public class CheckoutActivity extends AppCompatActivity {
    TextView totalHarga, namaBarang, mharga;
    Button btnCash, mCharge;
    String harga, cash, customer, tanggal;
    ArrayList<Barang> arrBarang, listBarang;
    ArrayList<Transaksi> arrTransaksi;
    CheckoutAdapter adapter;
    Barang barang;
    Transaksi transaksi;
    int idTransaksi, jumlahHarga;
    ListView lv;
    RecyclerView rv ;
    SQLite database;
    EditText mCash, mCustomer;
    NumberFormat formatRupiah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Locale localeID = new Locale("in", "ID");
        formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        namaBarang = findViewById(R.id.namaBarang);
        totalHarga = findViewById(R.id.totalHarga);
        btnCash = findViewById(R.id.btnPemabayaranCash);

//        lv = findViewById(R.id.listView);
        rv = findViewById(R.id.recview);
        database = new SQLite(this);

        Intent data = getIntent();
        final String strHarga = data.getStringExtra("totalHarga");
        jumlahHarga = Integer.parseInt(strHarga);
        arrBarang = (ArrayList<Barang>)data.getSerializableExtra("arrayList");

        totalHarga.setText(strHarga);
        adapter = new CheckoutAdapter
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

                mharga.setText(formatRupiah.format((double) jumlahHarga));
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
                        if (TextUtils.isEmpty(cash) || TextUtils.isEmpty(customer)){
                            Toast.makeText(CheckoutActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                        }else if (intCash < jumlahHarga && TextUtils.isEmpty(customer)){
                            Toast.makeText(CheckoutActivity.this, "Uang yang dimasukkan kurang dan lengkapi data", Toast.LENGTH_SHORT).show();
                        }else if (intCash < jumlahHarga){
                            Toast.makeText(CheckoutActivity.this, "Uang yang dimasukkan kurang", Toast.LENGTH_SHORT).show();
                        }else{
                            SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
                            String user = prefs.getString("username", "");
                            database.createTransaksi(new Transaksi((tanggal),(customer),(user),(jumlahHarga)));

                            arrTransaksi = database.getAllTransaksi();
                            transaksi = arrTransaksi.get(arrTransaksi.size() - 1);
                            idTransaksi = transaksi.getId();

                            for (Barang barang : arrBarang){
                                database.createDetailTransaksi(new DetailTransaksi((idTransaksi),(barang.getId()),barang.getJumlah()));
                                database.updateBarang(new Barang(barang.getId(), barang.getStock() - barang.getJumlah()));
                            }
//                            Set<Integer> unique = new HashSet<Integer>(arrId);
//                            for (Integer key : unique) {
//                                database.createDetailTransaksi(new DetailTransaksi((idTransaksi),(key),(Collections.frequency(arrId,key))));
//                                database.updateBarang(new Barang(key, getStock(key) - Collections.frequency(arrId,key)));
//                            }
                            Intent i = new Intent( CheckoutActivity.this, SuccessActivity.class);
                            i.putExtra("totalHarga",jumlahHarga);
                            i.putExtra("totalCash",intCash);
                            i.putExtra("idTransaksi", idTransaksi);
                            startActivity(i);
                            finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
        super.onActivityResult(requestCode, resultCode, data);
        int jumlah, posisi;
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            jumlah = data.getIntExtra(JUMLAH,0);
            posisi = data.getIntExtra (POSISI, 0);
            if (jumlah == 0){
                removeAt(posisi);
            }else {
                changeValueat(jumlah,posisi);
            }

        }}
        catch (Exception ex){
            Toast.makeText(this, ex.toString(),Toast.LENGTH_SHORT).show();

        }
    }

    public void removeAt(int position) {
        arrBarang.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, arrBarang.size());
    }
    public void changeValueat(int jumlah, int position) {
        arrBarang.get(position).setJumlah(jumlah);
        adapter.notifyItemChanged(position);
        jumlahHarga = 0;
        for (Barang barang : arrBarang) {
            jumlahHarga += barang.getHarga()*barang.getJumlah();
        }totalHarga.setText(formatRupiah.format((double) jumlahHarga));
    }
}
