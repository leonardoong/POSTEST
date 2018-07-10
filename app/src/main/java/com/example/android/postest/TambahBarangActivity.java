package com.example.android.postest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TambahBarangActivity extends AppCompatActivity{

    final int REQUEST_CODE_GALLERY = 999;
    AppCompatEditText mNamaBarang, mHargaBarang,
            mStok, mDeskripsi;
    TextInputLayout mNamaBarangLyt, mHargaBarangLyt,
            mStokLyt, mDeskripsiLyt;
    ImageView mGambarBarang;
    AppCompatButton mSimpanBarang;
    SQLite database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);
        mNamaBarangLyt = (TextInputLayout)findViewById(R.id.namaBarangTextInput);
        mHargaBarangLyt = (TextInputLayout)findViewById(R.id.hargaBarangTextInput);
        mStokLyt = (TextInputLayout)findViewById(R.id.stokTextInput);
        mDeskripsiLyt = (TextInputLayout)findViewById(R.id.deksripsiTextInput);
        mNamaBarang = (AppCompatEditText)findViewById(R.id.namaBarang);
        mHargaBarang = (AppCompatEditText)findViewById(R.id.hargaBarang);
        mStok = (AppCompatEditText)findViewById(R.id.stok);
        mDeskripsi = (AppCompatEditText)findViewById(R.id.deskripsi);
        mGambarBarang = (ImageView)findViewById(R.id.imgBarang);

        database = new SQLite(this);

        mSimpanBarang = (AppCompatButton)findViewById(R.id.simpanBarang);

        mGambarBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(TambahBarangActivity.this, new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
            }
        });

        mSimpanBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namaBarang = mNamaBarang.getText().toString();
                String hargaBarang = mHargaBarang.getText().toString();
                String stokBarang = mStok.getText().toString();
                String deskripsi = mDeskripsi.getText().toString();
                byte[] byteGambar = ImageViewtoBytes(mGambarBarang);

                if(!hargaBarang.isEmpty() || !stokBarang.isEmpty())
                {
                    try
                    {
                       if (validasiBarang() && validasiHargaBarang() && validasiStok() && validasiDeskripsi()){
                            int intHarga = Integer.parseInt(hargaBarang);
                            int intStok = Integer.parseInt(stokBarang);

                            database.createBarang(new Barang((namaBarang), (intHarga), (intStok), (deskripsi), (byteGambar)));

                            Toast.makeText(TambahBarangActivity.this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TambahBarangActivity.this, BarangActivity.class);
                            startActivity(intent);

                        }
                    }catch (NumberFormatException e)
                    {
                        Log.e("Parse Int:",e.getMessage());
                    }
                }
            }

            private byte[] ImageViewtoBytes(ImageView image) {
                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{
                Toast.makeText(TambahBarangActivity.this, "You don't have permission to access file", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data!=null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mGambarBarang.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean validasiBarang(){
        if (mNamaBarang.getText().toString().isEmpty()) {
            mNamaBarangLyt.setErrorEnabled(true);
            mNamaBarangLyt.setError("Nama barang tidak boleh kosong");
            return false;
        } else {
            mNamaBarangLyt.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validasiHargaBarang(){
        if (mHargaBarang.getText().toString().isEmpty()) {
            mHargaBarangLyt.setErrorEnabled(true);
            mHargaBarangLyt.setError("Harga barang tidak boleh kosong");
            return false;
        } else {
            mHargaBarangLyt.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validasiStok(){
        if (mStok.getText().toString().isEmpty()) {
            mStokLyt.setErrorEnabled(true);
            mStokLyt.setError("Stok tidak boleh kosong");
            return false;
        } else {
            mStokLyt.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validasiDeskripsi(){
        if (mDeskripsi.getText().toString().isEmpty()) {
            mDeskripsiLyt.setErrorEnabled(true);
            mDeskripsiLyt.setError("Deskripsi tidak boleh kosong");
            return false;
        } else {
            mDeskripsiLyt.setErrorEnabled(false);
            return true;
        }
    }

}
