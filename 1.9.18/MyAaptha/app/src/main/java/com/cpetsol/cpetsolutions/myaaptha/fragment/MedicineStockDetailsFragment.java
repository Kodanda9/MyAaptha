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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.MedicalStockAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.MedicineStockModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MedicineStockDetailsFragment extends Fragment {
    private View rootView;
    private ListView listView;
    Button addMedStkDtls;
    private String pId;
    private View localView;
    private Dialog mDialog;
    private RelativeLayout NoRecords;
    public MedicineStockDetailsFragment() {
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
        try{
            rootView=inflater.inflate(R.layout.fragment_medicine_stock_details, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());
            listView=(ListView)rootView.findViewById(R.id.lv);
            NoRecords = (RelativeLayout) rootView.findViewById(R.id.rlsuggestlist);
            StockDetailsTask stockdetails=new StockDetailsTask();
            stockdetails.execute();
            addMedStkDtls=(Button)rootView.findViewById(R.id.addM);
            addMedStkDtls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMedForm();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }

    private void addMedForm() {
        localView = View.inflate(getActivity(), R.layout.add_stock_data, null);
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


        final Spinner SpCategory   = (Spinner) this.mDialog.findViewById(R.id.sDocCategory);

        ImageView imgView=(ImageView)this.mDialog.findViewById(R.id.closeDialog);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }//


    private class StockDetailsTask extends AsyncTask<String,String,String>{
        private ArrayList<MedicineStockModel> rowItems;
        @Override
        protected void onPostExecute(String s) {

            rowItems = new ArrayList<MedicineStockModel>();
            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jsonArray=jsonObject.getJSONArray("Medicine Stock List");
                if(jsonArray.length()>0){
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        Log.i("sssssssss",jsonObject1.toString());
                        MedicineStockModel item=new MedicineStockModel();
                        item.setDrugName(jsonObject1.getString("drugName"));
                        item.setDrugType(jsonObject1.getString("drugType"));
                        item.setDosage(jsonObject1.getString("dosage"));
                        item.setQuantity(jsonObject1.getString("quantity"));
                        item.setThreshold(jsonObject1.getString("threshold"));
                        item.setAgencyName(jsonObject1.getString("agencyName"));
                        item.setRemarks(jsonObject1.getString("remarks"));
                        rowItems.add(item);
                    }
                }else{
                    NoRecords.setVisibility(View.VISIBLE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            MedicalStockAdapter adapter=new MedicalStockAdapter(getActivity(),rowItems);
            listView.setAdapter(adapter);
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            pId= SessinSave.getsessin("profile_id",getActivity());
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allMedicineStockDetails+pId,"GET",null);
        //    String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/allMedicineStockDetails/"+pId,"GET",null);
            return Content;
        }
    }
}


