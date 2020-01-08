package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfileActivity extends Activity {

    private String userProfile;
    TextView TvUserName,points, fullname,gender,userid,email,mobileno,bloodgroup;
    Button addfrnd,addrltv,reqsnt,unfrnd,accept,reject;
    ImageView UserImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("User Profile");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addfrnd=(Button)findViewById(R.id.AddFrndBtn) ;
        addrltv=(Button)findViewById(R.id.AddRelativeBtn);
        reqsnt=(Button)findViewById(R.id.requestsent);
        unfrnd=(Button)findViewById(R.id.btnUnFriend);
        accept=(Button)findViewById(R.id.Accept);
        reject=(Button)findViewById(R.id.Reject);
        UserImage=(ImageView)findViewById(R.id.userImage);


        TvUserName = (TextView) findViewById(R.id.userFullName);

;
        points = (TextView) findViewById(R.id.userPointsData);
        email = (TextView) findViewById(R.id.email);
        mobileno = (TextView) findViewById(R.id.mobileno);
        bloodgroup = (TextView) findViewById(R.id.bloodgroup);
        userid = (TextView) findViewById(R.id.useridData); ;
        fullname = (TextView) findViewById(R.id.userFullNameData);



        userProfile = getIntent().getStringExtra("userProfile");
        Log.i("user====>",userProfile);

        SearchUserInDetails userrunner = new SearchUserInDetails(userProfile);
        userrunner.execute();
addfrnd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FriendRequest frndrqst=new FriendRequest(userProfile);
        frndrqst.execute();
    }
});
        addrltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeRequest rltvrqst=new RelativeRequest(userProfile);
                rltvrqst.execute();
            }
        });
        unfrnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnFriend frndrqst=new UnFriend(userProfile);
                frndrqst.execute();
            }
        });

    }


    public class SearchUserInDetails extends AsyncTask<String, String, String> {
        final String toid;
        private JSONObject mainObj;
        private ProgressDialog pDialog;

        public SearchUserInDetails(String id) {
            this.toid = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog= ProgressDialog.show(UserProfileActivity.this,"Please wait","Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {

            String Content = AsyncTaskHelper.makeServiceCall(ApisHelper.allUsersDetailsById+ SessinSave.getsessin("profile_id",UserProfileActivity.this) + "?id=" +toid + "","GET",null);
        //    String Content = AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/userRest/allUsersDetailsById/"+ SessinSave.getsessin("profile_id",UserProfileActivity.this) + "?id=" +toid + "","GET",null);
                Log.i("Content======>",Content);

            return Content;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("asd",s);
            if (s != null) {
                try {
                    mainObj = new JSONObject(s);
                    Log.i("jsonObject-->", mainObj.toString());
                    JSONObject jsonObject1=new JSONObject(mainObj.getString("User Details"));

                    points.setText(jsonObject1.getString("points"));
                    userid.setText(jsonObject1.getString("profileId"));
                    email.setText(jsonObject1.getString(""));
                    mobileno.setText(jsonObject1.getString(""));
                    bloodgroup.setText(jsonObject1.getString(""));
                    email.setText(jsonObject1.getString(""));
                    mobileno.setText(jsonObject1.getString("mobileNo"));
                    bloodgroup.setText(jsonObject1.getString(""));
                    fullname.setText(jsonObject1.getString("fullName"));



                    if(jsonObject1.getString("gender").equalsIgnoreCase("Male")){
                   UserImage.setImageResource(R.drawable.male);
                    }else {
                        UserImage.setImageResource(R.drawable.female);
                    }
                    gender.setText(jsonObject1.getString("gender"));
                    fullname.setText(jsonObject1.getString("fullName"));
                     if(mainObj.getString("Reuest Count").equalsIgnoreCase("1")){
                        addfrnd.setVisibility(View.GONE);
                        addrltv.setVisibility(View.GONE);
                        reqsnt.setVisibility(View.VISIBLE);
                    }else if(mainObj.getString("Reuest Count").equalsIgnoreCase("0")&& mainObj.getString("Reuest Count_Reverse").equalsIgnoreCase("0")&& mainObj.getString("Accepted Count").equalsIgnoreCase("0")&&mainObj.getString("Accepted Count_Reverse").equalsIgnoreCase("0")){
                        addfrnd.setVisibility(View.VISIBLE);
                        addrltv.setVisibility(View.VISIBLE);
                        reqsnt.setVisibility(View.GONE);
                         unfrnd.setVisibility(View.GONE);
                    }else if(mainObj.getString("Accepted Count").equalsIgnoreCase("1")||mainObj.getString("Accepted Count_Reverse").equalsIgnoreCase("1")){
                        addfrnd.setVisibility(View.GONE);
                        addrltv.setVisibility(View.GONE);
                        reqsnt.setVisibility(View.GONE);
                        unfrnd.setVisibility(View.VISIBLE);
                    }else if(mainObj.getString("Reuest Count_Reverse").equalsIgnoreCase("1")){
                        addfrnd.setVisibility(View.GONE);
                        addrltv.setVisibility(View.GONE);
                        reqsnt.setVisibility(View.GONE);
                        unfrnd.setVisibility(View.GONE);
                        accept.setVisibility(View.VISIBLE);
                        reject.setVisibility(View.VISIBLE);
                    }




                } catch (JSONException e) {
                    Log.e("JSON Parser", "Error parsing data " + e.toString());
                    e.printStackTrace();

                }

                // Do you work here on success
            } else {
//                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
            if(pDialog.isShowing()){  pDialog.dismiss();  }
        }

    }


    private class FriendRequest extends AsyncTask<String,String,String>{
   String tId;

        public FriendRequest(String userProfile) {
            this.tId=userProfile;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/addRequest"+"/" + SessinSave.getsessin("profile_id",UserProfileActivity.this)+"?to_Id="+tId+"&relation=Friend","GET",null);
            // http://myaaptha.com/pp/userRest/addRequest/900000010501?to_Id=900000200802&relation=Friend
            return Content1;
        }


        @Override
        protected void onPostExecute(String s) {
            Log.i("array==>",s);
            SearchUserInDetails userrunner = new SearchUserInDetails(userProfile);
            userrunner.execute();
            super.onPostExecute(s);
        }
    }

    private class RelativeRequest extends AsyncTask<String,String,String>{
        String tId;
        public RelativeRequest(String userProfile) {
            this.tId=userProfile;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/addRequest"+"/" + SessinSave.getsessin("profile_id",UserProfileActivity.this)+"?to_Id="+tId+"&relation=Relative","GET",null);
            // http://myaaptha.com/pp/userRest/addRequest/900000010501?to_Id=900000200802&relation=Friend
            return Content1;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("result",s);
            SearchUserInDetails userrunner = new SearchUserInDetails(userProfile);
            userrunner.execute();

            super.onPostExecute(s);
        }
    }

    private class UnFriend extends AsyncTask<String,String,String>{
        String tId;
        public UnFriend(String userProfile) {
            this.tId=userProfile;
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content1=AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/unFriend"+"/" + SessinSave.getsessin("profile_id",UserProfileActivity.this)+"?id="+tId+"","GET",null);
            // http://myaaptha.com/pp/userRest/addRequest/900000010501?to_Id=900000200802&relation=Friend
            return Content1;
        }

        @Override
        protected void onPostExecute(String s) {
            SearchUserInDetails userrunner = new SearchUserInDetails(userProfile);
            userrunner.execute();
            super.onPostExecute(s);
        }
    }
}
