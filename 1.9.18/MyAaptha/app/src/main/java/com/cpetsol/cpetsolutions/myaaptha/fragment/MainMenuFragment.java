package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.AllUsersDataActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.InboxActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.MenuAppointmentActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.MessageBox;
import com.cpetsol.cpetsolutions.myaaptha.activity.RolesActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.SearchUserActivity;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;

public class MainMenuFragment extends Fragment {
    private View rootView;
    private TextView TvAppts,TvSearchUser,TvUserData,TvInbox,Tvmsgbox,Roles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {


//            rootView = inflater.inflate(R.layout.comingsoon, container, false);
            rootView = inflater.inflate(R.layout.main_menufragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
            AppHelper.setupHideleyboard(rootView,getActivity());

            TvUserData = (TextView)rootView.findViewById(R.id.tvUserData);
            TvAppts = (TextView)rootView.findViewById(R.id.tvAppts);
            TvSearchUser = (TextView)rootView.findViewById(R.id.tvSearchUser);
            TvInbox = (TextView)rootView.findViewById(R.id.inbox);
            Tvmsgbox = (TextView)rootView.findViewById(R.id.msg);
            Roles = (TextView)rootView.findViewById(R.id.roles);

            Tvmsgbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(getActivity(),MessageBox.class);
                    startActivity(i);
                }
            });

     //       getActivity().getFragmentManager().beginTransaction().replace(R.id.ll_mainFragnav, new GraphsFragment()).commit();

            TvUserData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(),AllUsersDataActivity.class);
                    startActivity(in);
//                 getSupportFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new AllUserData()).commit();
                }
            });
            TvAppts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(),MenuAppointmentActivity.class);
                    startActivity(in);
                }
            });
            TvSearchUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(),SearchUserActivity.class);
                    startActivity(in);
                }
            });
            TvInbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(),InboxActivity.class);
                    startActivity(in);
                }
            });
            Roles.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getActivity(),RolesActivity.class);
                    startActivity(in);
                }
            });





        } catch (Exception e) {        }
        return rootView;
    }

}
