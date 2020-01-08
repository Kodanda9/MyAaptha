package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.NavDrawerListAdapter;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AboutUsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ArticlesFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ContactUsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DrHomeFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.FaqFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ForumsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MainLoginSignUpFrag;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MedLabsFragment;
import com.cpetsol.cpetsolutions.myaaptha.model.NavDrawerItem;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter Navadapter;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String refresh;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);


      //  getSupportActionBar().setTitle(Html.fromHtml("<font color='#ff0000'>ActionBarTitle </font>"));
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNav);//Botom Navigation View
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        try {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.insta_blue_bg)));


            refresh = getIntent().getStringExtra("REFRESH");

            mTitle = mDrawerTitle = getTitle();

            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load slide menu items
            navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);// nav drawer icons from resources
            mDrawerLayout = (DrawerLayout) findViewById(R.id.navigationDrawerLayout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
            navDrawerItems = new ArrayList<NavDrawerItem>();
            for (int i = 0; i <= 7; i++) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
            }


            if ((SessinSave.getsessin("UserTypes", NavigationActivity.this).isEmpty())) {
            } else {
                //User Types
                JSONArray array = new JSONArray(SessinSave.getsessin("UserTypes", NavigationActivity.this));

                JSONObject obj = array.getJSONObject(0);
                for (int i = 0; i <= 100; i++) {
                    try {
                        if (obj.has(String.valueOf(i))) {
                            navDrawerItems.add(new NavDrawerItem(obj.getString(String.valueOf(i)), R.drawable.circle_icon));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            navMenuIcons.recycle();
            mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    displayView(i);
                    if (i == 0 || i == 3) {
                        View view1 = navigation.findViewById(R.id.bottom_nav_home);
                        view1.performClick();
                    } else if (i == 4) {
                        View view1 = navigation.findViewById(R.id.bottom_nav_actical);
                        view1.performClick();
                    } else if (i == 5) {
                        View view1 = navigation.findViewById(R.id.bottom_nav_forum);
                        view1.performClick();
                    } else if (i == 2) {
                        displayView(2);
                    } else if (i == 6) {
                        displayView(6);
                    } else if (i == 7) {
                        displayView(7);
                    } else if (i == 1) {
                        displayView(1);
                    }
                }
            });


            Navadapter = new NavDrawerListAdapter(NavigationActivity.this, navDrawerItems);  // setting the nav drawer list adapter
            mDrawerList.setAdapter(Navadapter);
            setupDrawer();
            getSupportActionBar().setIcon(R.drawable.logo);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);


//            if(savedInstanceState == null){
//                View view1 = navigation.findViewById(R.id.bottom_nav_home);
//                view1.performClick();
//            }
            if (refresh.isEmpty() || refresh.equalsIgnoreCase("TAB1")) {
                View view1 = navigation.findViewById(R.id.bottom_nav_home);
                view1.performClick();
            } else if (refresh.equalsIgnoreCase("TAB2")) {
                View view1 = navigation.findViewById(R.id.bottom_nav_actical);
                view1.performClick();
            } else if (refresh.equalsIgnoreCase("TAB3")) {
                View view1 = navigation.findViewById(R.id.bottom_nav_forum);
                view1.performClick();
            } else if (refresh.equalsIgnoreCase("TAB4")) {
//                displayView(8);
//                View view1 = navigation.findViewById(R.id.bottom_nav_home);
//                view1.performClick();
            } else if (refresh.equalsIgnoreCase("LOGOUT")) {
//                displayView(8);
                View view1 = navigation.findViewById(R.id.bottom_nav_account);
                view1.performClick();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//onCreate


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(NavigationActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
//         else if (id == R.id.menu_notification) {
//             openNotificationsPopUp();
//             return true;
//         }else if (id == R.id.menu_message) {
//             openMessagesPopUp();
//             return true;
//         }

        return super.onOptionsItemSelected(item);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_nav_home:
                    displayView(0);
                    return true;
                case R.id.bottom_nav_actical:
                    displayView(4);
                    return true;
                case R.id.bottom_nav_forum:
                    displayView(5);
                    return true;
                case R.id.bottom_nav_account:
                    displayView(8);
                    return true;
            }
            return false;
        }
    };

    private void displayView(int position) {
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case 0:
                fragment = new DrHomeFragment();  //      RLwtHm.setVisibility(View.VISIBLE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "Home";

                break;
            case 1:
                fragment = new AboutUsFragment(); //    RLwtHm.setVisibility(View.GONE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "About Us";
                break;
            case 2:
                fragment = new ContactUsFragment();//   RLwtHm.setVisibility(View.GONE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "Contact Us";
                break;
            case 3:
                fragment = new DrHomeFragment();   //   RLwtHm.setVisibility(View.VISIBLE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "Home";
                break;
            case 4:
                fragment = new ArticlesFragment(); //  RLwtArticle.setVisibility(View.VISIBLE);    RLwtAc.setVisibility(View.GONE);    RLwtHm.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "Articles";
                break;
            case 5:
                fragment = new ForumsFragment();     //  RLwtForum.setVisibility(View.VISIBLE);      RLwtAc.setVisibility(View.GONE);    RLwtHm.setVisibility(View.GONE);   RLwtArticle.setVisibility(View.GONE);
                title = "Forums";
                break;
            case 6: fragment = new MedLabsFragment();  //  RLwtHm.setVisibility(View.GONE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "Medical Lab";
                break;
            case 7: fragment = new FaqFragment();     //   RLwtHm.setVisibility(View.GONE);  RLwtAc.setVisibility(View.GONE);    RLwtArticle.setVisibility(View.GONE);   RLwtForum.setVisibility(View.GONE);
                title = "";
                break;
            case 8:
                title = "Account";
                if (SessinSave.getsessin("profile_id", NavigationActivity.this).equalsIgnoreCase("")) {
                    fragment = new MainLoginSignUpFrag();
                } else {
                    Intent intent = new Intent(NavigationActivity.this, MainNavigationActivity.class);
                    startActivity(intent);
                }


                break;

            default:
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_content_Frame, fragment).commit();
            // fragmentManager.beginTransaction().addToBackStack(HomeFragment.class.getName());
            // fragmentManager.popBackStack(String name,
            // fragmentManager.POP_BACK_STACK_INCLUSIVE)

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
//            setTitle("  "+navMenuTitles[position]);
            setTitle(title);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }//displayView


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Navigation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
