package com.example.android.postest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
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
    TextInputLayout txtNamaLayout, txtHargaLayout, txtDeskripsiLayout, txtStokLayout;
    AppCompatEditText editNama, editHarga, editStok, editDeskripsi;
    private FloatingActionButton btnEdit;
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
        Typeface raleway = Typeface.createFromAsset(getAssets(),"fonts/Raleway-SemiBold.ttf");
        Typeface roboto = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Intent i = getIntent();
        id = i.getStringExtra("idBarang");

        dbBarang = new SQLite(this);
        barang = dbBarang.getBarang(id);
        initViews(barang);
        byte[] test = barang.getGambar();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(test);
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        image.setImageBitmap(theImage);
        //TYPEFACE

        txtDeskripsi.setTypeface(roboto);
        txtHarga.setTypeface(roboto);
        txtNama.setTypeface(roboto);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailBarangActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.editbarang_layout, null);
                editNama = mView.findViewById(R.id.editNamaBarang);editNama.setText(barang.getNama());
                editHarga = mView.findViewById(R.id.editHargaBarang);editHarga.setText(String.valueOf(barang.getHarga()));
                editStok= mView.findViewById(R.id.editStock);editStok.setText(String.valueOf(barang.getStock()));
                editDeskripsi = mView.findViewById(R.id.editDeskripsi);editDeskripsi.setText(barang.getDeskripsi());
              txtDeskripsiLayout = mView.findViewById(R.id.editDeskripsiTextInput);
              txtNamaLayout = mView.findViewById(R.id.editNamaBarangTextInput);
              txtHargaLayout = mView.findViewById(R.id.editHargaBarangTextInput);
              txtStokLayout = mView.findViewById(R.id.editStockTextInput);
                mBuilder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        barang.setId(Integer.parseInt(id));
                        barang.setNama(editNama.getText().toString().trim());
                        barang.setHarga(Integer.parseInt(editHarga.getText().toString().trim()));
                        barang.setStock(Integer.parseInt(editStok.getText().toString().trim()));
                        barang.setDeskripsi(editDeskripsi.getText().toString());
                        initViews(barang);
                        dbBarang.updateBarang(barang);
                        dialog.dismiss();

                    }
                });

                mBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.biru));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.biru));
            }
        });





    }

    public void initViews(Barang barang){
        image = findViewById(R.id.imageBarang);

        txtNama = findViewById(R.id.txtNamaBarang);txtNama.setText(barang.getNama());
        txtHarga = findViewById(R.id.txtHargaBarang);txtHarga.setText(String.valueOf(barang.getHarga()));
        txtStok = findViewById(R.id.txtStock); txtStok.setText(String.valueOf(barang.getStock()));
        txtDeskripsi = findViewById(R.id.txtDeskripsi);txtDeskripsi.setText(barang.getDeskripsi());


        btnEdit = findViewById(R.id.btnEditBarang);

    }

    private boolean validasiNama(){
        if (editNama.getText().toString().isEmpty()) {
            txtNamaLayout.setErrorEnabled(true);
            txtNamaLayout.setError("Nama barang tidak boleh kosong");
            return false;
        } else {
            txtNamaLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validasiStok(){
        if (editStok.getText().toString().isEmpty()) {
            txtStokLayout.setErrorEnabled(true);
            txtStokLayout.setError("Stok tidak boleh kosong");
            return false;
        } else {
            txtStokLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validasiHarga(){
        if (editHarga.getText().toString().isEmpty()) {
            txtHargaLayout.setErrorEnabled(true);
            txtHargaLayout.setError("Harga barang tidak boleh kosong");
            return false;
        } else {
            txtHargaLayout.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validasiDeskripsi(){
        if (editDeskripsi.getText().toString().isEmpty()) {
            txtDeskripsiLayout.setErrorEnabled(true);
            txtDeskripsiLayout.setError("Deskripsi tidak boleh kosong");
            return false;
        } else {
            txtDeskripsiLayout.setErrorEnabled(false);
            return true;
        }
    }
}
