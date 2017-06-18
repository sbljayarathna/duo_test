package com.sameera.duotest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by Sameera on 6/17/17.
 */

public class SharedPref {
    private static final String FILE_KEY = "com.sameera.duotest.file.key";
    private static final String TOKEN_KEY = FILE_KEY + "token";

    public static SharedPreferences getPref(Context ctx) {
        return ctx.getSharedPreferences(SharedPref.FILE_KEY, Context.MODE_PRIVATE);
    }

    public static void setAuthToken(@NonNull Context context, String token) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public static String getAuthToken(@NonNull Context context) {
        return getPref(context).getString(TOKEN_KEY, null);
    }


}
