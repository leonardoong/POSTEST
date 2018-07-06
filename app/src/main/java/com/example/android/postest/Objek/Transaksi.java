package com.example.android.postest.Objek;

/**
 * Created by Leonardo on 7/2/2018.
 */

public class Transaksi {
    String tanggal, customer, user;
    int totalPenjualan, id;

    public Transaksi(String tanggal, String customer, String user, int totalPenjualan) {
            this.tanggal = tanggal;
            this.customer = customer;
            this.user = user;
        this.totalPenjualan = totalPenjualan;
    }

    public Transaksi(){

    }

    public Transaksi( int id,String tanggal, String customer, String user, int totalPenjualan) {
        this.tanggal = tanggal;
        this.customer = customer;
        this.user = user;
        this.totalPenjualan = totalPenjualan;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTotalPenjualan() {
        return totalPenjualan;
    }

    public void setTotalPenjualan(int totalPenjualan) {
        this.totalPenjualan = totalPenjualan;
    }
}
