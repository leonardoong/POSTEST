package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.postest.Database.SQLite;
import com.example.android.postest.Objek.Barang;

import static com.example.android.postest.Adapter.CheckoutAdapter.ID_BARANG;
import static com.example.android.postest.Adapter.CheckoutAdapter.JUMLAH;
import static com.example.android.postest.Adapter.CheckoutAdapter.POSISI;
import static com.example.android.postest.Adapter.CheckoutAdapter.REQUEST_CODE;

public class AddJumlahActivity extends AppCompatActivity {

    private TextView mNama, mJumlah, mDeskripsi;
    private ImageButton mTambah, mKurang ;
    private  Button mRemove, mSave;
    private int jumlah, posisi, id;
    private Barang barang ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jumlah);

        SQLite db = new SQLite(getApplicationContext());
        final Intent i = getIntent();
        jumlah = i.getIntExtra(JUMLAH,1);
        posisi = i.getIntExtra(POSISI, 0) ;
        id = i.getIntExtra(ID_BARANG,0);
        barang = db.getBarang(String.valueOf(id));

        initViews();

        mTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlah>1){
                    mJumlah.setText(String.valueOf(--jumlah));
                }
            }
        });

        mKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJumlah.setText(String.valueOf(++jumlah));
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = 0;
                i.putExtra(JUMLAH,jumlah);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra(JUMLAH,jumlah);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }


    private void initViews (){
        mNama = findViewById(R.id.namaBarang);
        mJumlah = findViewById(R.id.txtJumlah);
        mDeskripsi = findViewById(R.id.deskripsiBarang);
        mTambah = findViewById(R.id.addBarang);
        mKurang = findViewById(R.id.decrBarang);
        mRemove = findViewById(R.id.btnRemove);
        mSave = findViewById(R.id.btnSave);

        mNama.setText(barang.getNama());
        mDeskripsi.setText(barang.getDeskripsi());
        mJumlah.setText(String.valueOf(jumlah));
    }



}
