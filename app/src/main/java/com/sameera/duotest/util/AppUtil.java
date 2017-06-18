package com.sameera.duotest.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.sameera.duotest.R;

/**
 * Created by Sameera on 6/18/17.
 */

public class AppUtil {

    /**
     * Check the network connection of the device.
     *
     * @param context current context value
     * @return true if network connection available false otherwise
     */
    public static boolean checkNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetworks = connectivityManager.getActiveNetworkInfo();
        if (activeNetworks != null && activeNetworks.isConnected()) {
            return activeNetworks.isConnectedOrConnecting();
        }
        return false;
    }

    public static void showSystemSettingsDialog(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

        } else {
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    public static void checkInternetAlert(Context context, DialogInterface.OnClickListener positiveButton) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(context.getResources().getString(R.string.msg_title_no_network))
                .setMessage(context.getResources().getString(R.string.msg_turn_on_network))
                .setPositiveButton(context.getResources().getString(R.string.text_settings), positiveButton)
                .setNegativeButton(context.getResources().getString(R.string.text_cancel), (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
