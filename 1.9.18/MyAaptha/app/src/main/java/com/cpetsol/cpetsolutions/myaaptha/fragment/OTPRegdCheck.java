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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;

import org.json.JSONObject;
public class OTPRegdCheck extends Fragment {


    private static String password,mobileNo,fullName,selectedGender,selectedProfession;
    private static JSONObject permanentAddress;
    private View rootView;
    private Button OTPButton;
    private EditText EtOTP;
    private TextView TvResend;


    public OTPRegdCheck() {
    }
    public static Fragment newInstance(String fName, String psWord, String sGender, String mobNo, String prof, JSONObject pAddress) {
        OTPRegdCheck frag=new OTPRegdCheck();
        fullName =fName;
        password=psWord;
        selectedGender=sGender;
        mobileNo=mobNo;
        selectedProfession=prof;
        permanentAddress=pAddress;
    return  frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {
                parent.removeView(rootView);
            }
        }//if
        try{
            rootView=inflater.inflate(R.layout.fragment_otpregd_check,container,false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());

            TextView TvMsg=(TextView)rootView.findViewById(R.id.mobNoEnds);

           TvResend=(TextView) rootView.findViewById(R.id.tv_resend);
            EtOTP=(EditText)rootView.findViewById(R.id.et_otp_field);
            OTPButton=(Button)rootView.findViewById(R.id.otp_button);
            OTPButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(EtOTP.getText().toString().length()==6)
                    {
                        AsyncTaskRunner runner = new AsyncTaskRunner(fullName,password,mobileNo);
                        runner.execute();
                    }else{
                        EtOTP.setError("6 Digits mandatory");
                    }

                }
            });
            TvResend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ResendAsyncTask runner=new ResendAsyncTask();
                    runner.execute();
                }
            });
//            Intent intent=getIntent();
//            fullname = intent.getStringExtra("fullname");
//            password = intent.getStringExtra("password");
//            confirmPassword = intent.getStringExtra("confirmPassword");
//            mobileNo = intent.getStringExtra("mobileNo");
            TvMsg.setText("OTP sent to your registered mobile number ends with"+mobileNo.substring(mobileNo.length()-4,mobileNo.length()));


        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }//onCreate

    private class AsyncTaskRunner  extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        String fName,psword,mobNumb,strOTP;
        public AsyncTaskRunner(String fullname, String password , String mobileNo) {
            this.fName=fullname;
            this.psword=password;
            this.mobNumb=mobileNo;
        }
        @Override
        protected void onPreExecute() {
            strOTP= EtOTP.getText().toString();
            progressDialog = ProgressDialog.show(getActivity(), "ProgressDialog","loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{

                obj= new JSONObject();
                obj.accumulate("fullName",fName);
                obj.accumulate("password",psword);
                obj.accumulate("gender",selectedGender);
                obj.accumulate("mobileNo",mobNumb);
                obj.accumulate("profession",selectedProfession);
                obj.accumulate("permanentAddress",permanentAddress);

                Log.i("SignUp Obj==>",obj.toString());


            }catch (Exception e){
                e.printStackTrace();
            }

            String content= AsyncTaskHelper.makeServiceCall( ApisHelper.checkotp+"/"+strOTP,"POST",obj);
            return content;

        }//doInBackground


        @Override
        protected void onPostExecute(String result) {

            if(result.equalsIgnoreCase("true")){
                Toast.makeText(getActivity(),"Succesfully Registered", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().add(R.id.login_frame,new LoginFragment() ).commit();
                getActivity().getFragmentManager().beginTransaction().remove(OTPRegdCheck.this).commit();
            }else if(result.equalsIgnoreCase("false")){
                Toast.makeText(getActivity(),"Invalid", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }//onPostExecute

    }//AsyncTaskRunner


    private class ResendAsyncTask  extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        String strOTP;
        @Override
        protected void onPreExecute() {
            strOTP= EtOTP.getText().toString();
            progressDialog = ProgressDialog.show(getActivity(), "Please wait", "loading...");
        }
        @Override
        protected String doInBackground(String... params) {

            String content= AsyncTaskHelper.makeServiceCall(""+ApisHelper.otp+"/"+mobileNo,"GET",null);
            Log.i("content-->",content);

            return content;

        }//doInBackground


        @Override
        protected void onPostExecute(String result) {
            Log.i("OnPost-->",result);
            if(result.equalsIgnoreCase("true")){
                Toast.makeText(getActivity(),"Valid", Toast.LENGTH_LONG).show();
            }else if(result.equalsIgnoreCase("false")){
                Toast.makeText(getActivity(),"Invalid", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }//onPostExecute

    }





}
