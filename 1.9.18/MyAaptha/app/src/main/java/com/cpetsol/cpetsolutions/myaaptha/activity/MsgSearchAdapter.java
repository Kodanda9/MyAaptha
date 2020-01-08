package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.MsgSearchModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/20/2018.
 */
public class MsgSearchAdapter extends BaseAdapter{
    private Activity activity;
    private ArrayList<MsgSearchModel>model;
    private LayoutInflater inflater;

    public MsgSearchAdapter(MessageBox messageBox, ArrayList<MsgSearchModel> item) {
        this.activity=messageBox;
        this.model=item;
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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.msgs_lv_row, null);
        TextView Name=(TextView)view.findViewById(R.id.name);
        TextView Relative=(TextView) view.findViewById(R.id.relation);
        Button Message=(Button)view.findViewById(R.id.msg);
        final  MsgSearchModel  models=model.get(i);
        Name.setText(models.getFullName());
        if (models.getRelation().equalsIgnoreCase("Relative")){
            Relative.setBackgroundResource(R.drawable.relativesimg);
        }else{
            Relative.setBackgroundResource(R.drawable.friendsimg);
        }





        return view;
    }
}
