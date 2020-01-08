package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Dialog;
import android.app.Fragment;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.LabTestDetails_Adapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.LabDetails_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class LabTest_DetailsFragment extends Fragment {
    private static View rootView;
    private ListView listview;
    private Button addlab;
    private View localView;
    private Dialog mDialog;
    private Spinner spin;
    private EditText pricelab;
    private EditText pricehome;
    private EditText disc;
    private EditText remark;
    private Button updat;
    private ArrayList<String> namesList,idsList;
    private String qualificationId;



    public LabTest_DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }//if
        try {
            rootView = inflater.inflate(R.layout.fragment_lab_test__details, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
            listview = (ListView) rootView.findViewById(R.id.labtest_listview);
            addlab = (Button) rootView.findViewById(R.id.add);
            addlab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   AddLab();
                }
            });
            LabDetailsTask task = new LabDetailsTask();
            task.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;

    }

    private void AddLab() {
        localView = View.inflate(getActivity(), R.layout.labedit_form, null);
        localView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in_enter));
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
        spin=(Spinner)this.mDialog.findViewById(R.id.spinner);
        pricelab=(EditText)this.mDialog.findViewById(R.id.priceatlab);
        pricehome=(EditText)this.mDialog.findViewById(R.id.priceathome);
        disc=(EditText)this.mDialog.findViewById(R.id.discount);
        remark=(EditText)this.mDialog.findViewById(R.id.remarks);
        updat=(Button)this.mDialog.findViewById(R.id.update);
        updat.setText("Submit");
        updat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitTask task=new SubmitTask();
                task.execute();
            }
        });

       ListLabTests task=new ListLabTests();
        task.execute();


    }

    private class LabDetailsTask extends AsyncTask<String, String, String> {
        private ArrayList<LabDetails_Model> rowItems;
        String pId;

        @Override
        protected void onPreExecute() {
            pId = SessinSave.getsessin("profile_id", getActivity());
            Log.i("niiiiiiiii", pId);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.allLabTests + pId, "GET", null);
          //  String Content = AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/allLabTests/" + pId, "GET", null);
            Log.i("niiiiiiiii", Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("=======>", s);
            rowItems = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LabDetails_Model model = new LabDetails_Model();
                        model.setId(jsonObject.getString("id"));
                        model.setTestName(jsonObject.getString("testName"));
                        model.setPriceAtLab(jsonObject.getString("priceAtLab"));
                        model.setPriceAtHome(jsonObject.getString("priceAtHome"));
                        model.setDiscount(jsonObject.getString("discount"));
                        model.setRemarks(jsonObject.getString("remarks"));
                        model.setFullName(jsonObject.getString("fullName"));
                        model.setStatus(jsonObject.getString("status"));
                        rowItems.add(model);
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            LabTestDetails_Adapter adapter = new LabTestDetails_Adapter(getActivity(), rowItems);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            super.onPostExecute(s);
        }
    }
    private class ListLabTests extends AsyncTask<String,String,String>{
        String[] arr_lab,arr_lab_codes;
        ArrayList<String> Lablist;

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.listLabTests,"GET",null);
      //      String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/listLabTests","GET",null);
            Log.i("Bhavani",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                namesList = new ArrayList<String>();
                idsList = new ArrayList<String>();
                JSONObject jsonobject=new JSONObject(s);
                JSONArray jsonarray=jsonobject.getJSONArray("result");

                if (jsonarray.length()>0){
                    for (int i=0;i<jsonarray.length();i++){
                        JSONObject jsonobject1= jsonarray.getJSONObject(i);
                       namesList.add(jsonobject1.getString("labTestName"));
                        idsList.add(jsonobject1.getString("id"));
                    }
                    namesList.add(0,"Select");
                    idsList.add(0,"");

                }
                spin.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,namesList));
              /*  for(int i = 0; i < jsonarray.length(); i++){

//             if("5".equalsIgnoreCase(arr_lab_codes[i])){
//                   spin.setSelection(AppHelper.setValueToSpinner(spin,arr_lab_codes[i]));
//                }
            }*/
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String SelfullName=adapterView.getItemAtPosition(position).toString();

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


            super.onPostExecute(s);
        }
    }

    private class SubmitTask extends AsyncTask<String,String,String>{
        String pId;
        String testname,priceatlab,priceathome,discount,remarks,labid;
        @Override
        protected void onPreExecute() {
            testname=spin.getSelectedItem().toString();
            priceatlab=pricelab.getText().toString();
            priceathome=pricehome.getText().toString();
            discount=disc.getText().toString();
            remarks=remark.getText().toString();
            pId=SessinSave.getsessin("profile_id",getActivity());
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("testName",testname);
                object.accumulate("priceAtLab",priceatlab);
                object.accumulate("priceAtHome",priceathome);
                object.accumulate("discount",discount);
                object.accumulate("remarks",remarks);
                object.accumulate("id",labid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String content1=AsyncTaskHelper.makeServiceCall(ApisHelper.saveTest+pId,"POST",object);
       //     String content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/saveTest/"+pId,"POST",object);
            return content1;
        }
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(),"Successfully Added",Toast.LENGTH_LONG).show();
            getActivity().getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest_DetailsFragment()).commit();
            super.onPostExecute(s);
        }
    }
}
