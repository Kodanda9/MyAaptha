package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {


    private View rootView;
    private PieChart pieChart;

    public GraphsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {   parent.removeView(rootView);    }
        }//if
        try {
            rootView = inflater.inflate(R.layout.graphs_fragment, container, false);

            pieChart = (PieChart)rootView.findViewById(R.id.chart2);

            GraphAsyncTask runner=new GraphAsyncTask();   runner.execute();

        }catch (Exception e){   e.printStackTrace();
        }
        return rootView;
    }


    public class GraphAsyncTask extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allUserData1+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
       //     String content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/u/allUserData/"+ SessinSave.getsessin("profile_id",getActivity()),"GET",null);
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
            if(pDialog.isShowing()){    pDialog.dismiss();    }
//            Log.i("Graphs  onPost:",s);
            try {
                generatePieChartData(s);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }//ComapnyAsyncTask


    private void generatePieChartData(String str) {
        int famCount=0; int eduCount=0;int empCount=0;int prescCount=0;int famDocCount=0;int emergContCount=0;int uploadDocCount=0;int healthDetCount=0;int otherDetCount=0;
        try {
            JSONArray mainArray=new JSONArray(str);
            for (int i=0;i<mainArray.length();i++){
                JSONObject obj=mainArray.getJSONObject(i);
                if(obj.getString("documentCategory").equalsIgnoreCase("Family Members"))
                {
                    famCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Education")){
                    eduCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Employment Details")){
                    empCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Prescription")){
                    prescCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Family Doctor")){
                    famDocCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Emergency Contact")){
                    emergContCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Upload Documents")){
                    uploadDocCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Health Data")){
                    healthDetCount+=1;
                }else if(obj.getString("documentCategory").equalsIgnoreCase("Other Details")){
                    otherDetCount+=1;
                }




            }




            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

//            pieChart.setCenterTextTypeface(mTfLight);
            pieChart.setCenterText(generateCenterSpannableText());

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);
            int colorBlack = Color.parseColor("#000000");
            pieChart.setEntryLabelColor(colorBlack);
            pieChart.setTransparentCircleColor(Color.WHITE);
            pieChart.setTransparentCircleAlpha(110);

            pieChart.setHoleRadius(58f);
            pieChart.setTransparentCircleRadius(61f);

            pieChart.setDrawCenterText(true);

            pieChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true);
            pieChart.setHighlightPerTapEnabled(true);
            // pieChart.setUnit(" €");
//             pieChart.setDrawUnitsInChart(true);

            // add a selection listener
//            pieChart.setOnChartValueSelectedListener(this);

            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
//Toast.makeText(getActivity(),""+h.getX(),Toast.LENGTH_LONG).show();
                    if(h.getX()==0.0){//Pending
//                        openEducationPopUp(0);
                    }else if(h.getX()==1.0){//Completed
//                        openEducationPopUp(3);
                    }else if(h.getX()==2.0){//Inf Needed
//                        openEducationPopUp(4);
                    }else if(h.getX()==3.0){//In Progress
//                        openEducationPopUp(1);
                    }else if(h.getX()==4.0){//Cancelled
//                        openEducationPopUp(2);
                    }


                }//onValueSelected
                @Override
                public void onNothingSelected() {
                    Log.i("VAL SELECTED","Nothing Selected");
                }
            });


            ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
            if(famCount != 0){
                entries.add(new PieEntry(famCount,"Family Members", getResources().getColor(R.color.blue_mylocation_beacon_outline)));
            }if(eduCount != 0){
                entries.add(new PieEntry(eduCount,"Education" , getResources().getColor(R.color.insta_green_deli) ));
            }if(empCount != 0){
                entries.add(new PieEntry(empCount,"Employment", getResources().getColor(R.color.yellow) ));
            }
//            if(undRecoverPsCount != 0){
//            entries.add(new PieEntry(empCount,"Employment", getResources().getColor(R.color.brown) ));
//            }
            if(prescCount != 0){
                entries.add(new PieEntry(prescCount,"Prescription", getResources().getColor(R.color.red_color_dark) ));
            }if(famDocCount != 0){
                entries.add(new PieEntry(famDocCount,"Family Doctor", getResources().getColor(R.color.abs__background_holo_dark) ));
            }if(emergContCount != 0){
                entries.add(new PieEntry(emergContCount,"Emergency Contact", getResources().getColor(R.color.abs__bright_foreground_disabled_holo_light) ));
            }if(uploadDocCount != 0){
                entries.add(new PieEntry(uploadDocCount,"Upload Documents", getResources().getColor(R.color.accent) ));
            }if(healthDetCount != 0){
                entries.add(new PieEntry(healthDetCount,"Health", getResources().getColor(R.color.android_green) ));
            }if(otherDetCount != 0){
                entries.add(new PieEntry(otherDetCount,"Other Details", getResources().getColor(R.color.app_back_gray) ));
            }





            pieChart.setDragDecelerationFrictionCoef(0.95f);
            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
            // pieChart.spin(2000, 0, 360);
            PieDataSet dataSet = new PieDataSet(entries, "Results");

            dataSet.setColors(new int[] { R.color.blue_mylocation_beacon_outline, R.color.insta_green_deli, R.color.yellow, R.color.brown, R.color.red_color_dark , R.color.common_signin_btn_default_background , R.color.blue_cc , R.color.android_green , R.color.accent , R.color.ab_bg_end , R.color.app_back_gray }, getActivity());

            dataSet.setDrawIcons(false);

            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(5f);

            dataSet.setSelectionShift(0f);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
//            data.setValueFormatter(new ItemsFormatter());
            data.setValueTextSize(11f);
//            data.setValueTextColor(Color.WHITE);
//            data.setValueTypeface(mTfLight);
            pieChart.setData(data);
//            pieChart.setDrawSliceText(false);//hide labels
            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();


        }catch (Exception e){
            e.printStackTrace();
        }



    }//PieChart

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("User Data");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 10, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }


    private class ItemsFormatter implements com.github.mikephil.charting.formatter.IValueFormatter {
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return ""+value;
        }
    }
}
