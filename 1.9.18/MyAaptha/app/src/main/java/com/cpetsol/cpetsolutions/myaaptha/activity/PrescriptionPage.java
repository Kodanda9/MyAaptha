package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.PrescriptionAdapter;
import com.cpetsol.cpetsolutions.myaaptha.adapter.PrescriptionlabAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PrescriptionPage extends AppCompatActivity {
    TextView Drname,Specialization,Adress,Patientname,date,Complaints,Drobservation,Labtest,Back;
    ListView listviewmed,listviewlab;
    private ArrayList<PrescriptionModel> rowItems;
    private ArrayList<PrescriptionModel> rowItem;
    private String docid,spec;
    private TextView norecords,medication,labtests;
    private View localView;
    private Dialog mDialog;
    private ImageView cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_page);

        getSupportActionBar().hide();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Prescription");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent data=getIntent();
        final String data1 = data.getStringExtra("AppointmentObj");
        Log.i("=======>",data1);
        String data2 = data.getStringExtra("AppointmentObj1");
        String data3 = data.getStringExtra("AppointmentObj2");
        String data4 = data.getStringExtra("AppointmentObj3");
        String data5 = data.getStringExtra("AppointmentObj4");

        Drname=(TextView)findViewById(R.id.drname);



       /* anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Drname.setTextColor(ContextCompat.getColor(PrescriptionPage.this, R.color.blue_aboutUs));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Drname.setTextColor(ContextCompat.getColor(PrescriptionPage.this, R.color.orange));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
     //   Drname .startAnimation(anim);

        Specialization=(TextView)findViewById(R.id.specialization);
        Adress=(TextView)findViewById(R.id.adress);
        Patientname=(TextView)findViewById(R.id.patientname);
        date=(TextView)findViewById(R.id.date);
        Complaints=(TextView)findViewById(R.id.complaints);
        Drobservation=(TextView)findViewById(R.id.drobservation);
        labtests=(TextView) findViewById(R.id.labtests);
        labtests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                labtestspopup(data1);
            }
        });
        medication=(TextView) findViewById(R.id.medication);
        medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicationpopup(data1);
            }
        });


        Drname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in= new Intent(PrescriptionPage.this,DoctorOverviewActivity.class);

                in.putExtra("DoctorId",docid);
                in.putExtra("DrSpcelization",spec);
                PrescriptionPage.this.startActivity(in);
            }
        });
      /*  listviewmed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

       /* listviewlab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });*/

        Patientname.setText(data2);
    //    String data41=AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(data4);


        date.setText(data4);

    PrescriptionPageRunner runner=new PrescriptionPageRunner(data1,data3);
       runner.execute();
      /* PrescTask task=new PrescTask(data1);
       task.execute();*/

    }

    private void labtestspopup(String data1) {
        localView = View.inflate(this, R.layout.labtestpopup, null);
        localView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_enter));
        this.mDialog = new Dialog(this, R.style.NewDialog);
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
        cancel=(ImageView)mDialog.findViewById(R.id.cancel);
        listviewlab=(ListView)mDialog.findViewById(R.id.listviewlab);
        norecords=(TextView)mDialog.findViewById(R.id.norecords);
        LabTask task1=new LabTask(data1);
        task1.execute();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

    }

    private void medicationpopup(String data1) {
        localView = View.inflate(this, R.layout.medicationpopup, null);
        localView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_enter));
        this.mDialog = new Dialog(this, R.style.NewDialog);
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
        listviewmed=(ListView)mDialog.findViewById(R.id.listviewmed);
        norecords=(TextView)mDialog.findViewById(R.id.norecords);
        cancel=(ImageView)mDialog.findViewById(R.id.cancel);
        PrescTask task=new PrescTask(data1);
        task.execute();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }


    private class PrescriptionPageRunner extends AsyncTask<String,String,String> {
        String data,hspname;

        public PrescriptionPageRunner(String data1, String data3) {
            this.data=data1;
            this.hspname=data3;

        }


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.patientPrescriptions+data,"GET",null);
           // String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/patientPrescriptions?appId="+data,"GET",null);
             Log.i("========>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
              JSONArray array=new JSONArray(s);
                JSONObject object=array.getJSONObject(0);
                JSONObject object1=object.getJSONObject("Doctor Info");

              Drname.setText("Dr."+object1.getString("name"));
                docid=object1.getString("doctorId");
               spec=object1.getString("specilization");

                Specialization.setText(object1.getString("specilization"));
                Adress.setText(hspname+", "+object1.getString("localities")+", Hyderabad");

                Complaints.setText(object.getString("Complaints"));
                Drobservation.setText(object.getString("Observation"));
//+object1.getString("location")


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class PrescTask extends AsyncTask<String,String,String>{
        String data;
        private ProgressDialog pDialog;
        public PrescTask(String data1) {
            this.data=data1;
        }

        @Override
        protected void onPreExecute() {

      //      pDialog= ProgressDialog.show(getApplicationContext(),"Please wait","Loading...");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.patientPrescriptions+data,"GET",null);
            //String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/patientPrescriptions?appId="+data,"GET",null);
            Log.i("========>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems = new ArrayList<PrescriptionModel>();
            try {
                JSONArray array=new JSONArray(s);
                JSONObject object=array.getJSONObject(0);
                JSONArray array1=object.getJSONArray("Medicines");
                if(array1.length()>0){
                    for(int i=0;i<array1.length();i++){
                        PrescriptionModel model=new PrescriptionModel();
                        JSONObject object1=array1.getJSONObject(i);
                        model.setDrugName(object1.getString("id"));
                        model.setDrugName(object1.getString("drugName"));
                        model.setDosage(object1.getString("dosage"));
                        model.setQty(object1.getString("qty"));
                        model.setDrugType(object1.getString("drugType"));
                        model.setInstruction(object1.getString("instruction"));
                        rowItems.add(model);
                      Log.i("kkkkkk",rowItems.toString());
  //                      if (pDialog.isShowing()) {    pDialog.dismiss();       }
                    }
                }else {
                    norecords.setVisibility(View.VISIBLE);
                }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrescriptionAdapter adapter=new PrescriptionAdapter(PrescriptionPage.this,rowItems);
            listviewmed.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            super.onPostExecute(s);

        }
    }

    private class LabTask extends AsyncTask<String,String,String> {
        String data;
        public LabTask(String data1) {
            this.data=data1;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.patientPrescriptions+data,"GET",null);
      //      String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/patientPrescriptions?appId="+data,"GET",null);
            Log.i("========>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItem = new ArrayList<PrescriptionModel>();
            try {
                JSONArray array=new JSONArray(s);
                JSONObject object=array.getJSONObject(0);
                JSONArray jsonArray=object.getJSONArray("LabTests");
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        PrescriptionModel model=new PrescriptionModel();
                        JSONObject object1=jsonArray.getJSONObject(i);
                        model.setId(object1.getString("id"));
                        model.setTestName(object1.getString("testName"));
                        model.setFileName(object1.getString("fileName"));
                             rowItem.add(model);

                    }
                }else{
                    norecords.setVisibility(View.VISIBLE);
                }
                Log.i("ROwitem",rowItem.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            PrescriptionlabAdapter adapter=new PrescriptionlabAdapter(PrescriptionPage.this,rowItem);
            listviewlab.setAdapter(adapter);


            super.onPostExecute(s);


        }
    }
}
