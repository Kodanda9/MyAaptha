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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.EmployeeDataAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.EmployeeModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EmployeeDataFragment extends Fragment {


    private View rootView;
    private Button add;
    private View EmployView;
    private Dialog EmployDialog;
    private Spinner Hospitals,EmploymentType,EmploymentName;
    private EditText remarks;
    private ListView employeedatalist;
    private ArrayList<EmployeeModel> emplist;
    private ArrayList<String> owner,owner_id;
    private ArrayList<String> hospitals,array_hospital_codes,roless,roles_id;
    private String qualificationId;



    public EmployeeDataFragment() {
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
        }//if
        try {
            rootView = inflater.inflate(R.layout.fragment_employee_data, container, false);
               add=(Button)rootView.findViewById(R.id.add);
               employeedatalist=(ListView)rootView.findViewById(R.id.listview);
            EmployeeDataRunner runner=new EmployeeDataRunner();
            runner.execute();

           add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddEmployeePopup();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;

    }

    private void AddEmployeePopup() {
        EmployView=View.inflate(getActivity(),R.layout.employee_add,null);
        EmployView.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in_enter));
        this.EmployDialog=new Dialog(getActivity(),R.style.NewDialog);
        this.EmployDialog.setContentView(EmployView);
        this.EmployDialog.setCancelable(true);
        this.EmployDialog.show();

        Window window = this.EmployDialog.getWindow();
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
        Hospitals=(Spinner)EmployDialog.findViewById(R.id.hospitals);
        EmploymentType=(Spinner)EmployDialog.findViewById(R.id.employmenttype);
        EmploymentName=(Spinner)EmployDialog.findViewById(R.id.employ);
        remarks=(EditText)EmployDialog.findViewById(R.id.remarks);
        Employeedata runner=new Employeedata();
        runner.execute();
          Button Submit=(Button)EmployDialog.findViewById(R.id.Submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveEmployeeData task =new SaveEmployeeData();
                task.execute();
            }
        });



    }

    private class Employeedata extends AsyncTask<String,String,String>{

        private ArrayList<String> ownerlist;
        private ArrayList<String> hospitallist;

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.employeeData+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
           Log.i("Content",ApisHelper.employeeData+ SessinSave.getsessin("profile_id",getActivity()));
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("userNameId");
                owner=new ArrayList<String>();
                owner_id=new ArrayList<String>();

                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object1=array.getJSONObject(i);
                        owner.add(object1.getString("fullName")+"("+object1.getString("id")+")");
                      owner_id.add(object1.getString("id"));
                    }
                    owner.add(0,"Select");
                    owner_id.add(0,"");

                    EmploymentName.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,owner));
                    EmploymentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String SelfullName=adapterView.getItemAtPosition(i).toString();

                            for(int j = 0; j <owner.size(); j++){
                                if(SelfullName.equalsIgnoreCase(owner.get(j))){
                                    qualificationId = owner_id.get(j);
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//

                }

                JSONArray array1=object.getJSONArray("hospitals");
                hospitals = new ArrayList<String>();
                array_hospital_codes= new ArrayList<String>();
                if(array1.length()>0){
                    for(int i=0;i<array1.length();i++){
                        JSONObject object2=array1.getJSONObject(i);
                        hospitals.add(object2.getString("hospitalName")+"("+object2.getString("localities")+")");
                        array_hospital_codes.add(object2.getString("hospitalId"));
                    }
                    hospitals.add(0,"Select");
                    array_hospital_codes.add(0,"");

                    Hospitals.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,hospitals));
Hospitals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String SelfullName=adapterView.getItemAtPosition(i).toString();

        for(int j = 0; j <hospitals.size(); j++){
            if(SelfullName.equalsIgnoreCase(hospitals.get(j))){
                qualificationId = array_hospital_codes.get(j);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
});
                }

                JSONArray array2=object.getJSONArray("Roles Data");
                roless=new ArrayList<String>();
                roles_id=new ArrayList<String>();
                if (array2.length()>0){
                    for(int i=0;i<array2.length();i++){
                        JSONObject object2=array2.getJSONObject(i);
                        roless.add(object2.getString("roleDetails"));
                        roles_id.add(object2.getString("id"));
                    }
                    roless.add(0,"Select");
                    roles_id .add(0,"");
                   Log.i("hello",roless.toString());
                    EmploymentType.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,roless));
              EmploymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                      String SelfullName=adapterView.getItemAtPosition(i).toString();

                      for(int j = 0; j <roless.size(); j++){
                          if(SelfullName.equalsIgnoreCase(roless.get(j))){
                              qualificationId = roles_id.get(j);
                          }
                      }
                  }

                  @Override
                  public void onNothingSelected(AdapterView<?> adapterView) {

                  }
              });
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class SaveEmployeeData extends AsyncTask<String,String,String>{
        String hspname,employtype,ownername,remark;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hspname=Hospitals.getSelectedItem().toString();
            employtype=EmploymentType.getSelectedItem().toString();
            ownername=EmploymentName.getSelectedItem().toString();
            remark=remarks.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("hospitals.hospitalId",hspname);
                object.accumulate("employmentType",employtype);
                object.accumulate("ownerDetails.profileId",ownername);
                object.accumulate("remarks",remark);
            } catch (JSONException e) {
                e.printStackTrace();
            }
          String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.saveEmployeeData+SessinSave.getsessin("profile_id",getActivity()),"POST",object);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(),"Sucessfully Added"+s,Toast.LENGTH_LONG).show();
        }
    }

    private class EmployeeDataRunner extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {

            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.viewEmployeeData+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            Log.i("Content",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
        emplist=new ArrayList<>();
            super.onPostExecute(s);
            try {
                JSONArray array=new JSONArray(s);
                Log.i("Content",s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        EmployeeModel model=new EmployeeModel();
                        model.setHospitalName(object.getString("hospitalName"));
                        model.setHospitalName(object.getString("hospitalId"));
                        model.setDoctorId(object.getString("doctorId"));
                        model.setName(object.getString("name"));
                        model.setLocation(object.getString("location"));
                        model.setLocalities(object.getString("localities"));
                        model.setAddress(object.getString("address"));
                        model.setQualification(object.getString("qualification"));
                        model.setSpecialization(object.getString("specialization"));
                        model.setPrice(object.getString("price"));
                        model.setExperience(object.getString("experience"));
                        model.setGender(object.getString("gender"));
                        model.setLongitude(object.getString("longitude"));
                        model.setSunday(object.getString("sunday"));
                        model.setMonday(object.getString("monday"));
                        model.setTuesday(object.getString("tuesday"));
                        model.setWednesday(object.getString("wednesday"));
                        model.setThursday(object.getString("thursday"));
                        model.setFriday(object.getString("friday"));
                        model.setSaturday(object.getString("saturday"));
                        model.setHospitalName(object.getString("opPercentage"));
                        model.setIpPercentage(object.getString("ipPercentage"));
                        model.setId(object.getString("labPercentage"));
                        model.setHospitalName(object.getString("id"));
                        model.setEmpType(object.getString("empType"));
                        model.setRemarks(object.getString("remarks"));
                        emplist.add(model);
                        EmployeeDataAdapter adapter=new EmployeeDataAdapter(getActivity(),emplist);
                       employeedatalist.setAdapter(adapter);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*private class AddEmployeetask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
Remarks=remarks.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            object.accumulate("hospitals.hospitalId",hospitalid);
            object.accumulate("employmentType",hospitalid);
            object.accumulate("ownerDetails.profileId",hospitalid);
            object.accumulate("remarks",Remarks);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }*/
}
