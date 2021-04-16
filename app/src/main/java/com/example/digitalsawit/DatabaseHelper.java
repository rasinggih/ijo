package com.example.digitalsawit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CountDownTimer;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "db_ops.db", null,
                2);
        //fix version 205
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Setting awal dan User
        db.execSQL("CREATE TABLE tbl_username (username text PRIMARY KEY, password text, " +
                "no_registration text, empcode text, no_telp text, empname text, " +
                "position_name text, loc_code text, departmentcode text, comp_id text, " +
                "site_id text, shiftcode varchar, deptcode varchar, divcode varchar, " +
                "gangcode varchar, reg_rn varchar, vh_status varchar, adm_status varchar, " +
                "isresetpassword varchar, isdeleteuser varchar, blob1 blob);");


        db.execSQL("CREATE TABLE tbl_pickup_data (datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar," +
                " text6 varchar, text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar," +
                " text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar," +
                " text19 varchar, text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar," +
                " text25 varchar, text26 varchar, text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, " +
                "blob3 blob, blob4 blob, blob5 blob);");


        db.execSQL("CREATE TABLE tbl_pickup_data2 (datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar," +
                " text6 varchar, text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar," +
                " text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar," +
                " text19 varchar, text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar," +
                " text25 varchar, text26 varchar, text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, " +
                "blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE tbl_pickup_data3 (datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar," +
                " text6 varchar, text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar," +
                " text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar," +
                " text19 varchar, text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar," +
                " text25 varchar, text26 varchar, text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, " +
                "blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE tbl_pickup_data4 (datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar," +
                " text6 varchar, text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar," +
                " text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar," +
                " text19 varchar, text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar," +
                " text25 varchar, text26 varchar, text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, " +
                "blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE tbl_pickup_data5 (datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar," +
                " text6 varchar, text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar," +
                " text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar," +
                " text19 varchar, text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar," +
                " text25 varchar, text26 varchar, text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, " +
                "blob3 blob, blob4 blob, blob5 blob);");


        db.execSQL("CREATE TABLE tbl_docrn (no_registration varchar, doc_id varchar, bulan varchar, tahun varchar, last_number varchar);");


        db.execSQL("CREATE TABLE transaction_header_ops (documentno varchar, datatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");


        db.execSQL("CREATE TABLE transaction_detail1_ops (documentno varchar, datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");


        db.execSQL("CREATE TABLE transaction_detail2_ops (documentno varchar, datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE transaction_detail3_ops (documentno varchar, datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE transaction_detail4_ops (documentno varchar, datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");

        db.execSQL("CREATE TABLE transaction_detail5_ops (documentno varchar, datatype varchar, subdatatype varchar, date1 date, date2 date, date3 date, " +
                "date4 date, date5 date, text1 varchar, text2 varchar, text3 varchar, text4 varchar, text5 varchar, text6 varchar, " +
                "text7 varchar, text8 varchar, text9 varchar, text10 varchar, text11 varchar, text12 varchar, " +
                "text13 varchar, text14 varchar, text15 varchar, text16 varchar, text17 varchar, text18 varchar, text19 varchar, " +
                "text20 varchar, text21 varchar, text22 varchar, text23 varchar, text24 varchar, text25 varchar, text26 varchar, " +
                "text27 varchar, text28 varchar, text29 varchar, text30 varchar, blob1 blob, blob2 blob, blob3 blob, blob4 blob, blob5 blob);");


        db.execSQL("CREATE TABLE tracking_unit (nodoc varchar, drivercode varchar, " +
                "latitude varchar, longitude varchar, time varchar, status_upload varchar);");

        db.execSQL("CREATE TABLE tbl_version (systemcode varchar, systemname varchar, versionnumber integer, versionname varchar, remarks varchar, status varchar, " +
                "versionnumber_new integer, versionname_new varchar, tdate_new varchar, remarks_new varchar);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_username");
        db.execSQL("DROP TABLE IF EXISTS tbl_pickup_data");
        db.execSQL("DROP TABLE IF EXISTS tbl_pickup_data2");
        db.execSQL("DROP TABLE IF EXISTS tbl_pickup_data3");
        db.execSQL("DROP TABLE IF EXISTS tbl_pickup_data4");
        db.execSQL("DROP TABLE IF EXISTS tbl_pickup_data5");
        db.execSQL("DROP TABLE IF EXISTS tbl_docrn");
        db.execSQL("DROP TABLE IF EXISTS transaction_header_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_detail1_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_detail2_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_detail3_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_detail4_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_detail5_ops");
        db.execSQL("DROP TABLE IF EXISTS transaction_header_ops");
        db.execSQL("DROP TABLE IF EXISTS tracking_unit");
        db.execSQL("DROP TABLE IF EXISTS tbl_version");
        onCreate(db);
    }

    public boolean generate_tbl_version(String v_versionnumber_new, String v_versionname_new, String v_tdate, String v_remarks_new) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from tbl_version");
        ContentValues contentValues = new ContentValues();
        contentValues.put("systemcode", "00");
        contentValues.put("systemname", "OPS SYSTEM");
        contentValues.put("versionnumber",1);
        contentValues.put("versionname", "Version 0.1");
        contentValues.put("versionnumber_new", v_versionnumber_new);
        contentValues.put("versionname_new", v_versionname_new);
        contentValues.put("tdate_new", v_tdate);
        contentValues.put("remarks_new", v_remarks_new);

        // Insert Row
        long insert = db.insert("tbl_version", null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void delete_data_username(){
        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.execSQL("delete from tbl_username");
    }

    public void update_password(String password){
        SQLiteDatabase dbwrite = this.getWritableDatabase();
        dbwrite.execSQL("update tbl_username set password = '"+password+"'");
    }

    public boolean generate_tbl_username(String username, String password, String empcode, String no_telp,
                                         byte[] blob1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from tbl_username");
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("empcode",empcode);
        contentValues.put("no_telp", no_telp);
        contentValues.put("empname", username);
        contentValues.put("blob1", blob1);


        // Insert Row
        long insert = db.insert("tbl_username", null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean update_gambar(byte[] blob1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("blob1", blob1);

        // Insert Row
        long insert = db.update("tbl_username", contentValues, null, null);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }


    public byte[] get_gambar_user() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT blob1 FROM tbl_username;", null);
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            return cursor.getBlob(0);
        } else {
            return null;
        }
    }


    public String get_tbl_version(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query1 = "select systemcode, systemname, versionnumber, versionname, remarks, status," +
                " ifnull (versionnumber_new, versionnumber) versionnumber_new," +
                " ifnull (versionname_new, versionname) versionname_new," +
                " ifnull (tdate_new,'-') tdate_new, ifnull (remarks_new, '-') remarks_new from tbl_version";

        Cursor cursor = db.rawQuery(query1, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            return cursor.getString(i).toString();
        } else {
            return null;
        }
    }

    public String get_tbl_username(int i) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query1 = "select * from tbl_username";
        Cursor cursor = db.rawQuery(query1, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            return cursor.getString(i).toString();
        } else {
            return "0";
        }
    }


    public Boolean updatestatusversion(String new_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", new_status);

        long update = db.update("tbl_version", contentValues, null, null);
        if (update == -1) {
            return false;
        } else {
            return true;
        }
    }
}