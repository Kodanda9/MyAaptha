package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.ArticleCateogoryModel;

import java.util.ArrayList;

/**
 * Created by Admin on 7/5/2018.
 */

public class ArticleCateogoryAdapter extends BaseAdapter {
    private ArrayList<ArticleCateogoryModel> ArticalItems;
    private  Activity activity;
    private LayoutInflater inflater;
    private ListView subcatlist;
    private Typeface fontAwesomeFont;
    private View editview;



    public ArticleCateogoryAdapter(Activity activity, ArrayList<ArticleCateogoryModel> articleCat) {
        this.activity=activity;
        this.ArticalItems=articleCat;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.admin_articles_lv_row, null);

        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        TextView artname=(TextView)view.findViewById(R.id.cateogoryname);
        TextView subartname=(TextView)view.findViewById(R.id.subcat);

//        TextView  faiSymb = (TextView) view.findViewById(R.id.tvfai_symb);                   faiSymb.setTypeface(fontAwesomeFont);
        TextView TVEditIcon= (TextView) view.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) view.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);
        final ArticleCateogoryModel model = ArticalItems.get(i);
        artname.setText(model.getCategoryName());
        subartname.setText(model.getSubCategoryName());
        TVEditIcon.setOnClickListener(new View.OnClickListener() {
            public Dialog editDialog;


            @Override
            public void onClick(View view) {
                editview=View.inflate(activity,R.layout.article_editor,null);
                editview.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
                this.editDialog=new Dialog(activity,R.style.NewDialog);
                this.editDialog.setContentView(editview);
                this.editDialog.setCancelable(true);
                this.editDialog.show();

                Window window = this.editDialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER | Gravity.CENTER;
                window.setGravity(Gravity.CENTER);
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
                wlp.dimAmount = 0.0f;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                wlp.windowAnimations = R.anim.slide_move;

                window.setAttributes(wlp);
                window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

            }
        });
        return view;
    }


}
