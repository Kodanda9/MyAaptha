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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.Drugs_Adapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Drugs_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class Drugs_PricesFragment extends Fragment {
   private View rootView;
private ListView listView;
    private Button addDrug,subm,edit,delete,hold;
    private View localView;
    private Dialog mDialog;
    private String pId;
    private Spinner drug,dosagedrug;
    String PD_DosageCode;
    EditText price,quantity,thresholdquant,discount;
    private RelativeLayout NoRecords;


    public Drugs_PricesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {  parent.removeView(rootView);   }
        }//if
        try{ // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_drugs__prices, container, false);
            listView=(ListView)rootView.findViewById(R.id.drugs_listview);
            NoRecords = (RelativeLayout) rootView.findViewById(R.id.rlsuggestlist);
            DrugsTask task=new DrugsTask();
            task.execute();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   updateForm();
                }
            });
            addDrug=(Button)rootView.findViewById(R.id.addDp);
            addDrug.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateForm();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }


    private void updateForm() {
        localView = View.inflate(getActivity(), R.layout.medicine_update_form, null);
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

         drug=(Spinner)this.mDialog.findViewById(R.id.drugname_drug);
        dosagedrug=(Spinner)this.mDialog.findViewById(R.id.dosage_drug);
        price=(EditText)this.mDialog.findViewById(R.id.price_drug);
        quantity=(EditText)this.mDialog.findViewById(R.id.quantity_drug);
        thresholdquant=(EditText)this.mDialog.findViewById(R.id.thresholdquantity_drug);
        discount=(EditText)this.mDialog.findViewById(R.id.discout_drug);
        DrugNamesTask task=new DrugNamesTask();
        task.execute();
        subm=(Button)this.mDialog.findViewById(R.id.drug_submit);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostTask task1=new PostTask();
                task1.execute();
            }
        });



       /* ImageView imgView=(ImageView)this.mDialog.findViewById(R.id.closeDialog);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });*/
    }

    private class DrugsTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            pId= SessinSave.getsessin("profile_id",getActivity());
            super.onPreExecute();
        }

        ArrayList<Drugs_Model>rowItems;
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allDrugPriceList+pId,"GET",null);
      //      String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/allDrugPriceList/"+pId,"GET",null);
           Log.i("=====>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems=new ArrayList<>();
            try {
                JSONArray array=new JSONArray(s);
                if (array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        Drugs_Model model=new Drugs_Model();
                        model.setDrugName(object.getString("drugName"));
                        model.setDosage(object.getString("dosage"));
                        model.setQuantity(object.getString("quantity"));
                        model.setPrice(object.getString("price"));
                        model.setThresholdQuantity(object.getString("thresholdQuantity"));
                        model.setDiscount(object.getString("discount"));
                        rowItems.add(model);
                        Log.i("........",rowItems.toString());
                    }
                }else{
                    NoRecords.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Drugs_Adapter adapter=new Drugs_Adapter(getActivity(),rowItems);
            listView.setAdapter(adapter);

            super.onPostExecute(s);
        }

    }

    private class DrugNamesTask extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String drug=AsyncTaskHelper.makeServiceCall(ApisHelper.drugsList,"GET",null);
      //      String drug=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/drugsList","GET",null);
         Log.i("=======>",drug);
            return drug;
        }

        @Override
        protected void onPostExecute(String s) {
            int k = 0;
            try {



                final JSONArray jsonArray=new JSONArray(s);
                final String[] arr_drugs = new String[jsonArray.length()];
                final String[] arr_drugid= new String[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(k);
                    arr_drugs[i]=jsonObject.getString("drugName");
                   arr_drugid[i]=jsonObject.getString("id");
                    Log.i("hii====>",arr_drugs[i]);
                    k++;
                }
                ArrayList<String>ar=new ArrayList<String>(Arrays.asList(arr_drugs));
                drug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        String spinnerItem=adapterView.getItemAtPosition(position).toString();
                        for (int i=0;i<jsonArray.length();i++){
                            if(spinnerItem.equalsIgnoreCase(arr_drugs[i])) {
                                PD_DosageCode = arr_drugid[i];

                           /*  DosageTask task=new DosageTask(PD_DosageCode);
                                task.execute();*/

                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                drug.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,ar));



            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class DosageTask extends AsyncTask<String,String,String> {
        String Dosage;
        public DosageTask(String pd_dosageCode) {
            this.Dosage=pd_dosageCode;
        }

        @Override
        protected String doInBackground(String... strings) {
            String dosg=AsyncTaskHelper.makeServiceCall(ApisHelper.drugPrice+Dosage,"GET",null);
    //        String dosg=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/drugPrice/allDosages/"+Dosage,"GET",null);
            return dosg;
        }

        @Override
        protected void onPostExecute(String s) {
            int N=0;
            try {
           final  JSONObject jsonObject=new JSONObject(s);

                JSONArray array=jsonObject.getJSONArray("result");
                Log.i("=======",array.toString());
                final String[] arr_dosg = new String[array.length()];
                Log.i("=======", String.valueOf(array.length()));

                for(int l=0;l<arr_dosg.length;l++){
                    Log.i("===========",arr_dosg[l]);
                    arr_dosg[l]=array.getString(Integer.parseInt("result"));
                }
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr_dosg));
                arrayList.add(0,"Select");
                dosagedrug.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,arrayList));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class PostTask extends AsyncTask<String,String,String>{
        String drugname,dosg,pric,quanty,tQuanty,disc;
        @Override
        protected void onPostExecute(String s) {
            Log.i("=======>",s);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            drugname=drug.getSelectedItem().toString();
//            dosg=dosagedrug.getSelectedItem().toString();
            pric=price.getText().toString();
            quanty=quantity.getText().toString();
            tQuanty=thresholdquant.getText().toString();
            disc=discount.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();

            try {
              //  object.accumulate("id",PD_DosageCode);
                object.accumulate("drugName",PD_DosageCode);
                object.accumulate("dosage",dosg);
                object.accumulate("price",pric);
                object.accumulate("quantity",quanty);
                object.accumulate("thresholdQuantity",tQuanty);
                object.accumulate("discount",disc);

            } catch (JSONException e) {
                e.printStackTrace();


            }
            String content=AsyncTaskHelper.makeServiceCall(ApisHelper.storeDrugPrice+SessinSave.getsessin("profile_id",getActivity())+"?drugName="+PD_DosageCode,"POST",object);
       //     String content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/storeDrugPrice/900000040901?drugName="+PD_DosageCode,"POST",object);
            return content;
        }
    }
}
