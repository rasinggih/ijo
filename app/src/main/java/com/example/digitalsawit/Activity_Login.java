package com.example.digitalsawit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kishan.askpermission.AskPermission;
import com.kishan.askpermission.ErrorCallback;
import com.kishan.askpermission.PermissionCallback;
import com.kishan.askpermission.PermissionInterface;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class Activity_Login extends AppCompatActivity {

    //Inisialisasi DatabaseHelper
    DatabaseHelper dbhelper;

    //Inisialisasi String
    private String v_tvpassdlg, v_tvpassdlg2, v_password_input;
    public static String v_dlg_title,
            v_dlg_btn1, v_dlg_btn2, v_dlg_btn3,
            v_rtn_dlg_string, v_rtn_dlg_string2, v_rtn_dlg_string3;
    public static String v_username, v_tvpass, v_tvpass2, return_koneksi, url_data;
    private String status_koneksi = "NO";

    //Inisialisasi Integer
    private int  RESULT_LOAD_IMG = 100, v_data_proses;

    //Inisialisasi ByteArray
    byte[] gambar;

    //Inisialisasi Edittext
    EditText et_username, et_password;

    //Inisialisasi Button
    Button btnlogin;

    //Inisialisasi ImageView
    ImageView imgphoto, imglogo, imgbackground, imgtakephotoreg, imagedialog;

    //Inisialisasi TextView
    TextView tv_lupasandi, tv_hapususername, tvpass, tvversion, tvusername, tvlogo, tvnamasystem, tvLoginHeader, logoOrang;

    //Inisialisasi Dialog
    Dialog dialogregistrasi, dialoglupasandi, dialog, dialoggambar;
    public static ProgressDialog pDialog;
    ProgressDialog loading;
    DialogHelper dialogHelper;
    Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Start Of onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Inisialisasi DatabaseHelper
        dbhelper        = new DatabaseHelper(this);
        String str = getResources().getString(R.string.app_name);

        //Inisialisasi Edittext
        et_password     = findViewById(R.id.et_password);

        //Inisialisasi Button
        btnlogin        = findViewById(R.id.btnlogin);

        //Inisialisasi TextView
        tvLoginHeader   = findViewById(R.id.textView4);
        logoOrang       = findViewById(R.id.textView3);
        tv_lupasandi    = findViewById(R.id.txt_lupasandi);
        tv_hapususername= findViewById(R.id.txt_lupasandi2);
        tvpass          = findViewById(R.id.tv_pass3);
        tvversion       = findViewById(R.id.tvversion);
        tvusername      = findViewById(R.id.txt_username);
        tvlogo          = findViewById(R.id.textView6);
        tvnamasystem    = findViewById(R.id.textView2);

        imgphoto        = findViewById(R.id.myPict2);
        imglogo         = findViewById(R.id.myPict);
        imgbackground   = findViewById(R.id.imageView2);

        //Inisialisasi Dialog
        dialogHelper    = new DialogHelper(this);
        pDialog = new ProgressDialog(this);

        //Inisialisasi Dialog Progress
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_progress);
        ProgressBar text = dialog.findViewById(R.id.progressBar);
        TextView text2 = dialog.findViewById(R.id.textView271);
        text2.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar2);

        //Inisialisasi Lupa Sandi
        dialoglupasandi             = new Dialog(this);
        dialoglupasandi.setCanceledOnTouchOutside(false);
        dialoglupasandi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoglupasandi.setContentView(R.layout.activity_frm_lupasandi);
        Window window               = dialoglupasandi.getWindow();
        dialoglupasandi.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText etkodeverifikasi   = (EditText)dialoglupasandi.findViewById(R.id.et_kode_verifikasi);
        TextView tvlppasspass       = (TextView)dialoglupasandi.findViewById(R.id.tv_lppass_pass);
        TextView tvlppasspass2      = (TextView)dialoglupasandi.findViewById(R.id.tv_lppass_pass2);
        TextView tvpassdlg          = (TextView)dialoglupasandi.findViewById(R.id.tv_pass);
        TextView tvpassdlg2         = (TextView)dialoglupasandi.findViewById(R.id.tv_pass2);
        EditText etlppasspass       = (EditText)dialoglupasandi.findViewById(R.id.et_lppass_pass);
        EditText etlppasspass2      = (EditText)dialoglupasandi.findViewById(R.id.et_lppass_pass2);
        Button btnverifikasi        = (Button)dialoglupasandi.findViewById(R.id.btn_verifikasi);
        Button btnverifikasi2       = (Button)dialoglupasandi.findViewById(R.id.btn_verifikasi4);
        Button btnlppasssave        = (Button)dialoglupasandi.findViewById(R.id.btn_lppass_save);
        EditText etregnik           = (EditText)dialoglupasandi.findViewById(R.id.et_reg_nik);
        EditText etregtelp          = (EditText)dialoglupasandi.findViewById(R.id.et_reg_telp);
        ConstraintLayout clsandi    = (ConstraintLayout)dialoglupasandi.findViewById(R.id.clsandi);
        ConstraintLayout clkode     = (ConstraintLayout)dialoglupasandi.findViewById(R.id. constraintLayout26);

        btnverifikasi.setText("DAPATKAN KODE");
        v_tvpassdlg     = "OFF";
        v_tvpassdlg2    = "OFF";
        v_tvpass        = "OFF";
        tvlppasspass.setVisibility(View.INVISIBLE);
        tvlppasspass2.setVisibility(View.INVISIBLE);
        tvpassdlg.setVisibility(View.INVISIBLE);
        tvpassdlg2.setVisibility(View.INVISIBLE);
        etlppasspass.setVisibility(View.INVISIBLE);
        etlppasspass2.setVisibility(View.INVISIBLE);
        btnlppasssave.setVisibility(View.INVISIBLE);

        //Inisialisasi Dialog Registrasi
        dialogregistrasi = new Dialog(this);
        dialogregistrasi.setCanceledOnTouchOutside(false);
        dialogregistrasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogregistrasi.setContentView(R.layout.activity_frm_registrasi);
        dialogregistrasi.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialogregistrasi.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        EditText etregnikreg            = dialogregistrasi.findViewById(R.id.et_reg_nik);
        EditText etregtelpreg           = dialogregistrasi.findViewById(R.id.et_reg_telp);
        EditText etkodeverifikasireg    = dialogregistrasi.findViewById(R.id.et_kode_verifikasi);
        EditText etregusername          = dialogregistrasi.findViewById(R.id.et_reg_username);
        EditText etregpass              = dialogregistrasi.findViewById(R.id.et_reg_pass);
        EditText etregpass2             = dialogregistrasi.findViewById(R.id.et_reg_pass2);

        TextView tvregusername          = dialogregistrasi.findViewById(R.id.tv_reg_username);
        TextView tvregpass              = dialogregistrasi.findViewById(R.id.tv_reg_pass);
        TextView tvregpass2             = dialogregistrasi.findViewById(R.id.tv_reg_pass2);
        TextView tvpassreg              = dialogregistrasi.findViewById(R.id.tv_pass);
        TextView tvpass2                = dialogregistrasi.findViewById(R.id.tv_pass2);

        Button btnregsave               = dialogregistrasi.findViewById(R.id.btn_reg_save);
        Button btnverifikasireg         = dialogregistrasi.findViewById(R.id.btn_verifikasi);
        Button btnverifikasi2reg        = dialogregistrasi.findViewById(R.id.btn_verifikasi2);

        imgtakephotoreg       = dialogregistrasi.findViewById(R.id.myPict3);

        ConstraintLayout clpass         = dialogregistrasi.findViewById(R.id.clpass);
        ConstraintLayout clkodereg      = dialogregistrasi.findViewById(R.id.constraintLayout12);

        //Inisialisasi Dialog Gambar
        dialoggambar                 = new Dialog(this);
        dialoggambar.setContentView(R.layout.dialog_gambar);
        imagedialog         = dialoggambar.findViewById(R.id.imgDlgFotoUser);
        TextView tvnik      = dialoggambar.findViewById(R.id.tvNikDlgShowImg);
        TextView tvnama     = dialoggambar.findViewById(R.id.tvNamaDlgShowImg);
        tvnik.setText(dbhelper.get_tbl_username(3));
        tvnama.setText(dbhelper.get_tbl_username(5));

        //Inisialisasi Fungsi Edittext
        etkodeverifikasi.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etkodeverifikasireg.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etregnik.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etregnikreg.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        generate_version();

        //UbahTema
        try {
            btnlogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
            tvLoginHeader.setTextColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            tv_lupasandi.setTextColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            tv_hapususername.setTextColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            tvusername.setTextColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            logoOrang.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                et_password.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
            }

            if (Build.VERSION.SDK_INT >= 21) {
                Window statusbar = getWindow();
                statusbar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                statusbar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                statusbar.setStatusBarColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!dbhelper.get_count_tbl_username().equals("0")) {
            tvusername.setText(dbhelper.get_tbl_username(0));
            btnlogin.setText("LOGIN");
            et_password.setEnabled(true);
            tv_lupasandi.setVisibility(View.VISIBLE);
            tv_hapususername.setVisibility(View.VISIBLE);
            tvpass.setVisibility(View.VISIBLE);
            tvversion.setVisibility(View.VISIBLE);
            et_password.setVisibility(View.VISIBLE);

            try {
                tvnamasystem.setText(dbhelper.get_tbl_username(25));
            } catch (Exception e) {
                e.printStackTrace();
                tvnamasystem.setText("NAMA SYSTEM");
            }

            // Menampilkan Photo Profile
            try {
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(0), 0, dbhelper.get_gambar_user(0).length);
                imgphoto.setImageBitmap(compressedBitmap);
                imgphoto.setVisibility(View.VISIBLE);
                imgphoto.setBackground(null);
                //imgphoto.setForeground(null);
                imagedialog.setEnabled(true);
                imgphoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialoggambar.show();
                        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(0),  0,
                                dbhelper.get_gambar_user(0).length);
                        imagedialog.setImageBitmap(compressedBitmap);
                        imagedialog.setBackground(null);
                        imagedialog.setVisibility(View.VISIBLE);
                        imagedialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialoggambar.dismiss();
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Menampilkan Logo
            try {
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(1), 0, dbhelper.get_gambar_user(1).length);
                imglogo.setImageBitmap(compressedBitmap);
                imglogo.setVisibility(View.VISIBLE);
                imglogo.setBackground(null);
                tvlogo.setVisibility(View.GONE);
                //imgphoto.setForeground(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(2), 0, dbhelper.get_gambar_user(2).length);
                imgbackground.setImageBitmap(compressedBitmap);
                imgbackground.setVisibility(View.VISIBLE);
                imgbackground.setBackground(null);
                //imgphoto.setForeground(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            et_password.setEnabled(false);
            et_password.setVisibility(View.GONE);
            btnlogin.setText("REGISTRASI");
            btnlogin.setVisibility(View.VISIBLE);
            tv_lupasandi.setVisibility(View.GONE);
            tv_hapususername.setVisibility(View.GONE);
            tvpass.setVisibility(View.GONE);
        }

        imgphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (dbhelper.get_gambar_user(0)!=null) {
                        dialoggambar.show();
                        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(0), 0,
                                dbhelper.get_gambar_user(0).length);
                        imagedialog.setImageBitmap(compressedBitmap);
                        imagedialog.setBackground(null);
                        imagedialog.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS ) != PackageManager.PERMISSION_GRANTED)
                    || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED)) {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

                    // You can show your dialog message here but instead I am
                    // showing the grant permission dialog box
                    ActivityCompat.requestPermissions(this, new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.SEND_SMS},
                            10);

                }
                else {
                    //Requesting permission
                    ActivityCompat.requestPermissions(this, new String[] {
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.SEND_SMS,
                                    Manifest.permission.READ_PHONE_STATE},
                                    10);

                }
            }

