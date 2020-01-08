package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ArticalIndCommentAdapter;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ArticlesFragment;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticalAnswersModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleIndetailAct extends AppCompatActivity {
    private static String art_id;
    private View rootView;
    private JSONObject mainObj;
    private String likeDislikes,articalCount,comments;
    private String artId,artName,artDesc,artPublishDate,artTags,artOverview    ;
    private TextView TvArticalName,TvPostedBy,TvTags,TvPublishDate,TvDisLike,TvLike,TvOverView;
    private ArrayList<ArticalAnswersModel> listrowItems;
    private ListView LVAns;
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_indetail);
        String art_id = getIntent().getStringExtra("ArticalId");

    ArticalIndetails Qrunner=new ArticalIndetails(art_id);
        Qrunner.execute();
        
        ImageView imgBack=(ImageView)findViewById(R.id.goBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction() .replace(R.id.nav_content_Frame, new ArticlesFragment() ).commit();
            }
        });





    }

    private class ArticalIndetails extends AsyncTask<String, String, String> {
        private final String id;
        ProgressDialog pDialog;

        public ArticalIndetails(String id) {
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(ArticleIndetailAct.this,"Please wait","Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
        //    String content= AsyncTaskHelper.makeServiceCall(ApisHelper.articlesInDetailed+SessinSave.getsessin("profile_id",getApplicationContext())+"?id="+id+"","GET",null);
            String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/articlesInDetailed/900000010501?id=2" ,"GET",null);
        //    Log.i("URL", ApisHelper.articlesInDetailed+SessinSave.getsessin("profile_id",getApplicationContext())+"?id="+id+"");
           Log.i("URL", content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("forumInDetailed onPost:",s);

            setValues(s);

        }
    }//ArticalIndetails

    private void setValues(String s) {
        try {
            mainObj=new JSONObject(s);                       TvLike.setText(mainObj.getString("flikeCount"));  TvDisLike.setText(mainObj.getString("fdisLikeCount"));
            Log.i("jsonObj-->",mainObj.toString());

//           likesDislikesOfAns=mainObj.getString("Answers Like & dislike");
            JSONObject forumObj=new JSONObject(mainObj.getString("Forum Details"));
            JSONObject likesDislikesObj=new JSONObject(mainObj.getString("Answers Like & dislike "));
            TvArticalName.setText(forumObj.getString("question"));
            TvPublishDate.setText(forumObj.getString("dateCreated"));
            TvTags.setText(forumObj.getString("subCategories"));

            JSONObject udObj=new JSONObject(forumObj.getString("userDetails"));
            TvPostedBy.setText(udObj.getString("fullName") );

            listrowItems = new ArrayList<ArticalAnswersModel>();
            JSONArray ansArrray=new JSONArray(mainObj.getString("Answers") );
            if(ansArrray.length()>0){
                for (int i = 0; i < ansArrray.length(); i++){
                    JSONObject obj = ansArrray.getJSONObject(i);

                    String lkDislk= likesDislikesObj.getString(obj.getString("id"));
                    String[] result = lkDislk.substring(1,lkDislk.length()-1).split(",");
                    Log.i("======",result[0]+"      "+  result[1]);

                    ArticalAnswersModel model=new ArticalAnswersModel(obj.getString("id"),obj.getString("answer"),obj.getString("referenceId"),obj.getString("forum"),obj.getString("userDetails"),obj.getString("dateCreated"),obj.getString("viewStatus"),result[0],result[1] );
                    listrowItems.add(model);
                }

            }else{
                Toast.makeText(ArticleIndetailAct.this,"No Comments", Toast.LENGTH_LONG).show();
            }
            ArticalIndCommentAdapter adapter = new ArticalIndCommentAdapter(ArticleIndetailAct.this, listrowItems);
            LVAns.setAdapter(adapter);
            adapter.notifyDataSetChanged();

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
    }



}
