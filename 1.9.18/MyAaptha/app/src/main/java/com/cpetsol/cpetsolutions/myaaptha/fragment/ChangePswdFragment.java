package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePswdFragment extends Fragment {


    private View rootView;
    private Typeface fontAwesomeFont;
    private EditText EtConfps,EtNewPsw,EtConfNPSW;
    private Dialog mDialog;

    public ChangePswdFragment() {
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
            rootView=inflater.inflate(R.layout.change_pswd_fragment,container,false);
//            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());

            fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
            PD_changepsw_Initialization();
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }//onCreate

    private void PD_changepsw_Initialization() {
//        TextView TvPhNo=(TextView)rootView.findViewById(R.id.tvPhNo);        TvPhNo.setTypeface(fontAwesomeFont);
        TextView TvCP=(TextView)rootView.findViewById(R.id.tvcp);            TvCP.setTypeface(fontAwesomeFont);
        TextView TvNP=(TextView)rootView.findViewById(R.id.tvNP);            TvNP.setTypeface(fontAwesomeFont);
        TextView TvCNP=(TextView)rootView.findViewById(R.id.tvCNP);          TvCNP.setTypeface(fontAwesomeFont);

//         EtMobNo=(EditText)rootView.findViewById(R.id.et_mobNumb);
        EtConfps=(EditText)rootView.findViewById(R.id.et_confps);
        EtNewPsw=(EditText)rootView.findViewById(R.id.et_newPsw);
        EtConfNPSW=(EditText)rootView.findViewById(R.id.et_confNewpsw);

        Button submitButton = (Button)rootView.findViewById(R.id.changePSWSubmit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EtNewPsw.getText().toString().equalsIgnoreCase(EtConfNPSW.getText().toString())) {
                    validations();
                }else{
                    Toast.makeText(getActivity(),"Passwords are not matching", Toast.LENGTH_LONG).show();
                }
            }
        });
    }//init

    private void validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.changepassword_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.changepassword_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.changepassword_comptypes_array);
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
//                if(AppHelper.phoneNumberValidation(EtMobNo.getText().toString())){

                if(TextUtils.isEmpty(EtNewPsw.getText().toString()) || EtNewPsw.getText().toString().length() <6 )
                {
                    EtNewPsw.setError("You must have 6 characters in your password");
                    EtConfNPSW.setError("You must have 6 characters in your password");
                }else{
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
                }
//                }else{Toast.makeText(getActivity(),"Phone number is not valid",Toast.LENGTH_LONG).show();
//                    Et_mobNumb.setError("Phone Number Is Not Valid");
//                }//phNo Verification
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }//validations

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String currentPSW,newPSW,confPassWord,mobNo ;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            mobNo= SessinSave.getsessin("MobileNo",getActivity());
            currentPSW=EtConfps.getText().toString();
            newPSW  =EtNewPsw.getText().toString();
            confPassWord=EtConfNPSW.getText().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("mobileNo",mobNo);
                obj.accumulate("currentPw",currentPSW);
                obj.accumulate("newPassword",newPSW);
                obj.accumulate("confirmPw",confPassWord);
                Log.i("OBJ-->",obj.toString());
            }catch (Exception e){
//                ExceptionHandler handler=new ExceptionHandler(getActivity());
//                handler.setExc("Exc in AsyncTaskRunner @ ChangePassword");
            }

            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.changePasswordNext+"/"+ SessinSave.getsessin("profile_id",getActivity())+"","POST",obj);
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            Toast.makeText(getActivity(),result, Toast.LENGTH_LONG).show();
            try {
                JSONObject mainObj=new JSONObject(result);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
                   OTPPopUp();
                    
                }else{
                    Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            if(result.equalsIgnoreCase("true")){
//                LL1.setVisibility(View.GONE);
//                LL2.setVisibility(View.VISIBLE);
//            }else if(result.equalsIgnoreCase("false")){
//                Toast.makeText(getActivity(),"Invalid",Toast.LENGTH_LONG).show();
//            }
            progressDialog.dismiss();
        }//onPostExecute


    }//AsuncTask class

    private void OTPPopUp() {
        View uploadLocalView = View.inflate(getActivity(), R.layout.check_otp_activity, null);
//        AppHelper.setupHideleyboard(uploadLocalView,getActivity());
        uploadLocalView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.zoom_in_enter));
        this.mDialog = new Dialog(getActivity(), R.style.NewDialog);
        this.mDialog.setContentView(uploadLocalView);
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

        final EditText ETOTP = (EditText) uploadLocalView.findViewById(R.id.et_otp_field);
        Button BtnSubmit = (Button) uploadLocalView.findViewById(R.id.otp_button);
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ETOTP.getText().toString().isEmpty()){
                    ETOTP.setError("Please Enter OTP");
                }else{
                    CheckOTPAsyncTask runner=new CheckOTPAsyncTask(SessinSave.getsessin("MobileNo",getActivity()),ETOTP.getText().toString());
                    runner.execute();
                }

            }
        });
    }//PopUp



    private class CheckOTPAsyncTask  extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        String mobNumb,otp;
        public CheckOTPAsyncTask(String mobNo, String s) {
            mobNumb=mobNo;
            otp=s;
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
                obj.accumulate("otp",otp);

                Log.i("Obj==>",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            String content= AsyncTaskHelper.makeServiceCall( ApisHelper.checkChangeOtp,"POST",obj);
            return content;
        }//doInBackground
        @Override
        protected void onPostExecute(String result) {
            Log.i("111111",result);
            Toast.makeText(getActivity(),result, Toast.LENGTH_LONG).show();
            try {
                JSONObject mainObj=new JSONObject(result);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    Intent in =new Intent( getActivity(),RegistrationActivity.class);
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
