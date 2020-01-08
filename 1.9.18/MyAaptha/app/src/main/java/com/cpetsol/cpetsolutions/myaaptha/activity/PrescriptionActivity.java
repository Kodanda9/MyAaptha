package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.MedTestModel;
import com.cpetsol.cpetsolutions.myaaptha.util.RecordDialog;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class PrescriptionActivity extends AppCompatActivity {
    private EditText ETComplaint,ETDrObserv;
    private LinearLayout container1,container,LLMedTstBl,LLLabTstBl,record;
    private TextView TvLabTest,TvMedTest;
    private View localView;
    private Dialog mDialog;
    private EditText EtDesc;
    private Spinner SDrugType,SDosage,EtQuantity;
    private AutoCompleteTextView EtDrugname,ETAddLabTest;
    JSONArray LabArray=new JSONArray();
    JSONArray AudioArray=new JSONArray();
    JSONArray medArray=new JSONArray();
    private Button BtnSubmit;
    private ImageView ImgAddLabTest;
    ArrayList medList=new ArrayList();
    private ListView LvMedTest;
    private ArrayList<MedTestModel> ItemsList;
    private String strDrgType,strDosage;
    private JSONObject apppintmentObj;
    private TextView TvName,TvDate;
    private String patientId,appId,patientName,drugName,drugType,quant,labName;
    private ProgressDialog prgDialog;
    RecordDialog recordDialog;
    private TextView TvAudio,Tvdrname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_activity);

        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Prescription");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TvLabTest = (TextView)findViewById(R.id.tvLabTest);      LLLabTstBl=(LinearLayout)findViewById(R.id.llLabTest);
        TvMedTest = (TextView)findViewById(R.id.addMedTest);     LLMedTstBl=(LinearLayout)findViewById(R.id.llMedTest);

        ImageView imgMike  = (ImageView)findViewById(R.id.imgMike);
        TvAudio  = (TextView)findViewById(R.id.tvUploadAudio);
        Tvdrname  = (TextView)findViewById(R.id.drname);
        Tvdrname.setText(SessinSave.getsessin("FullName",PrescriptionActivity.this));

        prgDialog = new ProgressDialog(PrescriptionActivity.this);
        prgDialog.setCancelable(false);

        imgMike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recordDialog = RecordDialog.newInstance(PrescriptionActivity.this,"Record Audio");
                recordDialog.setMessage("Press for record");
                recordDialog.show(PrescriptionActivity.this.getFragmentManager(),"TAG");
                recordDialog.setPositiveButton("Save", new RecordDialog.ClickListener() {
                    @Override
                    public void OnClickListener(String path) {

                        prgDialog.setMessage("Uploading...");
                        prgDialog.show();
                        doFileUpload(path);

                    }
                });


            }
        });




        try {
            apppintmentObj=new JSONObject(getIntent().getStringExtra("AppointmentObj"));
            Log.i("apppintmentObj-->",apppintmentObj.toString());
            patientId=apppintmentObj.getString("id");
            patientName=apppintmentObj.getString("fullName");
            appId=apppintmentObj.getString("appId");


            TvName = (TextView)findViewById(R.id.tvname);       TvName.setText("Patient Name : "+patientName);
            TvDate = (TextView)findViewById(R.id.tvDate);       TvDate.setText(apppintmentObj.getString("bookedDate")+" / "+apppintmentObj.getString("timeSlot"));
            LvMedTest = (ListView)findViewById(R.id.medTest_lv);
            ETAddLabTest = (AutoCompleteTextView)findViewById(R.id.etAddLabTest);
            ETComplaint = (EditText)findViewById(R.id.etComplaint);
            ETDrObserv = (EditText)findViewById(R.id.etDrObs);
            ImgAddLabTest = (ImageView)findViewById(R.id.imgAdd);
            container = (LinearLayout)findViewById(R.id.container);
            container1 = (LinearLayout)findViewById(R.id.container1);
            record = (LinearLayout)findViewById(R.id.record);
            BtnSubmit = (Button) findViewById(R.id.presc_Submit);
            EtDrugname= (AutoCompleteTextView) findViewById(R.id.etDrugName);
            EtQuantity = (Spinner) findViewById(R.id.etQuantity);

            ArrayList<String> quant = new ArrayList<>();
            String t = "";
            for (int m = 1; m <= 100; m++) {

                quant.add(m + "");
                 }

            quant.add(0,"Select");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (PrescriptionActivity.this, android.R.layout.simple_spinner_item,
                            quant); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .select_dialog_singlechoice);
            EtQuantity.setAdapter(spinnerArrayAdapter);



            EtDesc = (EditText) findViewById(R.id.et_desc);
            SDrugType = (Spinner) findViewById(R.id.sDrugType);
            SDosage = (Spinner) findViewById(R.id.sDosage);
            Button AddMedTestBtn = (Button) findViewById(R.id.addMedTestBtn);
            TvLabTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(LLLabTstBl.getVisibility()==View.VISIBLE){
                        LLLabTstBl.setVisibility(View.GONE);

                    }else{
                        LLLabTstBl.setVisibility(View.VISIBLE);
                        if(LLMedTstBl.getVisibility()==View.VISIBLE){
                            LLMedTstBl.setVisibility(View.GONE);
                        }
                    }
                }
            });
            TvMedTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(LLMedTstBl.getVisibility()==View.VISIBLE){
                        LLMedTstBl.setVisibility(View.GONE);

                    }else{
                        LLMedTstBl.setVisibility(View.VISIBLE);
                        if(LLLabTstBl.getVisibility()==View.VISIBLE){
                            LLLabTstBl.setVisibility(View.GONE);}

                    }
                }
            });
            ImgAddLabTest.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    if(TextUtils.isEmpty(ETAddLabTest.getText().toString())){
                        return;
                    }

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.presc_add_row, null);
                    TextView textOut = (TextView)addView.findViewById(R.id.textout);
                    textOut.setText(ETAddLabTest.getText().toString());
                    ImageView buttonRemove = (ImageView)addView.findViewById(R.id.remove);
                    buttonRemove.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)addView.getParent()).removeView(addView);
                        }});
                    JSONObject labobj=new JSONObject();
                    try {
                        labobj.accumulate("labTestName",ETAddLabTest.getText().toString());
                        LabArray.put(labobj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    container.addView(addView);
                    ETAddLabTest.setText("");
                    LLLabTstBl.setVisibility(View.GONE);
                }});






            ETAddLabTest.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.i("beforeTextChanged", String.valueOf(charSequence));
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.i("OnTextChange", String.valueOf(charSequence));
                    if(charSequence.length()>0){
                       LabTestAsyncTask runner=new LabTestAsyncTask(String.valueOf(charSequence));
                        runner.execute();
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    Log.i("afterTextChanged", String.valueOf(editable));
                }
            });




            AddMedTestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.presc_med_add_row, null);
                    TextView textOut = (TextView)addView.findViewById(R.id.textout);
                    TextView textOut1 = (TextView)addView.findViewById(R.id.textout1);
                    TextView textOut2 = (TextView)addView.findViewById(R.id.textout2);
                    TextView textOut3 = (TextView)addView.findViewById(R.id.textout3);
                    TextView textOut4 = (TextView)addView.findViewById(R.id.textout4);




                    textOut.setText(EtDrugname.getText().toString());
                    textOut1.setText(SDrugType.getSelectedItem().toString());
                    textOut2.setText(SDosage.getSelectedItem().toString());
                    textOut3.setText(EtQuantity.getSelectedItem().toString());
                    textOut4.setText(EtDesc.getText().toString());

                    ImageView buttonRemove = (ImageView)addView.findViewById(R.id.remove);
                    buttonRemove.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout)addView.getParent()).removeView(addView);
                        }});
                    JSONObject medobj=new JSONObject();
                    try {
                        medobj.accumulate("drugName",EtDrugname.getText().toString());
                        medobj.accumulate("dosage",SDosage.getSelectedItem().toString());
                        medobj.accumulate("drugType",SDrugType.getSelectedItem().toString());
                        medobj.accumulate("qty",EtQuantity.getSelectedItem().toString());
                        medobj.accumulate("instruction",EtDesc.getText().toString());
                        medArray.put(medobj);
                    } catch (JSONException e) {  e.printStackTrace();    }
                    Log.i("medArray==>",medArray.toString());
                    container1.addView(addView);
                    EtDrugname.setText("");
                    EtDesc.setText("");
                    LLMedTstBl.setVisibility(View.GONE);
