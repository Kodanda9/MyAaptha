package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.LabTest_DetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.LabDetails_Model;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Admin on 6/5/2018.
 */

public class LabTestDetails_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<LabDetails_Model>model;
    private String labid;
    private String pId;
    private View localView;
    private Spinner spin;
    private String labname;
    private EditText pricelab,pricehome,disc,remark;
    private Button updat;
    private Dialog mDialog;
    private Typeface fontAwesomeFont;

//    private String spinid;

    public LabTestDetails_Adapter(Activity activity, ArrayList<LabDetails_Model> rowItems) {
        this.activity=activity;
        this.model=rowItems;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.labdetails_lv_row, null);

        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        TextView testname=(TextView)view.findViewById(R.id.labtest);
        TextView priceatlab=(TextView)view.findViewById(R.id.priceatlab);
        TextView priceathome=(TextView)view.findViewById(R.id.priceathome);
        TextView discount=(TextView)view.findViewById(R.id.discount);
        TextView remarks=(TextView)view.findViewById(R.id.remarks);
        final TextView Unhold=(Button)view.findViewById(R.id.unhold);



        final TextView  faiSymb = (TextView) view.findViewById(R.id.holdy);                   faiSymb.setTypeface(fontAwesomeFont);
        final TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        final TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);


        final LabDetails_Model data=model.get(i);
        labid=data.getId();
        pId=SessinSave.getsessin("profile_id",activity);
        testname.setText(data.getTestName());
        priceatlab.setText(data.getPriceAtLab());
        priceathome.setText(data.getPriceAtHome());
        discount.setText(data.getDiscount());
        remarks.setText(data.getRemarks());
        TVEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editform(data);
            }
        });
        faiSymb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TVEditIcon.setVisibility(View.INVISIBLE);
                TVDeleteIcon.setVisibility(View.INVISIBLE);
                faiSymb.setVisibility(View.INVISIBLE);
                Unhold.setVisibility(View.VISIBLE);
                HoldLabTestDetails task=new HoldLabTestDetails(labid);
                task.execute();
            }
        });
        Unhold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TVEditIcon.setVisibility(View.VISIBLE);
                TVDeleteIcon.setVisibility(View.VISIBLE);
                faiSymb.setVisibility(View.VISIBLE);
                Unhold.setVisibility(View.GONE);
                UnHoldLabTestDetails task=new UnHoldLabTestDetails(labid);
                task.execute();
            }
        });

        TVDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteTask task=new DeleteTask(labid);
                task.execute();
            }
        });
        return view;
    }

    private void Editform( final LabDetails_Model data) {
        localView = View.inflate(activity, R.layout.labedit_form, null);
        localView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in_enter));
        this.mDialog = new Dialog(activity, R.style.NewDialog);
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
     ImageView   cancel=(ImageView)this.mDialog.findViewById(R.id.cancel);
        updat=(Button)this.mDialog.findViewById(R.id.update);
        GetLabDetails runner=new GetLabDetails(data);
        runner.execute();
        ListLabTests task=new ListLabTests(data);
        task.execute();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        updat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateTask task=new UpdateTask(data);
                task.execute();
            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                     labname =adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class DeleteTask extends AsyncTask<String,String,String>{
       private String edit;

        public DeleteTask(String labid) {
            this.edit=labid;
        }


        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.deleteLabTestDetails+pId+"?id="+labid,"GET",null);
          //  String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/deleteLabTestDetails/"+pId+"?id="+labid,"GET",null);
            Log.i("========>",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
         Log.i("bhavani",s);
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest_DetailsFragment()).commit();
            super.onPostExecute(s);
        }
    }

    private class ListLabTests extends AsyncTask<String,String,String>{
        private final LabDetails_Model mdl;
        String[] arr_lab,arr_lab_codes;
        ArrayList<String> Lablist;

        public ListLabTests(LabDetails_Model data) {
            mdl =data;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.listLabTests,"GET",null);
       //     String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/listLabTests","GET",null);
        Log.i("Bhavani",Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                JSONObject jsonobject=new JSONObject(s);
                JSONArray jsonarray=jsonobject.getJSONArray("result");
                arr_lab = new String[jsonarray.length()];
                arr_lab_codes= new String[jsonarray.length()];
                if (jsonarray.length()>0){
                    for (int i=0;i<jsonarray.length();i++){
                        JSONObject jsonobject1= jsonarray.getJSONObject(i);
                        arr_lab[i]=jsonobject1.getString("labTestName");
                        arr_lab_codes[i]=jsonobject1.getString("id");
                        Log.i("Bhaavani",arr_lab[i]);
                        Log.i("Bhaavani",arr_lab_codes[i]);
                    }
                    Lablist = new ArrayList<String>(Arrays.asList(arr_lab));

                }
                spin.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,Lablist));
                for(int i = 0; i < jsonarray.length(); i++){
                    if(mdl.getTestName().equalsIgnoreCase(arr_lab[i])){
                        Log.i("Bhaavani",arr_lab[i]);
                        spin.setSelection(AppHelper.setValueToSpinner(spin,arr_lab[i]));

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }

    private class GetLabDetails extends AsyncTask<String,String,String> {
     private LabDetails_Model mode;
        String editid;

        public GetLabDetails(LabDetails_Model data) {
            mode=data;
        }


        @Override
        protected void onPreExecute() {
            editid=mode.getId();
            Log.i("vani",editid);
            super.onPreExecute();
        }




        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.updateLabTestDetails+pId+"?id="+editid,"GET",null);
      //      String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/updateLabTestDetails/"+pId+"?id="+editid,"GET",null);
          Log.i("Bhavani",ApisHelper.updateLabTestDetails+pId+"?id="+editid);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("Bhavani",s);
            try {
                JSONObject object=new JSONObject(s);
                JSONArray array=object.getJSONArray("map");
                JSONObject jsonObject=array.getJSONObject(0);
//                spinid=jsonObject.getString("testName");
                pricelab.setText(jsonObject.getString("priceAtLab"));
                pricehome.setText(jsonObject.getString("priceAtHome"));
                disc.setText(jsonObject.getString("discount"));
                 remark.setText(jsonObject.getString("remarks"));
                String pricelab=jsonObject.getString("priceAtLab");
                Log.i("Bhavani",pricelab);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest_DetailsFragment()).commit();
            super.onPostExecute(s);
        }
    }

    private class UpdateTask extends AsyncTask<String,String,String> {

        private  final LabDetails_Model mode;
        String testname,priceatlab,priceathome,discount,remarks,labid;
        String labids;

        public UpdateTask(LabDetails_Model data) {
            mode=data;
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject object=new JSONObject();
            try {
                object.accumulate("id",labids);
                object.accumulate("testName",testname);
                object.accumulate("priceAtLab",priceatlab);
                object.accumulate("priceAtHome",priceathome);
                object.accumulate("discount",discount);
                object.accumulate("remarks",remarks);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String content1=AsyncTaskHelper.makeServiceCall(ApisHelper.updateLabTestDetails+pId,"POST",object);
       //     String content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/labrest/updateLabTestDetails/"+pId,"POST",object);
            return content1;
        }

        @Override
        protected void onPreExecute() {
            testname=spin.getSelectedItem().toString();
            priceatlab=pricelab.getText().toString();
            priceathome=pricehome.getText().toString();
            discount=disc.getText().toString();
            remarks=remark.getText().toString();
            labids=mode.getId();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest_DetailsFragment()).commit();

            Toast.makeText(activity,"Successfully updated",Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }
    }


    private class UnHoldLabTestDetails extends AsyncTask<String,String,String>{
        private String lab;
        public UnHoldLabTestDetails(String labid) {
            lab=labid;

        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.unHoldLabTestDetails+SessinSave.getsessin("profile_id",activity)+"?id="+lab,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class HoldLabTestDetails extends AsyncTask<String,String,String>{
        String lab;
        public HoldLabTestDetails(String labid) {
            lab=labid;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.holdLabTestDetails+SessinSave.getsessin("profile_id",activity)+"?id="+lab,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
