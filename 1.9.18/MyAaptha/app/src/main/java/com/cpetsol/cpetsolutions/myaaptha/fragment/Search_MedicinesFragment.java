package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Search_Medicime_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.cpetsol.cpetsolutions.myaaptha.R.id.listview;

public class Search_MedicinesFragment extends Fragment {
    private View rootView;
    private EditText medNumber;
    private Button search;
   private TextView pfid,name,state,district;
    private LinearLayout searchLayout;
    private String Number;
    private TextView gender;
    private ImageView image;
    private Button predetails;
    private View localView;
    private Dialog mDialog;
    private TextView doctername,patientname;
    private ListView list;
    private TextView prescribedate;
    private String patname;
    private String patid;
    private Button generatebill;
    private ArrayList<Search_Medicime_Model> rowItems;
    private CheckBox check;
    private LayoutInflater inflater;
    private Activity Activity;
    private ArrayList<Search_Medicime_Model>data;
    private ArrayList<String> selectedStrings;
    private String drugType;
    List<Search_Medicime_Model> listMeds =new ArrayList<Search_Medicime_Model>();

    public Search_MedicinesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {  parent.removeView(rootView);   }
        }try{
        rootView=inflater.inflate(R.layout.fragment_search__medicines,container,false);
            medNumber=(EditText)rootView.findViewById(R.id.medicineNum);
            pfid=(TextView)rootView.findViewById(R.id.pfid);
            name=(TextView)rootView.findViewById(R.id.name_med);
            gender=(TextView)rootView.findViewById(R.id.gender_med);
            state=(TextView)rootView.findViewById(R.id.state_med);
            district=(TextView)rootView.findViewById(R.id.dist_med);
            image=(ImageView)rootView.findViewById(R.id.image);
            search=(Button) rootView.findViewById(R.id.search);
            predetails=(Button)rootView.findViewById(R.id.viewpreDtls);
            searchLayout=(LinearLayout)rootView.findViewById(R.id.numberLayout);
            predetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Presform();
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Number=medNumber.getText().toString();

                    Log.i("Bhavani>>>>>>>",Number);
//                    Toast.makeText(getActivity(),""+Number,Toast.LENGTH_LONG).show();
                    SearchTask task=new SearchTask(Number);  task.execute();
                    searchLayout.setVisibility(View.VISIBLE);
                }
            });

    }catch (Exception e){
        e.printStackTrace();
    }
    return rootView;

}

    private void Presform() {
        localView = View.inflate(getActivity(), R.layout.presc_form, null);
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
          doctername=(TextView)this.mDialog.findViewById(R.id.doctorname);
         patientname=(TextView)this.mDialog.findViewById(R.id.patientname);
        patientname.setText(patname);
         prescribedate=(TextView)this.mDialog.findViewById(R.id.date);

        list=(ListView)this.mDialog.findViewById(listview);
        generatebill=(Button)this.mDialog.findViewById(R.id.bill);
        PrescTask task=new PrescTask(patid);
        task.execute();
        generatebill.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(listMeds.size()>0){
            Billform();
        }else {
            Toast.makeText(getActivity(),"Please Select Atleast One Medicines",Toast.LENGTH_LONG ).show();
        }
    }
});

    }

    private void Billform() {
        localView = View.inflate(getActivity(), R.layout.bill_form, null);
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
        TextView date=(TextView)this.mDialog.findViewById(R.id.date);
        TextView name=(TextView)this.mDialog.findViewById(R.id.bilpatnam);


        name.setText(patname);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dat = sdf.format(new Date());
        date.setText(dat);
        BillTask task=new BillTask();
        task.execute();
        ListView listview=(ListView)this.mDialog.findViewById(R.id.billlist);

    }


    private class SearchTask extends AsyncTask<String,String,String>{
        String num;
        private ProgressDialog pDialog;

        public SearchTask(String number) {
            this.num=number;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.medicinesData+num,"GET",null);
           Log.i("=====>",Content);
        //   Log.i("=====>",AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/medicinesData?mobileOrName="+num,"GET",null));
            return Content;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonarray=new JSONArray(s);
                JSONObject jsonObject=jsonarray.getJSONObject(0);
                pfid.setText(jsonObject.getString("profileId"));
                name.setText(jsonObject.getString("name"));
                patname=jsonObject.getString("name");
                patid=jsonObject.getString("profileId");
                gender.setText(jsonObject.getString("gender"));
                district.setText(jsonObject.getString("district"));
                state.setText(jsonObject.getString("state"));

                pDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class PrescTask extends AsyncTask<String,String,String>{
        private String patientid;
        private ProgressDialog pDialog;

        public PrescTask(String patid) {
            this.patientid=patid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
      //      String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.medicinesData+patientid,"GET",null);
        String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/medicineDetails?pid="+patientid,"GET",null);
           Log.i("=======>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            rowItems = new ArrayList<Search_Medicime_Model>();

            try {
                JSONObject jsonobject=new JSONObject(s);
                JSONArray jsonarray=jsonobject.getJSONArray("Medicines");

                if(jsonarray.length()>0){

                    for(int i=0;i<jsonarray.length();i++){
                        JSONObject jsonObject=jsonarray.getJSONObject(i);
                        doctername.setText(jsonObject.getString("name"));
                        prescribedate.setText(jsonObject.getString("inTime"));
                        Search_Medicime_Model model=new Search_Medicime_Model();
                        model.setDrugName(jsonObject.getString("drugName"));
                        model.setDrugType(jsonObject.getString("drugType"));
                        model.setDosage(jsonObject.getString("dosage"));
                        model.setQty(jsonObject.getString("qty"));
                        rowItems.add(model);

                    }
                }else {
                    pDialog.dismiss();
                    Toast.makeText(getActivity(),"No Medicines",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
            Search_Medicine_Adapter adapter=new Search_Medicine_Adapter(getActivity(),rowItems);
            list.setAdapter(adapter);

            super.onPostExecute(s);
        }

    }

    private class BillTask extends AsyncTask<String,String,String> {
        String med = "" ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            for(int i=0;i<listMeds.size();i++){
                Search_Medicime_Model model = listMeds.get(i);

                if(i+1 == listMeds.size()){
                    med=med+(model.getDrugName() + "-" + model.getDosage() + "-" + model.getDrugType() + "-" + model.getQty());
                }else{
                    med=med+(model.getDrugName() + "-" + model.getDosage() + "-" + model.getDrugType() + "-" + model.getQty())+ "&";
                }
            }

        }
        @Override
        protected String doInBackground(String... strings) {
         String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.generateMedicinesBill+ SessinSave.getsessin("profile_id",getActivity())+"?medicines="+med,"GET",null);
    //        String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/generateMedicinesBill/900000080601?medicines=Paracetomol-5ml-Syrup-2&medicines=Cyclosporine-25+mg-TABLET-5","GET",null);

//            Log.i("========>",Content);
            Log.i("========>",ApisHelper.generateMedicinesBill+ SessinSave.getsessin("profile_id",getActivity())+"?medicines="+med);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj=new JSONObject(s);
                obj.getString("Total");

                try {
//                    JSONObject mainObj = obj.getJSONObject(obj.getString("map"));
                    JSONObject mainObj = new JSONObject(obj.getString("map"));

                    for(int j=0;j<listMeds.size();j++){
                        Search_Medicime_Model model = listMeds.get(j);
                        String medicine = model.getDrugName() + "-" + model.getDosage() + "-" + model.getDrugType() + "-" + model.getQty();
                        Log.i("Count-->",""+j+"  "+medicine);
//                            JSONObject locObj = new JSONObject(mainObj.getString(medicine));
//                            JSONObject locObj = mainObj.getJSONObject(i);
                        if(mainObj.has(medicine)){
                            JSONObject medicineObj = new JSONObject(mainObj.getString(medicine));



                            Log.i("ASDF-->",medicine+"\n"+medicineObj.getString("calculatedPrice")+ medicineObj.getString("unitPrice"));
//                                JSONObject medsOBJ = new JSONObject(mainObj.getString(medicine));
//
//                                Log.i("Medicines Details ",""+medsOBJ.getString("calculatePrice")+"  "+medsOBJ.getString("unitPrice"));
                        }
                    }



                }catch (Exception e){e.printStackTrace();}

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class Search_Medicine_Adapter extends BaseAdapter {



        public Search_Medicine_Adapter(Activity activity, ArrayList<Search_Medicime_Model> rowItems) {
            Activity=activity;
           data=rowItems;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (inflater == null)
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (view == null)
                view = inflater.inflate(R.layout.search_lv_row, null);
            TextView drugname = (TextView) view.findViewById(R.id.drugname);
            TextView drugtype = (TextView) view.findViewById(R.id.drugtype);
            TextView Dosage = (TextView) view.findViewById(R.id.dosage);
            TextView qty = (TextView) view.findViewById(R.id.quantity);
            final CheckBox check = (CheckBox) view.findViewById(R.id.checkbox);

            final Search_Medicime_Model medicine = data.get(i);
            drugname.setText(medicine.getDrugName());
            drugtype.setText(medicine.getDrugType());
            Dosage.setText(medicine.getDosage());
            qty.setText(medicine.getQty());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public String drugType;


                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        listMeds.add(medicine);

                    } else {
                        listMeds.remove(medicine);
                    }

                }
            });




            return view;


        }
    }

}
