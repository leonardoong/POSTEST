package com.example.android.postest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class SQLite extends SQLiteOpenHelper {
    Context cntx;
    SQLiteDatabase db;
    public static final String nama_db = "pos";
    public static final String tabel_barang = "tabel_barang";
    public static final String brg_id = "id";
    public static final String brg_nama = "nama";
    public static final String brg_harga = "harga";
    public static final String brg_stok = "stok";
    public static final String brg_deskripsi = "deskripsi";
    public static final String brg_gambar = "gambar";

    public SQLite(Context context) {
        super(context, nama_db, null, 1);
        this.cntx = context;
        db = this.getWritableDatabase();
        db.execSQL("create table if not exists " + tabel_barang + "(" + brg_id + " integer primary key autoincrement not null"
                + "," + brg_nama + " varchar(35), " + brg_harga + " integer, " + brg_stok + " integer, " + brg_deskripsi
                + " varchar(100), " + brg_gambar + " blob)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + tabel_barang + "(" + brg_id + " integer primary key autoincrement not null"
                + "," + brg_nama + " varchar(35), " + brg_harga + " integer, " + brg_stok + " integer, " + brg_deskripsi
                + " varchar(100), " + brg_gambar + " blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + tabel_barang);
        onCreate(sqLiteDatabase);
    }

    public boolean createBarang(Barang list) {

        ContentValues val = new ContentValues();
        val.put(brg_nama, list.getNama());
        val.put(brg_harga, list.getHarga());
        val.put(brg_stok, list.getStock());
        val.put(brg_deskripsi, list.getDeskripsi());
        val.put(brg_gambar, list.getGambar());
        long hasil = db.insert(tabel_barang, null, val);
        if (hasil == -1) {
            return false;

        } else {
            return true;
        }
    }

    public void ReadData(ArrayList<Barang> daftar) {
        Cursor cursor = this.getReadableDatabase().rawQuery("select id, nama, harga, stok,deskripsi, gambar from "
                + tabel_barang, null);
        while (cursor.moveToNext()) {
            daftar.add(new Barang(cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getString(4), cursor.getBlob(5)));
        }
    }


}
