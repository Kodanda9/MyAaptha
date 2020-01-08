package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.ViewPagerAdapter;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorAppointments;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorPastAppointments;
import com.cpetsol.cpetsolutions.myaaptha.fragment.UserAppointments;
import com.cpetsol.cpetsolutions.myaaptha.fragment.UserPastAppointments;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

public class MenuAppointmentActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    private UserAppointments tab1;
    private UserPastAppointments tab2;
    private DoctorAppointments tab3;
    private DoctorPastAppointments tab4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_appointment_activity);

        // getSupportActionBar().hide();

        overridePendingTransition(R.anim.enter_from_left, R.anim.enter_from_left);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Appointments");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.insta_blue_bg)));

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab1:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.tab2:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.tab3:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.tab4:
                                viewPager.setCurrentItem(4);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                    Log.d("page-->", "setChecked: False "+position);
                }
                else
                {
                    Log.d("page-->", "setChecked: True "+position);
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        tab1=new UserAppointments();
        tab2=new UserPastAppointments();
        tab3=new DoctorAppointments();
        tab4=new DoctorPastAppointments();


        String profession = SessinSave.getsessin("profession",MenuAppointmentActivity.this);
        if(profession.equalsIgnoreCase("Doctor")){
            adapter.addFragment(tab1);
            adapter.addFragment(tab2);
            adapter.addFragment(tab3);
            adapter.addFragment(tab4);
        }else{
            bottomNavigationView.getMenu().removeItem(R.id.tab3);
            bottomNavigationView.getMenu().removeItem(R.id.tab4);
            adapter.addFragment(tab1);
            adapter.addFragment(tab2);
        }




        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.down_from_top, R.anim.down_from_top);
    }



}
