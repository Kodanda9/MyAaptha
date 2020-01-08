package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;

/**
 * Created by Admin on 8/24/2018.
 */

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private String[] QuestionList;
    private String[] AnswerList;
    private LayoutInflater inflater;

    public CustomAdapter(Activity activity, String[] questionslist, String[] answerslist) {
        this.activity = activity;
        this.QuestionList = questionslist;
        this.AnswerList = answerslist;
    }

    @Override
    public int getCount() {
        return QuestionList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.faqslv_row, null);
        if (i % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        }
        TextView Question = (TextView)view.findViewById(R.id.question);
        TextView Answer = (TextView) view.findViewById(R.id.answer);
        Question.setText(QuestionList[i]);
        Answer.setText(AnswerList[i]);
        return view;
    }
}
