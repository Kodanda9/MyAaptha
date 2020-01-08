package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.CommentsAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.CommentsModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForumsAnswerActivity extends AppCompatActivity {

    private TextView Question,Date,FullName,Likes,DisLikes,FDislikes,Views,FLikes;
    private Typeface fontAwesomeFont;
    private String FRID;
    private ListView Comments;
    private ArrayList<CommentsModel> data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums_answer);
        FRID = getIntent().getStringExtra("id");

        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Forum");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        fontAwesomeFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fontawesome-webfont.ttf");

        Question=(TextView)findViewById(R.id.question);
        Comments=(ListView)findViewById(R.id.comments);
        Date=(TextView)findViewById(R.id.date);
        FullName=(TextView)findViewById(R.id.name);
        Likes=(TextView)findViewById(R.id.likes);
        DisLikes=(TextView)findViewById(R.id.dislikes);
        Views=(TextView)findViewById(R.id.views);
        FLikes=(TextView)findViewById(R.id.fai_like);      FLikes.setTypeface(fontAwesomeFont);
        FDislikes=(TextView)findViewById(R.id.fai_dislikes);    FDislikes.setTypeface(fontAwesomeFont);


        ForumsRunner runner=new ForumsRunner(FRID);
        runner.execute();




    }

    private class ForumsRunner extends AsyncTask<String,String,String>{
        String item;

        public ForumsRunner(String frid) {
            this.item=frid;
        }


        @Override
        protected String doInBackground(String... strings) {

            String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.forumInDetailed+ SessinSave.getsessin("profile_id",ForumsAnswerActivity.this)+"?id="+item,"GET",null);

            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            data=new ArrayList<CommentsModel>();
            try {
                JSONObject object=new JSONObject(s);
                JSONObject object1=object.getJSONObject("Forum Details");
                JSONObject object2=object1.getJSONObject("userDetails");
                Question.setText(object1.getString("question"));
                Date.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(object2.getString("dateCreated")));

                FullName.setText(object2.getString("fullName"));
                Likes.setText(object.getString("flikeCount"));
                DisLikes.setText(object.getString("fdisLikeCount"));
                JSONArray array=object.getJSONArray("Answers");





                if (array.length()>0){
                    for(int i=0;i<array.length();i++){
                        CommentsModel model=new CommentsModel();

                        JSONObject object3=array.getJSONObject(i);
                        model.setId(object3.getString("id"));
                        model.setAnswer(object3.getString("answer"));
                        model.setReferenceId(object3.getString("referenceId"));

                           JSONObject object5=object3.getJSONObject("forum");
                        model.setDateCreated(object2.getString("dateCreated"));
//                        model.setF(object3.getString("forum"));
                       JSONObject object4=object3.getJSONObject("userDetails");
                        model.setFullName(object4.getString("fullName"));
                        model.setDateCreated(object4.getString("dateCreated"));
                        data.add(model);

                    }
                }
                CommentsAdapter adapter=new CommentsAdapter(ForumsAnswerActivity.this,data);
                Comments.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
