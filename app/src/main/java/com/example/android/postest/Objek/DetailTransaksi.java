package com.example.android.postest.Objek;

/**
 * Created by Leonardo on 7/3/2018.
 */

public class DetailTransaksi {
    int idTransaksi, idBarang, jumlah;

    public DetailTransaksi(int idTransaksi, int idBarang, int jumlah) {
        this.idTransaksi = idTransaksi;
        this.idBarang = idBarang;
        this.jumlah = jumlah;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