//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.LOCATION_HARDWARE}, PERMIS);
            //reqPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EVENT MENAMPILKAN PASSWORD
                if (v_tvpass.equals("OFF")) {
                    v_tvpass = "NO";
                    tvpass.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.visibility_on, 0, 0, 0);
                    et_password.setTransformationMethod(
                            HideReturnsTransformationMethod.getInstance());
                }
                //EVENT MENYEMBUNYIKAN PASSWORD
                else {
                    v_tvpass = "OFF";
                    tvpass.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.visibility_off, 0, 0, 0);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //Inisalisasi Fungsi Button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etregnikreg.requestFocus();

                //EVENT DATA USERNAME MASIH KOSONG
                if (btnlogin.getText().toString().equals("REGISTRASI")) {
                    v_dlg_title = "Proses Registrasi Memerlukan Koneksi Internet dan Pulsa SMS\n\n " +
                            "Pastikan Koneksi Internet Anda Baik dan Memiliki Pulsa SMS untuk Melakukan Permintaan Registrasi." +
                            "\n\nApakah Anda Yakin Akan Melanjutkan?";
                    v_dlg_btn1 = "OK";
                    v_dlg_btn2 = "TIDAK";
                    dialogHelper.showDialogYesNo();
                    btnverifikasireg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(btnverifikasireg.getText().toString().equals("DAPATKAN KODE")){
                                if (TextUtils.isEmpty(etregnikreg.getText())) {
                                    etregnikreg.setError("Harap Isi NIK");
                                }
                                else if (TextUtils.isEmpty(etregtelpreg.getText())) {
                                    etregtelpreg.setError("Harap Isi No. Telp");
                                }
                                else {
                                    pDialog.setMessage("Silahkan Menunggu...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            url_data = "https://ntcorp.co.id/dsi/empstatus.php?p_empcode="+etregnikreg.getText().toString()
                                                    +"&p_notelp="+etregtelpreg.getText().toString();
                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                            JSONObject jsonBody = new JSONObject();

                                            final String requestBody = jsonBody.toString();
                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                    new Response.Listener<String>() {
                                                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonPost = new JSONObject(response.toString());
                                                                return_koneksi = jsonPost.getString("DT_STATUS");
                                                                if (Objects.equals(return_koneksi, "NOT_EMP")) {
                                                                    v_dlg_title = "NIK tidak terdaftar atau tidak aktif, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                } else if (Objects.equals(return_koneksi, "NOT_TELP")) {
                                                                    v_dlg_title = "Nomor Telepon Tidak Sesuai, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                }

//                                                                    if(return_koneksi.equals("VALID")){
                                                                else {
                                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                    JSONObject postData = new JSONObject();

                                                                    String ALLOWED_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
                                                                    Random generator = new Random();
                                                                    StringBuilder randomStringBuilder = new StringBuilder();
                                                                    int randomLength = generator.nextInt(6);
                                                                    final StringBuilder sb = new StringBuilder(6);
                                                                    for (int j = 0; j < 6; ++j)
                                                                        sb.append(ALLOWED_CHARACTERS.charAt(generator.nextInt(ALLOWED_CHARACTERS.length())));
                                                                    String message = String.valueOf(sb);
                                                                    sendSMS(etregtelpreg.getText().toString(),
                                                                            "Kode Verifikasi OPS Anda Adalah : " + message);
                                                                    new SendDeviceDetails().execute(
                                                                            "https://ntcorp.co.id/dsi/insert_verificationcode.php?p_notelp=" +
                                                                                    ""+etregtelpreg.getText().toString()+"&p_verificationcode="+message);
                                                                    // btnverifikasireg.setText("VERIFIKASI KODE");
                                                                    etregnikreg.setEnabled(false);
                                                                    etregtelpreg.setEnabled(false);
                                                                    v_dlg_title = "Kode Verifikasi Telah Dikirim, " +
                                                                            "Silahkan Periksa Kotak Masuk SMS Anda.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();

                                                                }
//                                                                            else{
//                                                                                v_dlg_title = "NIK/No.Telp tidak terdaftar atau tidak aktif, " +
//                                                                                        "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
//                                                                                v_dlg_btn1 = "OK";
//                                                                                dialogHelper.showDialogInfo();
//                                                                            }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    return_koneksi = "";
                                                    v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                    v_dlg_btn1 = "OK";
                                                    dialogHelper.showDialogInfo();
                                                }
                                            });
                                            queue.add(stringRequest);
                                            pDialog.dismiss();
                                            handler.removeCallbacks(this);
                                        }
                                    }, 1000);
                                    //    Toast.makeText(getApplicationContext(), "Verifikasi kode berhasil.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                pDialog.setMessage("Silahkan Menunggu...");
                                pDialog.setCancelable(false);
                                pDialog.show();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        url_data = "https://ntcorp.co.id/dsi/get_verificationcode.php?p_notelp="+etregtelpreg.getText().toString();
                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                        JSONObject jsonBody = new JSONObject();

                                        final String requestBody = jsonBody.toString();
                                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonPost = new JSONObject(response.toString());
                                                        return_koneksi = jsonPost.getString("VERIFICATIONCODE");

                                                        if (return_koneksi.equals(etkodeverifikasireg.getText().toString())) {
                                                            clpass.setVisibility(View.VISIBLE);
                                                            btnverifikasireg.setEnabled(false);
                                                            clkodereg.setVisibility(View.GONE);
                                                            btnverifikasireg.setVisibility(View.GONE);
                                                            btnverifikasi2reg.setEnabled(false);
                                                            btnverifikasi2reg.setVisibility(View.GONE);
                                                            etkodeverifikasireg.setEnabled(false);
                                                            etregtelpreg.setEnabled(false);
                                                            etregnikreg.setEnabled(false);
                                                            tvregusername.setVisibility(View.VISIBLE);
                                                            tvregpass.setVisibility(View.VISIBLE);
                                                            tvregpass2.setVisibility(View.VISIBLE);
                                                            tvpassreg.setVisibility(View.VISIBLE);
                                                            tvpass2.setVisibility(View.VISIBLE);
                                                            etregusername.setVisibility(View.VISIBLE);
                                                            etregpass.setVisibility(View.VISIBLE);
                                                            etregpass2.setVisibility(View.VISIBLE);
                                                            btnregsave.setVisibility(View.VISIBLE);
                                                            btnregsave.setText("PERMINTAAN REGISTRASI");
                                                        }
                                                        else {
                                                            v_dlg_title = "Kode Verifikasi Tidak Valid";
                                                            v_dlg_btn1 = "OK";
                                                            dialogHelper.showDialogInfo();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    return_koneksi = "";
                                                    v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                    v_dlg_btn1 = "OK";
                                                    dialogHelper.showDialogInfo();
                                                }
                                            });
                                        queue.add(stringRequest);
                                        pDialog.dismiss();
                                        handler.removeCallbacks(this);
                                    }
                                }, 1000);
                            }
                        }
                    });

                    btnverifikasi2reg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnverifikasi2reg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(etregnikreg.getText())) {
                                        etregnikreg.setError("Harap Isi NIK");
                                    }
                                    else if (TextUtils.isEmpty(etregtelpreg.getText())) {
                                        etregtelpreg.setError("Harap Isi No. Telp");
                                    }
                                    else if (TextUtils.isEmpty(etkodeverifikasireg.getText())) {
                                        etkodeverifikasireg.setError("Harap Kode Registrasi");
                                    }
                                    else {
                                        pDialog.setMessage("Silahkan Menunggu...");
                                        pDialog.setCancelable(false);
                                        pDialog.show();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                url_data = "https://ntcorp.co.id/dsi/empstatus.php?p_empcode="+etregnikreg.getText().toString()
                                                        +"&p_notelp="+etregtelpreg.getText().toString();
                                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                JSONObject jsonBody = new JSONObject();

                                                final String requestBody = jsonBody.toString();
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonPost = new JSONObject(response.toString());
                                                                return_koneksi = jsonPost.getString("DT_STATUS");
                                                                if (Objects.equals(return_koneksi, "NOT_EMP")) {
                                                                    v_dlg_title = "NIK tidak terdaftar atau tidak aktif, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                } else if (Objects.equals(return_koneksi, "NOT_TELP")) {
                                                                    v_dlg_title = "Nomor Telepon Tidak Sesuai, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                }

//                                                                    if(return_koneksi.equals("VALID")){
                                                                else{
                                                                    etregusername.setText("");
                                                                    etregpass.setText("");
                                                                    etregpass2.setText("");
                                                                    imgtakephotoreg.setImageDrawable(null);
                                                                    imgtakephotoreg.setBackgroundResource(R.drawable.ic_baseline_add_photo_100);
                                                                    url_data = "https://ntcorp.co.id/dsi/get_verificationcode.php?p_notelp="+etregtelpreg.getText().toString();
                                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                    JSONObject jsonBody = new JSONObject();

                                                                    final String requestBody = jsonBody.toString();
                                                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {
                                                                                    try {
                                                                                        JSONObject jsonPost = new JSONObject(response.toString());
                                                                                        return_koneksi = jsonPost.getString("VERIFICATIONCODE");

                                                                                        if(return_koneksi == null){
                                                                                            v_dlg_title = "Harap Lakukan Permintaan Kode Verifikasi";
                                                                                            v_dlg_btn1 = "OK";
                                                                                            dialogHelper.showDialogInfo();
                                                                                        }
                                                                                        else {
                                                                                            if (return_koneksi.equals(etkodeverifikasireg.getText().toString())) {
                                                                                                clpass.setVisibility(View.VISIBLE);
                                                                                                clkodereg.setVisibility(View.GONE);
                                                                                                btnverifikasireg.setVisibility(View.GONE);
                                                                                                btnverifikasi2reg.setVisibility(View.GONE);
                                                                                                etkodeverifikasireg.setEnabled(false);
                                                                                                etregtelpreg.setEnabled(false);
                                                                                                etregnikreg.setEnabled(false);
                                                                                                tvregusername.setVisibility(View.VISIBLE);
                                                                                                tvregpass.setVisibility(View.VISIBLE);
                                                                                                tvregpass2.setVisibility(View.VISIBLE);
                                                                                                tvpassreg.setVisibility(View.VISIBLE);
                                                                                                tvpass2.setVisibility(View.VISIBLE);
                                                                                                etregusername.setVisibility(View.VISIBLE);
                                                                                                etregpass.setVisibility(View.VISIBLE);
                                                                                                etregpass2.setVisibility(View.VISIBLE);
                                                                                                btnregsave.setVisibility(View.VISIBLE);
                                                                                                btnregsave.setText("PERMINTAAN REGISTRASI");
                                                                                            } else {
                                                                                                v_dlg_title = "Kode Verifikasi Tidak Valid";
                                                                                                v_dlg_btn1 = "OK";
                                                                                                dialogHelper.showDialogInfo();
                                                                                            }
                                                                                        }
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }

                                                                                }
                                                                            }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            return_koneksi = "";
                                                                            v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                                            v_dlg_btn1 = "OK";
                                                                            dialogHelper.showDialogInfo();
                                                                        }
                                                                    });
                                                                    queue.add(stringRequest);
                                                                }
