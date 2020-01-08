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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ForumAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ForumModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumsFragment extends Fragment {

    private static View rootView;
    private ListView listview;
    private RelativeLayout Rl_listSuggest;
    private Button BTab1,BTab2,BTab3;
    private ArrayList<ForumModel> listrowItems;
    private String pprofession;

    public ForumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {


//            rootView = inflater.inflate(R.layout.comingsoon, container, false);
            rootView = inflater.inflate(R.layout.forums_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView,getActivity());

            AsyncTaskRunner runner=new AsyncTaskRunner();  runner.execute();
            BTab1=(Button)rootView.findViewById(R.id.tab1);
            BTab2=(Button)rootView.findViewById(R.id.tab2);
            BTab3=(Button)rootView.findViewById(R.id.tab3);
            String patient_PerDetails= SessinSave.getsessin("LogInObj",getActivity());
          if( !patient_PerDetails.equalsIgnoreCase("")){
              JSONObject jsonDetails=new JSONObject(patient_PerDetails);
               pprofession=jsonDetails.getString("profession");
//              if(pprofession.equalsIgnoreCase("Doctor")){     BTab1.setVisibility(View.VISIBLE);
//              }
          }else{
              pprofession="Other";
          }

            listview=(ListView)rootView.findViewById(R.id.forumlist);
            Rl_listSuggest=(RelativeLayout)rootView.findViewById(R.id.rlsuggestlist);
          /*  listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(),"Clicked @"+i, Toast.LENGTH_LONG).show();
                    int itemPosition     = i;

                    Intent j=new Intent(getActivity(), ForumsAnswerActivity.class);
                    j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    j.putExtra("id", itemPosition);
                    startActivityForResult(j, 0);

                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });*/
        } catch (Exception e) {
        }
        return rootView;
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allForum,"GET",null);
            Log.i("URL", ApisHelper.allForum);
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            try {

                final JSONObject mainObj=new JSONObject(result);
               final JSONArray array=new JSONArray(mainObj.getString("Forum"));

//                listrowItems = new ArrayList<ForumModel>();
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject obj = array.getJSONObject(i);
//                    Log.i("Ob-->"+i,obj.toString());
//                    if(obj.getString("viewedBy").equalsIgnoreCase("All") && obj.getString("repliedBy").equalsIgnoreCase("All")){
//                        ForumModel item = new ForumModel(obj.getInt("id"), obj.getString("question"), obj.getString("categories"), obj.getString("subCategories"), obj.getString("viewedBy"), obj.getString("repliedBy"), obj.getString("dateCreated"), obj.getString("viewStatus"),mainObj.getString(String.valueOf(i+1)));
//                        listrowItems.add(item);
//                    }
//                }
//                ForumAdapter adapter = new ForumAdapter(getActivity(), listrowItems);
//                listview.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
Log.i("AAAA","OPTIONALLLLL");
                if(pprofession.equalsIgnoreCase("Doctor")){
                    ShowD2D(mainObj,array);
                    BTab1.setVisibility(View.VISIBLE);
                }else if(pprofession.equalsIgnoreCase("Other")){
                    ShowD2R(mainObj,array);
                    BTab2.setVisibility(View.VISIBLE);
                }else{
                    ShowD2R(mainObj,array);
                    BTab2.setVisibility(View.VISIBLE);
                }


                if(array.length()>0) {
                  BTab1.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                           ShowD2D(mainObj,array);
                      }
                  });
                    BTab2.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                            ShowD2R(mainObj,array);
                      }
                  });
                    BTab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ShowA2A(mainObj,array);
                        }
                    });

                }else{
                    Rl_listSuggest.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (progressDialog.isShowing()){    progressDialog.dismiss();   }
        }//onPostExecute


    }//AsyncTask class

    private void ShowA2A(JSONObject mainObj, JSONArray array1) {
        try {
            BTab3.setBackgroundColor(getResources().getColor(R.color.white));   BTab3.setTextColor(getResources().getColor(R.color.insta_blue_bg));
            BTab2.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab2.setTextColor(getResources().getColor(R.color.white));
            BTab1.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab1.setTextColor(getResources().getColor(R.color.white));


            listrowItems = new ArrayList<ForumModel>();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject obj = array1.getJSONObject(i);
                Log.i("Obj",obj.toString());
                if(obj.getString("viewedBy").equalsIgnoreCase("All") && obj.getString("repliedBy").equalsIgnoreCase("All")){
                    ForumModel item = new ForumModel(obj.getString("id"), obj.getString("question"), obj.getString("categories"), obj.getString("subCategories"), obj.getString("viewedBy"), obj.getString("repliedBy"), obj.getString("dateCreated"), obj.getString("viewStatus"),mainObj.getString(String.valueOf(i+1)));
                    listrowItems.add(item);
                }
            }
            ForumAdapter adapter = new ForumAdapter(getActivity(), listrowItems);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ShowD2R(JSONObject mainObj, JSONArray array1) {
        try {
            BTab2.setBackgroundColor(getResources().getColor(R.color.white));   BTab2.setTextColor(getResources().getColor(R.color.insta_blue_bg));
            BTab1.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab1.setTextColor(getResources().getColor(R.color.white));
            BTab3.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab3.setTextColor(getResources().getColor(R.color.white));

            listrowItems = new ArrayList<ForumModel>();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject obj = array1.getJSONObject(i);
                if(obj.getString("viewedBy").equalsIgnoreCase("All") && obj.getString("repliedBy").equalsIgnoreCase("Doctor")){
                    ForumModel item = new ForumModel(obj.getString("id"), obj.getString("question"), obj.getString("categories"), obj.getString("subCategories"), obj.getString("viewedBy"), obj.getString("repliedBy"), obj.getString("dateCreated"), obj.getString("viewStatus"),mainObj.getString(String.valueOf(i+1)));
                    listrowItems.add(item);
                }
            }
            ForumAdapter adapter = new ForumAdapter(getActivity(), listrowItems);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ShowD2D(JSONObject mainObj, JSONArray array1) {
       try {
           BTab1.setBackgroundColor(getResources().getColor(R.color.white));   BTab1.setTextColor(getResources().getColor(R.color.insta_blue_bg));
           BTab2.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab2.setTextColor(getResources().getColor(R.color.white));
           BTab3.setBackgroundColor(getResources().getColor(R.color.insta_menu_list_bg));   BTab3.setTextColor(getResources().getColor(R.color.white));



           listrowItems = new ArrayList<ForumModel>();
           for (int i = 0; i < array1.length(); i++) {
               JSONObject obj = array1.getJSONObject(i);
               if(obj.getString("viewedBy").equalsIgnoreCase("Doctor") && obj.getString("repliedBy").equalsIgnoreCase("Doctor")){
                   ForumModel item = new ForumModel(obj.getString("id"), obj.getString("question"), obj.getString("categories"), obj.getString("subCategories"), obj.getString("viewedBy"), obj.getString("repliedBy"), obj.getString("dateCreated"), obj.getString("viewStatus"),mainObj.getString(String.valueOf(i+1)));
                   listrowItems.add(item);
               }
           }
           ForumAdapter adapter = new ForumAdapter(getActivity(), listrowItems);
           listview.setAdapter(adapter);
           adapter.notifyDataSetChanged();
       }catch (Exception e){
           e.printStackTrace();
       }
    }





}
