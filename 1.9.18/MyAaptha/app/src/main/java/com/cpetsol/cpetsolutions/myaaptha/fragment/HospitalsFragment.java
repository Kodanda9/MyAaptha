package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.HospitalsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class HospitalsFragment extends Fragment {


    private View rootView;
    private ListView listview;
    private ArrayList<HospitalsModel> arraylist;
    private Button add;
    private View HospitalView;
    private Dialog HospitalDialog;
    private Spinner city;
    private EditText HospitalName,Roc,RegNumber,RegYear,Mobile,Telephone,Locality,Address,Longitude,Abouthsp,Url;
    private Button Submit,Update;
    private ImageView cancel;


    public HospitalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_hospitals, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());
             listview=(ListView)rootView.findViewById(R.id.hosplv);
              add=(Button)rootView.findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddHospitalDetailsFragment();
                }
            });
            HospitalRunner runner=new HospitalRunner();
            runner.execute();

        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

    private void AddHospitalDetailsFragment() {
        HospitalView=View.inflate(getActivity(),R.layout.hospital_add,null);
        HospitalView.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in_enter));
        this.HospitalDialog=new Dialog(getActivity(),R.style.NewDialog);
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
        city=(Spinner)HospitalDialog.findViewById(R.id.city);
HospitalName=(EditText)HospitalDialog.findViewById(R.id.hospitalname);
RegNumber=(EditText)HospitalDialog.findViewById(R.id.regnumber);
Roc=(EditText)HospitalDialog.findViewById(R.id.roc);
RegYear=(EditText)HospitalDialog.findViewById(R.id.et_Date);
Mobile=(EditText)HospitalDialog.findViewById(R.id.mobile);
Telephone=(EditText)HospitalDialog.findViewById(R.id.telephone);
Locality=(EditText)HospitalDialog.findViewById(R.id.locality);
Address=(EditText)HospitalDialog.findViewById(R.id.address);
Longitude=(EditText)HospitalDialog.findViewById(R.id.longitude);
Abouthsp=(EditText)HospitalDialog.findViewById(R.id.abouthsp);
Url=(EditText)HospitalDialog.findViewById(R.id.url);
Submit=(Button)HospitalDialog.findViewById(R.id.submit);
Update=(Button)HospitalDialog.findViewById(R.id.update);
cancel=(ImageView)HospitalDialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalDialog.dismiss();
            }
        });
        RegYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(RegYear,getActivity());
            }
        });
        CityRunner runner=new CityRunner();
        runner.execute();
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalsAddRunner runner1=new HospitalsAddRunner();
                runner1.execute();
            }
        });

    }

    private class HospitalRunner extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.viewHospitals,"GET",null);
         //   String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/viewHospitals","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arraylist=new ArrayList<HospitalsModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for (int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        HospitalsModel model=new HospitalsModel();
                        model.setHospitalId(object.getString("hospitalId"));
                        model.setHospitalName(object.getString("hospitalName"));
                        model.setLocation(object.getString("location"));
                        model.setLocalities(object.getString("localities"));
                        model.setAddress(object.getString("address"));
                        model.setStatus(object.getString("status"));
                        model.setLongitude(object.getString("longitude"));
                        model.setRegistrationNo(object.getString("registrationNo"));
                        model.setRoc(object.getString("roc"));
                        model.setRegistrationYear(object.getString("registrationYear"));
                        model.setTelephoneNo(object.getString("telephoneNo"));
                        model.setMobileNo(object.getString("mobileNo"));
                        model.setDetails(object.getString("details"));
                        model.setWebsiteUrl(object.getString("websiteUrl"));
                        model.setDateCreated(object.getString("dateCreated"));
                        model.setViewStatus(object.getString("viewStatus"));
                        arraylist.add(model);
                        HospitalsAdapter adapter=new HospitalsAdapter(getActivity(),arraylist);
                        listview.setAdapter(adapter);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class CityRunner extends AsyncTask<String,String,String> {
        private String[] arr_city;
        private String[] arr_city_codes;
        private ArrayList<String> citylist;



        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.locations,"GET",null);
        //    String Content=AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest /locations","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array=new JSONArray(s);
                arr_city = new String[array.length()];
                arr_city_codes= new String[array.length()];
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    arr_city[i]=object.getString("location");
                    arr_city_codes[i]=object.getString("id");

                }
                citylist = new ArrayList<String>(Arrays.asList(arr_city));
                city.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,citylist));
//

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class HospitalsAddRunner extends AsyncTask<String,String,String> {
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
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.saveHospitals,"POST",object);
         //   String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/saveHospitals","POST",object);
            return Content;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), "SuccesfullyUpdated", Toast.LENGTH_SHORT).show();
            Log.i("======>",s);
          HospitalDialog.dismiss();
          HospitalRunner runner=new HospitalRunner();
            runner.execute();
            super.onPostExecute(s);

        }
    }
}
