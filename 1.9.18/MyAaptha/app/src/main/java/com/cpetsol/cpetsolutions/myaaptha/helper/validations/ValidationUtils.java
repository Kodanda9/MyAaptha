package com.cpetsol.cpetsolutions.myaaptha.helper.validations;

import android.content.Context;

/**
 * Created by Kodanda.D on 9/1/2017.
 */

public class ValidationUtils {
    public static int getIdResourceByName(Context context, String strId) {
        int resId = -1;
        try {
            String packageName = context.getPackageName();
            resId = context.getResources().getIdentifier(strId, "id", packageName);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return resId;
    }
}
