package com.example.android.postest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import java.io.ByteArrayInputStream;

public class DetailBarangActivity extends AppCompatActivity {

    private TextView txtNama, txtHarga, txtStok, txtDeskripsi;
    private EditText editNama, editHarga, editStok, editDeskripsi;
    private Button btnEdit, btnUpdate, btnCancel;
    private String id,nama,deskripsi;
    private ImageView image;
    private int harga,stok;
    private Barang barang;
    private LinearLayout mLayoutText, mLayoutEdit;
    SQLite dbBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_barang);

        Intent i = getIntent();
        id = i.getStringExtra("idBarang");

        dbBarang = new SQLite(this);
        barang = dbBarang.getBarang(id);
        initViews(barang);
        byte[] test = barang.getGambar();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(test);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        image.setImageBitmap(theImage);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutEdit.setVisibility(View.VISIBLE);
                mLayoutText.setVisibility(View.GONE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barang.setId(Integer.parseInt(id));
                barang.setNama(editNama.getText().toString().trim());
                barang.setHarga(Integer.parseInt(editHarga.getText().toString().trim()));
                barang.setStock(Integer.parseInt(editStok.getText().toString().trim()));
                barang.setDeskripsi(editDeskripsi.getText().toString());
                initViews(barang);
                dbBarang.updateBarang(barang);
                mLayoutEdit.setVisibility(View.GONE);
                mLayoutText.setVisibility(View.VISIBLE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutEdit.setVisibility(View.GONE);
                mLayoutText.setVisibility(View.VISIBLE);
            }
        });
    }

    public void initViews(Barang barang){
        image = findViewById(R.id.imageBarang);
        mLayoutEdit = findViewById(R.id.layoutEdit);
        mLayoutText = findViewById(R.id.layoutText);
        txtNama = findViewById(R.id.txtNamaBarang);txtNama.setText(barang.getNama());
        txtHarga = findViewById(R.id.txtHargaBarang);txtHarga.setText(String.valueOf(barang.getHarga()));
        txtStok = findViewById(R.id.txtStock); txtStok.setText(String.valueOf(barang.getStock()));
        txtDeskripsi = findViewById(R.id.txtDeskripsi);txtDeskripsi.setText(barang.getDeskripsi());
        editNama = findViewById(R.id.editNamaBarang);editNama.setText(barang.getNama());
        editHarga = findViewById(R.id.editHargaBarang);editHarga.setText(String.valueOf(barang.getHarga()));
        editStok= findViewById(R.id.editStock);editStok.setText(String.valueOf(barang.getStock()));
        editDeskripsi = findViewById(R.id.editDeskripsi);editDeskripsi.setText(barang.getDeskripsi());
        btnUpdate = findViewById(R.id.btnUpdateBarang);
        btnEdit = findViewById(R.id.btnEditBarang);
        btnCancel = findViewById(R.id.btnCancelEdit);
    }
}
