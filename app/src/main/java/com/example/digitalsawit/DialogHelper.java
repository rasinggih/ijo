package com.example.digitalsawit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogHelper extends Dialog {

    public DialogHelper(Context context) {
        super(context);
    }
    //Define Object
    final Dialog dialog = new Dialog(getContext());
    EditText et_text1, et_text2;
    Button btn_ok,btn_ok2, btn_no;
    TextView tvtitle;

    public void showDialogRegistrasi() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_registrasi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg1_ok);
        btn_no = dialog.findViewById(R.id.btn_dlg1_no);
        et_text1 = dialog.findViewById(R.id.et_dlg1_niktelp);
        et_text2 = dialog.findViewById(R.id.et_dlg1_password);
        tvtitle = dialog.findViewById(R.id.tv_dlg1_title);

        //Inisialisasi Variable Return
        Activity_Login.v_rtn_dlg_string = "";
        Activity_Login.v_rtn_dlg_string2 = "";
        Activity_Login.v_rtn_dlg_string3 = "";

        //Inisialisasi Object Title/Text
        tvtitle.setText(Activity_Login.v_dlg_title);
        btn_ok.setText(Activity_Login.v_dlg_btn1);
        btn_no.setText(Activity_Login.v_dlg_btn2);
        et_text1.setText("");
        et_text2.setText("");

        et_text1.requestFocus();
        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "NEW";
                dialog.dismiss();
            }
        });

        //Event Klik Tombol NO
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "ULANG";
                if (TextUtils.isEmpty(et_text1.getText())) {
                    et_text1.setError("Tidak Boleh Kosong, Silahkan Ulangi");
                }
                else if (TextUtils.isEmpty(et_text2.getText())){
                    et_text2.setError("Tidak Boleh Kosong, Silahkan Ulangi");
                }
                else {
                    Activity_Login.pDialog.setMessage(
                            "Silahkan Menunggu, Sedang Proses PDKT dengan Sever...");
                    Activity_Login.pDialog.setCancelable(false);
                    Activity_Login.pDialog.show();
                    Activity_Login.v_rtn_dlg_string = "ULANG";
                    Activity_Login.v_rtn_dlg_string2 = et_text1.getText().toString();
                    Activity_Login.v_rtn_dlg_string3 = et_text2.getText().toString();

                    dialog.dismiss();
                }
            }
        });

        //Event Cancel Dialog
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity_Login.v_rtn_dlg_string = "CANCEL";
            }
        });

    }

    public void showDialogYesNo() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_yesno);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg2_ok);
        btn_no = dialog.findViewById(R.id.btn_dlg2_no);
        tvtitle = dialog.findViewById(R.id.tv_dlg2_title);

        //Inisialisasi Variable Return
        Activity_Login.v_rtn_dlg_string = "";

        //Inisialisasi Object Title/Text
        tvtitle.setText(Activity_Login.v_dlg_title);
        btn_ok.setText(Activity_Login.v_dlg_btn1);
        btn_no.setText(Activity_Login.v_dlg_btn2);

        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "OK";
                dialog.dismiss();
            }
        });

        //Event Klik Tombol NO
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "NO";
                dialog.dismiss();
            }
        });

        //Event Cancel Dialog
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity_Login.v_rtn_dlg_string = "CANCEL";
            }
        });

    }

    public void showDialogInfo() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_info);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg4_ok);
        tvtitle = dialog.findViewById(R.id.tv_dlg4_title);

        //Inisialisasi Object Title/Text
        tvtitle.setText(Activity_Login.v_dlg_title);
        btn_ok.setText(Activity_Login.v_dlg_btn1);

        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "OK";
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity_Login.v_rtn_dlg_string = "CANCEL";
            }
        });
    }

    public void showDialogYesNoYes() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_yesnoyes);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg5_ok1);
        btn_ok2 = dialog.findViewById(R.id.btn_dlg5_ok2);
        btn_no = dialog.findViewById(R.id.btn_dlg5_no);
        tvtitle = dialog.findViewById(R.id.tv_dlg5_title);

        //Inisialisasi Variable Return
        Activity_Login.v_rtn_dlg_string = "";

        //Inisialisasi Object Title/Text
        tvtitle.setText(Activity_Login.v_dlg_title);
        btn_ok.setText(Activity_Login.v_dlg_btn1);
        btn_no.setText(Activity_Login.v_dlg_btn2);
        btn_ok2.setText(Activity_Login.v_dlg_btn3);

        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "OK";
                dialog.dismiss();
            }
        });

        //Event Klik Tombol NO
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "NO";
                dialog.dismiss();
            }
        });

        //Event Klik Tombol OK2
        btn_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "OK2";
                dialog.dismiss();
            }
        });


        //Event Cancel Dialog
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity_Login.v_rtn_dlg_string = "CANCEL";
            }
        });

    }

    public void showDialogPassword() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_password);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg3_ok);
        btn_no = dialog.findViewById(R.id.btn_dlg3_no);
        tvtitle = dialog.findViewById(R.id.tv_dlg3_title);
        et_text2 = dialog.findViewById(R.id.et_dlg3_password);

        //Inisialisasi Variable Return
        Activity_Login.v_rtn_dlg_string = "";
        Activity_Login.v_rtn_dlg_string2 = "";

        //Inisialisasi Object Title/Text
        tvtitle.setText(Activity_Login.v_dlg_title);
        btn_ok.setText(Activity_Login.v_dlg_btn1);
        btn_no.setText(Activity_Login.v_dlg_btn2);

        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_text2.getText())){
                    et_text2.setError("Tidak Boleh Kosong, Silahkan Ulangi");
                }
                else {
                    Activity_Login.v_rtn_dlg_string = "OK";
                    Activity_Login.v_rtn_dlg_string2 = et_text2.getText().toString();
                    dialog.dismiss();
                }
            }
        });

        //Event Klik Tombol NO
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.v_rtn_dlg_string = "NO";
                dialog.dismiss();
            }
        });

        //Event Cancel Dialog
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Activity_Login.v_rtn_dlg_string = "CANCEL";
            }
        });

    }

    public void showDialogYesNoMenuutama() {
        //Inisialisasi Object
        dialog.setContentView(R.layout.dialog_yesno);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        btn_ok = dialog.findViewById(R.id.btn_dlg2_ok);
        btn_no = dialog.findViewById(R.id.btn_dlg2_no);
        tvtitle = dialog.findViewById(R.id.tv_dlg2_title);

        //Inisialisasi Variable Return
        MainActivity.v_rtn_dlg_string = "";

        //Inisialisasi Object Title/Text
        tvtitle.setText(MainActivity.v_dlg_title);
        btn_ok.setText(MainActivity.v_dlg_btn1);
        btn_no.setText(MainActivity.v_dlg_btn2);

        dialog.show();

        //Event Klik Tombol OK
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.v_rtn_dlg_string = "OK";
                dialog.dismiss();
            }
        });

        //Event Klik Tombol NO
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.v_rtn_dlg_string = "NO";
                dialog.dismiss();
            }
        });

        //Event Cancel Dialog
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MainActivity.v_rtn_dlg_string = "CANCEL";
            }
        });

    }

}