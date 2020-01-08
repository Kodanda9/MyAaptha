package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSignUpFragment extends Fragment {


    private View rootView;
    private Typeface fontAwesomeFont;
    private RadioGroup radioProfessionGroup,RGGender;
    EditText Et_fullName,Et_password,Et_mobNumb;
    private Spinner SCountry,SpState,SpDist;
    private TextView TvSignIn;
    private String selectedGender;
    private String selectedProfession;
    private String PD_stateCode;
    private String PD_distCode;

    public UserSignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {   parent.removeView(rootView);  }
        }//if
        try{
            rootView=inflater.inflate(R.layout.user_sign_up_fragment,container,false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            AppHelper.setupHideleyboard(rootView,getActivity());


            fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
            TextView TvCP=(TextView)rootView.findViewById(R.id.tvfoiFullName);            TvCP.setTypeface(fontAwesomeFont);
            TextView TvPSW=(TextView)rootView.findViewById(R.id.tvfoiPsw);                TvPSW.setTypeface(fontAwesomeFont);
            TextView TvmobNo=(TextView)rootView.findViewById(R.id.tvfoiMobNo);            TvmobNo.setTypeface(fontAwesomeFont);


            RGGender=(RadioGroup)rootView.findViewById(R.id.radioGenderGroup);
            radioProfessionGroup = (RadioGroup)rootView.findViewById(R.id.radioProfession);
            Et_fullName=(EditText)rootView.findViewById(R.id.et_fullName);
            Et_password=(EditText)rootView.findViewById(R.id.et_password);
            Et_mobNumb=(EditText)rootView.findViewById(R.id.et_mobNumb);
            SCountry=(Spinner)rootView.findViewById(R.id.sCountry);
            SpState=(Spinner)rootView.findViewById(R.id.sState);
            SpDist=(Spinner)rootView.findViewById(R.id.sDist);


            TvSignIn=(TextView)rootView.findViewById(R.id.tvSignIn);
            TvSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().add(R.id.login_frame,new LoginFragment() ).commit();
                    getActivity().getFragmentManager().beginTransaction().remove(UserSignUpFragment.this).commit();
                }
            });
            RGGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId)
                    {
                        case R.id.radioMale:
                            selectedGender="Male";
                            break;
                        case R.id.radioFemale:
                            selectedGender="Female";
                            break;
                        case R.id.radioOther:
                            selectedGender="Other";
                            break;
                    }
                }
            });
            radioProfessionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId)
                    {
                        case R.id.radioUser:
                            selectedProfession="User";
                            break;
                        case R.id.radioDoctor:
                            selectedProfession="Doctor";
                            break;
                        case R.id.radioNurse:
                            selectedProfession="Nurse";
                            break;
                        case R.id.radioLabAssistant:
                            selectedProfession="LabAssistant";
                            break;
                        case R.id.radioMedShopOwner:
                            selectedProfession="MedicalShopOwner";
                            break;

                    }
                }
            });

            ArrayList<String> ar = new ArrayList<String>();
            ar.add(0,"India");
            SCountry.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,ar));


            StatesAsyncRunner srunner = new StatesAsyncRunner();
                                srunner.execute();

            Button signUp_button=(Button)rootView.findViewById(R.id.signUp);
            signUp_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (RGGender.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(getActivity(),"Please Select Gender", Toast.LENGTH_LONG).show();
                    }else{
                        if (radioProfessionGroup.getCheckedRadioButtonId() == -1)
                        {
                            Toast.makeText(getActivity(),"Please Select Profession", Toast.LENGTH_LONG).show();
                        }else{
                            validations();
                        }
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }//onCreateView

    private void validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.signup_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.signup_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.signup_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(getActivity(),strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;
            }
            boolean isValidData = helper.validateData(getActivity().getBaseContext(), aList, rootView);
            if (!isValidData) {
                return;
            }else{
                if(AppHelper.phoneNumberValidation(Et_mobNumb.getText().toString())){
                    if(TextUtils.isEmpty(Et_password.getText().toString()) || Et_password.getText().toString().length() <6 )
                    {      Et_password.setError("You must have 6 characters in your password");
                    }else{
                        AsyncTaskRunner runner = new AsyncTaskRunner();
                        runner.execute();
                    }
                }else{
                    Toast.makeText(getActivity(),"Phone number is not valid", Toast.LENGTH_LONG).show();
                    Et_mobNumb.setError("Phone Number Is Not Valid");
                }//phNo Verification
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }//val

