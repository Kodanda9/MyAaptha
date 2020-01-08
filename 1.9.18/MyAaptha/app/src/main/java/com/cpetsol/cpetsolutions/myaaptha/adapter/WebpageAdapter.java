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
import com.cpetsol.cpetsolutions.myaaptha.model.WebPagesModel;

import java.util.ArrayList;

/**
 * Created by Admin on 8/21/2018.
 */

public class WebpageAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<WebPagesModel> model;
    private LayoutInflater inflater;
    public WebpageAdapter(Activity activity, ArrayList<WebPagesModel> arraylist) {
        this.activity=activity;
        this.model=arraylist;
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.webpagelv_row, null);
        CheckBox Check=(CheckBox)view.findViewById(R.id.check);
        TextView Webpage=(TextView) view.findViewById(R.id.webpage);
        final WebPagesModel mode=model.get(position);
        Webpage.setText(mode.getName());
        return view;
    }
}
