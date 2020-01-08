package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
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
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ArticleCateogoryAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticleCateogoryModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class AdminFragment extends Fragment {
    private static View rootView;
    private ListView cateogory;
    private Button cate,subcate;
    private View editview;
    private EditText cat;
    private Spinner catesave;
    private String[] arr_categories;
    private String[] arr_categories_codes;

    private ArrayList<String> categorieslist;
    private EditText Subcatesave;
    private String CategoryName;
    private int spinnerTextSize;



    public AdminFragment() {
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
            rootView = inflater.inflate(R.layout.admin_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
          cateogory=(ListView)rootView.findViewById(R.id.catlist);
          cate=(Button)rootView.findViewById(R.id.addc);
          subcate=(Button)rootView.findViewById(R.id.adds);
            CateogoryRunner runner=new CateogoryRunner();
            runner.execute();
cate.setOnClickListener(new View.OnClickListener() {
    public Dialog editDialog;


    @Override
    public void onClick(View view) {
        editview=View.inflate(getActivity(),R.layout.article_category,null);
        editview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in_enter));
        this.editDialog=new Dialog(getActivity(),R.style.NewDialog);
        this.editDialog.setContentView(editview);
        this.editDialog.setCancelable(true);
        this.editDialog.show();

        Window window = this.editDialog.getWindow();
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

        Button save=(Button)this.editDialog.findViewById(R.id.save);
         cat=(EditText)this.editDialog.findViewById(R.id.cateogory);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavecatRunner runner=new SavecatRunner();
                runner.execute();
            }
        });
    }
});
            subcate.setOnClickListener(new View.OnClickListener() {
                public Dialog editDialog;


                @Override
                public void onClick(View view) {
                    editview=View.inflate(getActivity(),R.layout.article_subcategory,null);
                    editview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in_enter));
                    this.editDialog=new Dialog(getActivity(),R.style.NewDialog);
                    this.editDialog.setContentView(editview);
                    this.editDialog.setCancelable(true);
                    this.editDialog.show();

                    Window window = this.editDialog.getWindow();
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

                    Button save=(Button)this.editDialog.findViewById(R.id.save);
                    catesave=(Spinner)this.editDialog.findViewById(R.id.cateogory);
                    Subcatesave=(EditText)this.editDialog.findViewById(R.id.subcateogory);

                    CategoryRunner runner=new CategoryRunner();
                    runner.execute();

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*SaveSubcatRunner runner=new SaveSubcatRunner( arr_categories_codes[i]);
                            runner.execute();*/
                        }
                    });
                }
            });
        } catch (InflateException e) {
            e.printStackTrace();
//
        }
        return rootView;
    }

    private class CateogoryRunner extends AsyncTask<String,String,String>{
        private ArrayList<ArticleCateogoryModel> ArticleCat;


        @Override
        protected String doInBackground(String... strings) {
      //      String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.allSubDistrictList,"GET",null);
          String Content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/ad/allSubCategories","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArticleCat=new ArrayList<ArticleCateogoryModel>();
            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("Sub Categories");

               if (array.length()>0){
                   for(int i=0;i<array.length();i++){
                       JSONObject obj=array.getJSONObject(i);
                       ArticleCateogoryModel model=new ArticleCateogoryModel();
                       model.setId(obj.getString("id"));
                       model.setSubCategoryName(obj.getString("subCategoryName"));
                       JSONObject obj1=obj.getJSONObject("articleCategoriesList");
                       model.setCategoryName(obj1.getString("categoryName"));
                       ArticleCat.add(model);
                       ArticleCateogoryAdapter adapter=new ArticleCateogoryAdapter(getActivity(),ArticleCat);
                       cateogory.setAdapter(adapter);
                   }
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    private class SaveSubcatRunner extends AsyncTask<String,String,String>{
        String Categry,SubCategry,catcode;

        public SaveSubcatRunner(String arr_categories_code) {
            this.catcode=arr_categories_code;
        }

        @Override
        protected void onPreExecute() {
      Categry=catesave.getSelectedItem().toString();
         SubCategry=Subcatesave.getText().toString();
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("categoryName",Categry);
                object.accumulate("subCategoryName",SubCategry);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.storeSubCategories+SessinSave.getsessin("profile_id",getActivity())+"?categoryId=","POST",object);
      //      String Content=AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/ad/storeSubCategories/"+ SessinSave.getsessin("profile_id",getActivity())+"?categoryId=","POST",object);
            return Content;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }

    private class SavecatRunner extends AsyncTask<String,String,String>{
        String artcat;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            artcat=cat.getText().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("categoryName",artcat);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.storeCategories+SessinSave.getsessin("profile_id",getActivity()),"POST",object);
        //    String Content=AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/ad/storeCategories/900000020901"+SessinSave.getsessin("profile_id",getActivity()),"POST",object);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("Content====>",s);
            Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();

        }
    }

    private class CategoryRunner extends AsyncTask<String,String,String>{
        private ArrayList<Object> arraylist;
        JSONArray array;


        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.allCategories,"GET",null);
      //      String Content=AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/ad/allCategories","GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray array=new JSONArray(s);
                arr_categories = new String[array.length()];
                arr_categories_codes= new String[array.length()];
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        arr_categories[i] = object.getString("categoryName");
                        arr_categories_codes[i] = object.getString("id");
                    }
                    categorieslist = new ArrayList<String>(Arrays.asList(arr_categories));
                    categorieslist.add(0,"----Select----");
                    catesave.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,categorieslist));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catesave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    ((TextView)adapterView.getChildAt(0)).setTextSize(spinnerTextSize);
                    CategoryName=adapterView.getItemAtPosition(position).toString();
                    Log.i("state Name:-->",CategoryName);

                    for(int i = 0; i <array.length(); i++){
                        if(CategoryName.equalsIgnoreCase(  arr_categories[i])){
//                            Toast.makeText(getActivity(),arr_state_codes[i],Toast.LENGTH_LONG).show();
                       //     SessinSave.saveSession("MainLocation", arr_categories_codes[i],getActivity());
                            SaveSubcatRunner runner=new SaveSubcatRunner( arr_categories_codes[i]);
                            runner.execute();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
