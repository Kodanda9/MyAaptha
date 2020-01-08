package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.QualificationsModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DoctorDetailsFragmen extends android.app.Fragment {
private Spinner experience,specialization,qualification,city;

    private View rootView;
    private ArrayList<String> scripts;
    private Button update,save;
    private EditText Name,MedCouncNo,Stateofmc,UniversityName,QualifiedYr,Yor,Locality,About;
    private JSONArray array;
ArrayList<QualificationsModel>mdl;
    private String qualname;
    private int spinnerTextSize;
    private String qualificationId;
    private ArrayList<String> namesList,idsList;


    public DoctorDetailsFragmen() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }

        try {
            rootView = inflater.inflate(R.layout.doctor_details_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
       //     button=(Button)rootView.findViewById(R.id.button);

           Name=(EditText)rootView.findViewById(R.id.name);
           MedCouncNo=(EditText)rootView.findViewById(R.id.medcounno);
           Stateofmc=(EditText)rootView.findViewById(R.id.stateofmc);
           UniversityName=(EditText)rootView.findViewById(R.id.universityname);
           QualifiedYr=(EditText)rootView.findViewById(R.id.qualifiedyr);
           Yor=(EditText)rootView.findViewById(R.id.yor);
            Yor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    { AppHelper.getPerfectDate(Yor,getActivity());
                    }
                }
            });
           Locality=(EditText)rootView.findViewById(R.id.locality);
           About=(EditText)rootView.findViewById(R.id.about);
           experience=(Spinner)rootView.findViewById(R.id.experience);
        specialization=(Spinner)rootView.findViewById(R.id.specialization);
        qualification=(Spinner)rootView.findViewById(R.id.qualification);
        city=(Spinner)rootView.findViewById(R.id.city);
  save=(Button)rootView.findViewById(R.id.save);
  update=(Button)rootView.findViewById(R.id.update);
            QualifiedYr.setOnClickListener(
                    new View.OnClickListener() {
                @Override
                public void onClick(View view) { AppHelper.getPerfectDate(QualifiedYr,getActivity());
                }
            });
            final QualificationsModel model=new QualificationsModel();
            SpecializationRunner runner=new SpecializationRunner();   runner.execute();
            QualificationRunner runner1=new QualificationRunner();       runner1.execute();
            CityRunner runner2=new CityRunner();            runner2.execute();
            UpdateDocData runner3= new UpdateDocData();            runner3.execute();

      /* save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SaveRunner runner=new SaveRunner();
        runner.execute();
    }
});*/
        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }


    private class SpecializationRunner extends AsyncTask<String,String,String> {
        private String special;
        private java.lang.String strDrgType;


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.specializations,"GET",null);
        //    String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest /specializations","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("=====>",s);
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(s);
            while (m.find()) {

                special=m.group(1);

            }
             scripts = new ArrayList<String>();


            scripts.add(special);
            specialization.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,scripts));

        }
    }

    private class QualificationRunner extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.qualifications,"GET",null);
         //   String Content=AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest /qualifications","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
             JSONArray array=new JSONArray(s);
              namesList = new ArrayList<String>();
              idsList = new ArrayList<String>();
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    namesList.add(object.getString("qualification"));
                    idsList.add(object.getString("id"));
                }
                namesList.add(0,"Select");
                idsList.add(0,"");
                qualification.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,namesList));


                qualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                        String SelfullName=parent.getItemAtPosition(position).toString();

                        for(int j = 0; j <namesList.size(); j++){
                            if(SelfullName.equalsIgnoreCase(namesList.get(j))){
                                qualificationId = idsList.get(j);
                            }
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });




            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private class CityRunner extends AsyncTask<String,String,String> {
        private String[] arr_city;
        private String[] arr_city_codes;
        private ArrayList<String> citylist;



        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.locations,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array=new JSONArray(s);
                arr_city = new String[array.length()];
                arr_city_codes= new String[array.length()];
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    arr_city[i]=object.getString("location");
                    arr_city_codes[i]=object.getString("id");

                }
                citylist = new ArrayList<String>(Arrays.asList(arr_city));
                city.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,citylist));
//

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class SaveRunner extends AsyncTask<String,String,String> {
        String name,qual,spec,exp,medno,smc,university,qualifiedyr,yor,citys,locality,abouturself;
        @Override
        protected void onPreExecute() {
            name=Name.getText().toString();
            medno=MedCouncNo.getText().toString();
            smc=Stateofmc.getText().toString();
            university=UniversityName.getText().toString();
            yor=Yor.getText().toString();
            abouturself=About.getText().toString();
            locality=Locality.getText().toString();
            qualifiedyr=QualifiedYr.getText().toString();
            citys=city.getSelectedItem().toString();
            exp=experience.getSelectedItem().toString();
            spec=specialization.getSelectedItem().toString();
            qual=qualification.getSelectedItem().toString();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("name",name);
                object.accumulate("medicalCouncilNo",medno);
                object.accumulate("stateMedicalCouncil",smc);
                object.accumulate("universityName",university);
                object.accumulate("docRegYear",yor);
                object.accumulate("description",abouturself);
                object.accumulate("localities",locality);
                object.accumulate("qualificationYear",qualifiedyr);
                object.accumulate("location",citys);
                object.accumulate("experience",exp);
                object.accumulate("specilization",spec);
                object.accumulate("qulification",qual);


            } catch (JSONException e) {
                e.printStackTrace();
            }
   String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.adddoctorsave+ SessinSave.getsessin("profile_id",getActivity()),"POST",object);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("result",s);
        }
    }

    private class UpdateDocData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.updateDoctor+SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("s",s);
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
                Name.setText(object.getString("name"));

                for (int i=0 ; i<idsList.size();i++){
                    if(object.getString("qulification").equalsIgnoreCase(idsList.get(i))){
                        qualification.setSelection(AppHelper.setValueToSpinner(qualification,namesList.get(i)));
                    }
                }


                Locality.setText(object.getString("localities"));
                city.setSelection(AppHelper.setValueToSpinner(city,object.getString("location")));
                specialization.setSelection(AppHelper.setValueToSpinner(specialization,object.getString("specilization")));

                experience.setSelection(AppHelper.setValueToSpinner(experience,object.getString("experience")));
                About.setText(object.getString("description"));
                MedCouncNo.setText(object.getString("medicalCouncilNo"));
                Stateofmc.setText(object.getString("stateMedicalCouncil"));
                QualifiedYr.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(object.getString("qualificationYear")));
                UniversityName.setText(object.getString("universityName"));
                Yor.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(object.getString("docRegYear")));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
