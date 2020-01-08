package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;

import static com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity.EmployLV;
import static com.cpetsol.cpetsolutions.myaaptha.activity.MenuPrescriptActivity.Norecords;

/**
 * Created by Admin on 8/1/2018.
 */

public class EmploymentDetails  extends Fragment {


    private View rootView;
    private String Appid;
    private String pfid;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        Appid= getArguments().getString("APPID");
        pfid = getArguments().getString("PFID");
        rootView= inflater.inflate(R.layout.employ_frag, container, false);
        EmployLV=(ListView)rootView.findViewById(R.id.employLV);
        Norecords=(ImageView)rootView.findViewById(R.id.norecords);
        return rootView;
    }


}

