package com.example.android.postest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class RiwayatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;;
    private RecyclerView rv;

    TransaksiAdapter adapter;
    String namaBarang;
    NavigationView navigationView;
    Toolbar toolbar;
    SQLite dbTransaksi;
    ArrayList<Transaksi> listTransaksi;
    int totalHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        rv = (RecyclerView)findViewById(R.id.recViewTransaksi);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigasi_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(2).setChecked(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.barang_id){
            startActivity(new Intent(getApplicationContext(),BarangActivity.class));

        } else if (id == R.id.transaksi_id){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        } else if (id == R.id.riwayat_id){
            startActivity(new Intent(getApplicationContext(),RiwayatActivity.class));
        }else if (id == R.id.logout_id){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
