package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.WebpageAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.WebPagesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddWebPageToRoleFragment extends Fragment {


    private View rootView;
    private Spinner RolesSpinner;
    private ArrayList<String> roleslist;
    private ArrayList<String> idsList;
    private ListView Webpages;
    private ArrayList<WebPagesModel> arraylist;



    public AddWebPageToRoleFragment() {
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
            rootView = inflater.inflate(R.layout.fragment_add_web_page_to_role, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());

           RolesSpinner =(Spinner)rootView.findViewById(R.id.rolesid);
           Webpages =(ListView)rootView.findViewById(R.id.webpages);


            RolesAsyncTask task=new RolesAsyncTask();
            task.execute();

        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

    private class RolesAsyncTask extends AsyncTask<String,String,String>{
        private ArrayList<Object> rolesarray;
        private String qualificationId;



        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.loadRoles,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            roleslist = new ArrayList<String>();
            idsList = new ArrayList<String>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                    roleslist.add(object.getString("roleDetails"));
                        idsList.add(object.getString("id"));
                    }
                }
                roleslist.add(0,"--Select--");
                idsList.add(0,"");
                RolesSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_single_choice,roleslist));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RolesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String SelfullName=adapterView.getItemAtPosition(i).toString();

                    for(int j = 0; j <roleslist.size(); j++){
                        if(SelfullName.equalsIgnoreCase(roleslist.get(j))){
                            qualificationId = idsList.get(j);
                        }
                    }
                    WebpagesAsyncTask task=new WebpagesAsyncTask();
                    task.execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private class WebpagesAsyncTask extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.webPages,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arraylist=new ArrayList<WebPagesModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        WebPagesModel model=new WebPagesModel();
                        JSONObject object=array.getJSONObject(i);
                        model.setId(object.getString("id"));
                        model.setName(object.getString("name"));
                        model.setUrl(object.getString("url"));
                        model.setLevel(object.getString("level"));
                        model.setOrderNumber(object.getString("orderNumber"));
                        model.setViewStatus(object.getString("viewStatus"));
                        model.setDateCreated(object.getString("dateCreated"));
                        model.setRefId(object.getString("refId"));
                        arraylist.add(model);
                    }


                }
                WebpageAdapter adapter=new WebpageAdapter(getActivity(),arraylist);
                Webpages.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
