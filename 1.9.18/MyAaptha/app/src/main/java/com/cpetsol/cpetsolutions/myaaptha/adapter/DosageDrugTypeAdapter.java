package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.DosageDrugTypeModel;

import java.util.ArrayList;

/**
 * Created by Admin on 8/27/2018.
 */

public class DosageDrugTypeAdapter extends BaseAdapter{
    private ArrayList<DosageDrugTypeModel> userItems;
    private  Activity activity;
    private LayoutInflater inflater;
    private Typeface fontAwesomeFont;


    public DosageDrugTypeAdapter(Activity activity, ArrayList<DosageDrugTypeModel> arraylist) {
        this.activity=activity;
        this.userItems=arraylist;
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
            view = inflater.inflate(R.layout.dosage_drugtype_lv_row, null);
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        TextView drugname=(TextView)view.findViewById(R.id.drugname_drug);
        TextView drugtype=(TextView)view.findViewById(R.id.drugType_drug);
        TextView dosage=(TextView)view.findViewById(R.id.dosage_drug);
        final DosageDrugTypeModel drugs=userItems.get(i);
        drugname.setText(drugs.getDrugName());
        drugtype.setText(drugs.getDrugType());
        dosage.setText(drugs.getDosage());
        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);

        return view;
    }
}
