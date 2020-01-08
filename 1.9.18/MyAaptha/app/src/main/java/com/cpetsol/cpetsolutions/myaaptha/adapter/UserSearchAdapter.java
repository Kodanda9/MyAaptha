package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserSearchModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import java.util.ArrayList;

/**
 * Created by Admin on 5/17/2018.
 */

public class UserSearchAdapter  extends BaseAdapter {

    private final ArrayList<UserSearchModel> FRItems;
    private  Activity activity;
    private LayoutInflater inflater;

    public UserSearchAdapter(Activity activity, ArrayList<UserSearchModel> rowItems) {
        this.activity=activity;
        this.FRItems=rowItems;
    }

    @Override
    public int getCount() {
        return FRItems.size();
    }

    @Override
    public Object getItem(int i) {
        return FRItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.fandr_lv_row, null);
         final  UserSearchModel   model=FRItems.get(position);

        TextView tvname=(TextView)convertView.findViewById(R.id.frName);
        tvname.setText(model.getFullName());
        tvname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                activity.getFragmentManager().beginTransaction().replace(R.id.nav_main_replace, PProfileDetails.newInstance(model.getId()) ).commit();
            }
        });
        TextView tvrelation=(TextView)convertView.findViewById(R.id.frRelation);
        if (model.getRelation().equalsIgnoreCase("Relative")){
            tvrelation.setBackgroundResource(R.drawable.relativesimg);
        }else{
            tvrelation.setBackgroundResource(R.drawable.friendsimg);
        }

        TextView BtnUnfriend=(TextView) convertView.findViewById(R.id.btnUnFriend);
        RelativeLayout Norecords=(RelativeLayout) convertView.findViewById(R.id.rlsuggestlist);
        BtnUnfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnfriendAsyncTask   runner=new UnfriendAsyncTask(model.getId());
                runner.execute();
            }
        });

    //    tvrelation.setText(model.getRelation());
      /* if(model.getRelation().equalsIgnoreCase("Friend"))
        {
            tvrelation.setText("Friend");
        }else{
            tvrelation.setText("Relative");
        }*/



        return convertView;
    }

    private class UnfriendAsyncTask extends AsyncTask<String, String, String> {
        private final String id;

        public UnfriendAsyncTask(String id) {
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall( ApisHelper.unFriend+"/"+ SessinSave.getsessin("profile_id",activity)+"?id="+id+"","GET",null);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("unFriend",s);
            if(s.equals("true")){
                Toast.makeText(activity,"Successfully Removed",Toast.LENGTH_LONG).show();
            }else if(s.equalsIgnoreCase("false")){
                Toast.makeText(activity,"Not done",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(activity,"Successfully Removed",Toast.LENGTH_LONG).show();
            }
            //Refresh Fragment Class
//            activity.getFragmentManager().beginTransaction().replace(R.id.nav_main_replace, new LoginFriendsAndRelatives()).commit();
        }
    }//UnfriendAsyncTask








}
