package com.example.digitalsawit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AppSetting extends AppCompatActivity {

    DatabaseHelper dbhelper;
    DialogHelper dialogHelper;
    Handler handler = new Handler();

    private byte[] gambar1, gambar2, gambar3;
    int v_data_proses;
    AutoCompleteTextView acAppLanguage;
    ImageView takeprofilePicture, imgprofile, imglogo, takeimagelogo, takeimagebackground, imgbackground;
    TextView tvBgImg, imgInsertLogo, tvnamasystem;
    Button btnsimpan;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        dbhelper = new DatabaseHelper(this);
        dialogHelper = new DialogHelper(this);
        takeprofilePicture = findViewById(R.id.myPictSetting);
        imgprofile  = findViewById(R.id.imageView3);
        acAppLanguage = findViewById(R.id.acAppLanguage);
        imglogo = findViewById(R.id.logoAplikasi);
        tvBgImg = findViewById(R.id.tvBackgroundImg);
        takeimagelogo   = findViewById(R.id.imageView5);
        imgInsertLogo   = findViewById(R.id.imgInsertLogo);
        takeimagebackground = findViewById(R.id.imageView4);
        imgbackground       = findViewById(R.id.backgroundAplikasi);
        tvnamasystem        = findViewById(R.id.textView23);
        btnsimpan           = findViewById(R.id.submitSetting);

        acAppLanguage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tvnamasystem.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try{
            tvnamasystem.setText(dbhelper.get_tbl_username(25));
        } catch (Exception e) {
            e.printStackTrace();
            tvnamasystem.setText("NAMA SYSTEM");
        }

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(0),
                    0, dbhelper.get_gambar_user(0).length);
            imgprofile.setImageBitmap(compressedBitmap);
            imgprofile.setVisibility(View.VISIBLE);
            imgprofile.setBackground(null);
//            profilePicture.setForeground(null);
            gambar1 = dbhelper.get_gambar_user(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(1),
                    0, dbhelper.get_gambar_user(1).length);
            imglogo.setImageBitmap(compressedBitmap);
            imglogo.setVisibility(View.VISIBLE);
            imglogo.setBackground(null);
            //imgphoto.setForeground(null);
            gambar2 = dbhelper.get_gambar_user(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(2),
                    0, dbhelper.get_gambar_user(2).length);
            imgbackground.setImageBitmap(compressedBitmap);
            imgbackground.setVisibility(View.VISIBLE);
            imgbackground.setBackground(null);
            //imgphoto.setForeground(null);
            gambar3 = dbhelper.get_gambar_user(2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_dlg_title = "Apakah anda yakin akan menyimpan perubahan?";
                Activity_Login.v_dlg_btn1 = "YA";
                Activity_Login.v_dlg_btn2 = "TIDAK";
                dialogHelper.showDialogYesNo();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.postDelayed(this, 500);
                        if (Activity_Login.v_rtn_dlg_string.equals("CANCEL") ||
                                Activity_Login.v_rtn_dlg_string.equals("NO")) {
                            Activity_Login.v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                        }
                        if (Activity_Login.v_rtn_dlg_string.equals("OK")) {
                            Activity_Login.v_rtn_dlg_string = "";
                            handler.removeCallbacks(this);
                            dbhelper.update_nama_system(tvnamasystem.getText().toString());
                            dbhelper.update_gambar(gambar1);
                            dbhelper.update_logo(gambar2);
                            dbhelper.update_background(gambar3);
                            finish();
                        }
                    }
                }, 500);

            }
        });

        takeprofilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_data_proses = 2;
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).
                        setFixAspectRatio(true).setAspectRatio(1, 1).start(AppSetting.this);
            }
        });

        takeimagelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_data_proses = 3;
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).
                        setFixAspectRatio(true).setAspectRatio(1, 1).start(AppSetting.this);
            }
        });

        takeimagebackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_data_proses = 4;
                CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).
                        setFixAspectRatio(true).setAspectRatio(1, 1).start(AppSetting.this);
            }
        });

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(v_data_proses == 2) {
            try {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        InputStream iStream =   getContentResolver().openInputStream(result.getUri());
                        gambar1 = getBytes(iStream);

                        if(gambar1.length>500000) {
                            Activity_Login.v_dlg_title = "Ukuran Gambar Terlalu Besar";
                            Activity_Login.v_dlg_btn1 = "OK";
                            dialogHelper.showDialogInfo();
                            gambar1 = dbhelper.get_gambar_user(0);
                        }
                        else{
                            imgprofile.setImageURI(result.getUri());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(v_data_proses == 3) {
            try {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        InputStream iStream =   getContentResolver().openInputStream(result.getUri());
                        gambar2 = getBytes(iStream);

                        if(gambar2.length>500000) {
                            Activity_Login.v_dlg_title = "Ukuran Gambar Terlalu Besar";
                            Activity_Login.v_dlg_btn1 = "OK";
                            dialogHelper.showDialogInfo();
                            gambar2 = dbhelper.get_gambar_user(1);
                        }
                        else{
                            imglogo.setImageURI(result.getUri());
                            imgInsertLogo.setText(result.getUri().toString());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//
        if(v_data_proses == 4) {
            try {
                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        InputStream iStream = getContentResolver().openInputStream(result.getUri());
                        gambar3 = getBytes(iStream);

                        if (gambar3.length > 500000) {
                            Activity_Login.v_dlg_title = "Ukuran Gambar Terlalu Besar";
                            Activity_Login.v_dlg_btn1 = "OK";
                            dialogHelper.showDialogInfo();
                            gambar3 = dbhelper.get_gambar_user(2);
                        }
                        else{
                            imgbackground.setImageURI(result.getUri());
                            tvBgImg.setText(result.getUri().toString());
                        }
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

}