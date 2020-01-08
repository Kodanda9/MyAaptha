package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Medicines_HallFragment;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.Medicine_Hall_Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 8/28/2018.
 */

public class LabHall_Adapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private TextView drugname,status;
    private View localView;
    private Dialog mDialog;
    private  String upid;
    private Button button;
    private EditText drugName,sideeffects,drugdetails;
    private ArrayList<Medicine_Hall_Model> data;
    private Typeface fontAwesomeFont;


    public LabHall_Adapter(Activity activity, ArrayList<Medicine_Hall_Model> rowItems) {
        this.activity=activity;
        this.data=rowItems;
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
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.medicinehall_lvrow, null);


        if (i % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightblue));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        }

        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");

        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);



        drugname=(TextView)view.findViewById(R.id.hall_drugname);
        status=(TextView)view.findViewById(R.id.hall_approve);
        final Medicine_Hall_Model model=data.get(i);
        drugname.setText(model.getDrugName());
        status.setText(model.getStatus());
        upid=model.getId();
        Log.i("upid",upid);

        TVEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateForm(model);
            }
        });
        TVDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DeleteTask task=new DeleteTask(model);
                task.execute();
            }
        });
        return view;
    }

    private void UpdateForm(final Medicine_Hall_Model model) {

        localView = View.inflate(activity, R.layout.medicinehall_editform, null);
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

        drugName=(EditText)this.mDialog.findViewById(R.id.hall_drugname);
        drugdetails=(EditText)this.mDialog.findViewById(R.id.hall_details);
        sideeffects=(EditText)this.mDialog.findViewById(R.id.hall_sideeffects);
        button=(Button)this.mDialog.findViewById(R.id.edit);
        ImageView Cancel=(ImageView)this.mDialog.findViewById(R.id.cancel);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });



        MedicineHallTask task=new MedicineHallTask(model);
        task.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              UpdateTask task=new UpdateTask(model);
                task.execute();
            }
        });
    }

    private class MedicineHallTask extends AsyncTask<String,String,String> {
        private final Medicine_Hall_Model modl;
        String dId;

        public MedicineHallTask(Medicine_Hall_Model mdl) {
            modl = mdl;
        }

        @Override
        protected void onPreExecute() {
            dId =  modl.getId();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.updateMedicinesHall+dId,"GET",null);
            //   String Content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/updateMedicinesHall?id="+dId,"GET",null);
            Log.i("jjjjjjj", Content);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("sssssss",s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                drugName.setText(jsonObject.getString("drugName"));
                drugdetails.setText(jsonObject.getString("details"));
                sideeffects.setText(jsonObject.getString("sideEffects"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }

    private class UpdateTask extends AsyncTask<String,String,String>{
        private final Medicine_Hall_Model modl;
        String dname,ddetails,dstatus,sideeffect;
        String updateid;

        public UpdateTask(Medicine_Hall_Model model) {
            modl = model;
        }

        @Override
        protected void onPreExecute() {
            updateid=modl.getId();
            dname=drugName.getText().toString();
            ddetails=drugdetails.getText().toString();
            sideeffect=sideeffects.getText().toString();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject=new JSONObject();
            try {

                jsonObject.accumulate("id",updateid);
                jsonObject.accumulate("drugName",dname);
                jsonObject.accumulate("details",ddetails);
                jsonObject.accumulate("sideEffects",sideeffect);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String content1=AsyncTaskHelper.makeServiceCall(ApisHelper.updateMedicinesHall,"POST",jsonObject);
            //     String content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/updateMedicinesHall","POST",jsonObject);
            return content1;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("=======>",s);
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
            Toast.makeText(activity,"Successfully updated",Toast.LENGTH_LONG).show();
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Medicines_HallFragment()).commit();

            super.onPostExecute(s);
        }
    }

    private class DeleteTask extends AsyncTask<String,String,String>{
        String deleteId;
        private final Medicine_Hall_Model modl;
        public DeleteTask(Medicine_Hall_Model model) {
            modl=model;
        }

        @Override
        protected void onPreExecute() {
            deleteId=modl.getId();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.deleteMedicinesHall+deleteId,"GET",null);
            //       String Content=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/medicalRest/deleteMedicinesHall?id="+deleteId,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("result=======>",s);
            Toast.makeText(activity,"Successfully Deleted",Toast.LENGTH_LONG).show();
            activity.getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Medicines_HallFragment()).commit();
            super.onPostExecute(s);
        }
    }
}
