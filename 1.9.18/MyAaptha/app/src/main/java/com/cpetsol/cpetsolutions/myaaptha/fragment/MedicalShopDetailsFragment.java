package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class MedicalShopDetailsFragment extends Fragment {
  private   EditText   MedOwnNme, MedShpNme, MedShpRegNo, ApprvBy, RegYr, QualificationYr;
  private   Button Update,Submit;
    private static View rootView;
  private   Spinner State, Dstct;
    private String pId;
    String PD_StateCode;
    String PD_distCode;
    private ArrayList<String> namesList,idsList ;
    public String stat,dist;
    private String qualificationId;



    public MedicalShopDetailsFragment() {
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
            rootView= inflater.inflate(R.layout.fragment_medical_shop_details, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
            MedOwnNme = (EditText) rootView.findViewById(R.id.medName);
            MedShpNme = (EditText) rootView.findViewById(R.id.medShopNm);
            MedShpRegNo = (EditText) rootView.findViewById(R.id.medShpRegNo);
            ApprvBy = (EditText) rootView.findViewById(R.id.apprvBy);
            RegYr = (EditText) rootView.findViewById(R.id.regYr);
            QualificationYr = (EditText) rootView.findViewById(R.id.QualYr);
            State = (Spinner) rootView.findViewById(R.id.state);
            Dstct = (Spinner) rootView.findViewById(R.id.district);
            Submit = (Button) rootView.findViewById(R.id.submit);
            Update = (Button) rootView.findViewById(R.id.update);


            MedicalShpDetailsAsyncTask msdRunner = new MedicalShpDetailsAsyncTask();
            msdRunner.execute();


            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateTask updatetask = new UpdateTask();
                    updatetask.execute();
                }
            });
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdateTask updatetask = new UpdateTask();
                    updatetask.execute();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }



    private class UpdateTask extends AsyncTask<String, String, String> {
        String medownname, medshpnme, medshpregno, apprvby, regyr, qualyr, state, dstrct;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            state=State.getSelectedItem().toString();
            dstrct=Dstct.getSelectedItem().toString();
           medownname = MedOwnNme.getText().toString();
            medshpnme = MedShpNme.getText().toString();
            medshpregno = MedShpRegNo.getText().toString();
            apprvby = ApprvBy.getText().toString();
            regyr = RegYr.getText().toString();
            qualyr = QualificationYr.getText().toString();

            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");

            pId = SessinSave.getsessin("profile_id", getActivity());

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("medicalShopOwnerName", medownname);
                jsonObject.accumulate("medicalShopName", medshpnme);
                jsonObject.accumulate("medicalShopRegNo", medshpregno);
                jsonObject.accumulate("approvedBy", apprvby);
                jsonObject.accumulate("medicalShopRegYear", regyr);
                jsonObject.accumulate("qualificationYear", qualyr);
                jsonObject.accumulate("stateCode",PD_StateCode);
                jsonObject.accumulate("districtCode",PD_distCode);

             Log.i("jsonObject",jsonObject.toString());
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("=====", jsonObject.toString());
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.saveMedicalOwnerDetails+pId, "POST", jsonObject);
        //    String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/saveMedicalOwnerDetails/"+pId, "POST", jsonObject);
            return Content;


        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), "Succesfully updated" + s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }

    private class StateTask extends AsyncTask<String, String, String> {

        private String state,district;
        public StateTask(String stat, String dist) {
            this.state=stat;
            this.district=dist;
        }

        @Override
        protected String doInBackground(String... strings) {
            String stat = AsyncTaskHelper.makeServiceCall(ApisHelper.loadStates, "GET", null);
            //    String stat = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/loadStates", "GET", null);
            return stat;
        }

        @Override
        protected void onPostExecute(String s) {
            int k = 0;
            try {
                final JSONArray arry = new JSONArray(s);

                final String[] arr_states = new String[arry.length()];
                final String[] arr_state_codes = new String[arry.length()];
                for (int i = 0; i < arry.length(); i++) {
                    JSONObject jsonCountry = arry.getJSONObject(k);
                    arr_states[i] = jsonCountry.getString("name");
                    arr_state_codes[i] = jsonCountry.getString("code");
                    k++;
                }
                ArrayList<String> ar = new ArrayList<String>(Arrays.asList(arr_states));
                State.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,ar));
               /* for(int i = 0; i < arry.length(); i++){
                    if(state.equalsIgnoreCase(arr_states[i])){
                        Log.i("Bhaavani",arr_states[i]);


                    }
                }*/
                State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_states[i])){
                                PD_StateCode=arr_state_codes[i];
                                State.setSelection(AppHelper.setValueToSpinner(State,state));
                                DistrictAsyncRunner runner = new DistrictAsyncRunner(arr_state_codes[i],district);
                                runner.execute();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getActivity(),"nothing Selected", Toast.LENGTH_LONG).show();
                    }
                });
               /* State.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_states[i])){
                                PD_StateCode=arr_state_codes[i];

                                DistrictAsyncRunner runner = new DistrictAsyncRunner(arr_state_codes[i]);
                                runner.execute();
                            }
                        }
                    }
                });*/
                super.onPostExecute(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DistrictAsyncRunner extends AsyncTask<String,String,String> {
        String countryCode,distr;
        ProgressDialog pDialog;



        public DistrictAsyncRunner(String arr_state_code, String district) {
            this.countryCode=arr_state_code;
            this.distr=district;
        }


        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall(ApisHelper.allDistrictList1+countryCode,"GET",null);
            Log.i("Content",ApisHelper.allDistrictList1+countryCode);
            //   String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/medicalShop/allDistrictList/"+countryCode,"GET",null);
            return Content1;
        }

        @Override
        protected void onPostExecute(String s) {
            int k=0;
            try {
                JSONObject json=new JSONObject(s);
                final JSONArray arry=json.getJSONArray("result");
                final String[] arr_dist = new String[arry.length()];
                final String[] arr_dist_codes = new String[arry.length()];
                final String[] arr_dist_states = new String[arry.length()];
                for(int i = 0; i < arry.length(); i++) {
                    JSONObject jsonCountry = arry.getJSONObject(k);
                    arr_dist[i] = jsonCountry.getString("name");
                    arr_dist_codes[i] = jsonCountry.getString("code");
                    arr_dist_states[i] = jsonCountry.getString("stateCode");

                    k++;
                }
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr_dist));
                arrayList.add(0,"Select");

                Dstct.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,arrayList));
              /*  for(int i = 0; i < arry.length(); i++){
                    if(distr.equalsIgnoreCase(arr_dist[i])){
                        Log.i("Bhaavani",arr_dist[i]);


                    }
                }*/
               Dstct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_dist[i])){
                                PD_distCode=arr_dist_codes[i];
                                Dstct.setSelection(AppHelper.setValueToSpinner(Dstct,distr));
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getActivity(),"nothing Selected", Toast.LENGTH_LONG).show();
                    }
                });

                /*  Dstct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String spinnerItem=adapterView.getItemAtPosition(position).toString();
                    for(int i = 0; i < arry.length(); i++){
                        if(spinnerItem.equalsIgnoreCase(arr_dist[i])){
                            PD_distCode=arr_dist_codes[i];
                        }
                    }
                }


            });*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    private class MedicalShpDetailsAsyncTask extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String stat = AsyncTaskHelper.makeServiceCall(ApisHelper.medicalShopDetails+SessinSave.getsessin("profile_id", getActivity())+"", "GET", null);
            return stat;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject mainObj =new JSONObject(s);
                Update.setVisibility(View.VISIBLE); Submit.setVisibility(View.GONE);
                MedOwnNme.setText(mainObj.getString("medicalShopOwnerName"));
                MedShpNme.setText(mainObj.getString("medicalShopName"));
                MedShpRegNo.setText(mainObj.getString("medicalShopRegNo"));
                ApprvBy.setText(mainObj.getString("approvedBy"));
                RegYr.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(mainObj.getString("medicalShopRegYear")));
                QualificationYr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(mainObj.getString("qualificationYear")));
                stat=mainObj.getString("stateCode");
                dist=mainObj.getString("districtCode");

                StateTask statetask = new StateTask(stat,dist);   statetask.execute();

//                mainObj.getString("districtCode") ;
//                mainObj.getString("medicalShopId") ;

            } catch (Exception e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
        }

    }
}