//                                                                                else{
//                                                                                    v_dlg_title = "NIK/No.Telp tidak terdaftar atau tidak aktif, " +
//                                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
//                                                                                    v_dlg_btn1 = "OK";
//                                                                                    dialogHelper.showDialogInfo();
//                                                                                }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        return_koneksi = "";
                                                        v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                        v_dlg_btn1 = "OK";
                                                        dialogHelper.showDialogInfo();
                                                    }
                                                });
                                                queue.add(stringRequest);
                                                pDialog.dismiss();
                                                handler.removeCallbacks(this);
                                            }
                                        }, 1000);
                                    }
                                }
                            });
                        }
                    });

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.postDelayed(this, 500);
                            if (v_rtn_dlg_string.equals("CANCEL") ||
                                    v_rtn_dlg_string.equals("NO")) {
                                v_rtn_dlg_string = "";
                                handler.removeCallbacks(this);
                            }
                            if (v_rtn_dlg_string.equals("OK")) {
                                v_rtn_dlg_string = "";
                                handler.removeCallbacks(this);
                                dialogregistrasi.show();
                                etregnikreg.setEnabled(true);
                                etregtelpreg.setEnabled(true);
                                etkodeverifikasireg.setEnabled(true);
                                etkodeverifikasireg.setText("");
                                btnverifikasireg.setText("DAPATKAN KODE");
                                btnregsave.setText("PERMINTAAN REGISTRASI");
                                v_tvpass = "OFF";
                                v_tvpass2 = "OFF";
                                clkodereg.setVisibility(View.VISIBLE);
                                clpass.setVisibility(View.GONE);
                                btnverifikasireg.setVisibility(View.VISIBLE);
                                btnverifikasi2reg.setVisibility(View.VISIBLE);
                                btnverifikasi2reg.setEnabled(true);
                                etregnikreg.setText("");
                                etregtelpreg.setText("");

                                tvpassreg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (v_tvpass.equals("OFF")) {
                                            v_tvpass = "NO";
                                            tvpassreg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_on, 0, 0, 0);
                                            etregpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                        }
                                        else {
                                            v_tvpass = "OFF";
                                            tvpassreg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_off, 0, 0, 0);
                                            etregpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                        }
                                    }
                                });

                                tvpass2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (v_tvpass2.equals("OFF")) {
                                            v_tvpass2 = "NO";
                                            tvpass2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_on, 0, 0, 0);
                                            etregpass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                        }
                                        else {
                                            v_tvpass2 = "OFF";
                                            tvpass2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_off, 0, 0, 0);
                                            etregpass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                        }
                                    }
                                });

                                imgtakephotoreg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        v_dlg_title = "Silahkan Pilih Aksi Anda Untuk Mengambil Gambar?";
