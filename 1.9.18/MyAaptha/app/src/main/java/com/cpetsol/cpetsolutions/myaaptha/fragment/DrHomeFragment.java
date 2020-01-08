package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DoctorsDataAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.model.DoctorsDataModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
public class DrHomeFragment extends Fragment {



    private static View rootView;
    private ListView listView1;
    private Spinner Sloc;
    private AutoCompleteTextView ET_locality,ET_spec;
    private LinearLayout EtrLL,EditLL;
    private float screenWidth,screenHeight;
    private ImageView imgUp;
    private TextView tvEditLocation,tvEditLocality,tvEditSpec;
    private int spinnerTextSize;
    private FloatingActionButton FABFilters;


    public DrHomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.dr_home_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));

            TextView Tv=(TextView)rootView.findViewById(R.id.tv);
            Log.i("11111", String.valueOf(Tv.getTextSize()));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            AppHelper.setupHideleyboard(rootView,getActivity());
            spinnerTextSize= (int) (getResources().getDimension(R.dimen.font_size) / getResources().getDisplayMetrics().density);

//            boolean online= AppHelper.isNetworkAvaliable(getActivity());
//            if(online){
                init();
//            }else{
//                AppHelper.displayErrorPopUp( AppHelper.noInternetConnMsg, AppHelper.noInternetConnContext ,  getActivity());
//            }

        } catch (InflateException e) {
            e.printStackTrace();
        }

        return rootView;
    }
    private void init() {


        listView1 = (ListView) rootView.findViewById(R.id.lv);

        Sloc=(Spinner)rootView.findViewById(R.id.main_loc);
        ET_locality=(AutoCompleteTextView)rootView.findViewById(R.id.et_locality);
        ET_spec=(AutoCompleteTextView)rootView.findViewById(R.id.et_spec);


//        Toast.makeText(getActivity()," S Height"+ET_spec.getTextSize(),Toast.LENGTH_LONG).show();

        tvEditLocation=(TextView)rootView.findViewById(R.id.edittLocation);
        tvEditLocality=(TextView)rootView.findViewById(R.id.editLocality);
        tvEditSpec=(TextView)rootView.findViewById(R.id.editSpec);
        EtrLL=(LinearLayout)rootView.findViewById(R.id.selLL);
        EditLL=(LinearLayout)rootView.findViewById(R.id.editLL);
        EditLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView1.setSelection(0);
            }
        });

        imgUp=(ImageView)rootView.findViewById(R.id.imgBottom);
        FABFilters=(FloatingActionButton)rootView.findViewById(R.id.fabFilter);


        imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView1.setSelection(0);
            }
        });

        ET_locality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    if((SessinSave.getsessin("MainLocation",getActivity()).isEmpty() ) ){
                        Toast.makeText(getActivity(),"Please Select Location", Toast.LENGTH_LONG).show();
                    }else{
                        LocalityAsyncTask runner=new LocalityAsyncTask(s.toString());
                        runner.execute();
                    }
                }
            }
        });

        ET_spec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    if((SessinSave.getsessin("MainLocation",getActivity()).isEmpty() ) ){
                     Toast.makeText(getActivity(),"Please Select Location",Toast.LENGTH_LONG).show();
                    }else{
                       SpecAsyncTask runner=new SpecAsyncTask(s.toString());
                       runner.execute();
                   }

                }
            }
        });


        Button btn=(Button)rootView.findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validations();
            }
        });

        LocationAsyncTask runner=new LocationAsyncTask();   runner.execute();

        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0) {
                    // check if we reached the top or bottom of the list
                    View v = listView1.getChildAt(0);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        imgUp.setVisibility(View.INVISIBLE);
                        // reached the top: visible header and footer
                        Log.i("OnScroll==0 ", "top reached");
                        EtrLL.setVisibility(View.VISIBLE);
                        EditLL.setVisibility(View.GONE);
//                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
                    }
                } else if (totalItemCount - visibleItemCount == firstVisibleItem) {
                    View v = listView1.getChildAt(totalItemCount - 1);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the bottom: visible header and footer
                        Log.i("Bottom", "bottom reached!");
                        tvEditLocation.setText(Sloc.getSelectedItem().toString());
                        tvEditLocality.setText(ET_locality.getText().toString());
                        tvEditSpec.setText(ET_spec.getText().toString());

                        imgUp.setVisibility(View.VISIBLE);

                        EtrLL.setVisibility(View.GONE);
                        EditLL.setVisibility(View.VISIBLE);
//                        getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
                    }
                } else if (totalItemCount - visibleItemCount > firstVisibleItem){
                    tvEditLocation.setText(Sloc.getSelectedItem().toString());
                    tvEditLocality.setText(ET_locality.getText().toString());
                    tvEditSpec.setText(ET_spec.getText().toString());

                    // on scrolling
                    EtrLL.setVisibility(View.GONE);
                    EditLL.setVisibility(View.VISIBLE);
//                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
                    Log.i("scroll", "on scroll");
                }
            }
        });

        DoctordListAsyncTask runner1=new DoctordListAsyncTask();
        runner1.execute();

        FABFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFiltersBottomView();
            }
        });


    }//init

    private void ShowFiltersBottomView() {
try {

}catch (Exception e){e.printStackTrace();}
    }

    private void Validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.main_doctors_data_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.main_doctors_data_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.main_doctors_data_comptypes_array);
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
            boolean isValidData = helper.validateData(getActivity(), aList, rootView);
            if (!isValidData) {
                return;
            }else{
                DoctordListAsyncTask runner=new DoctordListAsyncTask();
                runner.execute();
            }
        }catch (Exception e){
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in Validations @ HomeFragment");
        }


    }//submit
    public class LocationAsyncTask extends AsyncTask<String, String, String> {
        String stateCode;
        JSONArray array;
        String[] arr_state,arr_state_codes;
        //        private  ProgressDialog pDialog;
        ArrayList<String> Locationslist;
        private String LocationName;

        @Override
        protected void onPreExecute() {
//            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
//            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.drLocations,"GET",null);

//            Log.i("Api-->",ApisHelper.drLocations);
//            Log.i("content",content);
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.locations,"GET",null);
       //     String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/rest/locations","GET",null);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("Drs  onPost:",s);

            super.onPostExecute(s);

            try {
                array=new JSONArray(s);
                arr_state = new String[array.length()];
                arr_state_codes= new String[array.length()];

                for(int i = 0; i < array.length(); i++){
                    JSONObject jsonCountry=array.getJSONObject(i);
                    arr_state[i] = jsonCountry.getString("location");
                    arr_state_codes[i] = jsonCountry.getString("id");
                }
                Locationslist = new ArrayList<String>(Arrays.asList(arr_state));

            Sloc.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Locationslist));
