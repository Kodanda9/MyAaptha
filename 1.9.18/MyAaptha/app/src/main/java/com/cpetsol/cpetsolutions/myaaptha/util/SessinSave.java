package com.cpetsol.cpetsolutions.myaaptha.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kodanda.D on 9/4/2017.
 */

public class SessinSave {


    public static void clearSession(Context paramContext)
    {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("KEY", 0).edit();
        localEditor.clear();
        localEditor.commit();
    }
    public static void saveSession(String paramString1, String paramString2, Context context) {
       SharedPreferences.Editor editor= context.getSharedPreferences("KEY",0).edit();
                editor.putString(paramString1,paramString2);
                editor.commit();

    }

    public static String getsessin(String paramString, Context context) {
        return context.getSharedPreferences("KEY",0).getString(paramString,"");
    }
}
