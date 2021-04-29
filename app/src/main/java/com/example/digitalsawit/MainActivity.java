package com.example.digitalsawit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> listGroupMenu;
    HashMap<String, List<String>> listMenu;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    private AppBarConfiguration mAppBarConfiguration;
    public static String v_rtn_login, v_dlg_title,
            v_dlg_btn1, v_dlg_btn2, v_dlg_btn3,
            v_rtn_dlg_string, v_rtn_dlg_string2, v_rtn_dlg_string3, v_flag_menu, v_remarks_login;
    DialogHelper dialogHelper;
    Handler handler = new Handler();
    View hView;
    ConstraintLayout clnavheader;
    ImageView imglogo;
    DatabaseHelper dbhelper;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        dbhelper    = new DatabaseHelper(this);
        dialogHelper = new DialogHelper(this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        imglogo = hView.findViewById(R.id.myPictSetting2);
        clnavheader = hView.findViewById(R.id.clnavheader);

        try {
            clnavheader.getBackground().setColorFilter(Color.parseColor(dbhelper.get_tbl_username(26)), PorterDuff.Mode.SRC_ATOP);
            if (Build.VERSION.SDK_INT >= 21) {
                Window statusbar = getWindow();
                statusbar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                statusbar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                statusbar.setStatusBarColor(Color.parseColor(dbhelper.get_tbl_username(26)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(dbhelper.get_gambar_user(0), 0, dbhelper.get_gambar_user(0).length);
            imglogo.setImageBitmap(compressedBitmap);
            imglogo.setVisibility(View.VISIBLE);
            imglogo.setBackground(null);
            //imgphoto.setForeground(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        expandableListView = findViewById(R.id.expandableListView);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(), listGroupMenu.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getApplicationContext(), listGroupMenu.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (groupPosition == 2 && childPosition == 0) {
                    Intent intent = new Intent(MainActivity.this,
                            Activity_Download.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    onPause();
                }

                if (groupPosition == 2 && childPosition == 1) {
                    Intent intent = new Intent(MainActivity.this,
                            Activity_Upload.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    onPause();
                }

                if (groupPosition == 3 && childPosition == 0) {
                    Intent intent = new Intent(MainActivity.this,
                            AppSetting.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    onPause();
                }

//                if (groupPosition == 3 && childPosition == 1) {
//                    Intent intent = new Intent(MainActivity.this,
//                            Activity_Frm_Update_system.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    onPause();
//                }

                return false;
            }
        });

        prepareListData();
        expandableListAdapter = new com.example.digitalsawit.ExpandableListAdapter(this,
                listGroupMenu, listMenu, 1);
        expandableListView.setAdapter(expandableListAdapter);
    }

    public void openDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void prepareListData (){
        listGroupMenu = new ArrayList<String>();
        listMenu = new HashMap<String, List<String>>();
        listGroupMenu.add("MASTER DATA");
        List<String>  MASTERDATA =  Arrays.asList("MASTER DATA");
        listMenu.put(listGroupMenu.get(0), MASTERDATA);
        listGroupMenu.add("PROFIL PERUSAHAAN");
        List<String>  REPORT =  Arrays.asList("PROFIL PERUSAHAAN");
        listMenu.put(listGroupMenu.get(1), REPORT);
        listGroupMenu.add("UPLOAD & DOWNLOAD");
        List<String>  UPLOADDOWNLOAD =  Arrays.asList("DOWNLOAD DATA","UPLOAD DATA");
        listMenu.put(listGroupMenu.get(2), UPLOADDOWNLOAD);
        listGroupMenu.add("TENTANG SYSTEM");
        List<String>  SETUP =  Arrays.asList("AKUN","PEMBARUAN SYSTEM");;
        listMenu.put(listGroupMenu.get(3), SETUP);
    }

    @Override
    public void onBackPressed() {
        Activity_Login.v_dlg_title = "Apakah anda yakin akan keluar?";
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
                    Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 500);
    }
}