package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;

public class ShowContactUsDataFragment extends Fragment {
    private static View rootView;
    private ListView Feedback;


    public ShowContactUsDataFragment() {
        // Required empty public constructor
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
            rootView = inflater.inflate(R.layout.fragment_show_contact_us_data, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView,getActivity());

              Feedback=(ListView)rootView.findViewById(R.id.feedbacklv);
            FeedbackRunner runner=new FeedbackRunner();
            runner.execute();
        } catch (InflateException e) {
            e.printStackTrace();

        }
        return rootView;
    }

    private class FeedbackRunner extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
       //     String Content= AsyncTaskHelper.makeServiceCall(ApisHelper.)
            return null;
        }
    }
}
