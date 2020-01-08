package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.Search_Lab_Adapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Search_Lab_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.cpetsol.cpetsolutions.myaaptha.R.id.listview;


public class Search_LabTestsFragment extends Fragment {
    private View rootView;
    private EditText medNumber;
    private Button search;
    private TextView pfid, name, state, district;
    private LinearLayout searchLayout,labreport;
    private String Number;
    private TextView gender;
    private ImageView image;
    private Button predetails;
    private View localView;
    private Dialog mDialog;
    private TextView doctername, patientname;
    private ListView list;
    private TextView prescribedate;
    private String patname;
    private String patid;
    private Button generatebill;
    private ArrayList<Search_Lab_Model> rowItems;
    private CheckBox check;
    private Button labreports;


    public Search_LabTestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_search__lab_tests, container, false);
            medNumber = (EditText) rootView.findViewById(R.id.medicineNum);
            pfid = (TextView) rootView.findViewById(R.id.pfid);
            name = (TextView) rootView.findViewById(R.id.name_med);
            gender = (TextView) rootView.findViewById(R.id.gender_med);
            state = (TextView) rootView.findViewById(R.id.state_med);
            district = (TextView) rootView.findViewById(R.id.dist_med);
            image = (ImageView) rootView.findViewById(R.id.image);
            search = (Button) rootView.findViewById(R.id.search);
            predetails = (Button) rootView.findViewById(R.id.viewpreDtls);
            labreports = (Button) rootView.findViewById(R.id.viewlabreports);
            searchLayout = (LinearLayout) rootView.findViewById(R.id.numberLayout);

            predetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Presform();
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Number = medNumber.getText().toString();

                    Log.i("Bhavani>>>>>>>", Number);
                    Toast.makeText(getActivity(), "" + Number, Toast.LENGTH_LONG).show();
                    SearchTask task = new SearchTask(Number);
                    task.execute();
                    searchLayout.setVisibility(View.VISIBLE);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;


    }

    private void Presform() {
        localView = View.inflate(getActivity(), R.layout.lab_presc_form, null);
        localView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_enter));
        this.mDialog = new Dialog(getActivity(), R.style.NewDialog);
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
        doctername=(TextView)this.mDialog.findViewById(R.id.doctorname);
       /* patientname=(TextView)this.mDialog.findViewById(R.id.patientname);
        patientname.setText(patname);*/
        prescribedate=(TextView)this.mDialog.findViewById(R.id.date);

        list=(ListView)this.mDialog.findViewById(listview);
        generatebill=(Button)this.mDialog.findViewById(R.id.bill);
      PrescTask task=new PrescTask(patid);
        task.execute();
        generatebill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Billform();
            }
        });

    }

    private void Billform() {
        localView = View.inflate(getActivity(), R.layout.bill_form, null);
        localView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_enter));
        this.mDialog = new Dialog(getActivity(), R.style.NewDialog);
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
        TextView date=(TextView)this.mDialog.findViewById(R.id.date);
        TextView name=(TextView)this.mDialog.findViewById(R.id.bilpatnam);
        name.setText(patname);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dat = sdf.format(new Date());
        date.setText(dat);
      BillTask task=new BillTask();
        task.execute();
        ListView listview=(ListView)this.mDialog.findViewById(R.id.billlist);

    }


    private class SearchTask extends AsyncTask<String,String,String>{
        String num;
        public SearchTask(String number) {
            this.num=number;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.medicinesData+num,"GET",null);
         //   String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/medicinesData?mobileOrName="+num,"GET",null);
            Log.i("=====>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonarray=new JSONArray(s);
                JSONObject jsonObject=jsonarray.getJSONObject(0);
                pfid.setText(jsonObject.getString("profileId"));
                name.setText(jsonObject.getString("name"));
                patname=jsonObject.getString("name");
                patid=jsonObject.getString("profileId");

                gender.setText(jsonObject.getString("gender"));

                district.setText(jsonObject.getString("district"));
                state.setText(jsonObject.getString("state"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class PrescTask extends AsyncTask<String,String,String>{
        private String patientid;
        public PrescTask(String patid) {
            this.patientid=patid;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.labTestDetails+patientid,"GET",null);
         //   String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/labTestDetails?pid="+patientid,"GET",null);
            Log.i("=======>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems = new ArrayList<Search_Lab_Model>();

            try {
                JSONObject jsonobject=new JSONObject(s);
                JSONArray jsonarray=jsonobject.getJSONArray("Lab Tests");

                if(jsonarray.length()>0){

                    for(int i=0;i<jsonarray.length();i++){
                        JSONObject jsonObject=jsonarray.getJSONObject(i);
                        doctername.setText(jsonObject.getString("doctorName"));
                        prescribedate.setText(jsonObject.getString("presciptionDate"));
                       Search_Lab_Model model=new Search_Lab_Model();
                        model.setLabTestNames(jsonObject.getString("labTestNames"));
                        model.setLabTestName(jsonObject.getString("labTestName"));
                        model.setDoctorName(jsonObject.getString("doctorName"));
                        model.setHospitalName(jsonObject.getString("hospitalName"));
                        model.setPatientName(jsonObject.getString("patientName"));
                        model.setPresciptionDate(jsonObject.getString("presciptionDate"));
                        model.setAge(jsonObject.getString("age"));
                        model.setGender(jsonObject.getString("gender"));
                        model.setProfileId(jsonObject.getString("profileId"));
                        model.setPermanentAddress(jsonObject.getString("permanentAddress"));
                        model.setEmailId(jsonObject.getString("emailId"));
                        model.setMobileNo(jsonObject.getString("mobileNo"));
                        rowItems.add(model);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Search_Lab_Adapter adapter=new Search_Lab_Adapter(getActivity(),rowItems);
            list.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }

    private class BillTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.generateMedicinesBill+ SessinSave.getsessin("profile_id",getActivity())+"?medicines="+"Dolo650-650mg-Tab-20&medicines=Paracetomol-650mg-Tab-20","GET",null);
      //      String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/generateMedicinesBill/900000040901?medicines=Dolo650-650mg-Tab-20&medicines=Paracetomol-650mg-Tab-20","GET",null);

            Log.i("========>",Content);  return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject object=new JSONObject(s);
                object.getString("Total");
                JSONArray array=object.getJSONArray("map");
                if (array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);

                        JSONObject object1=obj.getJSONObject("Paracetomol-650mg-Tab-20");

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}