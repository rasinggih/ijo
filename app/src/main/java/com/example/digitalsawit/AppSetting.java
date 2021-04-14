package com.example.digitalsawit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

public class AppSetting extends AppCompatActivity {

    DatabaseHelper dbhelper;

    AutoCompleteTextView acAppLanguage;
    ImageView profilePicture, dropAppLanguage, imgInsertLogo;
    TextView tvBgImg;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        dbhelper = new DatabaseHelper(this);

        acAppLanguage = findViewById(R.id.acAppLanguage);
        dropAppLanguage = findViewById(R.id.dropAppLanguage);
        imgInsertLogo = findViewById(R.id.imgInsertLogo);
        tvBgImg = findViewById(R.id.tvBackgroundImg);

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(), 0, dbhelper.get_gambar_user().length);
            profilePicture.setImageBitmap(compressedBitmap);
            profilePicture.setVisibility(View.VISIBLE);
            profilePicture.setBackground(null);
            profilePicture.setForeground(null);
            profilePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}