package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticlesModel;

import java.util.List;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class ArticleAdapterClass extends BaseAdapter {
    private final List<ArticlesModel> ArticalItems;
    private final Activity activity;
    private LayoutInflater inflater;

    public ArticleAdapterClass(Activity activity, List<ArticlesModel> rowItems) {
        this.activity = activity;
        this.ArticalItems = rowItems;
    }

    @Override
    public int getCount() {
        return ArticalItems.size();
    }

    @Override
    public Object getItem(int i) {
        return ArticalItems.get(i);
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
            convertView = inflater.inflate(R.layout.articales_lv_row, null);

        final ArticlesModel model = ArticalItems.get(position);

        Log.i("Model==>"+position,model.toString());

        TextView tvView = (TextView) convertView.findViewById(R.id.tv_article);
        TextView tvOverView = (TextView) convertView.findViewById(R.id.tv_overview);

        TextView tvauthor = (TextView) convertView.findViewById(R.id.tv_author);
        TextView publishdate = (TextView) convertView.findViewById(R.id.tv_DateTime);
      //  TextView tags = (TextView) convertView.findViewById(R.id.tags_tv);
       // TextView likes = (TextView) convertView.findViewById(R.id.likes_tv);
        TextView calender = (TextView) convertView.findViewById(R.id.calender_tv);
        TextView views = (TextView) convertView.findViewById(R.id.views_tv);
        tvauthor.setText(model.getAuthors());
        views.setText(model.getViews()+"Views");
        calender.setText(AppHelper.ConvertDateFormatYYYYMMDD2DDMMYYYY(model.getPublishDate()));
      //  tags.setText(model.getTags());

        TextView author=(TextView)convertView.findViewById(R.id.tv_contact_icon);
        TextView viewsicon=(TextView)convertView.findViewById(R.id.tv_views_icon);
    //    TextView likesicon=(TextView)convertView.findViewById(R.id.tv_likes_icon);
        TextView calendericon=(TextView)convertView.findViewById(R.id.tv_calender_icon);
     //   TextView tagsicon=(TextView)convertView.findViewById(R.id.tv_tags_icon);
        TextView readmore=(TextView) convertView.findViewById(R.id.btn_readmore);
        Typeface fontawesome;
        fontawesome = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        author.setTypeface(fontawesome);
        viewsicon.setTypeface(fontawesome);
   //     likesicon.setTypeface(fontawesome);
        calendericon.setTypeface(fontawesome);
     //   tagsicon.setTypeface(fontawesome);





        tvView.setText(model.getArticleName());
        if(model.getOverview() != null){
            tvOverView.setText(Html.fromHtml(model.getOverview()));
        }



        /*readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(activity, model.getId(), Toast.LENGTH_LONG).show();
//                activity.getFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, ArticalIndetails_fragment.newInstance(model.getId())).commit();
                Intent in = new Intent(activity, ArticleIndetailAct.class);
                in.putExtra("ArticalId",model.getId()   );
                activity.startActivity(in);



            }
        });*/


        return convertView;
    }
}
