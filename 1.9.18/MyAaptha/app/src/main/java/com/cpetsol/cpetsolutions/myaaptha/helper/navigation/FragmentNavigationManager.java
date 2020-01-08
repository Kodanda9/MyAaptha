package com.cpetsol.cpetsolutions.myaaptha.helper.navigation;

import android.support.v4.app.FragmentManager;

import com.cpetsol.cpetsolutions.myaaptha.activity.MainNavigationActivity;

public class FragmentNavigationManager
        implements NavigationManager
{

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private MainNavigationActivity mActivity;

    public static FragmentNavigationManager obtain(MainNavigationActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(MainNavigationActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragmentAction(String title) {
//        showFragment(FragmentAction.newInstance(title), false);
    }

    @Override
    public void showFragmentComedy(String title) {
//        showFragment(FragmentComedy.newInstance(title), false);
    }

    @Override
    public void showFragmentDrama(String title) {
//        showFragment(FragmentDrama.newInstance(title), false);
    }

    @Override
    public void showFragmentMusical(String title) {
//        showFragment(FragmentMusical.newInstance(title), false);
    }

    @Override
    public void showFragmentThriller(String title) {
//        showFragment(FragmentThriller.newInstance(title), false);
    }

}
