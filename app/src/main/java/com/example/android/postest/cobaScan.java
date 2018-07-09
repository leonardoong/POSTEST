package com.example.android.postest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class cobaScan extends AppCompatActivity {
    TextView barcodeResult;
    Button scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_scan);
        barcodeResult = (TextView) findViewById(R.id.hasilScan);
        scan = (Button) findViewById(R.id.scan);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cobaScan.this, barcode.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
            if (data!=null){
                Barcode barcode = data.getParcelableExtra("barcode");
                barcodeResult.setText("Hasil Barcode : " + barcode.displayValue);
            } else {
                barcodeResult.setText("Barcode Tidak Ditemukan");
            }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
    }
    }
}