//    private class CountriesAsyncRunner extends AsyncTask<String, String, String> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//        @Override
//        protected String doInBackground(String... params) {
//            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allCountryList,"GET",null);
//            Log.i("URl  1--> ",ApisHelper.allCountryList);
//            return content;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            Log.i("CountriesonPostexc:",result);
//            int k=0;
//            try {
////                JSONObject json=new JSONObject(result);
////                String res=json.getString("result");
//                final JSONArray arry=new JSONArray(result);
//
//                final String[] arr_country = new String[arry.length()];
//                final String[] arr_country_codes= new String[arry.length()];
//                for(int i = 0; i < arry.length(); i++){
//                    JSONObject jsonCountry=arry.getJSONObject(k);
//                    arr_country[i] = jsonCountry.getString("name");
//                    arr_country_codes[i] = jsonCountry.getString("code");
//                    k++;
//                }
//                ArrayList<String> ar = new ArrayList<String>(Arrays.asList(arr_country));
//               ar.add(0,"Select");
//                SCountry.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,ar));
//
//                SCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        String spinnerItem=parent.getItemAtPosition(position).toString();
//
//                        for(int i = 0; i < arry.length(); i++){
//                            if(spinnerItem.equalsIgnoreCase(arr_country[i])){
//                                PD_CouuntryCode=arr_country_codes[i];
//
//                                StatesAsyncRunner runner = new StatesAsyncRunner(arr_country_codes[i]);
//                                runner.execute();
//                            }
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        Toast.makeText(getActivity(),"nothing Selected", Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            catch (Exception e){e.printStackTrace();}
//        }//onPostExecute
//    }//CountriesAsyncRunner
    private class StatesAsyncRunner extends AsyncTask<String, String, String> {

        ProgressDialog pDialog;



        @Override
        protected void onPreExecute() {
  //          pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
//            country_code=SessinSave.getsessin("country_code",getActivity());
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allStateList1,"GET",null);
         //   String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/profile/allStateList/1","GET",null);

            Log.i("URl  2 ",content);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
         //   if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("States onPostexc:",result);
            int k=0;
            try {
//                JSONObject json=new JSONObject(result);
//                String res=json.getString("result");
                final JSONArray arry=new JSONArray(result);
                final String[] arr_states = new String[arry.length()];
                final String[] arr_states_codes = new String[arry.length()];
                final String[] arr_states_ctrCodes = new String[arry.length()];

                for(int i = 0; i < arry.length(); i++){
                    JSONObject jsonCountry=arry.getJSONObject(k);
                    arr_states[i] = jsonCountry.getString("name");
                    arr_states_codes[i] = jsonCountry.getString("code");
                    arr_states_ctrCodes[i] = jsonCountry.getString("countryCode");
                    k++;
                }
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr_states));
                SpState.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arrayList));
                SpState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String spinnerItem=parent.getItemAtPosition(position).toString();

                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_states[i])){
                                PD_stateCode=arr_states_codes[i];
                                DistAsyncRunner runner = new DistAsyncRunner("1",arr_states_codes[i]);
                                runner.execute();
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getActivity(),"nothing Selected", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception e){e.printStackTrace();}
        }//onPostExecute
    }//StatesAsyncRunner
    private class DistAsyncRunner extends AsyncTask<String, String, String> {
        private final String countryCode,stateCode;
        private ProgressDialog pDialog;

        public DistAsyncRunner(String countryCode, String arr_states_code) {
            this.countryCode=countryCode;
            this.stateCode=arr_states_code;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(""+ApisHelper.allDistrictList+"/"+stateCode+"/"+countryCode+"","GET",null);
            Log.i("URL 3 ",""+ApisHelper.allDistrictList+"/"+stateCode+"/"+countryCode+"");
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("Dist onPostexc:",result);
            int k=0;
            try {
//                JSONObject json=new JSONObject(result);
//                String res=json.getString("result");
                final JSONArray arry=new JSONArray(result);
                final String[] arr_dist = new String[arry.length()];
                final String[] arr_dist_codes = new String[arry.length()];
                final String[] arr_dist_states = new String[arry.length()];
                final String[] arr_dist_country = new String[arry.length()];

                for(int i = 0; i < arry.length(); i++){
                    JSONObject jsonCountry=arry.getJSONObject(k);
                    arr_dist[i] = jsonCountry.getString("name");
                    arr_dist_codes[i] = jsonCountry.getString("code");
                    arr_dist_states[i] = jsonCountry.getString("stateCode");
                    arr_dist_country[i] = jsonCountry.getString("countryCode");
                    k++;
                }
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr_dist));
                arrayList.add(0,"Select");
                SpDist.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arrayList));
                SpDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String spinnerItem=parent.getItemAtPosition(position).toString();
                        for(int i = 0; i < arry.length(); i++){
                            if(spinnerItem.equalsIgnoreCase(arr_dist[i])){
                                PD_distCode=arr_dist_codes[i];
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getActivity(),"nothing Selected", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception e){e.printStackTrace();}
        }//onPostExecute
    }//DistAsyncRunner

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;
        String fullName,passWord,mobNo,state,dist;
        JSONObject permanentAddress= null;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");

            fullName=   Et_fullName.getText().toString();
            passWord=Et_password.getText().toString();
            mobNo=Et_mobNumb.getText().toString();
            state=SpState.getSelectedItem().toString();
            dist=SpDist.getSelectedItem().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;

            try{
                permanentAddress=new JSONObject();
                permanentAddress.accumulate("country","1");
                permanentAddress.accumulate("state",PD_stateCode);
                permanentAddress.accumulate("district",PD_distCode);
                obj= new JSONObject();
                obj.accumulate("fullName",fullName);
                obj.accumulate("password",passWord);
                obj.accumulate("gender",selectedGender);
                obj.accumulate("mobileNo",mobNo);
                obj.accumulate("profession",selectedProfession);
                obj.accumulate("permanentAddress",permanentAddress);

                Log.i("SignUp Obj==>",obj.toString());

            }catch (Exception e){

            }
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.otp,"POST",obj);
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            if(result.equalsIgnoreCase("true")){
                Toast.makeText(getActivity(),"Please Wait...", Toast.LENGTH_LONG).show();
                getActivity().getFragmentManager().beginTransaction().replace(R.id.login_frame, OTPRegdCheck.newInstance(fullName, passWord,selectedGender,  mobNo,selectedProfession,permanentAddress)).commit();
            }else if(result.equalsIgnoreCase("false")){
                Toast.makeText(getActivity(),"Invalid", Toast.LENGTH_LONG).show();
            }

         if(progressDialog.isShowing()){   progressDialog.dismiss();    }
        }//onPostExecute


    }//AsyncTask class







}
