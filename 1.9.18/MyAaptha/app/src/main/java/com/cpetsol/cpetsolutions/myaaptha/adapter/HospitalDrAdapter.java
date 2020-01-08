package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.HospitalOverviewActivity;
import com.cpetsol.cpetsolutions.myaaptha.model.HospitalDrModel;

import java.util.ArrayList;

/**
 * Created by Admin on 6/30/2018.
 */

public class HospitalDrAdapter extends BaseAdapter {

    private Activity activity;
    private final ArrayList<HospitalDrModel> userItems;
    private LayoutInflater inflater;
    private TextView DrName,qualification,HspNme,locality,cost,Days;
    private String friVals,monVals,tueVals,wedVals,thuVals,satVals,sunVals;
    public HospitalDrAdapter(HospitalOverviewActivity hospitalOverviewActivity, ArrayList<HospitalDrModel> rowItems) {
        this.activity=hospitalOverviewActivity;
        this.userItems=rowItems;
    }

    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int i) {
        return userItems.get(i);
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
            view = inflater.inflate(R.layout.hospitaldoctor_lvrow, null);
        DrName=(TextView)view.findViewById(R.id.DrName);
        qualification=(TextView)view.findViewById(R.id.qualification);
        Days=(TextView)view.findViewById(R.id.days);
      /*  HspNme=(TextView)view.findViewById(R.id.hspnme);
        locality=(TextView)view.findViewById(R.id.locality);*/
        cost=(TextView)view.findViewById(R.id.cost);
        final HospitalDrModel model=userItems.get(i);

        Log.i("====>",model.toString());

        DrName.setText("Dr."+model.getName());
        qualification.setText(model.getQualification()+","+model.getSpecialization()+","+model.getExperience()+"Years Experience");
       /* HspNme.setText(model.getHospitalName());
        locality.setText(model.getLocalities());*/
         cost.setText("Rs."+model.getPrice());

       /* Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/"+model.getLongitude())));
            }
        });*/


        if(model.getSunday().equalsIgnoreCase("null")){
            sunVals ="";
        }else{
            sunVals ="SUN :"+model.getSunday()+"   ";
        }
        Log.i("+++++++++",model.getFriday());
        if(model.getFriday().equalsIgnoreCase("null")){
            friVals="";
        }else{
            friVals ="FRI :"+model.getFriday()+"   " ;
        }
        if(model.getMonday().equalsIgnoreCase("null")){
            monVals="";
        }else{
            monVals ="MON :"+model.getMonday()+"   " ;
        }
        if(model.getTuesday().equalsIgnoreCase("null")){
            tueVals="";
        }else{
            tueVals ="TUE :"+model.getTuesday()+"   " ;
        }
        if(model.getWednesday().equalsIgnoreCase("null")){
            wedVals="";
        }else{
            wedVals ="WED :"+model.getWednesday()+"   " ;
        }
        if(model.getThursday().equalsIgnoreCase("null")){
            thuVals="";
        }else{
            thuVals ="THU :"+model.getThursday()+"   " ;
        }
        if(model.getSaturday().equalsIgnoreCase("null")){
            satVals="";
        }else{
            satVals ="SAT :"+model.getSaturday();
        }


        Days.setText(sunVals+monVals+tueVals+wedVals+thuVals+friVals+satVals);





        return view;
    }
}
