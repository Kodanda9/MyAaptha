package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;

import java.util.ArrayList;

/**
 * Created by Admin on 5/29/2018.
 */

public class DoctorsDataFragment extends Fragment {
    private View rootView;
   private Spinner monspinner,monendspinner,tuespinner,tueendspinner,wedspinner,wedendspinner,thuspinner,thuendspinner,frispinner,friendspinner,
            satspinner,satendspinner,sunspinner,sunendspinner,durat;
    public DoctorsDataFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }//if
        try {
            rootView = inflater.inflate(R.layout.doctors_data_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());

            monspinner=(Spinner)rootView.findViewById(R.id.monspinner);
            monendspinner=(Spinner)rootView.findViewById(R.id.monendspinner);
            tuespinner=(Spinner)rootView.findViewById(R.id.tuespinner);
            tueendspinner=(Spinner)rootView.findViewById(R.id.tueendspinner);
            wedspinner=(Spinner)rootView.findViewById(R.id.wedspinner);
            wedendspinner=(Spinner)rootView.findViewById(R.id.wedendspinner);
            thuspinner=(Spinner)rootView.findViewById(R.id.thuspinner);
            thuendspinner=(Spinner)rootView.findViewById(R.id.thuendspinner);
            frispinner=(Spinner)rootView.findViewById(R.id.frispinner);
            friendspinner=(Spinner)rootView.findViewById(R.id.friendspinner);
            satspinner=(Spinner)rootView.findViewById(R.id.satspinner);
            satendspinner=(Spinner)rootView.findViewById(R.id.satendspinner);
            sunspinner=(Spinner)rootView.findViewById(R.id.sunspinner);
            sunendspinner=(Spinner)rootView.findViewById(R.id.sunendspinner);
            durat=(Spinner)rootView.findViewById(R.id.durat);
            ArrayList<String> timeslotlist=new ArrayList<>();
            timeslotlist.add(0,"--Start Time--");
            String f="";
            for(int i=0;i<22;i++){
                for(int j=0;j<60;j=j+30){
                    if(j==0){
                        f=i+":"+"00";
                    }else {
                        f=i+":"+j;
                    }
                    timeslotlist.add(f);
                    monspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    tuespinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    wedspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    thuspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    frispinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    satspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));
                    sunspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,timeslotlist));

                }
            }

            ArrayList<String> endtimelist=new ArrayList<>();
            endtimelist.add(0,"--End Time--");
            String e="";
            for(int k=0;k<22;k++){
                for(int l=0;l<60;l=l+30){
                    if(l==0){
                        e=k+":"+"00";
                    }else {
                        e=k+":"+l;
                    }
                    endtimelist.add(e);

                    monendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    tueendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    wedendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    thuendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    friendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    satendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));
                    sunendspinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,endtimelist));

                }
            }
            ArrayList<String> duration=new ArrayList<>();
            String t="";
            for (int m=5;m<=60;m++){

                duration.add(m+"");
                durat.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,duration));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }//o
}
