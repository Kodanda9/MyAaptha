package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.PrescriptionPage;
import com.cpetsol.cpetsolutions.myaaptha.model.PrescriptionModel;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.FileLoader;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.listener.FileRequestListener;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.pojo.FileResponse;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.request.FileLoadRequest;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Admin on 6/22/2018.
 */

public class PrescriptionlabAdapter extends BaseAdapter {
    private final ArrayList<PrescriptionModel> userItems;
    private Activity activity;
    private LayoutInflater inflater;
    private Typeface fontAwesomeFont;
    private Dialog imageOrFileViewDialog;


    public PrescriptionlabAdapter(PrescriptionPage prescriptionPage, ArrayList<PrescriptionModel> rowItems) {
        this.activity = prescriptionPage;
        this.userItems = rowItems;
    }


    @Override
    public int getCount() {
        return userItems.size();
    }

    @Override
    public Object getItem(int i) {
        return userItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.prescriptionlablvrow, null);
        if (position % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.summer));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(activity, R.color.lightblue));
        }
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");
        final PrescriptionModel model = userItems.get(position);
        TextView test = (TextView) view.findViewById(R.id.testname);
        TextView TvFileName = (TextView) view.findViewById(R.id.tvFileName);

    if(model.getFileName().equalsIgnoreCase("")|| model.getFileName().equalsIgnoreCase("null")|| model.getFileName().isEmpty() ){
        TvFileName.setVisibility(View.GONE);
    }else {
        TvFileName.setVisibility(View.VISIBLE);
    }

        TvFileName.setTypeface(fontAwesomeFont);
        Log.i("+++", model.getTestName());
   /*     Toast.makeText(activity, "" + model.getTestName(), Toast.LENGTH_SHORT).show();*/
        test.setText(model.getTestName());
        TvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileLoader.with(activity)
                     .load("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg")
                      /*  .load("http://www.myaaptha.com/pp/UserFiles/900000010501/30_U_2018-calander.pdfdecryptedFile")*/
                        .fromDirectory("MyAaptha", FileLoader.DIR_EXTERNAL_PUBLIC)
                        .asFile(new FileRequestListener<File>() {
                            @Override
                            public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                                Glide.with(activity).load(response.getBody()).into(Imgpic);
                                showImageOrFile(model);
                            }

                            @Override
                            public void onError(FileLoadRequest request, Throwable t) {
                                Log.d("Activity", "onError: " + t.getMessage());
                            }
                        });


            }
        });
        return view;
    }

    private void showImageOrFile(PrescriptionModel model) {
        try {
            View imageOrFileView = View.inflate(activity, R.layout.showimageorview, null);
            imageOrFileView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in_enter));
            this.imageOrFileViewDialog = new Dialog(activity, R.style.NewDialog);
            this.imageOrFileViewDialog.setContentView(imageOrFileView);
            this.imageOrFileViewDialog.setCancelable(true);
            this.imageOrFileViewDialog.show();

            Window window = this.imageOrFileViewDialog.getWindow();
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

//       WebView wbvw=(WebView)this.imageOrFileViewDialog.findViewById(R.id.webView);
            final ImageView Imgpic = (ImageView) this.imageOrFileViewDialog.findViewById(R.id.imgPic);
            ImageView imgClose = (ImageView) this.imageOrFileViewDialog.findViewById(R.id.closeDialog);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageOrFileViewDialog.dismiss();
                }
            });
//       wbvw.getSettings().setJavaScriptEnabled(true);
//       wbvw.loadUrl("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg");
//       wbvw.loadUrl("http://www.myaaptha.com/pp/UserFiles/900000010501/30_U_2018-calander.pdfdecryptedFile");


            File imgFile = new File(Environment.getExternalStorageDirectory() + "/MyAaptha/1.jpg");

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Imgpic.setImageBitmap(myBitmap);

            }

//       //Asynchronously load file as generic file
//       FileLoader.with(activity)
//               .load("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg")
//               .fromDirectory("MyAaptha", FileLoader.DIR_EXTERNAL_PUBLIC)
//               .asFile(new FileRequestListener<File>() {
//                   @Override
//                   public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                       Glide.with(activity).load(response.getBody()).into(Imgpic);
//                   }
//
//                   @Override
//                   public void onError(FileLoadRequest request, Throwable t) {
//                       Log.d("Activity", "onError: " + t.getMessage());
//                   }
//               });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}