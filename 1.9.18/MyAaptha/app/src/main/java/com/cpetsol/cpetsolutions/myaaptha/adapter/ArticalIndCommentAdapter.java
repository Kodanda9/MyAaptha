package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticalAnswersModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class ArticalIndCommentAdapter  extends BaseAdapter {
    private final ArrayList<ArticalAnswersModel> rowItems;
    private final Activity activity;
    private LayoutInflater inflater;

    public ArticalIndCommentAdapter(Activity activity, ArrayList<ArticalAnswersModel> listrowItems) {
        this.activity=activity;
        this.rowItems=listrowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int i) {
        return rowItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.artical_ans_lv, null);

        TextView tvname1=(TextView)convertView.findViewById(R.id.fLvTv1);
        TextView TvForumLike=(TextView)convertView.findViewById(R.id.tvLike);
        TextView TvWrittenBy=(TextView)convertView.findViewById(R.id.writtenBy);
        TextView TvDateCreated=(TextView)convertView.findViewById(R.id.fLvTv2);
        TextView TvDisLike=(TextView)convertView.findViewById(R.id.tvDisLike);
        final ArticalAnswersModel model = rowItems.get(position);
        tvname1.setText("Answer: "+ model.getAnswer());
//        TvDateCreated.setText(AppHelper.ConvertJsonDate(model.getDateCreated()));
        TvForumLike.setText(model.getLikes());
        TvDisLike.setText(model.getDislikes());

        try {
            JSONObject userRegdObj=new JSONObject(model.getUserDetails());
            TvWrittenBy.setText(userRegdObj.getString("fullName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        tvname1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FourmIndetails Qrunner=new FourmIndetails(model.getId());
//                Qrunner.execute();
//            }
//        });

        return convertView;
    }
}
