package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CashActivity extends AppCompatActivity {

    TextView mharga;
    EditText mCash ;
    Button mCharge;
    String harga, cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);

        mharga = findViewById(R.id.txtTotalHarga);
        mCash = findViewById(R.id.txtTotalCash);
        mCharge = findViewById(R.id.btnCharge);

        Intent i = getIntent();
        harga = i.getStringExtra("totalHarga");

        mharga.setText(harga);

        mCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash = mCash.getText().toString();
                Intent i = new Intent( CashActivity.this, ReceiptActivity.class);
                i.putExtra("totalHarga",harga);
                i.putExtra("totalCash",cash);
                startActivity(i);
            }
        });
    }
}
