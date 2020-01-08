package com.cpetsol.cpetsolutions.myaaptha.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.CustomAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;

/**
 * Created by Admin on 8/24/2018.
 */

public class MedLabsFragment extends Fragment {
    private View rootView;
    private ListView MedLabs;
    private String[] questionslist={"Q: How to get Registration?","Q: Validation of profile details?","Q: How do i login?","Q: I forgot my password?",
            "Q? How do i change my password?","Q? Can i create my own profile ID?",
            "Q? How many can I add of Report Copies?","Q? Can I edit all my details?",
            "Q? Can I share my ID and Password to my friends or relatives?","Q? What is profile completeness?","Q? How do i edit my profile details?"};
    private String[] answerslist={"A: a) On Home page click on link Register\n" +
            "b) Create an account with all specified detatils including mobile number, the OTP (one time password) will be sent to your mobile number to confirm registration. \n" +
            "(Or)\n" +
            "Click on the link New User for free registration.",
            "A: All the profiles are validated before inclusion in the database. Express Validation of profile will take just 1 Day Turn round Time. Our support team meticulously validates each and every profile carefully on specific criterion before it is added to our database. E-mail will be sent to you once the profile is validated and added.",
            "A: On Home page click on the link Login enter your Mobile Number and Password into the respective boxes and click submit button",
            "A. Click on the link Forgot your password then enter your register Mobile no. into the box then submit and after OTP(one time password) will be sent to your register mobile no. and click submit button.",
            "A. Login to your register mobile no. and password. Under \"My Account\" click to \"edit info\", then click on \"change password\". Enter your Old password and New password then Confirm password and" +
                    " then click submit button. Your New password will be generate immidiately, please use the new password the next time you login.",
            "A. Every profile ID is unique and is allocated by the system. You cannot choose your own profile ID since it is system generated and cannot be changed under any circumstances.",
            "A. There is no limit for adding report copies. Copies must be in Pdf format and jpg format (no larger than 1000kb).",
            "A. Yes,at any time, you can update your profile by clicking on Modify My Profile button."
            ,"A. No. You cannot share your ID and Password.",
            "A. It indicates whether or not you have completed all the important details about yourself. More details will improve your profile credibility.",
            "A. You can edit your profile until you Login,plz go through the link Login."};
    private TextView Login,Register;



    public MedLabsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.med_labfragment, container, false);
            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
            AppHelper.setupHideleyboard(rootView,getActivity());
           MedLabs=(ListView)rootView.findViewById(R.id.med);
            Login=(TextView)rootView.findViewById(R.id.login);
            Register=(TextView)rootView.findViewById(R.id.register);
            CustomAdapter customAdapter = new CustomAdapter(getActivity(), questionslist, answerslist);
            MedLabs.setAdapter(customAdapter);
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500); //You can manage the blinking time with this parameter
            anim.setStartOffset(10);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);

       anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Login.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink));
                Register.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Login.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange));
                Register.setTextColor(ContextCompat.getColor(getActivity(), R.color.pink));

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
            Login .startAnimation(anim);
            Register .startAnimation(anim);
            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new LoginFragment()).commit();
                }
            });
            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new UserSignUpFragment()).commit();

                }
            });

        } catch (InflateException e) {
            e.printStackTrace();

        }
        return rootView;
    }


}