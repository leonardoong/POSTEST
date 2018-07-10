package com.example.android.postest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.Button;

import com.example.android.postest.Adapter.BarangAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import java.util.ArrayList;

public class BarangActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FloatingActionButton fabBarang;
    private RecyclerView rv;
    NavigationView navigationView;
    Toolbar toolbar;
    BarangAdapter adapter;
    SQLite dbBarang;
    ArrayList<Barang> listBarang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);
        rv = (RecyclerView)findViewById(R.id.recview);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigasi_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
            fabBarang = (FloatingActionButton)findViewById(R.id.fab_btn);
            fabBarang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tambahBarang = new Intent(BarangActivity.this, TambahBarangActivity.class);
             startActivity(tambahBarang);
                }
            });
//        mTambahBarang = (Button)findViewById(R.id.tambahBarang);
//        mTambahBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tambahBarang = new Intent(BarangActivity.this, TambahBarangActivity.class);
//                startActivity(tambahBarang);
//            }
//        });

        listBarang = new ArrayList<>();
        //membuat database baru
        dbBarang = new SQLite(this);
        //memanggil method readdata
        dbBarang.ReadData(listBarang);


        adapter = new BarangAdapter(this, listBarang);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.transaksi_id) {
           startActivity(new Intent(getApplicationContext(), MainActivity.class));
           finish();
       } else if (id == R.id.riwayat_id){
            startActivity(new Intent(getApplicationContext(),RiwayatActivity.class));
            finish();
        }else if (id == R.id.logout_id){
            backToLogin();
            finish();
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

    public void backToLogin (){
        SharedPreferences.Editor edit = getSharedPreferences("userSession", MODE_PRIVATE).edit();
        edit.remove("username");
        edit.commit();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
}
