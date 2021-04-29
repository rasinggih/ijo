package com.example.digitalsawit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class Activity_Download extends AppCompatActivity {

    //Inisialisasi DatabaseHelper
    DatabaseHelper dbhelper;

    ConstraintLayout clHeaderDownload, clBgDownload;
    Button btnOkDownload;
    ListView lvDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_download);

        //Inisialisasi DatabaseHelper
        dbhelper = new DatabaseHelper(this);

        clHeaderDownload = findViewById(R.id.clHeaderDownload);
        clBgDownload = findViewById(R.id.clBgDownload);
        btnOkDownload = findViewById(R.id.btnOkDownload);
        lvDownload = findViewById(R.id.lvDownload);

        preparedUserAppData("theme");
        preparedUserAppData("bgcolor");

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

    private void preparedUserAppData(String predefinedData) {
        if (predefinedData.equals("theme")) {
            try {
                clHeaderDownload.getBackground().setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)), PorterDuff.Mode.SRC_ATOP);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (predefinedData.equals("bgcolor")) {
            try {
                clBgDownload.setBackgroundColor(Color.parseColor(dbhelper.get_tbl_username(29)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Function Tombol Back
    @Override
    public void onBackPressed() {
        finish();
    }
}