//            for(int i = 0; i < array.length(); i++){
//                if("5".equalsIgnoreCase(arr_state_codes[i])){
//                    Sloc.setSelection(AppHelper.setValueToSpinner(Sloc,arr_state[i]));
//                }
//            }

                Sloc.setSelection(AppHelper.setValueToSpinner(Sloc,"Hyderabad"));
            }catch (Exception e){
                e.printStackTrace();
            }

            Sloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView)parent.getChildAt(0)).setTextSize(spinnerTextSize);
                    LocationName=parent.getItemAtPosition(position).toString();
                    Log.i("state Name:-->",LocationName);

                    for(int i = 0; i <array.length(); i++){
                        if(LocationName.equalsIgnoreCase(arr_state[i])){
//                            Toast.makeText(getActivity(),arr_state_codes[i],Toast.LENGTH_LONG).show();
                            SessinSave.saveSession("MainLocation",arr_state_codes[i],getActivity());
                            LocalityAsyncTask runner=new LocalityAsyncTask(arr_state_codes[i]);
                            runner.execute();
                        }
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }//AddEduAsyncTask
    public class LocalityAsyncTask extends AsyncTask<String, String, String> {
        private final String auto;
        private String LocId=SessinSave.getsessin("MainLocation",getActivity());

        public LocalityAsyncTask(String s) {
            this.auto=s;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = AsyncTaskHelper.makeServiceCall(""+ApisHelper.drlocalitiesSearch+"?term="+auto+"&location=" + LocId + "", "GET", null);
//            String content = AsyncTaskHelper.makeServiceCall("http://cpetsol.com/pp/rest/drlocalitiesSearch"+"?term="+auto+"&location=" + LocId + "", "GET", null);

            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("States onPost:",s);
            super.onPostExecute(s);

            ArrayList<String> scripts = new ArrayList<String>();
            try {
                JSONArray array=new JSONArray(s);
                Log.i("array:=>",array.toString());
                for (int i = 0; i < array.length(); i++){
                    JSONObject cityObj = array.getJSONObject(i);
                    String cityName=cityObj.getString("localityName");
                    scripts.add(cityName);
                }
            }catch (Exception e){
                e.printStackTrace();
//                ExceptionHandler handler=new ExceptionHandler(getActivity());
//                handler.setExc("Exc in LocalityAsyncTask @ HomeFragment");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, scripts);
            ET_locality.setThreshold(0);
            ET_locality.setAdapter(arrayAdapter);



//            DoctordListAsyncTask runner=new DoctordListAsyncTask();
//            runner.execute();


        }
    }//AddEduAsyncTask
    public class SpecAsyncTask extends AsyncTask<String, String, String> {
        private final String auto;
        String LocId=SessinSave.getsessin("MainLocation",getActivity());
//        private ProgressDialog pDialog;

        public SpecAsyncTask(String s) {
            this.auto=s;
        }

        @Override
        protected void onPreExecute() {
//            pDialog=ProgressDialog.show(getActivity(),"Please wait...","Loading");
        }

        @Override
        protected String doInBackground(String... strings) {//Specilizatiion o/p is in list ie Pending here
            String content = AsyncTaskHelper.makeServiceCall(""+ApisHelper.specilizationSearch+"?term="+auto, "GET", null);
//            String content = AsyncTaskHelper.makeServiceCall("http://cpetsol.com/pp/rest/specilizationSearch"+"?term="+auto, "GET", null);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){pDialog.dismiss();}
            Log.i("Spec onPost:",s);
            super.onPostExecute(s);

            ArrayList<String> scripts = new ArrayList<String>();
            try {
                JSONArray array=new JSONArray(s);
                Log.i("array:=>",array.toString());
                for (int i = 0; i < array.length(); i++){
                    JSONObject cityObj = array.getJSONObject(i);
                    String cityName=cityObj.getString("specialization");
                    scripts.add(cityName);
                }
            }catch (Exception e){
                e.printStackTrace();
//                ExceptionHandler handler=new ExceptionHandler(getActivity());
//                handler.setExc("Exc in SpecAsyncTask @ HomeFragment");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, scripts);
            ET_spec.setThreshold(0);
            ET_spec.setAdapter(arrayAdapter);
        }
    }//AddEduAsyncTask

    public class DoctordListAsyncTask extends AsyncTask<String, String, String> {
        private ArrayList<DoctorsDataModel> rowItems;
        private ProgressDialog pDialog;
        private String LocId=SessinSave.getsessin("MainLocation",getActivity());
        private String locality,spec;

        @Override
        protected void onPreExecute() {
            locality= ET_locality.getText().toString();
            spec= ET_spec.getText().toString();
     //       pDialog= ProgressDialog.show(getActivity(),"Please wait...","Loading");
        }

        @Override
        protected String doInBackground(String... strings) {
    //        String content = AsyncTaskHelper.makeServiceCall("http://localhost:8080/pp/doctorsData2?locations=1&specializations=&localities=", "GET", null);
         String content = AsyncTaskHelper.makeServiceCall(ApisHelper.drdoctorsData+"?locations="+LocId+"&specializations="+spec+"&localities="+locality+"", "GET", null);

          Log.i("=====>",content)  ;
            return content;
        }//
        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){pDialog.dismiss();}
          Log.i("States onPost:",s);
            super.onPostExecute(s);
            rowItems = new ArrayList<DoctorsDataModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){

                    for (int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        Log.i("Pos"+i,obj.toString());

                        DoctorsDataModel item = new DoctorsDataModel(obj.getString("doctorId"),obj.getString("name"),obj.getString("qulification"),obj.getString("experience"),obj.getString("localities"),obj.getString("specilization"),obj.getString("hospitalName"),obj.getString("clinicName"),obj.getString("price"),obj.getString("gender"),
                                obj.getString("sunday"),obj.getString("monday"),obj.getString("tuesday"),obj.getString("wednesday"),obj.getString("thursday"),obj.getString("friday"),obj.getString("saturday"),obj.getString("hospitalId"));

                        rowItems.add(item);
                    }

                }else{
                    Toast.makeText(getActivity(),"No Records Found", Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            DoctorsDataAdapter adapter = new DoctorsDataAdapter(getActivity(), rowItems);
            listView1.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }//AddEduAsyncTask


}
