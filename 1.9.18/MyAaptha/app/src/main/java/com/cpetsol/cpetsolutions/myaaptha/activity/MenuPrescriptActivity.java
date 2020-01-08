package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ViewPagerAdapter;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EducationDetails;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EmploymentDetails;
import com.cpetsol.cpetsolutions.myaaptha.fragment.InnerPrescriptionHistory;
import com.cpetsol.cpetsolutions.myaaptha.fragment.PrescriptionsHistory;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuPrescriptActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    private PrescriptionsHistory tab1;
    private EducationDetails tab2;
    private EmploymentDetails tab3;
    private InnerPrescriptionHistory tab4;
    private String Appid, Data;
    private TextView PatientName,AppointmentId;
    private View rootView;
    public static ImageView Norecords,GENDER;
    public static ListView Listview, EducationListview, EmployLV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_prescript_activity);

        Appid = getIntent().getStringExtra("AppointId");
        Data = getIntent().getStringExtra("data");
        Toast.makeText(this,""+Appid,Toast.LENGTH_LONG).show();
        Toast.makeText(this,""+Data,Toast.LENGTH_LONG).show();

        PatientName = (TextView) findViewById(R.id.patientname);
        AppointmentId = (TextView) findViewById(R.id.opid);
        GENDER = (ImageView) findViewById(R.id.gender);
        AppointmentId.setText("OP ID:"+Appid);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab1:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.tab2:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.tab3:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.tab4:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                    Log.d("page-->", "setChecked: False " + position);
                } else {
                    Log.d("page-->", "setChecked: True " + position);
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);


        final String appintmentDetails = getIntent().getStringExtra("AppointmentObj");
        Log.i("appintmentDetails-->", appintmentDetails);

        Button BtnSubmit = (Button) findViewById(R.id.btnInnerPresc);
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MenuPrescriptActivity.this, PrescriptionActivity.class);
                in.putExtra("AppointmentObj", appintmentDetails);
                startActivity(in);
            }
        });
        Button BtnPrescSubmit = (Button) findViewById(R.id.btnPublicPresc);
        BtnPrescSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MenuPrescriptActivity.this, PrescriptionActivity.class);
                in.putExtra("AppointmentObj", appintmentDetails);
                startActivity(in);
            }
        });

        PrescriptionHistoryRunner runner = new PrescriptionHistoryRunner();
        runner.execute();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tab1 = new PrescriptionsHistory();

        Bundle data = new Bundle();
        data.putString("APPID", Appid);
        data.putString("PFID", Data);
        tab1.setArguments(data);


        tab2 = new EducationDetails();
        data.putString("APPID", Appid);
        data.putString("PFID", Data);
        tab2.setArguments(data);

        tab3 = new EmploymentDetails();
        data.putString("APPID", Appid);
        data.putString("PFID", Data);
        tab3.setArguments(data);

        tab4 = new InnerPrescriptionHistory();
        data.putString("APPID", Appid);
        data.putString("PFID", Data);
        tab4.setArguments(data);


        adapter.addFragment(tab1);
        adapter.addFragment(tab2);
        adapter.addFragment(tab3);
        adapter.addFragment(tab4);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.down_from_top, R.anim.down_from_top);
    }


    private class PrescriptionHistoryRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
                  String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allProfileDetails+Data+"&appId="+Appid,"GET",null);
    //        String Content = AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/allProfileDetails?pid=900000030501&appId=78", "GET", null);
            Log.i("=====>", Content);


            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                PatientName.setText(object.getString("patientName"));
                JSONObject object1=object.getJSONObject("profile");
                if(object1.getString("gender").equalsIgnoreCase("Female")){
                    GENDER.setImageResource(R.drawable.females);
                }else{
                    GENDER.setImageResource(R.drawable.imagesmale);

                }


                /*JSONArray array = object.getJSONArray("datesList");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.getJSONObject(i);
                        model.setId(object1.getString("id"));
                        model.setFullName(object1.getString("fullName"));
                        model.setStatus(object1.getString("status"));
                        model.setRelation(object1.getString("relation"));
                        model.setMessage(object1.getString("message"));
                        model.setSpecialization(object1.getString("specialization"));
                        model.setTimeSlot(object1.getString("timeSlot"));
                        model.setBookedDate(object1.getString("bookedDate"));
                        model.setHospitalName(object1.getString("hospitalName"));
                        model.setAppId(object1.getString("appId"));
                        model.setInTime(object1.getString("inTime"));
                        model.setEndTime(object1.getString("endTime"));
                        model.setDoctorId(object1.getString("doctorId"));
                        model.setPoints(object1.getString("points"));
                        model.setPrice(object1.getString("price"));
                        model.setFamilyNames(object1.getString("familyNames"));
                        model.setLocalities(object1.getString("localities"));
                        model.setQualification(object1.getString("qualification"));
                        model.setCompany(object1.getString("company"));
                        model.setFileName(object1.getString("fileName"));
                        model.setPreId(object1.getString("preId"));
                        model.setReportId(object1.getString("reportId"));
                        model.setDosage(object1.getString("dosage"));
                        model.setHospitalId(object1.getString("hospitalId"));
                        model.setDetails(object1.getString("details"));
                        model.setStartTime(object1.getString("startTime"));
                        model.setOutTime(object1.getString("outTime"));
                        model.setDoctorName(object1.getString("doctorName"));
                        model.setMobileNo(object1.getString("mobileNo"));
                        arraylist1.add(model);
                    }

                    MenuPrecriptionAdapter adapter = new MenuPrecriptionAdapter(MenuPrescriptActivity.this, arraylist1);
                    Listview.setAdapter(adapter);
                } else {
                    Norecords.setVisibility(View.VISIBLE);
                }


                JSONArray array1 = object.getJSONArray("education");
                if (array1.length() > 0) {
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject object2 = array1.getJSONObject(i);
                        model.setId(object2.getString("id"));
                    model.setFullName(object2.getString("fullName"));
                    model.setStatus(object2.getString("status"));
                    model.setRelation(object2.getString("relation"));
                    model.setMessage(object2.getString("message"));
                    model.setSpecialization(object2.getString("specialization"));
                    model.setTimeSlot(object2.getString("timeSlot"));
                    model.setBookedDate(object2.getString("bookedDate"));
                    model.setHospitalName(object2.getString("hospitalName"));
                    model.setAppId(object2.getString("appId"));
                        model.setInTime(object2.getString("inTime"));
                    model.setEndTime(object2.getString("endTime"));
                    model.setDoctorId(object2.getString("doctorId"));
                    model.setPoints(object2.getString("points"));
                    model.setPrice(object2.getString("price"));
                    model.setFamilyNames(object2.getString("familyNames"));
                    model.setLocalities(object2.getString("localities"));
                        model.setQualification(object2.getString("qualification"));
                    model.setCompany(object2.getString("company"));
                    model.setFileName(object2.getString("fileName"));
                    model.setPreId(object2.getString("preId"));
                    model.setReportId(object2.getString("reportId"));
                    model.setDosage(object2.getString("dosage"));
                    model.setHospitalId(object2.getString("hospitalId"));
                        model.setDetails(object2.getString("details"));
                    model.setStartTime(object2.getString("startTime"));
                    model.setOutTime(object2.getString("outTime"));
                    model.setDoctorName(object2.getString("doctorName"));
                    model.setMobileNo(object2.getString("mobileNo"));
                        arraylist2.add(model);
                    }
                    EducationAdapter adapter1 = new EducationAdapter(MenuPrescriptActivity.this, arraylist2);
                    EducationListview.setAdapter(adapter1);
                } else {
                    Norecords.setVisibility(View.VISIBLE);
                }



            JSONArray array2=object.getJSONArray("employment");
            if(array2.length()>0){
                for(int i=0;i<array2.length();i++){
                    JSONObject object3=array1.getJSONObject(i);
                    model.setId(object3.getString("id"));
                    model.setFullName(object3.getString("fullName"));
                    model.setStatus(object3.getString("status"));
                    model.setRelation(object3.getString("relation"));
                    model.setMessage(object3.getString("message"));
                    model.setSpecialization(object3.getString("specialization"));
                    model.setTimeSlot(object3.getString("timeSlot"));
                    model.setBookedDate(object3.getString("bookedDate"));
                    model.setHospitalName(object3.getString("hospitalName"));
                    model.setAppId(object3.getString("appId"));
                    model.setInTime(object3.getString("inTime"));
                    model.setEndTime(object3.getString("endTime"));
                    model.setDoctorId(object3.getString("doctorId"));
                    model.setPoints(object3.getString("points"));
                    model.setPrice(object3.getString("price"));
                    model.setFamilyNames(object3.getString("familyNames"));
                    model.setLocalities(object3.getString("localities"));
                    model.setQualification(object3.getString("qualification"));
                    model.setCompany(object3.getString("company"));
                    model.setFileName(object3.getString("fileName"));
                    model.setPreId(object3.getString("preId"));
                    model.setReportId(object3.getString("reportId"));
                    model.setDosage(object3.getString("dosage"));
                    model.setHospitalId(object3.getString("hospitalId"));
                    model.setDetails(object3.getString("details"));
                    model.setStartTime(object3.getString("startTime"));
                    model.setOutTime(object3.getString("outTime"));
                    model.setDoctorName(object3.getString("doctorName"));
                    model.setMobileNo(object3.getString("mobileNo"));
                    arraylist3.add(model);
                }
                EmploymentAdapter adapter2=new EmploymentAdapter(MenuPrescriptActivity.this,arraylist3);
        //        EmployLV.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }

*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


