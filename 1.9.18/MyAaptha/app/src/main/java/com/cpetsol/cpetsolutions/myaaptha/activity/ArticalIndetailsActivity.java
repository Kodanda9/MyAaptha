package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticalAnswersModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticalIndetailsActivity extends AppCompatActivity {
    private TextView TvArticalName,TvPostedBy,TvTags,TvPublishDate,TvDisLike,TvLike,TvOverView;
    private ListView LVAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artical_indetails_activity);
        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Articles");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String art_id = getIntent().getStringExtra("ArticalId");

        ArticalIndetails Qrunner=new ArticalIndetails(art_id);
        Qrunner.execute();


        TvArticalName=(TextView)findViewById(R.id.articalTitle);
        TvPublishDate=(TextView)findViewById(R.id.publishDate);
        TvTags=(TextView)findViewById(R.id.tags);
        TvPostedBy=(TextView)findViewById(R.id.postedBy);
        TvOverView=(TextView)findViewById(R.id.overView);
        TvLike=(TextView)findViewById(R.id.tvLike);
        TvDisLike=(TextView)findViewById(R.id.tvDisLike);
        LVAns=(ListView)findViewById(R.id.lvComments);
        ImageView imgBack=(ImageView)findViewById(R.id.goBack);


    }//onCreate

    public class ArticalIndetails extends AsyncTask<String, String, String> {
        private final String id;
        ProgressDialog pDialog;
        private JSONObject mainObj;
        private ArrayList<ArticalAnswersModel> listrowItems;

        public ArticalIndetails(String id) {
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(ArticalIndetailsActivity.this,"Please wait","Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.articlesInDetailed+SessinSave.getsessin("profile_id",ArticalIndetailsActivity.this)+"?id="+id+"","GET",null);
            Log.i("URL", ApisHelper.articlesInDetailed+SessinSave.getsessin("profile_id",ArticalIndetailsActivity.this)+"?id="+id+"");
            return content;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                mainObj=new JSONObject(s);                       TvLike.setText(mainObj.getString("likeCount"));  TvDisLike.setText(mainObj.getString("disLikeCount"));
                Log.i("jsonObj-->",mainObj.toString());

//           likesDislikesOfAns=mainObj.getString("Answers Like & dislike");
                JSONObject artObj=new JSONObject(mainObj.getString("Articles Details"));
                JSONObject likesDislikesObj=new JSONObject(mainObj.getString("Comments Like & dislike "));
                JSONArray commentsArray=new JSONArray(mainObj.getString("Comments"));
                for (int i=0;i<commentsArray.length();i++){
                    JSONObject commentsObj = commentsArray.getJSONObject(i);
                }


                TvArticalName.setText(artObj.getString("articleName"));
                TvPublishDate.setText(AppHelper.ConvertJsonDateWithSec(artObj.getString("dateCreated")));
                TvTags.setText(artObj.getString("subCategories"));

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    TvOverView.setText(Html.fromHtml(artObj.getString("articleDescription"),Html.FROM_HTML_MODE_LEGACY));
                    //TvOverView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                } else {
                    TvOverView.setText(Html.fromHtml(artObj.getString("articleDescription")));
                }

//                JSONObject udObj=new JSONObject(artObj.getString("userDetails"));
                TvPostedBy.setText(artObj.getString("authors") );

//                listrowItems = new ArrayList<ArticalAnswersModel>();
//                JSONArray ansArrray=new JSONArray(mainObj.getString("Answers") );
//                if(ansArrray.length()>0){
//                    for (int i = 0; i < ansArrray.length(); i++){
//                        JSONObject obj = ansArrray.getJSONObject(i);
//
//                        String lkDislk= likesDislikesObj.getString(obj.getString("id"));
//                        String[] result = lkDislk.substring(1,lkDislk.length()-1).split(",");
//                        Log.i("======",result[0]+"      "+  result[1]);
//
//                        ArticalAnswersModel model=new ArticalAnswersModel(obj.getString("id"),obj.getString("answer"),obj.getString("referenceId"),obj.getString("forum"),obj.getString("userDetails"),obj.getString("dateCreated"),obj.getString("viewStatus"),result[0],result[1] );
//                        listrowItems.add(model);
//                    }
//
//                }else{
//                    Toast.makeText(ArticalIndetailsActivity.this,"No Comments", Toast.LENGTH_LONG).show();
//                }
//                ArticalIndCommentAdapter adapter = new ArticalIndCommentAdapter(ArticalIndetailsActivity.this, listrowItems);
//                LVAns.setAdapter(adapter);
//                adapter.notifyDataSetChanged();

////            likeDislikes = jsonObj.getString("Comments Like & dislike");
//            articalCount = jsonObj.getString("Saved Article Count");
////            comments = jsonObj.getString("Comments");
//            String artDetails=jsonObj.getString("Articles Details");
//            JSONObject jsonArtDetails=new JSONObject(artDetails);
//            artId   =jsonArtDetails.getString("id");
//            artName =jsonArtDetails.getString("articleName");
//            artOverview =jsonArtDetails.getString("overview");
//            artTags =jsonArtDetails.getString("tags");
//            artPublishDate =jsonArtDetails.getString("publishDate");
//            artDesc =jsonArtDetails.getString("articleDescription");
//
//            TvArticalName.setText(artName);
//            TvPublishDate.setText(artPublishDate);
//            TvTags.setText(artTags);
//            TvOverView.setText(Html.fromHtml(artDesc));
            }catch (Exception e){
                e.printStackTrace();
            }
            if(pDialog.isShowing()){    pDialog.dismiss();    }
        }
    }//ArticalIndetails

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
