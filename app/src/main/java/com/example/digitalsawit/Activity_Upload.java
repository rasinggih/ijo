package com.example.digitalsawit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Activity_Upload extends AppCompatActivity {

    //Inisialisasi DatabaseHelper
    DatabaseHelper dbhelper;

    ConstraintLayout clHeaderUpload, clBgUpload;
    Button btnOkUpload;
    ListView lvUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_upload);

        //Inisialisasi DatabaseHelper
        dbhelper= new DatabaseHelper(this);

        clBgUpload = findViewById(R.id.clBgUpload);
        clHeaderUpload = findViewById(R.id.clHeaderUpload);
        btnOkUpload = findViewById(R.id.btnOkUpload);
        lvUpload = findViewById(R.id.lvUpload);

        preparedUserAppData("theme");
        preparedUserAppData("bgcolor");

    }

    private void preparedUserAppData(String predefinedData) {
        if (predefinedData.equals("theme")) {
            try {
                clHeaderUpload.getBackground().setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)), PorterDuff.Mode.SRC_ATOP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (predefinedData.equals("bgcolor")) {
            try {
                clBgUpload.setBackgroundColor(Color.parseColor(dbhelper.get_tbl_username(29)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Function Cek Internet
    public boolean checkInternet() {
        Boolean internetStatus = false;
        ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            internetStatus = true;
        }
        return internetStatus;
    }

    //Function Tombol Back
    @Override
    public void onBackPressed() {
        finish();
    }
}