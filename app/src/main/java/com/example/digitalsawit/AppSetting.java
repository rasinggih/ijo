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
import android.view.View;
import android.widget.AutoCompleteTextView;
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
    private byte[] gambar;
    int v_data_proses;
    AutoCompleteTextView acAppLanguage;
    ImageView profilePicture, dropAppLanguage;
    TextView tvBgImg, imgInsertLogo;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        dbhelper = new DatabaseHelper(this);

        profilePicture = findViewById(R.id.myPictSetting);
        acAppLanguage = findViewById(R.id.acAppLanguage);
        dropAppLanguage = findViewById(R.id.dropAppLanguage);
        imgInsertLogo = findViewById(R.id.imgInsertLogo);
        tvBgImg = findViewById(R.id.tvBackgroundImg);

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(), 0, dbhelper.get_gambar_user().length);
            profilePicture.setImageBitmap(compressedBitmap);
            profilePicture.setVisibility(View.VISIBLE);
            profilePicture.setBackground(null);
//            profilePicture.setForeground(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_data_proses = 2;
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
                        profilePicture.setImageURI(result.getUri());
                        InputStream iStream =   getContentResolver().openInputStream(result.getUri());
                        gambar = getBytes(iStream);
                        dbhelper.update_gambar(gambar);

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
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}