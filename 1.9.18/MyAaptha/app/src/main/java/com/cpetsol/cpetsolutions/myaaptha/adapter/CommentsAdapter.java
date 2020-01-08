package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.ForumsAnswerActivity;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.CommentsModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/26/2018.
 */

public class CommentsAdapter extends BaseAdapter {
    private ArrayList<CommentsModel>item;
    private LayoutInflater inflater;
    private Activity activity;
    private Typeface fontAwesomeFont;


    public CommentsAdapter(ForumsAnswerActivity forumsAnswerActivity, ArrayList<CommentsModel> data) {
        this.activity=forumsAnswerActivity;
        this.item=data;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
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
            view = inflater.inflate(R.layout.comments_lv_row, null);
        TextView Answer=(TextView)view.findViewById(R.id.ans);
        TextView Date=(TextView)view.findViewById(R.id.date);
        TextView Name=(TextView)view.findViewById(R.id.name);
        TextView Likes=(TextView)view.findViewById(R.id.likes);
        TextView Dislikes=(TextView)view.findViewById(R.id.dislikes);
        TextView Reply=(TextView)view.findViewById(R.id.replyt);
        final LinearLayout LReply=(LinearLayout)view.findViewById(R.id.LLReply);
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        TextView  Likesf = (TextView) view.findViewById(R.id.fai_like);                   Likesf.setTypeface(fontAwesomeFont);
        TextView  Dislikesf = (TextView) view.findViewById(R.id.fai_dislikes);

        Dislikesf.setTypeface(fontAwesomeFont);
        final CommentsModel model=item.get(i);
        Answer.setText(model.getAnswer());
        Date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDateCreated()));
        Name.setText(model.getFullName());
        Log.i("Answer",model.getAnswer());
       if(model.getReferenceId()=="0"){
           

      }else {

      }

        Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LReply.getVisibility()==view.VISIBLE){
                    LReply.setVisibility(view.GONE);
                }else{
                    LReply.setVisibility(view.VISIBLE);
                }
            }
        });
        return view;
    }
}
