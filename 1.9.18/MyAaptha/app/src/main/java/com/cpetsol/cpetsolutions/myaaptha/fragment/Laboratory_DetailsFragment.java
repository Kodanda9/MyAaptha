package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Dialog;
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


public class Laboratory_DetailsFragment extends Fragment {
    private EditText labOwnNme, labShpNme, labShpRegNo,labApprvBy, labRegYr, labQualificationYr;
    private Button Submit;
    private static View rootView;
    private Spinner State, Dstct;
    private String pId,name;
    String PD_StateCode;
    String PD_distCode;
    private View localView;
    private Dialog mDialog;
    private Button update;


    public Laboratory_DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }//if
        try {
            rootView = inflater.inflate(R.layout.fragment_laboratory__details, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
           labOwnNme = (EditText) rootView.findViewById(R.id.labName);
            name=SessinSave.getsessin("FullName",getActivity());
            labOwnNme.setText(name);
           labShpNme = (EditText) rootView.findViewById(R.id.labShopNm);
          labShpRegNo = (EditText) rootView.findViewById(R.id.labShpRegNo);
           labApprvBy = (EditText) rootView.findViewById(R.id.labapprvBy);
            labRegYr = (EditText) rootView.findViewById(R.id.labregYr);
            labQualificationYr = (EditText) rootView.findViewById(R.id.labQualYr);
            State = (Spinner) rootView.findViewById(R.id.state);
            Dstct = (Spinner) rootView.findViewById(R.id.district);
            Submit = (Button) rootView.findViewById(R.id.submitlab);
            update = (Button) rootView.findViewById(R.id.updatelab);
            StateTask statetask = new StateTask();
            statetask.execute();

            GetDetailsTask task=new GetDetailsTask();
            task.execute();

            Submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
             SubmitTask submittask = new SubmitTask();
                    submittask.execute();
                    getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Laboratory_DetailsFragment()).commit();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;

    }

    private class SubmitTask extends AsyncTask<String, String, String> {
        String labownname, labshpnme, labshpregno, labapprvby, labregyr, labqualyr, state, dstrct;

        @Override
        protected void onPreExecute() {
            state=State.getSelectedItem().toString();
            dstrct=Dstct.getSelectedItem().toString();
            labownname = labOwnNme.getText().toString();
            labshpnme = labShpNme.getText().toString();
            labshpregno = labShpRegNo.getText().toString();
            labapprvby = labApprvBy.getText().toString();
            labregyr = labRegYr.getText().toString();
            labqualyr = labQualificationYr.getText().toString();
            pId = SessinSave.getsessin("profile_id", getActivity());



        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.accumulate("labOwnerName", labownname);
                jsonObject.accumulate("labName", labshpnme);
                jsonObject.accumulate("labRegNo", labshpregno);
                jsonObject.accumulate("approvalBy", labapprvby);
                jsonObject.accumulate("labRegYear", labregyr);
                jsonObject.accumulate("qualificationYear", labqualyr);
                jsonObject.accumulate("stateCode",PD_StateCode);
                jsonObject.accumulate("districtCode",PD_distCode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("=====", jsonObject.toString());
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.saveLabOwnerDetails+pId, "POST", jsonObject);
        //    String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/saveLabOwnerDetails/"+pId, "POST", jsonObject);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), "" + s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }

    private class StateTask extends AsyncTask<String, String, String> {
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
                State.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_states[i])){
                                PD_StateCode=arr_state_codes[i];

                            DistrictAsyncRunner runner = new DistrictAsyncRunner(arr_state_codes[i]);
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
        String countryCode;
        ProgressDialog pDialog;

        public DistrictAsyncRunner(String arr_state_code) {
            this.countryCode=arr_state_code;
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
                Dstct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_dist[i])){
                                PD_distCode=arr_dist_codes[i];
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

    private class GetDetailsTask extends AsyncTask<String,String,String> {
        String Profileid=SessinSave.getsessin("profile_id",getActivity());
        String labname,shopname,regno,regyr,apprvby,qualyr,stat,dist;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.viewLabOwnerInfo+Profileid,"GET",null);
    //        String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/viewLabOwnerInfo/"+Profileid,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("======>",s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                labname=jsonObject.getString("labOwnerName");
                shopname=jsonObject.getString("labName");
                regno=jsonObject.getString("labRegNo");
                regyr=jsonObject.getString("labRegYear");
                apprvby=jsonObject.getString("approvalBy");
                qualyr=jsonObject.getString("qualificationYear");
                stat=jsonObject.getString("stateCode");
                dist=jsonObject.getString("districtCode");
                labOwnNme.setText(labname);
                labShpNme.setText(shopname);
                labShpRegNo.setText(regno);

                labApprvBy.setText(apprvby);

                labRegYr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(regyr));

                labQualificationYr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(qualyr));

              State.setSelection(AppHelper.setValueToSpinner(State,stat));
                Dstct.setSelection(AppHelper.setValueToSpinner(Dstct,dist));



                Log.i("bhavaniiiiiii",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}

