package com.example.digitalsawit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.EditText;

public class AlertDialogManager {
    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if (status != null)
            // Setting alert dialog icon

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

        // Showing Alert Message
        alertDialog.show();
    }

    public static void dialogRegistrasi (Context context) {
        final Dialog dialog                 = new Dialog(context);
        dialog(R.layout.dialog_registrasi);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final Button registrasi_baru         = dialog.findViewById(R.id.btn_dlg1_ok);
        final Button registtrasi_ulang         = dialog.findViewById(R.id.btn_dlg1_no);
        final EditText etniktelpregistrasi             = dialog.findViewById(R.id.et_dlg1_niktelp);
        final EditText etpasswordregistrasi             = dialog.findViewById(R.id.et_dlg1_password);
        dialog.show();
    }

    private static void dialog(int dialog_registrasi) {
    }


}