package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DrHospitaladapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.DrHospitalModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorOverviewActivity extends AppCompatActivity {
    TextView name,qualification,description,specialization,education;
    private ListView LvHosps;
    private String drId,drspc;
    private Button feedback;
    private View localView;
    private Dialog mDialog;
    TextView drname,spec;
    private EditText clinic;
    private EditText treatment;
    private EditText aboutdoctor;
    private RadioGroup RGyesno;
    private String YesNo;
    private RadioGroup reviewRG;
    private String review;
    private CheckBox checkBox1,checkBox2,checkBox3,checkBox4,checkBox5;
    private String text,text1,text2,text3,text4,text5;
    private String drid;
    private String drn;
    private String specqua;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_overview_activity);
        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Doctor Data");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        drId = getIntent().getStringExtra("DoctorId");
        drspc=getIntent().getStringExtra("DrSpcelization");
        name=(TextView)findViewById(R.id.name);
        qualification=(TextView)findViewById(R.id.DrQualification);
        description=(TextView)findViewById(R.id.description);
        specialization=(TextView)findViewById(R.id.Specialization);
        education=(TextView)findViewById(R.id.education);
        LvHosps =(ListView)findViewById(R.id.lv_hosp);
        feedback=(Button)findViewById(R.id.Feedback);


        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute();

        MyAsyncTasks1 myAsyncTasks1 = new MyAsyncTasks1();
        myAsyncTasks1.execute();
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedbackform();
            }
        });


    }






    private void feedbackform() {
        localView = View.inflate(DoctorOverviewActivity.this, R.layout.doctor_feedback_form, null);
        localView.startAnimation(AnimationUtils.loadAnimation(DoctorOverviewActivity.this, R.anim.zoom_in_enter));
        this.mDialog = new Dialog(DoctorOverviewActivity.this, R.style.NewDialog);
        this.mDialog.setContentView(localView);
        this.mDialog.setCancelable(true);
        this.mDialog.show();

        Window window = this.mDialog.getWindow();
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
        drname=(TextView)mDialog.findViewById(R.id.drname);
        drname.setText(drn);

        spec=(TextView)mDialog.findViewById(R.id.specialization);
        spec.setText(specqua);
        clinic=(EditText)mDialog.findViewById(R.id.clinic);
        treatment=(EditText)mDialog.findViewById(R.id.problems);
        aboutdoctor=(EditText)mDialog.findViewById(R.id.abourdr);
        RGyesno = (RadioGroup) this.mDialog.findViewById(R.id.radioSNGroup);
        reviewRG = (RadioGroup) this.mDialog.findViewById(R.id.radioReviewGroup);
        reviewRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=reviewRG.indexOfChild(mDialog.findViewById(reviewRG.getCheckedRadioButtonId()));
                review=String.valueOf(pos+1);
            }
        });
        RGyesno.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGyesno.indexOfChild(mDialog.findViewById(RGyesno.getCheckedRadioButtonId()));
                YesNo=String.valueOf(pos+1);
            }
        });
        checkBox1=(CheckBox)mDialog.findViewById(R.id.checkBox1);

        if(checkBox1.isChecked()) {
           text=checkBox1.getText().toString();
        }
        checkBox2=(CheckBox)mDialog.findViewById(R.id.checkBox2);
        if(checkBox2.isChecked()) {
            text1=checkBox2.getText().toString();
        }
        checkBox3=(CheckBox)mDialog.findViewById(R.id.checkBox3);
        if(checkBox3.isChecked()) {
            text2=checkBox3.getText().toString();
        }
        checkBox4=(CheckBox)mDialog.findViewById(R.id.checkBox4);
        if(checkBox4.isChecked()) {
            text3=checkBox4.getText().toString();
        }
        checkBox5=(CheckBox)mDialog.findViewById(R.id.checkBox5);
        if(checkBox5.isChecked()) {
            text4=checkBox5.getText().toString();
        }
        submit=(Button)mDialog.findViewById(R.id.Submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedBackForm runner=new FeedBackForm(drId);
                runner.execute();
            }
        });
        ImageView v = (ImageView) this.mDialog.findViewById(R.id.closeDialog);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    private class MyAsyncTasks extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.doctorViewPage+drspc+"&doctorId="+drId,"GET",null);
         //   String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/doctorViewPage?specialization="+drspc+"&doctorId="+drId,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("array===>",s);

            try {
                JSONArray array = new JSONArray(s);
                JSONObject object=array.getJSONObject(0);
                JSONArray jsonArray=object.getJSONArray("Doctor Data");
                JSONObject jsonObject=jsonArray.getJSONObject(0);
                name.setText(jsonObject.getString("name"));
               drn=jsonObject.getString("name");
                specqua=jsonObject.getString("specilization")+","+jsonObject.getString("qulification");

                qualification.setText(jsonObject.getString("qulification")+","+jsonObject.getString("specilization")+","+jsonObject.getString("experience")+"Years Exp");
                description.setText(jsonObject.getString("description"));
                specialization.setText(jsonObject.getString("specilization"));
                education.setText(jsonObject.getString("qulification")+"("+jsonObject.getString("specilization")+")");
drid=jsonObject.getString("doctorId");

            } catch (JSONException e) {
                e.printStackTrace();
            }






            super.onPostExecute(s);

        }
    }
    private class MyAsyncTasks1 extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        private ArrayList<DrHospitalModel> rowItems;
        @Override
        protected void onPreExecute() {
//            pDialog = ProgressDialog.show(DoctorOverviewActivity.this, "Please wait", "Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.appointment+drId+"","GET",null);
       //     String content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/appointment?docId="+drId+"","GET",null);
            Log.i("onPostexc:", content);
            return content;

        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:", result);
            rowItems = new ArrayList<DrHospitalModel>();
            try {
                JSONArray array = new JSONArray(result);

                JSONArray hospArray =array.getJSONArray(0);
                JSONObject doctObj = array.getJSONObject(1);


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
                    model.setGender(obj.getString("gender"));
                    model.setSunday(obj.getString("sunday"));
                    model.setMonday(obj.getString("monday"));
                    model.setTuesday(obj.getString("tuesday"));
                    model.setWednesday(obj.getString("wednesday"));
                    model.setThursday(obj.getString("thursday"));
                    model.setFriday(obj.getString("friday"));
                    model.setSaturday(obj.getString("saturday"));
                    model.setLongitude(obj.getString("longitude"));
                    rowItems.add(model);
                }
//                } else {
//                    Log.i("","No Hospitals");
//                }
            } catch (Exception e) {
            }

            DrHospitaladapter adapter=new DrHospitaladapter(DoctorOverviewActivity.this,rowItems);
            LvHosps.setAdapter(adapter);
            adapter.notifyDataSetChanged();
//            if (pDialog.isShowing()) {    pDialog.dismiss();       }


        }//onPostExecute

    }

    private class FeedBackForm extends AsyncTask<String,String,String>{
        String cliniccenter,treatmnt,aboutdoc;
        ProgressDialog progressDialog;
String DrId;
        public FeedBackForm(String drId) {
            this.DrId=drId;
        }

        @Override
        protected void onPreExecute() {
            cliniccenter=clinic.getText().toString();
            treatmnt=treatment.getText().toString();
            aboutdoc= aboutdoctor.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj=new JSONObject();
            try {
                obj.accumulate("recommendDoctor",YesNo);
                obj.accumulate("visitedClinic",cliniccenter);
                obj.accumulate("problemOrTreatment",treatmnt);
                obj.accumulate("aboutDoctor",aboutdoc);
                obj.accumulate("happyWith",text);
                obj.accumulate("rating",review);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/saveFeedback/"+ SessinSave.getsessin("profile_id",DoctorOverviewActivity.this)+DrId,"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(DoctorOverviewActivity.this,"ThankYou for your Feedback",Toast.LENGTH_LONG).show();
            mDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}