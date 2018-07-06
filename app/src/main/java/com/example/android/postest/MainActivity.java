package com.example.android.postest;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.postest.Adapter.BarangAdapter;
import com.example.android.postest.Adapter.BarangTransaksiAdapter;
import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private RecyclerView rv;
   AppCompatButton mCheckout;
    BarangTransaksiAdapter adapter;
    String namaBarang;
    NavigationView navigationView;
    Toolbar toolbar;
    SQLite dbBarang;
    ArrayList<Barang> listBarang;
    int totalHarga;

    ArrayList<Barang> arrBarang = new ArrayList<>();
    ArrayList<Barang> arrayBarang;
    int idBarang;
    View myView;
    boolean isUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface raleway = Typeface.createFromAsset(getAssets(),"fonts/Raleway-SemiBold.ttf");
        Typeface roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        myView = findViewById(R.id.my_view);
        rv = (RecyclerView)findViewById(R.id.recViewTransaksi);
        mCheckout = (AppCompatButton) findViewById(R.id.btnCheckout);
        mCheckout.setTypeface(raleway);
        EditText search = (EditText) findViewById(R.id.cari);

        //buat cari
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        navigationView = (NavigationView) findViewById(R.id.navigasi_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        myView.setVisibility(View.INVISIBLE);
        isUp = false;
        listBarang = new ArrayList<>();
        //membuat database baru
        dbBarang = new SQLite(this);
        //memanggil method readdata
        dbBarang.ReadData(listBarang);
        totalHarga = 0;

        adapter = new BarangTransaksiAdapter(this, listBarang, new SetOnItemRecycleListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (!isUp) {
                    slideUp(myView);
                    int harga = listBarang.get(position).getHarga();
                    mCheckout.setText("Rp. " + String.valueOf(returnTotalHarga(harga)));
                    String hargaBarang = String.valueOf(harga);
                    namaBarang = listBarang.get(position).getNama();

                    arrayBarang = dbBarang.getAllBarang();
                    for (int i =0; i < arrayBarang.size(); i++){
                        Barang barang1 = arrayBarang.get(i);
                        if(namaBarang.equals(barang1.getNama())){
                            idBarang = barang1.getId();
                        }
                    }
                    Barang barang = new Barang(idBarang,namaBarang, harga);
                    arrBarang.add(barang);

                } else {

                    int harga = listBarang.get(position).getHarga();
                    mCheckout.setText("Rp. " + String.valueOf(returnTotalHarga(harga)));
                    String hargaBarang = String.valueOf(harga);
                    namaBarang = listBarang.get(position).getNama();

                    arrayBarang = dbBarang.getAllBarang();
                    for (int i =0; i < arrayBarang.size(); i++){
                        Barang barang1 = arrayBarang.get(i);
                        if(namaBarang.equals(barang1.getNama())){
                            idBarang = barang1.getId();
                        }
                    }
                    Barang barang = new Barang(idBarang,namaBarang, harga);
                    arrBarang.add(barang);
                }

                isUp = true;
            }
        });

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.barang_id){
            startActivity(new Intent(getApplicationContext(),BarangActivity.class));

        } else if (id == R.id.transaksi_id){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        } else if (id == R.id.riwayat_id){
            startActivity(new Intent(getApplicationContext(),RiwayatActivity.class));

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

    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    private void filter(String text){
        ArrayList<Barang> filterList = new ArrayList<>();
        for (Barang item : listBarang){
            if (item.getNama().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.filterList(filterList);
    }

}
