package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;

/**
 * Created by Admin on 8/27/2018.
 */

public class LabTest extends Fragment {
    private static View rootView;

    public LabTest() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.labtest_frag, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());

//            ImageView view = (ImageView)rootView.findViewById(R.id.about_img);
//            TextView textView=(TextView)rootView.findViewById(R.id.tvmission);
//            textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
//            Picasso.with(getActivity()).load(R.drawable.health).into(view);

        } catch (InflateException e) {
            e.printStackTrace();
//            ExceptionHandler handler=new ExceptionHandler(getActivity());
//            handler.setExc("Exc in onCreateView @ AboutUsFragment");
        }
        return rootView;
    }

}
