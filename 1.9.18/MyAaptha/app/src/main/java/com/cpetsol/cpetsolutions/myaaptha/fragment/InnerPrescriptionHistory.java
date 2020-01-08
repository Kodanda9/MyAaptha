package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpetsol.cpetsolutions.myaaptha.R;

/**
 * Created by Admin on 8/1/2018.
 */
public class InnerPrescriptionHistory extends Fragment{

    private View rootView;
    private String pfid;
    private String Appid;



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
        rootView= inflater.inflate(R.layout.inner_prescript_frag, container, false);
        return rootView;
    }

}