//                                        v_dlg_btn1 = "KAMERA";
//                                        v_dlg_btn2 = "GALERY";
//                                        dialogHelper.showDialogYesNo();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                handler.postDelayed(this, 500);
//                                                if (v_rtn_dlg_string.equals("NO")) {
//                                                    v_rtn_dlg_string = "";
//                                                    handler.removeCallbacks(this);
//                                                    v_data_proses   = 1;
//                                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                                                    photoPickerIntent.setType("image/*");
//                                                    startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
//                                                }
//                                                if (v_rtn_dlg_string.equals("CANCEL")) {
//                                                    v_rtn_dlg_string = "";
//                                                    handler.removeCallbacks(this);
//                                                }
//                                                if (v_rtn_dlg_string.equals("OK")) {
//                                                    v_rtn_dlg_string = "";
//                                                    handler.removeCallbacks(this);
//                                                    //Aktifitas yang dilakukan
//                                                    v_data_proses   = 2;
//                                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                                                        takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
//                                                        takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
//                                                        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//                                                        startActivityForResult(takePictureIntent, 1);
//                                                    }
//                                                }
//                                            }
//                                        }, 500);
                                        v_data_proses = 2;
                                        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).setFixAspectRatio(true).setAspectRatio(1, 1).start(Activity_Login.this);
                                    }
                                });

                                btnregsave.setOnClickListener(new View.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(View v) {
                                        if (TextUtils.isEmpty(etregusername.getText())) {
                                            etregusername.setError("Harap Isi Username");
                                        }
                                        else if (TextUtils.isEmpty(etregpass.getText())) {
                                            etregpass.setError("Kata Sandi Tidak Boleh Kosong");
                                            etregpass.requestFocus();
                                        }
                                        else {
                                            if (!etregpass.getText().toString().equals(etregpass2.getText().toString())) {
                                                v_dlg_title = "Kata Sandi Tidak Sama, Silahkan Ulangi";
                                                v_dlg_btn1 = "OK";
                                                dialogHelper.showDialogInfo();
                                            }
                                            else {
                                                try {
                                                    v_password_input = AESEnkrip.encrypt(etregpass2.getText().toString());
                                                    dbhelper.generate_tbl_username(etregusername.getText().toString(), v_password_input,
                                                            etregnikreg.getText().toString(), etregtelpreg.getText().toString(), gambar);
                                                    Bitmap compressedBitmap = BitmapFactory.decodeByteArray(gambar, 0, gambar.length);
                                                    imgphoto.setImageBitmap(compressedBitmap);
                                                    imgphoto.setVisibility(View.VISIBLE);
                                                    imgphoto.setBackground(null);
//                                                    imgphoto.setForeground(null);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                dialogregistrasi.dismiss();
                                                tvusername.setText(etregusername.getText().toString());
                                                gambar = null;
                                                btnlogin.setText("LOGIN");
                                                et_password.setEnabled(true);
                                                tv_lupasandi.setVisibility(View.VISIBLE);
                                                tv_hapususername.setVisibility(View.VISIBLE);
                                                tvversion.setVisibility(View.VISIBLE);
                                                tvpass.setVisibility(View.VISIBLE);
                                                et_password.setVisibility(View.VISIBLE);

                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }, 500);
                }

                //EVENT DATA USERNAME SUDAH ADA
                else {
                    try {
                        v_password_input = AESEnkrip.encrypt(et_password.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!dbhelper.get_tbl_username(1).equals(v_password_input)) {
                        v_dlg_title = "Kata Sandi Salah, Silahkan Ulangi";
                        v_dlg_btn1 = "OK";
                        dialogHelper.showDialogInfo();
                    }
                    else {
                        Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        et_password.setText("");
                    }
                }
            }
        });

        tv_hapususername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v_dlg_title = "Apakah anda yakin akan menghapus User Login?";
                v_dlg_btn1 = "OK";
                v_dlg_btn2 = "TIDAK";
                dialogHelper.showDialogYesNo();
                handler.postDelayed(new Runnable() {
                    @SuppressLint("NewApi")
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        handler.postDelayed(this, 500);
                        if (v_rtn_dlg_string.equals("CANCEL") ||
                                v_rtn_dlg_string.equals("NO")) {
                            v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                        }
                        if (v_rtn_dlg_string.equals("OK")) {
                            v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                            //Aktifitas yang dilakukan
                            et_password.setEnabled(false);
                            tvusername.setText("Silahkan Registrasi Terlebih Dahulu");
                            btnlogin.setText("REGISTRASI");
//                            txtdeleteuser.setVisibility(View.GONE);
//                            txtlupasandi.setVisibility(View.GONE);
//                            version.setEnabled(false);
                            dbhelper.delete_data_username();
                            v_username = null;
                            imgphoto.setImageBitmap(null);
                            imgphoto.setBackground(getApplicationContext().getDrawable(R.drawable.border_dialog));
//                            imgphoto.setForeground(getApplicationContext().getDrawable(R.drawable.username));
//                            Toast.makeText(getApplicationContext(),
//                                    "Berhasil menghapus Login User",
//                                    Toast.LENGTH_SHORT).show();
                            tv_lupasandi.setVisibility(View.GONE);
                            tv_hapususername.setVisibility(View.GONE);
                            tvpass.setVisibility(View.GONE);
                            et_password.setVisibility(View.GONE);
                            imagedialog.setEnabled(false);
                        }
                    }
                }, 500);
            }
        });

        tv_lupasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_dlg_title = "Apakah anda yakin akan mengganti Kata Sandi?";
                v_dlg_btn1 = "OK";
                v_dlg_btn2 = "TIDAK";
                dialogHelper.showDialogYesNo();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(this, 500);
                        if (v_rtn_dlg_string.equals("CANCEL") ||
                                v_rtn_dlg_string.equals("NO")) {
                            v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                        }
                        if (v_rtn_dlg_string.equals("OK")) {
                            v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                            dialoglupasandi.show();
                            etregtelp.setText(dbhelper.get_tbl_username(4));
                            etregnik.setText(dbhelper.get_tbl_username(3));
                            etregnik.setEnabled(false);
                            etregtelp.setEnabled(false);
                            //etregtelp.setText("");
                            clkode.setVisibility(View.VISIBLE);
                            btnverifikasi.setVisibility(View.VISIBLE);
                            btnverifikasi2.setVisibility(View.VISIBLE);
                            etkodeverifikasi.setVisibility(View.VISIBLE);
                            clsandi.setVisibility(View.GONE);
                            etregtelp.requestFocus();
                            etkodeverifikasi.setText("");
                            btnverifikasi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (etregnik.getText().toString().equals("")) {
                                        v_dlg_title = "Harap Isi NIK Anda";
                                        v_dlg_btn1 = "OK";
                                        dialogHelper.showDialogInfo();
                                    }
                                    else if (etregtelp.getText().toString().equals("")) {
                                        v_dlg_title = "Harap Isi No. Telp Anda";
                                        v_dlg_btn1 = "OK";
                                        dialogHelper.showDialogInfo();
                                    }
                                    else {
                                        pDialog.setMessage("Silahkan Menunggu...");
                                        pDialog.setCancelable(false);
                                        pDialog.show();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                url_data = "https://ntcorp.co.id/dsi/empstatus.php?p_empcode=" + etregnik.getText().toString()
                                                        + "&p_notelp=" + etregtelp.getText().toString();
                                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                JSONObject jsonBody = new JSONObject();

                                                final String requestBody = jsonBody.toString();
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONObject jsonPost = new JSONObject(response.toString());
                                                                    return_koneksi = jsonPost.getString("DT_STATUS");
                                                                    if (Objects.equals(return_koneksi, "NOT_EMP")) {
                                                                        v_dlg_title = "NIK tidak terdaftar atau tidak aktif, " +
                                                                                "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                        v_dlg_btn1 = "OK";
                                                                        dialogHelper.showDialogInfo();
                                                                    } else if (Objects.equals(return_koneksi, "NOT_TELP")) {
                                                                        v_dlg_title = "Nomor Telepon Tidak Sesuai, " +
                                                                                "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                        v_dlg_btn1 = "OK";
                                                                        dialogHelper.showDialogInfo();
                                                                    }

//                                                                    if(return_koneksi.equals("VALID")){
                                                                    else {
                                                                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                        JSONObject postData = new JSONObject();

                                                                        String ALLOWED_CHARACTERS = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
                                                                        Random generator = new Random();
                                                                        StringBuilder randomStringBuilder = new StringBuilder();
                                                                        int randomLength = generator.nextInt(6);
                                                                        final StringBuilder sb = new StringBuilder(6);
                                                                        for (int j = 0; j < 6; ++j)
                                                                            sb.append(ALLOWED_CHARACTERS.charAt(generator.nextInt(ALLOWED_CHARACTERS.length())));
                                                                        String message = String.valueOf(sb);
                                                                        sendSMS(etregtelp.getText().toString(),
                                                                                "Kode Verifikasi OPS Anda Adalah : " + message);
                                                                        new SendDeviceDetails().execute(
                                                                                "https://ntcorp.co.id/dsi/insert_verificationcode.php?p_notelp=" +
                                                                                        "" + etregtelp.getText().toString() + "&p_verificationcode=" + message);
                                                                        // btnverifikasireg.setText("VERIFIKASI KODE");
                                                                        etkodeverifikasi.setEnabled(true);
                                                                        etkodeverifikasi.setText("");
                                                                        etlppasspass.setText("");
                                                                        etlppasspass2.setText("");
                                                                        v_dlg_title = "Kode Verifikasi Telah Dikirim, " +
                                                                                "Silahkan Periksa Kotak Masuk SMS Anda.";
                                                                        v_dlg_btn1 = "OK";
                                                                        dialogHelper.showDialogInfo();
                                                                    }
//                                                                    else {
//                                                                        v_dlg_title = "NIK/No.Telp tidak terdaftar atau tidak aktif, " +
//                                                                                "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
//                                                                        v_dlg_btn1 = "OK";
//                                                                        dialogHelper.showDialogInfo();
//                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }
                                                        }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        return_koneksi = "";
                                                        v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                        v_dlg_btn1 = "OK";
                                                        dialogHelper.showDialogInfo();
                                                    }
                                                });
                                                queue.add(stringRequest);
                                                pDialog.dismiss();
                                                handler.removeCallbacks(this);
                                            }
                                        }, 1000);
                                    }
                                }
                            });

                            btnverifikasi2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(etregnik.getText())) {
                                        etregnik.setError("Harap Isi NIK");
                                    }
                                    else if (TextUtils.isEmpty(etregtelp.getText())) {
                                        etregtelp.setError("Harap Isi No. Telp");
                                    }
                                    else if (TextUtils.isEmpty(etkodeverifikasi.getText())) {
                                        etkodeverifikasi.setError("Harap Kode Registrasi");
                                    }
                                    else {
                                        pDialog.setMessage("Silahkan Menunggu...");
                                        pDialog.setCancelable(false);
                                        pDialog.show();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                url_data = "https://ntcorp.co.id/dsi/empstatus.php?p_empcode="+etregnik.getText().toString()
                                                        +"&p_notelp="+etregtelp.getText().toString();
                                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                JSONObject jsonBody = new JSONObject();

                                                final String requestBody = jsonBody.toString();
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject jsonPost = new JSONObject(response.toString());
                                                                return_koneksi = jsonPost.getString("DT_STATUS");

                                                                if (Objects.equals(return_koneksi, "NOT_EMP")) {
                                                                    v_dlg_title = "NIK tidak terdaftar atau tidak aktif, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                } else if (Objects.equals(return_koneksi, "NOT_TELP")) {
                                                                    v_dlg_title = "Nomor Telepon Tidak Sesuai, " +
                                                                            "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
                                                                    v_dlg_btn1 = "OK";
                                                                    dialogHelper.showDialogInfo();
                                                                }

//                                                                    if(return_koneksi.equals("VALID")){
                                                                else{
                                                                    url_data = "https://ntcorp.co.id/dsi/get_verificationcode.php?p_notelp="+etregtelp.getText().toString();
                                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                    JSONObject jsonBody = new JSONObject();

                                                                    final String requestBody = jsonBody.toString();
                                                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                                                                            new Response.Listener<String>() {
                                                                                @Override
                                                                                public void onResponse(String response) {
                                                                                    try {
                                                                                        JSONObject jsonPost = new JSONObject(response.toString());
                                                                                        return_koneksi = jsonPost.getString("VERIFICATIONCODE");

                                                                                        if(return_koneksi == null){
                                                                                            v_dlg_title = "Harap Lakukan Permintaan Kode Verifikasi";
                                                                                            v_dlg_btn1 = "OK";
                                                                                            dialogHelper.showDialogInfo();
                                                                                        }
                                                                                        else {
                                                                                            if (return_koneksi.equals(etkodeverifikasi.getText().toString())) {
                                                                                                etregnik.setEnabled(false);
                                                                                                etregtelp.setEnabled(false);
                                                                                                btnverifikasi.setVisibility(View.GONE);
                                                                                                btnverifikasi2.setVisibility(View.GONE);
                                                                                                clsandi.setVisibility(View.VISIBLE);
                                                                                                etkodeverifikasi.setEnabled(false);
                                                                                                clkode.setVisibility(View.GONE);
                                                                                                tvlppasspass.setVisibility(View.VISIBLE);
                                                                                                tvlppasspass2.setVisibility(View.VISIBLE);
                                                                                                tvpassdlg.setVisibility(View.VISIBLE);
                                                                                                tvpassdlg2.setVisibility(View.VISIBLE);
                                                                                                etlppasspass.setVisibility(View.VISIBLE);
                                                                                                etlppasspass2.setVisibility(View.VISIBLE);
                                                                                                btnlppasssave.setVisibility(View.VISIBLE);
                                                                                                etkodeverifikasi.setText("");
                                                                                                etlppasspass.setText("");
                                                                                                etlppasspass2.setText("");

                                                                                            } else {
                                                                                                v_dlg_title = "Kode Verifikasi Tidak Valid";
                                                                                                v_dlg_btn1 = "OK";
                                                                                                dialogHelper.showDialogInfo();
                                                                                            }
                                                                                        }
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }

                                                                                }
                                                                            }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            return_koneksi = "";
                                                                            v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                                            v_dlg_btn1 = "OK";
                                                                            dialogHelper.showDialogInfo();
                                                                        }
                                                                    });
                                                                    queue.add(stringRequest);
                                                                }