//                medicinePopUp();
                }
            });
            EtDrugname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.i("beforeTextChanged", String.valueOf(charSequence));
                }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Log.i("OnTextChange", String.valueOf(charSequence));
                    if(charSequence.length()>0){
                        AutoDrugNmAsyncTask runner=new AutoDrugNmAsyncTask(String.valueOf(charSequence));
                        runner.execute();
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {
                    Log.i("afterTextChanged", String.valueOf(editable));
                }
            });

            BtnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONObject medObj=new JSONObject();
                        medObj.accumulate("name",patientName);
                        medObj.accumulate("doctorObservation",ETDrObserv.getText().toString());
                        medObj.accumulate("complaints",ETComplaint.getText().toString());
                        medObj.accumulate("labTest",LabArray);
                        medObj.accumulate("prescriptions",medArray);

                        Log.i("medObj===>",medObj.toString());

                        SaveAsyncTask runner=new SaveAsyncTask(medObj);   runner.execute();
                    }catch (Exception e){  e.printStackTrace();   }
                }
            });

            for (int i=0;i<medList.size();i++){
                JSONObject obj=(JSONObject)medList.get(i);
                Log.i("111111111-->"+i,obj.toString());
            }

        } catch (JSONException e) {    e.printStackTrace();   }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        }

    }//onCreate




    private class AutoDrugNmAsyncTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private String dname,drugName;

        public AutoDrugNmAsyncTask(String s) {
            dname=s;
        }
        @Override
        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(PrescriptActivity.this,     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String   content= AsyncTaskHelper.makeServiceCall(ApisHelper.drugNameSearch+dname+"","GET",null);
            Log.i("D NAme===>",ApisHelper.drugNameSearch+dname);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc-->:",result);
            ArrayList<String> scripts = new ArrayList<String>();
            Log.i("onPostexc-->:",result);

            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        String drugName=object.getString("drugName");
                        scripts.add(drugName);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (PrescriptionActivity.this, android.R.layout.simple_spinner_item,
                            scripts); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .select_dialog_singlechoice);
            EtDrugname.setAdapter(spinnerArrayAdapter);
            EtDrugname.setThreshold(0);
            EtDrugname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DrugTypeAsyncTask runner=new DrugTypeAsyncTask( adapterView.getItemAtPosition(i).toString());   runner.execute();
                }
            });
