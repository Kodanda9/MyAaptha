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
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ArticleAdapterClass;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticlesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticlesFragment extends Fragment {


    private View rootView;
    private ListView LV;

    public ArticlesFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(rootView != null)
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
//            rootView = inflater.inflate(R.layout.comingsoon, container, false);
            rootView = inflater.inflate(R.layout.articles_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView,getActivity());

//            boolean online = AppHelper.isNetworkAvaliable(getActivity());
//            if (online) {
                AsyncTaskRunner runner=new AsyncTaskRunner();       runner.execute();
                LV=(ListView)rootView.findViewById(R.id.lv);
//            } else {     AppHelper.displayErrorPopUp(AppHelper.noInternetConnMsg, AppHelper.noInternetConnContext, getActivity());
//            }

        }catch (Exception e){     e.printStackTrace();
        }
        return rootView;
    }//onCreate


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        List<ArticlesModel> rowItems;
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {

            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allArticleNames,"GET",null);
     //       String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/allArticleNames","GET",null);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);

            try {
//                JSONObject ob=new JSONObject(result);
//                JSONArray array=new JSONArray(ob.getString("result"));
                JSONArray array=new JSONArray(result);
                Log.i("Artical array:=>",array.toString());
                if(array.length()>0) {
                    rowItems = new ArrayList<ArticlesModel>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                     ArticlesModel model = new ArticlesModel();
                        model.setFiles(obj.getString("files"));
                        model.setId(obj.getString("id"));
                        model.setArticleName(obj.getString("articleName"));
                        model.setOverview(obj.getString("overview"));
                        model.setTags(obj.getString("tags"));
                        model.setPublishDate(obj.getString("publishDate"));
                        model.setArticleDescription(obj.getString("articleDescription"));
                        model.setAuthors(obj.getString("authors"));
                        model.setCategories(obj.getString("categories"));
                        model.setSubCategories(obj.getString("subCategories"));
                        model.setStatus(obj.getString("status"));
                        model.setViews(obj.getString("views"));
                        model.setFileName(obj.getString("fileName"));
                        model.setDno(obj.getString("dno"));
                        model.setUserDetails(obj.getString("userDetails"));
                        model.setDateCreated(obj.getString("dateCreated"));
                        model.setViewStatus(obj.getString("viewStatus"));


                        rowItems.add(model);
                    }
                    ArticleAdapterClass adapter = new ArticleAdapterClass(getActivity(), rowItems);
                    LV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else{
//                    Rl_listSuggest.setVisibility(View.VISIBLE);
                }
                Log.i("rowItems:::",rowItems.toString());

            }catch (Exception e){
                e.printStackTrace();
            }

            if (progressDialog.isShowing()){progressDialog.dismiss();}
        }//onPostExecute


    }//AsuncTask class


}
