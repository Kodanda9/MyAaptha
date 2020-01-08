package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Admin on 7/7/2018.
 */

public class HospitalsAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private final ArrayList<HospitalsModel> model;
    private String direction;
    private Typeface fontAwesomeFont;
    private View HospitalView;
    private Dialog HospitalDialog;
    private EditText HospitalName,Roc,RegNumber,RegYear,Mobile,Telephone,Locality,Address,Longitude,Abouthsp,Url;
    private Button Submit,Update;
    private Spinner city;
    private String hospid;



    public HospitalsAdapter(Activity activity, ArrayList<HospitalsModel> arraylist) {
        this.activity=activity;
        this.model=arraylist;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.hospital_lv_row, null);
 TextView hospital=(TextView)view.findViewById(R.id.hospname);
 TextView city=(TextView)view.findViewById(R.id.city);
 TextView locality=(TextView)view.findViewById(R.id.locality);
 TextView address=(TextView)view.findViewById(R.id.address);
 TextView map=(TextView)view.findViewById(R.id.map);
 TextView status=(TextView)view.findViewById(R.id.status);

        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");

        //     TextView  faiSymb = (TextView) view.findViewById(R.id.tvfai_symb);                   faiSymb.setTypeface(fontAwesomeFont);
        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);   TVDeleteIcon.setTypeface(fontAwesomeFont);

        final HospitalsModel data=model.get(i);


        hospital.setText(data.getHospitalName());
        if(data.getLocation().equalsIgnoreCase("1")){
            city.setText("Hyderabad");
        }
        direction=data.getLongitude();

        locality.setText(data.getLocalities());
        address.setText(data.getAddress());
      //  map.setText(data.getLongitude());
        status.setText(data.getStatus());
map.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/"+direction)));
    }
});
        TVEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditHospitalDetails(data);
            }
        });
        return view;
    }

    private void EditHospitalDetails(final HospitalsModel data) {

        HospitalView=View.inflate(activity,R.layout.hospital_add,null);
        HospitalView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.HospitalDialog=new Dialog(activity,R.style.NewDialog);
        this.HospitalDialog.setContentView(HospitalView);
        this.HospitalDialog.setCancelable(true);
        this.HospitalDialog.show();

        Window window = this.HospitalDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;
        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        city=(Spinner)HospitalDialog.findViewById(R.id.city);                       city.setSelection(AppHelper.setValueToSpinner(city,data.getLocation()));
        HospitalName=(EditText)HospitalDialog.findViewById(R.id.hospitalname);       HospitalName.setText(data.getHospitalName());
        RegNumber=(EditText)HospitalDialog.findViewById(R.id.regnumber);             RegNumber.setText(data.getRegistrationNo());
        Roc=(EditText)HospitalDialog.findViewById(R.id.roc);                          Roc.setText(data.getRoc());
        RegYear=(EditText)HospitalDialog.findViewById(R.id.et_Date);                     RegYear.setText(data.getRegistrationYear());
        Mobile=(EditText)HospitalDialog.findViewById(R.id.mobile);                         Mobile.setText(data.getMobileNo());
        Telephone=(EditText)HospitalDialog.findViewById(R.id.telephone);                   Telephone.setText(data.getTelephoneNo());
        Locality=(EditText)HospitalDialog.findViewById(R.id.locality);                     Locality.setText(data.getLocalities());
        Address=(EditText)HospitalDialog.findViewById(R.id.address);                       Address.setText(data.getAddress());
        Longitude=(EditText)HospitalDialog.findViewById(R.id.longitude);                   Longitude.setText(data.getLongitude());
        Abouthsp=(EditText)HospitalDialog.findViewById(R.id.abouthsp);                     Abouthsp.setText(data.getDetails());
        Url=(EditText)HospitalDialog.findViewById(R.id.url);                                Url.setText(data.getWebsiteUrl());
          hospid=data.getHospitalId();

        RegYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(RegYear,activity);
            }
        });


       CityRunner runner=new CityRunner();
        runner.execute();
        Submit=(Button)HospitalDialog.findViewById(R.id.submit);
        Submit.setVisibility(View.GONE);
        Update=(Button)HospitalDialog.findViewById(R.id.update);
        Update.setVisibility(View.VISIBLE);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
HospitalRunner runner=new HospitalRunner();
                runner.execute();
            }
        });


    }

    private class CityRunner extends AsyncTask<String,String,String> {
        private String[] arr_city;
        private String[] arr_city_codes;
        private ArrayList<String> citylist;


        @Override
        protected String doInBackground(String... strings) {
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.locations, "GET", null);
    //        String Content = AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest /locations", "GET", null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array = new JSONArray(s);
                arr_city = new String[array.length()];
                arr_city_codes = new String[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    arr_city[i] = object.getString("location");
                    arr_city_codes[i] = object.getString("id");

                }
                citylist = new ArrayList<String>(Arrays.asList(arr_city));
                city.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, citylist));
//

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class HospitalRunner extends AsyncTask<String,String,String>{
        String hspnme,regnumb,regyr,roc,mob,tel,cty,lclty,adrs,lngtd,abouthsp,websiteurl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hspnme=HospitalName.getText().toString();
            roc=Roc.getText().toString();
            regnumb=RegNumber.getText().toString();
            regyr=RegYear.getText().toString();
            mob=Mobile.getText().toString();
            cty=city.getSelectedItem().toString();
            tel=Telephone.getText().toString();
            lclty=Locality.getText().toString();
            adrs=Address.getText().toString();
            lngtd=Longitude.getText().toString();
            abouthsp=Abouthsp.getText().toString();
            websiteurl=Url.getText().toString();

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("id", hospid);
                object.accumulate("hospitalName", hspnme);
                object.accumulate ("location", 1);
                object.accumulate("localities", lclty);
                object.accumulate("address", adrs);
                object.accumulate("status", "Approved");
                object.accumulate("longitude", lngtd);
                object.accumulate("registrationNo", regnumb);
                object.accumulate("roc", roc);
                object.accumulate ("registrationYear", regyr);
                object.accumulate("telephoneNo", tel);
                object.accumulate("mobileNo", mob);
                object.accumulate("details", abouthsp);
                object.accumulate("websiteUrl", websiteurl);
                Log.i("bbbbbbbbb",object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.updateHospitals,"POST",object);
         //   String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/updateHospitals","POST",object);
            return Content;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(activity, "SuccesfullyUpdated", Toast.LENGTH_SHORT).show();
            Log.i("======>",s);
            HospitalDialog.dismiss();
           HospitalRunner runner=new HospitalRunner();
            runner.execute();
            super.onPostExecute(s);

        }
    }

}