//            if(progressDialog.isShowing()){ progressDialog.dismiss(); }
        }//onPostExecute
    }//AsyncTaskRunner
    private class DrugTypeAsyncTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private String drugName,drugType;
        private ArrayList<CharSequence> DrygTypelist;
        public DrugTypeAsyncTask(String s) {
            drugName=s;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PrescriptionActivity.this,     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String   content= AsyncTaskHelper.makeServiceCall(ApisHelper.loadDrugTypesByDrugName+drugName+"","GET",null);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc-->:",result);
            if(progressDialog.isShowing()){ progressDialog.dismiss(); }
//            int k=0;
//            try {
//                array=new JSONArray(s);
//                arr_state = new String[array.length()];
//                arr_state_codes= new String[array.length()];
//
//                for(int i = 0; i < array.length(); i++){
//                    JSONObject jsonCountry=array.getJSONObject(k);
//                    arr_state[i] = jsonCountry.getString("location");
//                    arr_state_codes[i] = jsonCountry.getString("id");
//                    k++;
//                }
//                Locationslist = new ArrayList<String>(Arrays.asList(arr_state));
//                Locationslist.add(0,"Select");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            DrygTypelist = new ArrayList<CharSequence>();


            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        String drugType=object.getString("drugType");
                        DrygTypelist.add(drugType);
                    }
                }
                DrygTypelist.add(0,"Select");
            } catch (JSONException e) {
                e.printStackTrace();
            }



            ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter<CharSequence>
                    (PrescriptionActivity.this, android.R.layout.simple_spinner_item,
                            DrygTypelist); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .select_dialog_singlechoice);
            SDrugType.setAdapter(spinnerArrayAdapter);





 //           SDrugType.setAdapter(new ArrayAdapter<String>(PrescriptionActivity.this,android.R.layout.simple_spinner_dropdown_item,DrygTypelist));
            SDrugType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ((TextView)parent.getChildAt(0)).setTextSize(spinnerTextSize);
                    strDrgType=parent.getItemAtPosition(position).toString();
                    Log.i("strDrgType Name:-->",strDrgType);
                    DrugQuanteAsyncTask runner=new DrugQuanteAsyncTask(drugName,SDrugType.getSelectedItem().toString());   runner.execute();
