package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DrHospitaladapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.DrHospitalModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorHospitalsActivity extends AppCompatActivity {
    private TextView TvDoctrName,TvExp,TvQual;
    private ListView LvHosps;
    private String drId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_hospitals_activity);
        getSupportActionBar().hide();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Book Appointment ");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         drId = getIntent().getStringExtra("DoctorId");
        TvDoctrName =(TextView)findViewById(R.id.Tv_doctName);
        TvQual =(TextView)findViewById(R.id.Tv_qual);
        TvExp =(TextView)findViewById(R.id.Tv_exp);
        LvHosps =(ListView)findViewById(R.id.lv_hosp);


        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();

    }


    private class MyAsyncTasks extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        private ArrayList<DrHospitalModel> rowItems;
        @Override
        protected void onPreExecute() {
            pDialog = ProgressDialog.show(DoctorHospitalsActivity.this, "Please wait", "Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.appointment+drId+"", "GET", null);
         //   String content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/appointment?docId="+drId+"", "GET", null);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:", result);
            rowItems = new ArrayList<DrHospitalModel>();
            try {
                JSONArray array = new JSONArray(result);
                // JSONArray array = new JSONArray(array1);
                JSONArray hospArray =array.getJSONArray(0);
                JSONObject doctObj = array.getJSONObject(1);

                TvDoctrName.setText("Dr."+doctObj.getString("name"));
                TvExp.setText(doctObj.getString("experience")+"years experience");
                TvQual.setText(doctObj.getString("specilization")+","+doctObj.getString("qulification"));
//                doctObj.getString("specilization");
//                doctObj.getString("hospitalName");
                Log.i("-->",doctObj.getString("name"));


//                if (hospArray.length() > 0) {
                for (int i = 0; i < hospArray.length(); i++) {


                    JSONObject obj = hospArray.getJSONObject(i);

                    Log.i(""+i+"-->",obj.getString("hospitalName"));

                    DrHospitalModel model = new DrHospitalModel();
                    model.setHospitalName(obj.getString("hospitalName"));
                    model.setHospitalId(obj.getString("hospitalId"));
                    model.setDoctorId(obj.getString("doctorId"));
                    model.setName(obj.getString("name"));
                    model.setLocation(obj.getString("location"));
                    model.setLocalities(obj.getString("localities"));
                    model.setAddress(obj.getString("address"));
                    model.setQualification(obj.getString("qualification"));
                    model.setSpecialization(obj.getString("specialization"));
                    model.setPrice(obj.getString("price"));
                    model.setExperience(obj.getString("experience"));
                    model.setSunday(obj.getString("sunday"));
                    model.setMonday(obj.getString("monday"));
                    model.setTuesday(obj.getString("tuesday"));
                    model.setWednesday(obj.getString("wednesday"));
                    model.setThursday(obj.getString("thursday"));
                    model.setFriday(obj.getString("friday"));
                    model.setSaturday(obj.getString("saturday"));
                    model.setLongitude(obj.getString("longitude"));

                    model.setGender(obj.getString("gender"));
                  model.setQulification(doctObj.getString("qulification"));
                    rowItems.add(model);
                }
//                } else {
//                    Log.i("","No Hospitals");
//                }
            } catch (Exception e) {
            }

            DrHospitaladapter adapter=new DrHospitaladapter(DoctorHospitalsActivity.this,rowItems);
            LvHosps.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (pDialog.isShowing()) {    pDialog.dismiss();       }


        }//onPostExecute

    }



}
