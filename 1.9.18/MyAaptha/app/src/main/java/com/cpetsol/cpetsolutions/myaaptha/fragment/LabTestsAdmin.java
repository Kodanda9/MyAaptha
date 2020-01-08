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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.LabHall_Adapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Medicine_Hall_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 8/28/2018.
 */

public class LabTestsAdmin extends Fragment {
    private static View rootView;
    private View localView;
    private Dialog mDialog;
    private ListView lv;
    private EditText drugName,drugdetails,sideeffects;
    private Button button;



    public LabTestsAdmin() {
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
            rootView = inflater.inflate(R.layout.fragment_medicines__hall, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
            lv=(ListView)rootView.findViewById(R.id.lv_medicinehall);
            Button Add=(Button)rootView.findViewById(R.id.add);
            Add.setText("Add Test");
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AddPopup();
                }
            });
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());
          MedicineHallTask task=new MedicineHallTask();
            task.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;

    }

    private void AddPopup() {

        localView = View.inflate(getActivity(), R.layout.medicinehall_editform, null);
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

        drugName=(EditText)this.mDialog.findViewById(R.id.hall_drugname);
        drugdetails=(EditText)this.mDialog.findViewById(R.id.hall_details);
          sideeffects=(EditText)this.mDialog.findViewById(R.id.hall_sideeffects);
            button=(Button)this.mDialog.findViewById(R.id.edit);
        button.setText("Submit");
        ImageView Cancel=(ImageView)this.mDialog.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Addtask task=new Addtask();
                task.execute();
            }
        });


    }


    private class MedicineHallTask extends AsyncTask<String,String,String> {
        ArrayList<Medicine_Hall_Model> rowItems;
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allLabTestInfo,"GET",null);
               Log.i("======>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems=new ArrayList<>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        Medicine_Hall_Model model=new Medicine_Hall_Model();
                        model.setId(object.getString("id"));
                        model.setDrugName(object.getString("labTestName"));
                        model.setDetails(object.getString("details"));
                        model.setSideEffects(object.getString("sideEffects"));
                        model.setStatus(object.getString("status"));
                        model.setUserDetails(object.getString("userDetails"));
                        model.setViewStatus(object.getString("viewStatus"));
                        rowItems.add(model);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LabHall_Adapter adapter=new LabHall_Adapter(getActivity(),rowItems);

            lv.setAdapter(adapter);


            super.onPostExecute(s);
        }
    }

    private class Addtask extends AsyncTask<String,String,String>{
        String dname,ddetails,dstatus,sideeffect;
        String updateid;



        @Override
        protected void onPreExecute() {
            dname=drugName.getText().toString();
            ddetails=drugdetails.getText().toString();
            sideeffect=sideeffects.getText().toString();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.accumulate("drugName",dname);
                jsonObject.accumulate("details",ddetails);
                jsonObject.accumulate("sideEffects",sideeffect);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("=====>",jsonObject.toString());
            String content1=AsyncTaskHelper.makeServiceCall(ApisHelper.storeMedicinesHall+ SessinSave.getsessin("profile_id",getActivity()),"POST",jsonObject);
            //     String content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/updateMedicinesHall","POST",jsonObject);
            return content1;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("=======>",s);
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
            Toast.makeText(getActivity(),"Successfully updated",Toast.LENGTH_LONG).show();
            getActivity().getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Medicines_HallFragment()).commit();

            super.onPostExecute(s);
        }
    }
}
