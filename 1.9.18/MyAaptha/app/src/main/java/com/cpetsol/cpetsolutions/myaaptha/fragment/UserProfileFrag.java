package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpetsol.cpetsolutions.myaaptha.R;


public class UserProfileFrag extends android.app.Fragment {


    public UserProfileFrag() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_profile_frag, container, false);
    }



}
