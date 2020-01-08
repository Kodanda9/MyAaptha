package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;


public class EditRoleFragment extends Fragment {
    private Spinner spinnerid;
    private View rootView;
    private int spinnerTextSize;
    public EditRoleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }//if
        try {
            rootView = inflater.inflate(R.layout.edit_role_fragment, container, false);
           rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());

            getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            AppHelper.setupHideleyboard(rootView,getActivity());
            spinnerTextSize= (int) (getResources().getDimension(R.dimen.font_size) / getResources().getDisplayMetrics().density);

//            boolean online= AppHelper.isNetworkAvaliable(getActivity());
//            if(online){
            init();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void init() {
        spinnerid=(Spinner)rootView.findViewById(R.id.spinner);
        spinnerid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }
}
