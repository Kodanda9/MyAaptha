package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMobileNoFragment extends Fragment {


    private View rootView;
    private EditText et_mobNumb;
    private Dialog mDialog;

    public ChangeMobileNoFragment() {
        // Required empty public constructor
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
            rootView=inflater.inflate(R.layout.change_mobile_no_fragment,container,false);
//            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());

            et_mobNumb=(EditText)rootView.findViewById(R.id.et_mobNumbid);

            Button Login_button=(Button) rootView.findViewById(R.id.btnSubmit);
//            BlockButton btn=(BlockButton)rootView.findViewById(R.id.login_button);
            Login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et_mobNumb.getText().toString().isEmpty()){
                        et_mobNumb.setError("Please Enter Mobile Number");
                    }else{
                        AsyncTaskRunner runner = new AsyncTaskRunner(et_mobNumb.getText().toString());
                        runner.execute();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return rootView;
    }//onCreateView

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String mobNo,oldMobNo;
        public AsyncTaskRunner(String s) {
            mobNo=s;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            oldMobNo= SessinSave.getsessin("MobileNo",getActivity());
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("oldMobileNo",oldMobNo);
                obj.accumulate("mobileNo",mobNo);
            }catch (Exception e){
            }

            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.generateOtp_changeMobileNo,"POST",obj);
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
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
            }catch (Exception e){
                e.printStackTrace();
            }
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.checkOtp_changeMobileNo+"/"+ SessinSave.getsessin("profile_id",getActivity())+"","POST",obj);
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