//                                                                    else{
//                                                                        v_dlg_title = "NIK/No.Telp tidak terdaftar atau tidak aktif, " +
//                                                                                "silahkan hubungi HRD/Personalia dan silahkan ulangi.";
//                                                                        v_dlg_btn1 = "OK";
//                                                                        dialogHelper.showDialogInfo();
//                                                                    }
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            return_koneksi = "";
                                                            v_dlg_title = "Harap Periksa Jaringan Internet Anda";
                                                            v_dlg_btn1 = "OK";
                                                            dialogHelper.showDialogInfo();
                                                        }
                                                    });
                                                queue.add(stringRequest);
                                                pDialog.dismiss();
                                                handler.removeCallbacks(this);
                                            }
                                        }, 1000);
                                        //  Toast.makeText(getApplicationContext(), "Verifikasi kode berhasil.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            btnlppasssave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(etlppasspass.getText())) {
                                        v_dlg_title = "Kata Sandi Tidak Boleh Kosong";
                                        v_dlg_btn1 = "OK";
                                        dialogHelper.showDialogInfo();
                                        etlppasspass.setError("Harap Isi Kata Sandi");
                                        etlppasspass.requestFocus();
                                    } else {
                                        if (etlppasspass.getText().toString().equals(etlppasspass2.getText().toString())) {
                                            try {
                                                v_password_input = AESEnkrip.encrypt(etlppasspass2.getText().toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            dbhelper.update_password(v_password_input);
                                            dialoglupasandi.dismiss();
                                            v_dlg_title = "Perubahan Kata Sandi Berhasil";
                                            v_dlg_btn1 = "OK";
                                            dialogHelper.showDialogInfo();

                                        } else {
                                            v_dlg_title = "Kata Sandi Tidak Sama, Silahkan Ulangi";
                                            v_dlg_btn1 = "OK";
                                            dialogHelper.showDialogInfo();
                                            etlppasspass2.setError("Kata Sandi Harus Sama");
                                            etlppasspass2.requestFocus();
                                        }
                                    }
                                }
                            });

                            tvpassdlg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (v_tvpassdlg.equals("OFF")) {
                                        v_tvpassdlg = "NO";
                                        tvpassdlg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_on, 0, 0, 0);
                                        etlppasspass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    }
                                    else {
                                        v_tvpassdlg = "OFF";
                                        tvpassdlg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_off, 0, 0, 0);
                                        etlppasspass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    }
                                }
                            });

                            tvpassdlg2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (v_tvpassdlg2.equals("OFF")) {
                                        v_tvpassdlg2 = "NO";
                                        tvpassdlg2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_on, 0, 0, 0);
                                        etlppasspass2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    }
                                    else {
                                        v_tvpassdlg2 = "OFF";
                                        tvpassdlg2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visibility_off, 0, 0, 0);
                                        etlppasspass2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    }
                                }
                            });
                        }
                    }
                }, 500);
            }
        });

        //StartActivity();

        //End Of onCreate
    }

    //Activity Result Gambar
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (v_data_proses == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    //imgphoto.setImageBitmap(selectedImage);
                    imgtakephotoreg.setVisibility(View.VISIBLE);
                    imgtakephotoreg.setBackground(null);
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                    gambar = stream1.toByteArray();
                    imagedialog.setEnabled(true);
                    if (gambar.length>500000) {
                        v_dlg_title = "Ukuran Gambar Max 2MB";
                        v_dlg_btn1 = "OK";
                        dialogHelper.showDialogInfo();
                        gambar = null;
                        imgtakephotoreg.setImageBitmap(null);
                        imgtakephotoreg.setBackground(getApplicationContext().getDrawable(R.drawable.border_dialog));
                        //imgtakephotoreg.setForeground(getApplicationContext().getDrawable(R.drawable.take_image));
                    }
                    else {
                        imgtakephotoreg.setBackground(null);
                        //imgtakephotoreg.setForeground(null);
                        imgtakephotoreg.setImageBitmap(selectedImage);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }

        if(v_data_proses == 2) {
            try {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        InputStream iStream =   getContentResolver().openInputStream(result.getUri());
                        gambar = getBytes(iStream);
                        if (gambar.length > 2000000) {
                            v_dlg_title = "Ukuran Gambar Terlalu Besar";
                            v_dlg_btn1 = "OK";
                            dialogHelper.showDialogInfo();
                            gambar = null;
                        }
                        else {
                            ((ImageView) dialogregistrasi.findViewById(R.id.myPict3)).setImageURI(result.getUri());
                        }

                       // gambar = result.getUri();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 512;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    //Function Generate Version
    void generate_version() {
        url_data = "https://ntcorp.co.id/dsi/dsi_version.php?p_systemcode=00";
        RequestQueue queue = Volley.newRequestQueue(this);
        return_koneksi = null;
        JSONObject jsonBody = new JSONObject();
        final String requestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonPost = new JSONObject(response.toString());
                            return_koneksi = "OK";
                            dbhelper.generate_tbl_version(jsonPost.getString("VERSIONNUMBER"),
                                    jsonPost.getString("VERSIONNAME"),
                                    jsonPost.getString("TDATE"),
                                    jsonPost.getString("REMARKS"));
                            String status_update;
                            try {
                                if (!dbhelper.get_tbl_version(2).equals(dbhelper.get_tbl_version(6))) {
                                    status_update = "NEW";
                                    dbhelper.updatestatusversion(status_update);
                                    tvversion.setTextColor(0xFFD51616);
                                    tvversion.setText("Update System Tersedia");
                                    v_dlg_title = "Update System Tersedia\n\n"+ dbhelper.get_tbl_version(7)+ "\n[" +
                                            dbhelper.get_tbl_version(8)+ "]\n\n" +dbhelper.get_tbl_version(9);
                                    v_dlg_btn1 = "OK";
                                    dialogHelper.showDialogInfo();

                                    tvversion.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            v_dlg_title = "Update System Tersedia\n\n"+ dbhelper.get_tbl_version(7)+ "\n[" +
                                                    dbhelper.get_tbl_version(8)+ "]\n\n" +dbhelper.get_tbl_version(9);
                                            v_dlg_btn1 = "OK";
                                            dialogHelper.showDialogInfo();
                                        }
                                    });
                                } else {
                                    status_update = "NO";
                                    dbhelper.updatestatusversion(status_update);
                                    tvversion.setTextColor(0xff305031);
                                    tvversion.setText(dbhelper.get_tbl_version(3));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                return_koneksi = null;
                v_dlg_title = "Anda Berada Pada Mode Offline";
                v_dlg_btn1 = "OK";
                dialogHelper.showDialogInfo();
                String status_update;
                status_update = "NO";
                dbhelper.updatestatusversion(status_update);
                tvversion.setTextColor(0xff305031);
                tvversion.setText(dbhelper.get_tbl_version(3));

            }
        });
        queue.add(stringRequest);
    }


    //Function Generate Version
    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }



    //Background Proses Start Activity
    private void StartActivity() {
        class AddEmployee extends AsyncTask<Void, Void, Void> {
            int i = 1;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @SuppressLint("SetTextI18n")
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);

            }

            @Override
            protected Void doInBackground(Void... v) {

                return null;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    public void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
//        registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(getBaseContext(), "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(getBaseContext(), "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(getBaseContext(), "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(getBaseContext(), "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(SENT));
//
//        //---when the SMS has been delivered---
//        registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private static final int REQUEST_PERMISSIONS = 20;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void reqPermission() {
        new AskPermission.Builder(this).setPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .setCallback((PermissionCallback) this)
                .setErrorCallback((ErrorCallback) this)
                .request(REQUEST_PERMISSIONS);
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

        v_dlg_title = "Apakah anda yakin akan keluar?";
        v_dlg_btn1 = "YA";
        v_dlg_btn2 = "TIDAK";
        dialogHelper.showDialogYesNo();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 500);
                if (v_rtn_dlg_string.equals("CANCEL") ||
                        v_rtn_dlg_string.equals("NO")) {
                    v_rtn_dlg_string = "";
                    handler.removeCallbacks(this);
                }
                if (v_rtn_dlg_string.equals("OK")) {
                    v_rtn_dlg_string = "";
                    handler.removeCallbacks(this);
                    finish();

                }
            }
        }, 500);
    }
}