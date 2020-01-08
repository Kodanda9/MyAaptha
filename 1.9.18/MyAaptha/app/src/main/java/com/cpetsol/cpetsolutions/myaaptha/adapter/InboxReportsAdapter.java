package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.InboxModel;

import java.util.ArrayList;

/**
 * Created by Admin on 6/30/2018.
 */

public class InboxReportsAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<InboxModel>report;
    private LayoutInflater inflater;
    private TextView patientname,doctor,date,status,docrev;

    public InboxReportsAdapter(FragmentActivity inboxActivity, ArrayList<InboxModel> reports) {
        this.activity=inboxActivity;
        this.report=reports;
    }

    @Override
    public int getCount() {
        return report.size();
    }

    @Override
    public Object getItem(int i) {
        return report.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null)
            inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view==null)
            view=inflater.inflate(R.layout.reports_lv_row,null);
        patientname=(TextView)view.findViewById(R.id.ptnm);
        doctor=(TextView)view.findViewById(R.id.doctor);
        date=(TextView)view.findViewById(R.id.date);
        status=(TextView)view.findViewById(R.id.status);
        docrev=(TextView)view.findViewById(R.id.docrev);
        final InboxModel model = report.get(i);
        Log.i("=====>",model.toString());
        patientname.setText(model.getFamilyNames());
        Log.i("+++++>",model.getFamilyNames());
        doctor.setText(model.getFullName());

      date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getInTime()));
        status.setText(model.getStatus());
        docrev.setText(model.getDetails());
        return view;
    }
}
