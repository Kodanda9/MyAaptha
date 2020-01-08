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
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;

import org.json.JSONObject;

import java.util.ArrayList;

public class ContactUsFragment extends Fragment {
    private static View rootView;
    private EditText etName,etEmail,etMsg;
    private Button contact_submit;
    public ContactUsFragment() {
        // Required empty public constructor
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
            rootView = inflater.inflate(R.layout.contactus_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());

            etName=(EditText)rootView.findViewById(R.id.edName);
            etEmail=(EditText)rootView.findViewById(R.id.edEmail);
            etMsg=(EditText)rootView.findViewById(R.id.edmsg);
            TextView TvName=(TextView) rootView.findViewById(R.id.tvName);

//            if(TvName.getTextSize()==16){
//                Toast.makeText(getActivity(),TvName.getTextSize()+" dimensions",Toast.LENGTH_LONG).show();
//            }else if(TvName.getTextSize()==25){
//                Toast.makeText(getActivity(),TvName.getTextSize()+" large",Toast.LENGTH_LONG).show();
//            }else if(TvName.getTextSize()==20){
//                Toast.makeText(getActivity(),TvName.getTextSize()+" sw600",Toast.LENGTH_LONG).show();
//            }else {
//                Toast.makeText(getActivity(),etMsg.getTextSize()+" Other",Toast.LENGTH_LONG).show();
//            }

            contact_submit=(Button) rootView.findViewById(R.id.contact_submit);
            contact_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validations();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ ContactUsFragment");
        }
        return rootView;
    }

    private void validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.contactus_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.contactus_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.contactus_comptypes_array);
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
//                        View view1 = v.findViewById(android.R.id.content);
//                        View view1 = rootView.findViewById(R.id.contactus_fragment);
            boolean isValidData = helper.validateData(getActivity().getBaseContext(), aList, rootView);
            if (!isValidData) {
                return;
            }else{
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }

        }catch (Exception e){
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in validations @ ContactUsFragment");
        }

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String name,email,message ;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");

            name = etName.getText().toString();
            email  = etEmail.getText().toString();
            message = etMsg.getText().toString();
        }
        @Override
        protected String doInBackground(String... params) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("name",name);
                obj.accumulate("email",email);
                obj.accumulate("message",message);
                Log.i("OBJ-->",obj.toString());
            }catch (Exception e){
            }
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.contactUs,"POST",obj);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
           if(result.equalsIgnoreCase("true")){
               Toast.makeText(getActivity().getApplicationContext(),"Thank you for contacting us,we will solve your issue soon", Toast.LENGTH_LONG).show();
//               Toast.makeText(getActivity(),"Successfylly Submitted",Toast.LENGTH_LONG).show();
            }else if(result.equalsIgnoreCase("false")){
                Toast.makeText(getActivity(),"Invalid Credentials", Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
        }//onPostExecute


    }//AsuncTask class


}
