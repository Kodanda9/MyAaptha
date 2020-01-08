package com.cpetsol.cpetsolutions.myaaptha.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class AppHelper {


    private static SimpleDateFormat dateFormatter;
    private static DatePickerDialog currDatePickerDialog;
    private static Toast logToast;
    private static Date d;

    public static  String ConvertJsonDateWithSec(String date)
    { 
        if(date!=null){
            String jsondate="/Date("+date+")/";
            jsondate=jsondate.replace("/Date(", "").replace(")/", "");
            long time = Long.parseLong(jsondate);
             d= new Date(time);
        }
       
//        return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d).toString();
    }//ConvertJsonDate
    public static  String ConvertJsonDateWithSecOnlyDate(String date)
    {
        String jsondate="/Date("+date+")/";
        jsondate=jsondate.replace("/Date(", "").replace(")/", "");
        long time = Long.parseLong(jsondate);
        Date d= new Date(time);
//        return new SimpleDateFormat("dd/MM/yyyy").format(d).toString();
        return new SimpleDateFormat("dd-MM-yyyy").format(d).toString();
    }//ConvertJsonDate

    public static boolean phoneNumberValidation(String str) {
        if((str.startsWith("7")||str.startsWith("8")||str.startsWith("9")) && (Integer.toString(str.length()).equalsIgnoreCase("10")  )  ){
            return true;
        } else {
            return false;
        }
    }//phoneNumberValidation

    public static int setValueToSpinner(Spinner spinner, String string) {
        int index = 0;
        for(int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(string)){
                Log.i("spinner:"+i+"",spinner.getItemAtPosition(i).toString());
                index = i;
                break;
            }
        }
        return index;
    }//setValueToSpinner

    public static void setupHideleyboard(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboardInAppHelper(activity);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupHideleyboard(innerView,activity);
            }
        }
    }//setupHideleyboard
    private static void hideSoftKeyboardInAppHelper(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }//hideSoftKeyboardInAppHelper

    public static void DatePickerDialodPopUp(final Activity context, final EditText edittext) {
        final String dateValue=edittext.getText().toString();
        Log.i("Date Str:->",dateValue);
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        currDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar  newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edittext.setText(dateFormatter.format(newDate.getTime()));
                edittext.setError(null);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        currDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        currDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        currDatePickerDialog.show();
        Button ok = currDatePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ok.setTextColor(Color.rgb(64, 131, 207));

        Button cancel = currDatePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancel.setTextColor(Color.rgb(64, 131, 207));
    }//DatePickerDialodPopUp
    public static void DatePickerDialodPopUp1(final Activity context, final EditText edittext) {
        final String dateValue=edittext.getText().toString();
        Log.i("Date Str:->",dateValue);
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        currDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar  newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edittext.setText(dateFormatter.format(newDate.getTime()));
                edittext.setError(null);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//        currDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
//        currDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        currDatePickerDialog.show();
        Button ok = currDatePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        ok.setTextColor(Color.rgb(64, 131, 207));

        Button cancel = currDatePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancel.setTextColor(Color.rgb(64, 131, 207));
    }//DatePickerDialodPopUp


//    public static void timePickerDialog(Activity activity, final EditText editText) {
//        // Get Current Time
//        final Calendar c = Calendar.getInstance();
//        int mHour = c.get(Calendar.HOUR_OF_DAY);
//        int mMinute = c.get(Calendar.MINUTE);
//
//        // Launch Time Picker Dialog
//        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
//                new TimePickerDialog.OnTimeSetListener() {
//
//                    public String time;
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.MINUTE, minute);
//                        calendar.set(Calendar.HOUR,hourOfDay);
//                        time = calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+" "
//                                + ((calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM");
//
//                        Log.e("time",time);
//                        editText.setText(time);
//                    }
//                }, mHour, mMinute, false);
//        timePickerDialog.show();
//    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }//emailValidator


    public static void logAndToast(Activity activity, String msg) {
        Log.d("Log-->"+activity+"   :", msg);
        if (logToast != null) {
            logToast.cancel();
        }
        logToast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        logToast.show();
    }


    public static JSONArray RemoveJSONArray(JSONArray array, int pos) throws JSONException {
        JSONArray dummy = new JSONArray();
        for(int i=0;i<array.length();i++){
            if(i==pos){

            }else {
                JSONObject obj=array.getJSONObject(i);
                JSONObject labobj=new JSONObject();
                labobj.accumulate("labTestName",obj.getString("labTestName"));
                dummy.put(labobj);
            }
        }
        return dummy;
    }

    public static void getPerfectDate(final EditText editText, Activity activity)  {
        try {
            final Calendar myCalendar = Calendar.getInstance();

            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
//                view.updateDate(2016, 5, 22);
                    Log.i("AAAA-->",""+year+"   "+monthOfYear+" "+dayOfMonth);
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    String myFormat = "dd-MM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editText.setText(sdf.format(myCalendar.getTime()));
                }
            };

            if(editText.getText().toString().isEmpty()){

                new DatePickerDialog(activity, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }else{
//                Date etDate = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").parse(editText.getText().toString());
                Date etDate = new SimpleDateFormat("MM-dd-yyyy").parse(editText.getText().toString());
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(etDate);
                Log.i("Getting Date:-->",""+ calendar.get(Calendar.YEAR)+"  "+calendar.get(Calendar.MONTH) +"  "+calendar.get(Calendar.DAY_OF_MONTH));

                new DatePickerDialog(activity,date, calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) ,calendar.get(Calendar.DAY_OF_MONTH)).show();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String ConvertDateFormatYYYYMMDD2DDMMYYYY(String string) {
        Calendar mydate = new GregorianCalendar();
        try {

            String[] split = string.split("-");
            String yyyy=split[0];
            String mm=split[1];
            String dd=split[2];
            String date = dd+"-"+mm+"-"+yyyy;

            Date thedate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(date);

            mydate.setTime(thedate);
          mydate.add(Calendar.MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
     //   return mydate.get(Calendar.MONTH)+"/"+mydate.get(Calendar.DAY_OF_MONTH)+"/"+mydate.get(Calendar.YEAR);
        return mydate.get(Calendar.DAY_OF_MONTH)+"-"+mydate.get(Calendar.MONTH)+"-"+mydate.get(Calendar.YEAR);
    }
    public static String ConvertDateFormatDDMMYYYY2YYYYMMDD(String string) {
        Calendar mydate = new GregorianCalendar();
        try {

            String[] split = string.split("-");
            String dd=split[0];
            String mm=split[1];
            String yyyy=split[2];
            String date = yyyy+"-"+mm+"-"+dd;

            Date thedate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(date);

            mydate.setTime(thedate);
            mydate.add(Calendar.MONTH, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    //    return mydate.get(Calendar.MONTH)+"/"+mydate.get(Calendar.DAY_OF_MONTH)+"/"+mydate.get(Calendar.YEAR);
        return mydate.get(Calendar.YEAR)+"-"+mydate.get(Calendar.MONTH)+"-"+mydate.get(Calendar.DAY_OF_MONTH);
    }




}
