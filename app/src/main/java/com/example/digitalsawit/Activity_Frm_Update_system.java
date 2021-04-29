package com.example.digitalsawit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Activity_Frm_Update_system extends AppCompatActivity {

    //Inisialisasi DatabaseHelper
    DatabaseHelper dbhelper;

    ConstraintLayout clHeaderUpdateSystem, clBgUpdateSystem;
    Button btnOkUpdateSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_update_system);

        //Inisialisasi DatabaseHelper
        dbhelper = new DatabaseHelper(this);

        clHeaderUpdateSystem = findViewById(R.id.clHeaderUpdateSystem);
        clBgUpdateSystem = findViewById(R.id.clBgUpdateSystem);
        btnOkUpdateSystem = findViewById(R.id.btnOkUpdateSystem);

        preparedUserAppData("theme");
        preparedUserAppData("bgcolor");

    }

    private void preparedUserAppData(String predefinedData) {
        if (predefinedData.equals("theme")) {
            try {
                clHeaderUpdateSystem.getBackground().setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)), PorterDuff.Mode.SRC_ATOP);
                btnOkUpdateSystem.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (predefinedData.equals("bgcolor")) {
            try {
                clBgUpdateSystem.setBackgroundColor(Color.parseColor(dbhelper.get_tbl_username(29)));
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