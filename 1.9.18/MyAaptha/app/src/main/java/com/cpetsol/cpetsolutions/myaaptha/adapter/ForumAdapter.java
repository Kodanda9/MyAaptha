package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
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
import com.cpetsol.cpetsolutions.myaaptha.model.ForumModel;

import java.util.List;


public class ForumAdapter extends BaseAdapter {
    private final List<ForumModel> rowItems;
    private final Activity activity;
    private LayoutInflater inflater;

    public ForumAdapter(Activity activity, List<ForumModel> rowItems) {
        this.activity=activity;
        this.rowItems=rowItems;
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
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.forum_lv, null);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightgray));
        }



        TextView tvname1=(TextView)convertView.findViewById(R.id.forum1);
        LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.linear_layout);
        TextView TvForumLike=(TextView)convertView.findViewById(R.id.tvForumLikes);
        TextView TvDateCreated=(TextView)convertView.findViewById(R.id.tvDateCreated);
        final ForumModel model = rowItems.get(position);
        tvname1.setText(model.getQuestion());
        TvForumLike.setText("Likes: "+model.getLikes());
        TvDateCreated.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getDateCreated()));

    //    ETdate.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDateCreated()));


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //     activity.getFragmentManager().beginTransaction().replace(R.id.mainframe_container, ForumInDetails.newInstance(model.getId()) ).commit();

//                FourmIndetails Qrunner=new FourmIndetails(model.getId());
//                Qrunner.execute();

                Intent j=new Intent(activity, ForumsAnswerActivity.class);
                j.putExtra("id", model.getId());
                Log.i("ID",model.getId());
            activity.startActivity(j);

            }
        });
        return convertView;

    }


//    public class FourmIndetails extends AsyncTask<String, String, String> {
//        private final int id;
//        ProgressDialog pDialog;
//
//        public FourmIndetails(int id) {
//            this.id=id;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.forumInDetailed+"/"+ SessinSave.getsessin("profile_id",activity)+"?id="+id+"","GET",null);
//            return content;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            activity.getFragmentManager().beginTransaction().replace(R.id.mainframe_container, ForumInDetails.newInstance(s) ).commit();
//            if(pDialog.isShowing()){    pDialog.dismiss();    }
//            Log.i("forumInDetailed onPost:",s);
//        }
//    }//AddEduAsyncTask
    
    
    
}
