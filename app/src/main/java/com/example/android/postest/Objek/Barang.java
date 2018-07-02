package com.example.android.postest.Objek;

import android.content.Intent;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class Barang {
    int harga, stock;
    String nama, deskripsi;
    byte[] gambar;

    public Barang( String nama, int harga, int stock, String deskripsi, byte[] gambar){
        //this.id = id;
        this.harga = harga;
        this.stock = stock;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

}
