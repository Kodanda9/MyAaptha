package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.cpetsol.cpetsolutions.myaaptha.R;
public class MainLoginSignUpFrag extends Fragment {


    private View rootView;

    public MainLoginSignUpFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.main_login_sign_up_fragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            if(savedInstanceState==null)
            {
                getFragmentManager().beginTransaction().add(R.id.login_frame,new LoginFragment() ).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

}
