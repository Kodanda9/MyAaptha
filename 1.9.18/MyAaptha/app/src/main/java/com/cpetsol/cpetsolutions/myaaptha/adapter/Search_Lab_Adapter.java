package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.Search_Lab_Model;

import java.util.ArrayList;

/**
 * Created by Admin on 6/6/2018.
 */

public class Search_Lab_Adapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Activity activity;
    private ArrayList<Search_Lab_Model>data;
    public Search_Lab_Adapter(Activity activity, ArrayList<Search_Lab_Model> rowItems) {
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
            view = inflater.inflate(R.layout.search_lab_lv_row, null);
        CheckBox check=(CheckBox)view.findViewById(R.id.checkbox);
        TextView lab=(TextView)view.findViewById(R.id.labname);
        final Search_Lab_Model labs=data.get(i);
        lab.setText(labs.getLabTestName());
        return view;
    }
}