//                    for(int i = 0; i <array.length(); i++){
//                        if(LocationName.equalsIgnoreCase(arr_state[i])){
////                            Toast.makeText(getActivity(),arr_state_codes[i],Toast.LENGTH_LONG).show();
//                            SessinSave.saveSession("MainLocation",arr_state_codes[i],getActivity());
////                            LocalityAsyncTask runner=new LocalityAsyncTask(arr_state_codes[i]);
////                            runner.execute();
//                        }
//                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }//onPostExecute
    }//AsyncTaskRunner
    private class DrugQuanteAsyncTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private ArrayList<String> DrygQuntlist;
        public DrugQuanteAsyncTask(String s, String s1) {
            drugName = s;
            drugType = s1;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PrescriptionActivity.this, "Please wait", "loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.loadQuantityByDosage + drugName + "&drugType=" + drugType + "", "GET", null);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc-->:", result);
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(result);
            while (m.find()) {
                quant = m.group(1);
            }
//            Toast.makeText(getApplicationContext(),quant,Toast.LENGTH_LONG).show();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            DrygQuntlist = new ArrayList<String>();
            DrygQuntlist.add(0, "Select");
            DrygQuntlist.add(quant);
            SDosage.setAdapter(new ArrayAdapter<String>(PrescriptionActivity.this, android.R.layout.select_dialog_singlechoice, DrygQuntlist));
            SDosage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strDosage = adapterView.getItemAtPosition(i).toString();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }//AsyncTaskRunner
    }//DrugQuanteAsyncTask

    public class SaveAsyncTask extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        JSONObject obj;
        public SaveAsyncTask(JSONObject medObj) {
            obj = medObj;
        }
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PrescriptionActivity.this, "Please wait", "loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.prescription_save + "/"+ SessinSave.getsessin("profile_id",PrescriptionActivity.this)+"/"+appId+"?patientId="+patientId+"", "POST", obj);
            Log.i("Api-->",ApisHelper.prescription_save + "/"+ SessinSave.getsessin("profile_id",PrescriptionActivity.this)+"/"+appId+"?patientId="+patientId+"");
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj=new JSONObject(s);
                if(obj.getString("status").equalsIgnoreCase("SUCCESS")){
                    Toast.makeText(PrescriptionActivity.this,"Prescription Successfully saved",Toast.LENGTH_LONG).show();
                    Intent in=new Intent(PrescriptionActivity.this,NavigationActivity.class);
                    startActivity(in);
                    finish();
                }else{
                    Toast.makeText(PrescriptionActivity.this,"Some Issue is going on, we will Solve this ASAP",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("AddEduAsyncTask onPost:", s);
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }//NavSubmitAsyncTask

    private void doFileUpload(String path){

        try{

            String urlString = "http://myaaptha.com/pp/fileupload/"+SessinSave.getsessin("profile_id",PrescriptionActivity.this);

            File file=new File(path);

            RequestParams params = new RequestParams();
            params.put("file",file);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(urlString, params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("123456789-->","onSuccess");
                    prgDialog.cancel();
                    String line=new String(responseBody);
                    Log.i("123456789-->",line);
                    Pattern p = Pattern.compile("\"([^\"]*)\"");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        TvAudio.setText(m.group(1));

                        if(TextUtils.isEmpty(TvAudio.getText().toString())){
                            return;
                        }

                        LayoutInflater layoutInflater =
                                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View addView = layoutInflater.inflate(R.layout.presc_add_row, null);
                        TextView textOut = (TextView)addView.findViewById(R.id.textout);
                        textOut.setText(TvAudio.getText().toString());
                        ImageView buttonRemove = (ImageView)addView.findViewById(R.id.remove);
                        buttonRemove.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                ((LinearLayout)addView.getParent()).removeView(addView);
                            }});
                        JSONObject audioobj=new JSONObject();
                        try {
                            audioobj.accumulate("AudioName",TvAudio.getText().toString());
                            AudioArray.put(audioobj);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        record.addView(addView);
                        TvAudio.setText("");
                //        LLLabTstBl.setVisibility(View.GONE);
                    }
                };







                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("123456789-->","onFailure");
                    prgDialog.cancel();

                }
            });

        }
        catch (Exception e)
        { Log.i("123456789-->","Exceptiobn");
            prgDialog.cancel();
        }
    }


    private class LabTestAsyncTask extends AsyncTask<String,String,String>{
        String term;
        public LabTestAsyncTask(String s) {
            this.term=s;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.labTestSearch+term,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ArrayList<String> scripts = new ArrayList<String>();
            Log.i("onPostexc-->:",result);

            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        String labName=object.getString("labTestName");
                        scripts.add(labName);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PrescriptionActivity.this, android.R.layout.select_dialog_item, scripts);
            ETAddLabTest.setThreshold(0);
            ETAddLabTest.setAdapter(arrayAdapter);


        }
    }
}
