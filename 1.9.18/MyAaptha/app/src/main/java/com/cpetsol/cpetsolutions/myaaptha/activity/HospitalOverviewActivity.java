package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.HospitalDrAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalDrModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalOverviewActivity extends AppCompatActivity {
    private TextView address,hospitalname,locality,phone,map,email,abouthsp;
ListView doctorslist;
    private ArrayList<HospitalDrModel> rowItems;
    private Button adddoc;
    private String mapid;
    private String hspid;
    private String ownerId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_overview);
        getSupportActionBar().hide();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Hospital Data");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
         hspid = intent.getExtras().getString("HospitalId");

        hospitalname=(TextView)findViewById(R.id.hospitalname);
        address=(TextView)findViewById(R.id.adress);
        locality=(TextView)findViewById(R.id.locality);
        phone=(TextView)findViewById(R.id.mobileno);
        map=(TextView)findViewById(R.id.map);
        email=(TextView)findViewById(R.id.email);
        abouthsp=(TextView)findViewById(R.id.abouthsp);
        doctorslist=(ListView)findViewById(R.id.lv_hospdocs);
        adddoc=(Button)findViewById(R.id.adddoctor);

        HospitalRunner runner=new HospitalRunner();
        runner.execute();
        adddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HospitalOverviewActivity.this,DoctorsData.class);
                i.putExtra("Owner",ownerId);
                startActivity(i);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/"+mapid)));
            }
        });
    }

    private class HospitalRunner extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/hospital?hospitalId="+hspid,"GET",null);
            Log.i("Content","http://myaaptha.com/pp/rest/hospital?hospitalId="+hspid);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            rowItems = new ArrayList<HospitalDrModel>();
            try {
                JSONArray jsonarray=new JSONArray(s);
                JSONObject jsonobject=jsonarray.getJSONObject(0);
                 ownerId=jsonobject.getString("ownerId");
                if (ownerId.equalsIgnoreCase(SessinSave.getsessin("profile_id",HospitalOverviewActivity.this))){
                    adddoc.setVisibility(View.VISIBLE);
                }else {
                    adddoc.setVisibility(View.GONE);
                }
                JSONObject object=jsonobject.getJSONObject("Hospital Data");
                hospitalname.setText(object.getString("hospitalName"));
                address.setText(object.getString("address"));
                locality.setText(object.getString("localities"));
                phone.setText(object.getString("mobileNo")+","+object.getString("telephoneNo"));
                email.setText(object.getString("websiteUrl"));
                abouthsp.setText(object.getString("details"));
                JSONArray array=jsonobject.getJSONArray("Doctors");
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object1=array.getJSONObject(i);
                        HospitalDrModel model=new HospitalDrModel();
                        model.setHospitalName(object1.getString("hospitalName"));
                        model.setHospitalId(object1.getString("hospitalId"));
                        model.setDoctorId(object1.getString("doctorId"));
                        model.setName(object1.getString("name"));
                        model.setLocation(object1.getString("location"));
                        model.setLocalities(object1.getString("localities"));
                        model.setAddress(object1.getString("address"));
                        model.setQualification(object1.getString("qualification"));
                        model.setSpecialization(object1.getString("specialization"));
                        model.setPrice(object1.getString("price"));
                        model.setExperience(object1.getString("experience"));
                        model.setGender(object1.getString("gender"));
                        model.setLongitude(object1.getString("longitude"));
                        mapid=object1.getString("longitude");
                        model.setSunday(object1.getString("sunday"));
                        model.setMonday(object1.getString("monday"));
                        model.setTuesday(object1.getString("tuesday"));
                        model.setWednesday(object1.getString("wednesday"));
                        model.setThursday(object1.getString("thursday"));
                        model.setFriday(object1.getString("friday"));
                        model.setSaturday(object1.getString("saturday"));
                        model.setId(object1.getString("id"));
                        model.setEmpType(object1.getString("empType"));
                        model.setRemarks(object1.getString("remarks"));
                          rowItems.add(model);
                        HospitalDrAdapter adapter=new HospitalDrAdapter(HospitalOverviewActivity.this,rowItems);
                        doctorslist.setAdapter(adapter);
                        
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
