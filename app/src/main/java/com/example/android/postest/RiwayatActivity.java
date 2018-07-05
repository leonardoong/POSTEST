package com.example.android.postest;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.postest.Adapter.BarangTransaksiAdapter;
import com.example.android.postest.Adapter.TransaksiAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.Transaksi;

import java.util.ArrayList;

public class RiwayatActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView rv;

    TransaksiAdapter adapter;
    String namaBarang;

    SQLite dbTransaksi;
    ArrayList<Transaksi> listTransaksi;
    int totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        rv = (RecyclerView)findViewById(R.id.recViewTransaksi);


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
                            Intent transaksi = new Intent(RiwayatActivity.this, MainActivity.class);
                            startActivity(transaksi);
                        }else if(id == R.id.nav_barang){
                            Intent barang = new Intent(RiwayatActivity.this,BarangActivity.class);
                            startActivity(barang);
                        }else if(id ==R.id.nav_riwayat){
                            Intent barang = new Intent(RiwayatActivity.this,RiwayatActivity.class);
                            startActivity(barang);
                        }
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        return true;
                    }
                });

        listTransaksi = new ArrayList<>();
        //membuat database baru
        dbTransaksi = new SQLite(this);
        //memanggil method readdata
        listTransaksi = dbTransaksi.getAllTransaksi();

        adapter = new TransaksiAdapter(this, listTransaksi);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
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
