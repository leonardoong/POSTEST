package com.example.android.postest;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class Barang {
    int harga, stock;
    String nama, deskripsi;

    public Barang( String nama, int harga, int stock, String deskripsi){
        //this.id = id;
        this.harga = harga;
        this.stock = stock;
        this.nama = nama;
        this.deskripsi = deskripsi;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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
