package com.example.android.postest;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.android.postest.Adapter.BarangAdapter;
import com.example.android.postest.Adapter.BarangTransaksiAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView rv;
    private Button mCheckout;
    BarangTransaksiAdapter adapter;
    String namaBarang;

    SQLite dbBarang;
    ArrayList<Barang> listBarang;
    int totalHarga;

    ArrayList<Barang> arrBarang = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        rv = (RecyclerView)findViewById(R.id.recViewTransaksi);
        mCheckout = findViewById(R.id.btnCheckout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        if (id == R.id.nav_transaksi) {
                            Intent transaksi = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(transaksi);
                        }else if(id == R.id.nav_barang){
                            Intent barang = new Intent(MainActivity.this,BarangActivity.class);
                            startActivity(barang);
                        }else if(id ==R.id.nav_riwayat){
                            Intent barang = new Intent(MainActivity.this,RiwayatActivity.class);
                            startActivity(barang);
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });

        listBarang = new ArrayList<>();
        //membuat database baru
        dbBarang = new SQLite(this);
        //memanggil method readdata
        dbBarang.ReadData(listBarang);
        totalHarga = 0;

        adapter = new BarangTransaksiAdapter(this, listBarang, new SetOnItemRecycleListener() {
            @Override
            public void onItemClick(View v, int position) {
                int harga = listBarang.get(position).getHarga();
                mCheckout.setText("CHECKOUT : " + String.valueOf(returnTotalHarga(harga)));
                String hargaBarang = String.valueOf(harga);
                namaBarang = listBarang.get(position).getNama();
                Barang barang = new Barang(namaBarang, harga);
                arrBarang.add(barang);
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle b = new Bundle();
                Intent checkout = new Intent(MainActivity.this, CheckoutActivity.class);
                checkout.putExtra("arrayList", arrBarang);
                checkout.putExtra("totalHarga", String.valueOf(totalHarga));
//                checkout.putExtras(b);
                startActivity(checkout);
            }
        });
    }

    public int returnTotalHarga(int harga) {
        totalHarga += harga;
        return totalHarga;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
