package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ArticalIndCommentAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticalAnswersModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticalIndetails_fragment extends Fragment {

    private static String art_id;
    private View rootView;
    private JSONObject mainObj;
    private String likeDislikes,articalCount,comments;
    private String artId,artName,artDesc,artPublishDate,artTags,artOverview    ;
    private TextView TvArticalName,TvPostedBy,TvTags,TvPublishDate,TvDisLike,TvLike,TvOverView;
    private ArrayList<ArticalAnswersModel> listrowItems;
    private ListView LVAns;
    public ArticalIndetails_fragment() {
    }
    public static ArticalIndetails_fragment newInstance(String s) {
        ArticalIndetails_fragment frag=new ArticalIndetails_fragment();
        art_id=s;
        return  frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)
        {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.artical_indetails_fragment_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView,getActivity());


            ArticalIndetails Qrunner=new ArticalIndetails(art_id);
            Qrunner.execute();

            TvArticalName=(TextView)rootView.findViewById(R.id.articalTitle);
            TvPublishDate=(TextView)rootView.findViewById(R.id.publishDate);
            TvTags=(TextView)rootView.findViewById(R.id.tags);
            TvPostedBy=(TextView)rootView.findViewById(R.id.postedBy);
            TvOverView=(TextView)rootView.findViewById(R.id.overView);
            TvLike=(TextView)rootView.findViewById(R.id.tvLike);
            TvDisLike=(TextView)rootView.findViewById(R.id.tvDisLike);
            LVAns=(ListView)rootView.findViewById(R.id.lvComments);
            ImageView imgBack=(ImageView)rootView.findViewById(R.id.goBack);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction() .replace(R.id.nav_content_Frame, new ArticlesFragment() ).commit();
                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
        return rootView;
    }//onCreateView



    public class  ArticalIndetails extends AsyncTask<String, String, String> {
        private final String id;
        ProgressDialog pDialog;

        public ArticalIndetails(String id) {
            this.id=id;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.articlesInDetailed+"/"+ SessinSave.getsessin("profile_id",getActivity())+"?id="+id+"","GET",null);
            Log.i("URL",ApisHelper.articlesInDetailed+"/"+ SessinSave.getsessin("profile_id",getActivity())+"?id="+id+"");
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
                Toast.makeText(getActivity(),"No Comments", Toast.LENGTH_LONG).show();
            }
            ArticalIndCommentAdapter adapter = new ArticalIndCommentAdapter(getActivity(), listrowItems);
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
