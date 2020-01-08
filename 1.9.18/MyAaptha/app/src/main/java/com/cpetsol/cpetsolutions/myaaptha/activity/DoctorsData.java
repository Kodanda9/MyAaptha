package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ViewHospitalsDataaFrag;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorsData extends AppCompatActivity {
    private Spinner monspinner, monendspinner, tuespinner, tueendspinner, wedspinner, wedendspinner, thuspinner, thuendspinner, frispinner, friendspinner,
            satspinner, satendspinner, sunspinner, sunendspinner, durat,doctor,hospital,op,ip,lab;
    private Button submit;
    EditText price,OP,IP,Lab;
    private String ownerId;
    private String qualificationId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_data);
        ownerId= getIntent().getStringExtra("Owner");
        getSupportActionBar().hide();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Time slots");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Toast.makeText(DoctorsData.this,"ss"+ownerId,Toast.LENGTH_LONG).show();
        monspinner = (Spinner) findViewById(R.id.monspinner);
        monendspinner = (Spinner) findViewById(R.id.monendspinner);
        tuespinner = (Spinner) findViewById(R.id.tuespinner);
        tueendspinner = (Spinner) findViewById(R.id.tueendspinner);
        wedspinner = (Spinner) findViewById(R.id.wedspinner);
        wedendspinner = (Spinner) findViewById(R.id.wedendspinner);
        thuspinner = (Spinner) findViewById(R.id.thuspinner);
        thuendspinner = (Spinner) findViewById(R.id.thuendspinner);
        frispinner = (Spinner) findViewById(R.id.frispinner);
        friendspinner = (Spinner) findViewById(R.id.friendspinner);
        satspinner = (Spinner) findViewById(R.id.satspinner);
        satendspinner = (Spinner) findViewById(R.id.satendspinner);
        sunspinner = (Spinner) findViewById(R.id.sunspinner);
        sunendspinner = (Spinner) findViewById(R.id.sunendspinner);
        durat = (Spinner) findViewById(R.id.durat);
        doctor = (Spinner) findViewById(R.id.doctorslist);
        hospital = (Spinner) findViewById(R.id.hospitalnames);
        price=(EditText)findViewById(R.id.price);
        OP=(EditText)findViewById(R.id.op);
        IP=(EditText)findViewById(R.id.ip);
        Lab=(EditText)findViewById(R.id.lab);
        submit=(Button)findViewById(R.id.submit);

      DoctorsListTask task=new DoctorsListTask();
        task.execute();

        ArrayList<String> timeslotlist = new ArrayList<>();
        timeslotlist.add(0, "Start Time");
        String f = "";
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 60; j = j + 30) {
                if (j == 0) {
                    f = i + ":" + "00";
                } else {
                    f = i + ":" + j;
                }
                timeslotlist.add(f);
                monspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                tuespinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                wedspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                thuspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                frispinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                satspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));
                sunspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, timeslotlist));

            }
        }

        ArrayList<String> endtimelist = new ArrayList<>();
        endtimelist.add(0, "End Time");
        String e = "";
        for (int k = 0; k < 22; k++) {
            for (int l = 0; l < 60; l = l + 30) {
                if (l == 0) {
                    e = k + ":" + "00";
                } else {
                    e = k + ":" + l;
                }
                endtimelist.add(e);

                monendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                tueendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                wedendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                thuendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                friendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                satendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));
                sunendspinner.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, endtimelist));

            }
        }
        ArrayList<String> duration = new ArrayList<>();
        String t = "";
        for (int m = 5; m <= 60; m++) {

            duration.add(m + "");
            durat.setAdapter(new ArrayAdapter<String>(DoctorsData.this, android.R.layout.simple_spinner_dropdown_item, duration));
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorsDataRunner runner=new DoctorsDataRunner();
                runner.execute();

            }
        });

    }//onCreatea


    private class DoctorsDataRunner extends AsyncTask<String,String,String> {
        String hosp,doc,sun,sunday,mon,monday,tue,tuesday,wed,wednesday,thu,thursday,fri,friday,sat,saturday,cost,duration,op,ip,lab;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            hosp=hospital.getSelectedItem().toString();
            doc=doctor.getSelectedItem().toString();
            sun=sunspinner.getSelectedItem().toString();
            sunday=sunendspinner.getSelectedItem().toString();
            mon=monspinner.getSelectedItem().toString();
            monday=monendspinner.getSelectedItem().toString();
            tue=tuespinner.getSelectedItem().toString();
            tuesday=tueendspinner.getSelectedItem().toString();
            wed=wedspinner.getSelectedItem().toString();
            wednesday=wedendspinner.getSelectedItem().toString();
            thu=thuspinner.getSelectedItem().toString();
            thursday=thuendspinner.getSelectedItem().toString();
            fri=frispinner.getSelectedItem().toString();
            friday=friendspinner.getSelectedItem().toString();
            sat=satspinner.getSelectedItem().toString();
            saturday=satendspinner.getSelectedItem().toString();
            cost=price.getText().toString();
            duration=durat.getSelectedItem().toString();
           op= OP.getText().toString();
            ip=IP.getText().toString();
            lab=Lab.getText().toString();


        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("doctorName",doc);
                object.accumulate("hospitalName",hosp);
                object.accumulate("sundayStartTime",sun);
                object.accumulate("sundayEndTime",sunday);
                object.accumulate("mondayStartTime",mon);
                object.accumulate("mondayEndTime",monday);
                object.accumulate("tuesdayStartTime",tue);
                object.accumulate("tuesdayEndTime",tuesday);
                object.accumulate("wednesdayStartTime",wed);
                object.accumulate("wednesdayEndTime",wednesday);
                object.accumulate("thursdayStartTime",thu);
                object.accumulate("thursdayEndTime",thursday);
                object.accumulate("fridayStartTime",fri);
                object.accumulate("fridayEndTime",friday);
                object.accumulate("saturdayStartTime",sat);
                object.accumulate("doctsaturdayEndTimeorName",saturday);
                object.accumulate("price",cost);
                object.accumulate("op",op);
                object.accumulate("ip",ip);
                object.accumulate("lab",lab);
                object.accumulate("duration",duration);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveHospitalsData,"POST",object);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_LONG).show();
            Intent i=new Intent(DoctorsData.this, ViewHospitalsDataaFrag.class);
            startActivity(i);
        }
    }

    private class DoctorsListTask extends AsyncTask<String,String,String>{
        private ArrayList<String> doctorlist,hospitallist;
        private ArrayList<String> idslist,idsList;



        @Override
        protected String doInBackground(String... strings) {
          String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.hospitalsData+SessinSave.getsessin("profile_id",DoctorsData.this)+"/"+ownerId,"GET",null);
            return Content;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Doctors Data");
                doctorlist=new ArrayList<String>();
                idslist=new ArrayList<String>();
                hospitallist=new ArrayList<String>();
                idsList=new ArrayList<String>();
                if (jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        doctorlist.add(object.getString("fullName"));
                        idslist.add(object.getString("id"));
                    }
                    doctorlist.add(0,"Select");
                    idslist.add(0,"");
                }
                JSONArray array1=jsonObject.getJSONArray("Hospitals");
                if(array1.length()>0){
                    for(int i=0;i<array1.length();i++){
                        JSONObject object1=array1.getJSONObject(i);
                        hospitallist.add(object1.getString("hospitalName"));
                        idsList.add(object1.getString("hospitalId"));
                    }
                    hospitallist.add(0,"Select");
                    idsList.add(0,"");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            doctor.setAdapter(new ArrayAdapter<String>(DoctorsData.this,android.R.layout.simple_list_item_single_choice,doctorlist));
            doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String SelfullName=adapterView.getItemAtPosition(position).toString();

                    for(int j = 0; j <doctorlist.size(); j++){
                        if(SelfullName.equalsIgnoreCase(doctorlist.get(j))){
                            qualificationId = idslist.get(j);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            hospital.setAdapter(new ArrayAdapter<String>(DoctorsData.this,android.R.layout.simple_list_item_single_choice,hospitallist));
            hospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    String SelfullName=adapterView.getItemAtPosition(position).toString();

                    for(int j = 0; j <hospitallist.size(); j++){
                        if(SelfullName.equalsIgnoreCase(hospitallist.get(j))){
                            qualificationId = idsList.get(j);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}//class