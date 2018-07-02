package com.example.android.postest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.postest.Objek.Barang;
import com.example.android.postest.Objek.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardo on 6/27/2018.
 */

public class SQLite extends SQLiteOpenHelper {
    private static final String KOLOM_BRG_GAMBAR = "gambar";
    Context cntx;
    SQLiteDatabase db;

    public static final String NAMA_DATABASE = "pos";
    public static final String TABEL_BARANG = "TABEL_BARANG";
    public static final String KOLOM_BRG_ID = "id";
    public static final String KOLOM_BRG_NAMA = "nama";
    public static final String KOLOM_BRG_HARGA = "harga";
    public static final String KOLOM_BRG_STOK = "stok";
    public static final String KOLOM_BRG_DESKRIPSI = "deskripsi";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String TABLE_TRANSAKSI = "TABEL_TRANSAKSI";

    private static final String KOLOM_TRN_ID = "id";
    private static final String KOLOM_TRN_TANGGAL = "tanggal";
    private static final String KOLOM_TRN_CUSTOMER = "nama_customer";
    private static final String KOLOM_TRN_USER = "user_name";
    private static final String KOLOM_TRN_TOTAL = "total_penjualan";


    private String CREATE_ITEM_TABLE = "create table if not exists "+ TABEL_BARANG + "(" + KOLOM_BRG_ID + " integer primary key autoincrement not null" +
            "," + KOLOM_BRG_NAMA +" varchar(35), "+ KOLOM_BRG_HARGA + " integer, "+ KOLOM_BRG_STOK + " integer, "+ KOLOM_BRG_DESKRIPSI +" varchar(100), "+
            KOLOM_BRG_GAMBAR + " blob)" ;

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String CREATE_TABEL_TRANSAKSI = "CREATE TABLE IF NOT EXISTS " + TABLE_TRANSAKSI + "( " +KOLOM_TRN_ID+ "INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KOLOM_TRN_TANGGAL + " DATE, " + KOLOM_TRN_CUSTOMER + " TEXT, " + KOLOM_TRN_USER + "TEXT, " + KOLOM_TRN_TOTAL +
            " INTEGER)";

    public SQLite(Context context){
        super(context, NAMA_DATABASE, null, 1);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABEL_TRANSAKSI);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABEL_TRANSAKSI);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ TABEL_BARANG);
        sqLiteDatabase.execSQL("drop table if exists "+ CREATE_TABEL_TRANSAKSI);
        onCreate(sqLiteDatabase);
    }

    public boolean createBarang(Barang list) {

        ContentValues val = new ContentValues();
        db = getWritableDatabase();
        val.put(KOLOM_BRG_NAMA, list.getNama());
        val.put(KOLOM_BRG_HARGA, list.getHarga());
        val.put(KOLOM_BRG_STOK, list.getStock());
        val.put(KOLOM_BRG_DESKRIPSI, list.getDeskripsi());
        val.put(KOLOM_BRG_GAMBAR, list.getGambar());
        long hasil = db.insert(TABEL_BARANG, null, val);
        if (hasil==-1) {
            return false;

        } else {
            return true;
        }
    }
    

    public void ReadData(ArrayList<Barang> daftar){
        Cursor cursor = this.getReadableDatabase().rawQuery("select id, nama, harga, stok,deskripsi, gambar from "
                + TABEL_BARANG, null);
        while (cursor.moveToNext()){
            daftar.add(new Barang(cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getString(4), cursor.getBlob(5)));
        }
    }

//    --------------------------------------------------------------------

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


}
