package com.example.digitalsawit.ui.home;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalsawit.DatabaseHelper;
import com.example.digitalsawit.GPSTracker;
import com.example.digitalsawit.Activity_Login;
import com.example.digitalsawit.MainActivity;
import com.example.digitalsawit.R;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private final int MAX_NUMBER = 99;
    private int notification_numbercount = 1;


    DatabaseHelper dbhelper;
    public static TextView tvjabatanuser, tvnamauser;
    ImageView sensusphtpage, analisdaunpage, sampledaunpage;
    TabLayout tabLayout;
    ListView lvfragment, lvhistory;
    ImageView imagepemanen, imgdropkendala, imgcamkendala;
    byte[] gambar2, gambar, gambar1;
    ImageView imageselesai, openDrawerBtn, imgMiniProfile;
    AutoCompleteTextView ackendala, aclokasikendala;
    TextView tvSystemNameFragmentHome, tvNotifCounter;
    EditText etdesckendala, etpanjangkendala, etlebarkendala, etluaskendala, filtertglhistory;
    Button btnsimpankendala, btnrefresh;
    String lat_awal, long_awal, savedate;
    ScrollView scrollkendala;
    ConstraintLayout clRiwayatFragment, clBgMainActivity;

    private List<String> lables;
    private ArrayAdapter<String> dataAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dbhelper = new DatabaseHelper(getContext());

        tvnamauser = root.findViewById(R.id.tvNamaUser);
        tvSystemNameFragmentHome = root.findViewById(R.id.systemNameFragmentHome);
        clBgMainActivity = root.findViewById(R.id.clBgMainActivity);
        tvjabatanuser = root.findViewById(R.id.tvJabatanUser);
        sensusphtpage = root.findViewById(R.id.sensusphtpage);
        analisdaunpage = root.findViewById(R.id.pageanalisdaun);
        imgMiniProfile = root.findViewById(R.id.imgMiniProfile);
        tvNotifCounter = root.findViewById(R.id.tvNotifCounter);
        sampledaunpage = root.findViewById(R.id.pagesampledaun);
        tabLayout = root.findViewById(R.id.tabLayout);
        openDrawerBtn = root.findViewById(R.id.openDrawerBtn);
        scrollkendala = root.findViewById(R.id.scrollViewKendala);
        lvfragment = root.findViewById(R.id.lvfragment);
        btnrefresh = root.findViewById(R.id.btnRefreshHome);
        lvhistory = root.findViewById(R.id.lvHistoryTransaksi);
        ackendala = root.findViewById(R.id.autoCompleteTextView39);
        imgdropkendala = root.findViewById(R.id.imageView56);
        etdesckendala = root.findViewById(R.id.etdesckendala);
        btnsimpankendala = root.findViewById(R.id.btnKendalaok);
        imgcamkendala = root.findViewById(R.id.imgkendala);
        aclokasikendala = root.findViewById(R.id.autoCompleteTextView5);
        aclokasikendala.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etpanjangkendala = root.findViewById(R.id.editTextNumberDecimal);
        etlebarkendala = root.findViewById(R.id.editTextNumberDecimal2);
        etluaskendala = root.findViewById(R.id.editTextNumberDecimal3);
        clRiwayatFragment = root.findViewById(R.id.clRiwayatFragment);
        filtertglhistory = root.findViewById(R.id.etFilterTglHistory);

        String savedate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        filtertglhistory.setText(savedate);
        tvnamauser.setText(dbhelper.get_tbl_username(0));
        tvjabatanuser.setText(dbhelper.get_tbl_username(3));

        savedate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        preparedUserAppData("theme");
        preparedUserAppData("sysname");
        preparedUserAppData("bgcolor");

        openDrawerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        lvfragment.setVisibility(View.VISIBLE);
                        clRiwayatFragment.setVisibility(View.GONE);
                        scrollkendala.setVisibility(View.GONE);
                        break;
                    case 1:
                        clRiwayatFragment.setVisibility(View.VISIBLE);
                        lvfragment.setVisibility(View.GONE);
                        scrollkendala.setVisibility(View.GONE);
                        break;
                    case 2:
                        clRiwayatFragment.setVisibility(View.GONE);
                        lvfragment.setVisibility(View.GONE);
                        scrollkendala.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        imgcamkendala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gambar = null;
                gambar2 = null;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        });

        filtertglhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        btnsimpankendala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ackendala.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Harap Pilih Kendala",
                            Toast.LENGTH_SHORT).show();
                }
                else if (etdesckendala.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Harap Isi Deskripsi Kendala",
                            Toast.LENGTH_SHORT).show();
                }
                else if (aclokasikendala.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Harap Isi Lokasi Kendala",
                            Toast.LENGTH_SHORT).show();
                }
                else if (imgcamkendala.getDrawable()==null) {
                    Toast.makeText(getContext(),"Harap Ambil Gambar Kendala",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    getLocation();
                    String nodoc = "LOG/KDL/" + new SimpleDateFormat("ddMMyy/HHmmss", Locale.getDefault()).format(new Date());
//                    dbhelper.insert_kendala(nodoc, ackendala.getText().toString(),
//                            aclokasikendala.getText().toString(), lat_awal, long_awal, etdesckendala.getText().toString(), etpanjangkendala.getText().toString(), etlebarkendala.getText().toString(),
//                            etluaskendala.getText().toString(), gambar1, "LOG");
                    ackendala.setText("");
                    etdesckendala.setText("");
                    aclokasikendala.setText("");
                    etpanjangkendala.setText("");
                    etlebarkendala.setText("");
                    etluaskendala.setText("");
                    Toast.makeText(getContext(),"Berhasil Menyimpan Kendala",
                            Toast.LENGTH_SHORT).show();
                    gambar1 = null;
                    imgcamkendala.setImageResource(R.drawable.ic_menu_camera);
                }
            }
        });

        tvSystemNameFragmentHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseNumberNotif();
            }
        });

        return root;
    }

    void datepicker() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                SimpleDateFormat nodocDateFormat = new SimpleDateFormat("ddMMyyyy", Locale.US);
                filtertglhistory.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap photoCamera = (Bitmap) data.getExtras().get("data");
            imgcamkendala.setVisibility(View.VISIBLE);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photoCamera.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            gambar1 = stream.toByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(gambar1, 0, gambar1.length);
            imgcamkendala.setImageBitmap(compressedBitmap);
            imgcamkendala.setBackground(null);
        }
    }

    private void preparedUserAppData(String predefinedData) {
        if (predefinedData.equals("theme")) {
            try {
                openDrawerBtn.setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)));
                imgMiniProfile.setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)));
                tvSystemNameFragmentHome.setTextColor(Color.parseColor(dbhelper.get_tbl_username(26)));
                btnsimpankendala.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
                btnrefresh.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dbhelper.get_tbl_username(26))));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (predefinedData.equals("sysname")) {
            try {
                tvSystemNameFragmentHome.setText(dbhelper.get_tbl_username(25));
            } catch (Exception e) {
                e.printStackTrace();
                tvSystemNameFragmentHome.setText("NAMA SYSTEM");
            }
        }

        if (predefinedData.equals("bgcolor")) {
            try {
                clBgMainActivity.setBackgroundColor(Color.parseColor(dbhelper.get_tbl_username(29)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void increaseNumberNotif() {
        notification_numbercount++;
        tvNotifCounter.setText(String.valueOf(notification_numbercount));
    }

    private void getLocation() {
        GPSTracker gps = new GPSTracker (getContext());
        double latitude = gps.getLatitude();
        double longitude= gps.getLongitude();
        lat_awal = String.valueOf(latitude);
        long_awal = String.valueOf(longitude);
    }
}