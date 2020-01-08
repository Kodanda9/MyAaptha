package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.MainNavigationActivity;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class    LoginFragment extends Fragment {


    private View rootView;
    private TextView TvForgetPw;
    private EditText et_pswrd,et_mobNumb;
    private View localView;
    private Dialog mDialog;
    private Typeface fontAwesomeFont;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {   parent.removeView(rootView);    }
        }//if
        try{
            rootView=inflater.inflate(R.layout.login_fragment,container,false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());


            fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
            TextView TvCP=(TextView)rootView.findViewById(R.id.tvfoiUn);            TvCP.setTypeface(fontAwesomeFont);
            TextView TvPSW=(TextView)rootView.findViewById(R.id.tvfoiPsw);          TvPSW.setTypeface(fontAwesomeFont);



            TvForgetPw=(TextView)rootView.findViewById(R.id.tvforgetPassword);
            et_mobNumb=(EditText)rootView.findViewById(R.id.et_loginmobNum);
            et_pswrd=(EditText)rootView.findViewById(R.id.et_loginpassword);
            TextView tv_signUpClick=(TextView)rootView.findViewById(R.id.signUpClick);
            tv_signUpClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction().add(R.id.login_frame,new UserSignUpFragment()).commit();
                    getActivity().getFragmentManager().beginTransaction().remove(LoginFragment.this).commit();
                }
            });

            Button Login_button=(Button) rootView.findViewById(R.id.login_button);
            Login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validations();
                }
            });
            TvForgetPw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    forgetPopUp();
//                    getFragmentManager().beginTransaction().replace(R.id.registration_activity, new ForgetPasswordFragment()).commit();
                }
            });
//            TvLogIn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//Intent in =new Intent(getActivity(),ProfileDetailsActivity.class);    startActivity(in);
//                }
//            });
            AppHelper.setupHideleyboard(rootView,getActivity());
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }//onCreate

    private void forgetPopUp() {
        localView = View.inflate(getActivity(),R.layout.forget_passwd_otpfield, null);
        AppHelper.setupHideleyboard(localView,getActivity());
        localView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.zoom_in_enter));
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

        final EditText ETMobNo = (EditText) this.mDialog.findViewById(R.id.etMobNo);
        Button Btn_Submit = (Button) this.mDialog.findViewById(R.id.fpwButton);
        Btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETMobNo.getText().toString().isEmpty()){
                    ETMobNo.setError("Please Enter Mobile Number");
                }else{
                    AsyncTaskRunner runner = new AsyncTaskRunner(ETMobNo.getText().toString());
                    runner.execute();
                }
            }
        });
        ImageView imgClose  = (ImageView) this.mDialog.findViewById(R.id.closeDialog);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDialog.isShowing()){ mDialog.dismiss();   }
            }
        });


    }

    private void validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.login_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.login_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.login_comptypes_array);
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

                if(AppHelper.phoneNumberValidation(et_mobNumb.getText().toString())){

//                            if(TextUtils.isEmpty(et_pswrd.getText().toString()) || et_pswrd.getText().toString().length() <6 )
//                            { et_pswrd.setError("You must have 6 characters in your password");
//                            }else{
                    LoginAsyncTask runner=new LoginAsyncTask();
                    runner.execute();
//                            }
                }else{
                    Toast.makeText(getActivity(),"Phone number is not valid", Toast.LENGTH_LONG).show();
                    et_mobNumb.setError("Phone Number Is Not Valid");
                }//phNo Verification
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }//validations

    private class LoginAsyncTask extends AsyncTask<String, String, String> {

        private String mobNo,passWord;
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            mobNo=   et_mobNumb.getText().toString();
            passWord=   et_pswrd.getText().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("mobileNo",mobNo);
                obj.accumulate("password",passWord);
            }catch (Exception e){
e.printStackTrace();
            }
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.profileHomeRest,"POST",obj);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("Login OnPost:-->",result);
            progressDialog.dismiss();
            try {


                JSONArray mainArray=new JSONArray(result);
                Log.i("mainArray-->",mainArray.toString());

                JSONObject obj1=mainArray.getJSONObject(0);
                if(obj1.getString("Status").equalsIgnoreCase("Success")){
                    Toast.makeText(getActivity(),"Login Succesfully Done.",Toast.LENGTH_LONG).show();

//                    getActivity().getFragmentManager().beginTransaction().remove(LoginFragment.this).commit();
//                    getActivity().getFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new DrHomeFragment()).commit();
                    JSONObject obj2 = mainArray.getJSONObject(1);
                    SessinSave.saveSession("LogInObj",obj2.toString(),getActivity());
                    SessinSave.saveSession("UserTypes",mainArray.getJSONArray(2).toString(),getActivity());

                    Log.i("!#--UserTypes->",mainArray.getJSONArray(2).toString());

                    SessinSave.saveSession("profile_id",obj2.getString("profileId"),getActivity());
                    SessinSave.saveSession("MobileNo",obj2.getString("mobileNo"),getActivity());
                    SessinSave.saveSession("FullName",obj2.getString("fullName"),getActivity());
                    SessinSave.saveSession("profession",obj2.getString("profession"),getActivity());
                    Intent in =new Intent(getActivity() , MainNavigationActivity.class);
                    startActivity(in);


                    getActivity().finish();

                }else  if(obj1.getString("Status").equalsIgnoreCase("Error")){
                    Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                }

//                JSONObject mainObj=new JSONObject(result);
//                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//
//
//
////                    SessinSave.saveSession("profile_id",obj.getString("profileId"),getActivity());
////                    SessinSave.saveSession("MobileNo",obj.getString("mobileNo"),getActivity());
////                    SessinSave.saveSession("FullName",obj.getString("fullName"),getActivity());
////                    Toast.makeText(getActivity(),SessinSave.getsessin("profileId",getActivity()),Toast.LENGTH_LONG).show();
//
//                    Intent in =new Intent(getActivity(), MainNavigationActivity.class);
//                    startActivity(in);
//                    getActivity().finish();
//                    Toast.makeText(getActivity(),"Login Successfull", Toast.LENGTH_LONG).show();
//                }else  if(mainObj.getString("status").equalsIgnoreCase("ERROR")){
//                    Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
//                }
            }catch (Exception e){
                e.printStackTrace();
            }



        }//onPostExecute


    }//LoginAsyncTask class

    private class AsyncTaskRunner  extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        String mobNumb;
        public AsyncTaskRunner(String s) {
            mobNumb=s;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "ProgressDialog","loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("mobileNo",mobNumb);
            }catch (Exception e){
                e.printStackTrace();
            }
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.forgotNextMyAaptha ,"POST",obj);
            return content;
        }//doInBackground
        @Override
        protected void onPostExecute(String result) {
            Log.i("LogInRes -->",result);
            try {
                JSONObject mainObj=new JSONObject(result);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    Intent in =new Intent( getActivity(),CheckOTP.class);
//                    in.putExtra("mobNo",mobNumb);
//                    startActivity(in);
//                    getActivity().finish();
                }else{  Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }//onPostExecute
    }//AsyncTaskRunner




}
