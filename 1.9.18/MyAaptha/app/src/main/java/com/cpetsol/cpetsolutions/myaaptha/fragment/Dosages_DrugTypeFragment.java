package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.DosageDrugTypeAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.DosageDrugTypeModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Dosages_DrugTypeFragment extends Fragment {
    private static View rootView;
    private ListView listview;

    public Dosages_DrugTypeFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_dosages__drug_type, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());
            listview=(ListView)rootView.findViewById(R.id.med_drug_dosage);
            DrugDosageTask task= new DrugDosageTask();
            task.execute();

        } catch (Exception e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ ContactUsFragment");
        }
        return rootView;
    }


    private class DrugDosageTask extends AsyncTask<String,String,String>{

        private ArrayList<DosageDrugTypeModel> arraylist;


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allDosages+SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arraylist=new ArrayList<DosageDrugTypeModel>();
            try {
                JSONArray array=new JSONArray(s);

                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        DosageDrugTypeModel model=new DosageDrugTypeModel();
                        model.setId(object.getString("id"));
                        model.setDrugName(object.getString("drugName"));
                        model.setDrugType(object.getString("drugType"));
                        model.setDosage(object.getString("dosage"));
                    arraylist.add(model);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
          DosageDrugTypeAdapter adapter =new DosageDrugTypeAdapter(getActivity(),arraylist);
            listview.setAdapter(adapter);
        }
    }
}
