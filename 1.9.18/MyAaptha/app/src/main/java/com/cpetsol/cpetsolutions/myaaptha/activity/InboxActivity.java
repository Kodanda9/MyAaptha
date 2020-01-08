package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ViewPagerAdapter;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorReports;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorReviewInf;
import com.cpetsol.cpetsolutions.myaaptha.fragment.RequestData;
import com.cpetsol.cpetsolutions.myaaptha.fragment.UserReports;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.InboxModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    private RequestData tab1;
    private DoctorReviewInf tab2;
    private UserReports tab3;
    private DoctorReports tab4;
    private ViewPagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

       // getSupportActionBar().hide();

        overridePendingTransition(R.anim.up_from_bottom, R.anim.up_from_bottom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Inbox");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.insta_blue_bg)));
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

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
                    Log.d("page-->", "setChecked: False "+position);
                }
                else
                {
                    Log.d("page-->", "setChecked: True "+position);
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        InboxData runner=new InboxData();
        runner.execute();
        setupViewPager(viewPager);



    }//onCreate


    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tab1=new RequestData();
        tab2=new DoctorReviewInf();
        tab3=new UserReports();
        tab4=new DoctorReports();

        String profession = SessinSave.getsessin("profession",InboxActivity.this);
        if(profession.equalsIgnoreCase("Doctor")){
            adapter.addFragment(tab1);
            adapter.addFragment(tab2);
            adapter.addFragment(tab3);
            adapter.addFragment(tab4);
        }else{
            bottomNavigationView.getMenu().removeItem(R.id.tab4);
            adapter.addFragment(tab1);
            adapter.addFragment(tab2);
            adapter.addFragment(tab3);
        }






        if("Do".equalsIgnoreCase("Docdassf")){

        }


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


    private class InboxData extends AsyncTask<String,String,String> {
        private ArrayList<InboxModel> inbox;

        @Override
        protected String doInBackground(String... strings) {
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.inBox + SessinSave.getsessin("profile_id", InboxActivity.this), "GET", null);
            //    String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/inBox/900000010501","GET",null);
            Log.i("Content", Content);
            return Content;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            inbox = new ArrayList<InboxModel>();

            InboxModel model = new InboxModel();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

           if(jsonObject.getString("Is this Doctor ?").equalsIgnoreCase("Doctor")){


           }else{

           }




                /*JSONArray array1=jsonObject.getJSONArray("Doctor Reports");
                InboxModel model1=new InboxModel();
                if(array1.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array1.getJSONObject(i);
                        model1.setId(object.getString("id"));
                        model1.setFullName(object.getString("fullName"));
                        model1.setRelation(object.getString("relation"));
                        model1.setStatus(object.getString("status"));
                        model1.setMessage(object.getString("message"));
                        model1.setSpecialization(object.getString("specialization"));
                        model1.setTimeSlot(object.getString("timeSlot"));
                        model1.setBookedDate(object.getString("bookedDate"));
                        model1.setHospitalName(object.getString("hospitalName"));
                        model1.setAppId(object.getString("appId"));
                        model1.setInTime(object.getString("inTime"));
                        model1.setDoctorId(object.getString("doctorId"));
                        model1.setPoints(object.getString("points"));
                        model1.setPrice(object.getString("price"));
                        model1.setFamilyNames(object.getString("familyNames"));
                        model1.setLocalities(object.getString("localities"));
                        model1.setQualification(object.getString("qualification"));
                        model1.setCompany(object.getString("company"));
                        model1.setFileName(object.getString("fileName"));
                        model1.setReportId(object.getString("reportId"));
                        model1.setDosage(object.getString("dosage"));
                        model1.setHospitalId(object.getString("hospitalId"));
                        model1.setDetails(object.getString("details"));

                        doctorreports.add(model1);
                        Log.i("vani===",doctorreports.toString());
                        InboxDoctorViewAdapter view=new InboxDoctorViewAdapter(getActivity(),doctorreports);
                        doctorData.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }
                JSONArray array2=jsonObject.getJSONArray("user Reports");
                InboxModel model2=new InboxModel();
                if(array2.length()>0){
                    for(int i=0;i<array2.length();i++){
                        JSONObject object=array2.getJSONObject(i);
                        model2.setId(object.getString("id"));
                        model2.setFullName(object.getString("fullName"));
                        model2.setRelation(object.getString("relation"));
                        model2.setStatus(object.getString("status"));
                        model2.setMessage(object.getString("message"));
                        model2.setSpecialization(object.getString("specialization"));
                        model2.setTimeSlot(object.getString("timeSlot"));
                        model2.setBookedDate(object.getString("bookedDate"));
                        model2.setHospitalName(object.getString("hospitalName"));
                        model2.setAppId(object.getString("appId"));
                        model2.setInTime(object.getString("inTime"));
                        model2.setDoctorId(object.getString("doctorId"));
                        model2.setPoints(object.getString("points"));
                        model2.setPrice(object.getString("price"));
                        model2.setFamilyNames(object.getString("familyNames"));
                        model2.setLocalities(object.getString("localities"));
                        model2.setQualification(object.getString("qualification"));
                        model2.setCompany(object.getString("company"));
                        model2.setFileName(object.getString("fileName"));
                        model2.setReportId(object.getString("reportId"));
                        model2.setDosage(object.getString("dosage"));
                        model2.setHospitalId(object.getString("hospitalId"));
                        model2.setDetails(object.getString("details"));
                        userreports.add(model2);

                        InboxUserViewAdapter view=new InboxUserViewAdapter(getActivity(),userreports);
                        userData.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }
                JSONArray array3=jsonObject.getJSONArray("Doctor Reviewed Info");
                InboxModel model3=new InboxModel();
                if(array3.length()>0){
                    for(int i=0;i<array3.length();i++){
                        JSONObject object=array3.getJSONObject(i);
                        Log.i("===>",object.toString());
                        model3.setId(object.getString("id"));
                        model3.setFullName(object.getString("fullName"));
                        model3.setRelation(object.getString("relation"));
                        model3.setStatus(object.getString("status"));
                        model3.setMessage(object.getString("message"));
                        model3.setSpecialization(object.getString("specialization"));
                        model3.setTimeSlot(object.getString("timeSlot"));
                        model3.setBookedDate(object.getString("bookedDate"));
                        model3.setHospitalName(object.getString("hospitalName"));
                        model3.setAppId(object.getString("appId"));
                        model3.setInTime(object.getString("inTime"));
                        model3.setDoctorId(object.getString("doctorId"));
                        model3.setPoints(object.getString("points"));
                        model3.setPrice(object.getString("price"));
                        model3.setFamilyNames(object.getString("familyNames"));
                        model3.setLocalities(object.getString("localities"));
                        model3.setQualification(object.getString("qualification"));
                        model3.setCompany(object.getString("company"));
                        model3.setFileName(object.getString("fileName"));
                        model3.setReportId(object.getString("reportId"));
                        model3.setDosage(object.getString("dosage"));
                        model3.setHospitalId(object.getString("hospitalId"));
                        model3.setDetails(object.getString("details"));
                        Reports.add(model3);
                        Log.i("Bhav",model3.toString());
                        InboxReportsAdapter view=new InboxReportsAdapter(getActivity(),Reports);
                        reportss.setAdapter(view);
                        view.notifyDataSetChanged();
                    }
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}

