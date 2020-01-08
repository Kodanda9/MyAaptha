 package com.cpetsol.cpetsolutions.myaaptha.activity;

 import android.content.Intent;
 import android.content.res.Configuration;
 import android.graphics.drawable.ColorDrawable;
 import android.os.Bundle;
 import android.support.annotation.NonNull;
 import android.support.design.widget.BottomNavigationView;
 import android.support.v4.app.Fragment;
 import android.support.v4.app.FragmentTransaction;
 import android.support.v4.view.GravityCompat;
 import android.support.v4.widget.DrawerLayout;
 import android.support.v7.app.ActionBarDrawerToggle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.ExpandableListAdapter;
 import android.widget.ExpandableListView;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.cpetsol.cpetsolutions.myaaptha.R;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.AddRoleToWebPageFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.AddWebPageToRoleFragm;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.AddWebPageToRoleFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.AddaroleFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.AdminFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ApplicationDocumentationFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ChangeMobileNoFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ChangePswdFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorDetailsFragmen;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Dosages_DrugTypeFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Drugs_PricesFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.EditRoleFrag;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.EmployeeDataFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalDoctorsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalsApprovalFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.LabTest;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.LabTest_DetailsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.LabTestsAdmin;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Laboratory_DetailsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.MainMenuFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicalShopDetailsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicalShop_AdminFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicineStockDetailsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Medicines_HallFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Pathlab_Details_AdminFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Search_LabTestsFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.Search_MedicinesFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ShowContactUsDataFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.UserProfileFrag;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ViewHospitalOwnerFragment;
 import com.cpetsol.cpetsolutions.myaaptha.fragment.ViewHospitalsDataaFrag;
 import com.cpetsol.cpetsolutions.myaaptha.helper.navigation.CustomExpandableListAdapter;
 import com.cpetsol.cpetsolutions.myaaptha.helper.navigation.ExpandableListDataSource;
 import com.cpetsol.cpetsolutions.myaaptha.helper.navigation.FragmentNavigationManager;
 import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

 import org.json.JSONArray;
 import org.json.JSONObject;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;

 public class MainNavigationActivity extends AppCompatActivity {
     private DrawerLayout mDrawerLayout;
     private ActionBarDrawerToggle mDrawerToggle;
     private String mActivityTitle;
     private String[] items;
     private ExpandableListView mExpandableListView;
     private ExpandableListAdapter mExpandableListAdapter;
     private List<String> mExpandableListTitle;
     private FragmentNavigationManager mNavigationManager;
     private ArrayList<String> listDataHeader;
     private HashMap<String, List<String>> listDataChild;
     private Map<String, List<String>> mExpandableListData;
     private TextView TvSearchUser,TvAppts,TvUserData;
     private Menu menu;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_navigation_activity);
//                 SessinSave.saveSession("profile_id","900000032002",MainNavigationActivity.this);

         getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                 .getColor(R.color.insta_blue_bg)));
setTitle("My Account");
         mDrawerLayout = (DrawerLayout) findViewById(R.id.mainNavdrawer_layout);
         mActivityTitle = getTitle().toString();
//         TvUserData = (TextView) findViewById(R.id.tvUserData);
//        TvAppts = (TextView) findViewById(R.id.tvAppts);
//        TvSearchUser = (TextView) findViewById(R.id.tvSearchUser);
         mExpandableListView = (ExpandableListView) findViewById(R.id.navList);
         mNavigationManager = FragmentNavigationManager.obtain(this);
         prepareListData();

         initItems();

         LayoutInflater inflater = getLayoutInflater();
         View listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
         mExpandableListView.addHeaderView(listHeaderView);

         mExpandableListData = ExpandableListDataSource.getData(this);
         mExpandableListTitle = new ArrayList(mExpandableListData.keySet());

         final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNav);//Botom Navigation View
         navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
         navigation.setSelectedItemId(R.id.bottom_nav_account);
         addDrawerItems();
         setupDrawer();

         if (savedInstanceState == null) {
//             getSupportFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new GraphsFragment()).commit();
             getSupportFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new MainMenuFragment()).commit();
         }

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);
//         TvUserData.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent in = new Intent(MainNavigationActivity.this,AllUsersDataActivity.class);
//                 startActivity(in);
////                 getSupportFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new AllUserData()).commit();
//             }
//         });
//         TvAppts.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent in = new Intent(MainNavigationActivity.this,MenuAppointmentActivity.class);
//                 startActivity(in);
//             }
//         });
//         TvSearchUser.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent in = new Intent(MainNavigationActivity.this,SearchUserActivity.class);
//                 startActivity(in);
//             }
//         });


     }//onCreate


     private void prepareListData() {
        try{
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
//         listDataHeader.add("Administration");
//         listDataHeader.add("School Management");
            listDataHeader.add("Home");
            listDataHeader.add("DashBoard");
            listDataHeader.add(SessinSave.getsessin("FullName",MainNavigationActivity.this));
            List<String>   navDrawerItems = new ArrayList<String>();
            navDrawerItems.add("Home");
            navDrawerItems.add("About Us");
            navDrawerItems.add("Contact Us");
            navDrawerItems.add("Book Appoinment");
            navDrawerItems.add("Articles");
            navDrawerItems.add("Forums");
            navDrawerItems.add("Medical Labs");
            navDrawerItems.add("FAQ's");
            List<String> comingSoon = new ArrayList<String>();
            List<String> proarray = new ArrayList<String>();
            proarray.add("Change Mobile Number");
            proarray.add("Change Password");
//         proarray.add("Change Profile Picture");
//         proarray.add("Your Profile");
            proarray.add("Log Out");

            //User Types
            JSONArray array =new JSONArray(SessinSave.getsessin("UserTypes",MainNavigationActivity.this));

            JSONObject obj =array.getJSONObject(0);
            for (int i=0;i<=100;i++){
                try {
                        if(obj.has(String.valueOf(i))){
                            comingSoon.add(obj.getString(String.valueOf(i)));
                        }
                }catch (Exception e){e.printStackTrace();}
            }
            listDataChild.put(listDataHeader.get(0), navDrawerItems);
            listDataChild.put(listDataHeader.get(1), comingSoon);
            listDataChild.put(listDataHeader.get(2), proarray);
        }catch (Exception e){
            e.printStackTrace();
        }
     }//inIt

     private void initItems() {
//         items = getResources().getStringArray(R.array.film_genre);
     }
     private void addDrawerItems() {
         mExpandableListAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild);
         mExpandableListView.setAdapter(mExpandableListAdapter);
//        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);


         mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
             int previousGroup = -1;
             @Override
             public void onGroupExpand(int groupPosition) {
                 if(groupPosition != previousGroup)
                     mExpandableListView.collapseGroup(previousGroup);
                 previousGroup = groupPosition;
                 if( groupPosition == 0){
                     getSupportActionBar().setTitle("Home");
                 }
                 else if( groupPosition == 1){
                     getSupportActionBar().setTitle("Dash Board");
                 }else if( groupPosition == 2){
                     getSupportActionBar().setTitle(SessinSave.getsessin("FullName",MainNavigationActivity.this));
                 }

             }
         });

         mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
             @Override
             public void onGroupCollapse(int groupPosition) {
                 getSupportActionBar().setTitle(R.string.app_name);
             }
         });

         mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
             @Override
             public boolean onChildClick(ExpandableListView parent, View v,
                                         int groupPosition, int childPosition, long id) {
                 String selectedItem = ((List) (listDataChild.get(listDataHeader.get(groupPosition)))).get(childPosition).toString();
            if(selectedItem.equalsIgnoreCase(SessinSave.getsessin("FullName",MainNavigationActivity.this)))
                setTitle(selectedItem);
 Log.i("Selection --->",selectedItem);
//   String selectedItem = ((List) (listDataChild.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
                 getSupportActionBar().setTitle(selectedItem);
                 Fragment fragment = null;
                 switch (selectedItem){
                     case "Change Mobile Number":    getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new ChangeMobileNoFragment()).commit();
                         break;
                     case "Change Password":    getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new ChangePswdFragment()).commit();
                         break;
                     case "Log Out":
                         SessinSave.saveSession("LogInObj","",MainNavigationActivity.this);
                         SessinSave.saveSession("profile_id","",MainNavigationActivity.this);
                         SessinSave.saveSession("MobileNo","",MainNavigationActivity.this);
                         SessinSave.saveSession("FullName","",MainNavigationActivity.this);
//                         SessinSave.saveSession("UserTypeID","",MainNavigationActivity.this);

                         Toast.makeText(MainNavigationActivity.this,"Logging Out...", Toast.LENGTH_LONG).show();

                         finish();
                         Intent in3 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
                         in3.putExtra("REFRESH","LOGOUT");
                         startActivity(in3);
                         break;
                     case "User Profile": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFrag()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "View Hospitals Data": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new ViewHospitalsDataaFrag()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Doctor Details": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new DoctorDetailsFragmen()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Admin": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AdminFragment()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Application Documentation": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new ApplicationDocumentationFragment()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Edit Role": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new EditRoleFrag()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Add Role To Web Page": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddRoleToWebPageFragment()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Add Web Page To Role": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddWebPageToRoleFragment()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;
                     case "Add Role": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddaroleFragment()).commit();
                         // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                         break;


                     case "Hospitals Approval": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new HospitalsApprovalFragment()).commit();
                         break;
                     case "Hospitals"  : getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new HospitalsFragment()).commit();
                         break;
                     case "Doctors_Admin"  :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddWebPageToRoleFragm()).commit();
                         break;
                   /*  case "Registration"   :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new RegistrationFragment()).commit();
                         break;*/
                     case "Hospital Doctors"   :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new HospitalDoctorsFragment()).commit();
                         break;
                     case "View Hospital Owner"   :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new ViewHospitalOwnerFragment()).commit();
                         break;
                     case "Employee Data"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new EmployeeDataFragment()).commit();
                         break;
                     case "Medical Shop Details"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new MedicalShopDetailsFragment()).commit();

                         break;
                     case "Medicine Stock Details"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new MedicineStockDetailsFragment()).commit();

                         break;
                     case "MedicalShop_Admin"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new MedicalShop_AdminFragment()).commit();

                         break;
                     case "Medicines Hall"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Medicines_HallFragment()).commit();

                         break;
                     case "Search Medicines"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Search_MedicinesFragment()).commit();

                         break;
                     case "Drugs Prices"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Drugs_PricesFragment()).commit();

                         break;
                     case "Dosages & DrugType"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Dosages_DrugTypeFragment()).commit();

                         break;
                     case "Laboratory Details"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Laboratory_DetailsFragment()).commit();

                         break;
                     case "Lab Test Details"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest_DetailsFragment()).commit();

                         break;

                     case "Search Lab Tests"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Search_LabTestsFragment()).commit();

                         break;
                     case "Path lab Details_Admin"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Pathlab_Details_AdminFragment()).commit();

                         break;
                     case "Show Contact Us Data"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new ShowContactUsDataFragment()).commit();

                         break;
                     case "Medicines Hall_Admin"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new Medicines_HallFragment()).commit();

                         break;
                     case "Lab Tests"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTest()).commit();

                         break;
                     case "Lab Tests_Admin"   :getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new LabTestsAdmin()).commit();

                         break;
                     default:break;
                 }
                 if (fragment != null) {
                     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                     ft.replace(R.id.main_nav_content_frame, fragment);
                     ft.commit();
                 }
                 mDrawerLayout.closeDrawer(GravityCompat.START);
                 return false;
             }
         });
     }

     private void setupDrawer() {
         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

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
     public boolean onCreateOptionsMenu(Menu menu) {
         this.menu = menu;
         getMenuInflater().inflate(R.menu.mainnav_items, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         if (mDrawerToggle.onOptionsItemSelected(item)) {
             return true;
         }
         switch (item.getItemId()) {
             case R.id.action_menu1: //getFragmentManager().beginTransaction().replace(R.id.lvNavMain, new LoginFragment()).commit();
                 return true;
             case R.id.action_menu2:   getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new ChangeMobileNoFragment()).commit();
                 return true;
             case R.id.action_changepsw: getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new ChangePswdFragment()).commit();
                 return true;
             case R.id.action_menu3:
                 SessinSave.saveSession("LogInObj","",MainNavigationActivity.this);
                 SessinSave.saveSession("profile_id","",MainNavigationActivity.this);
                 SessinSave.saveSession("MobileNo","",MainNavigationActivity.this);
                 SessinSave.saveSession("FullName","",MainNavigationActivity.this);

                 Toast.makeText(MainNavigationActivity.this,"Logging Out...", Toast.LENGTH_LONG).show();
//                 finish();
//                 Intent in4 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
//                 in4.putExtra("REFRESH","LOGOUT");
//                 startActivity(in4);
//                 finish();
                 finish();
                 Intent in3 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
                 in3.putExtra("REFRESH","");
                 startActivity(in3);

                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }

     }//onOptionsItemSelected

     private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
             = new BottomNavigationView.OnNavigationItemSelectedListener() {

         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             switch (item.getItemId()) {
                 case R.id.bottom_nav_home:
                     finish();
                     Intent in1 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
                     in1.putExtra("REFRESH","TAB1");
                     startActivity(in1);
                     return true;
                 case R.id.bottom_nav_actical:
                     finish();
                     Intent in2 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
                     in2.putExtra("REFRESH","TAB2");
                     startActivity(in2);
                     return true;
                 case R.id.bottom_nav_forum:
                     finish();
                     Intent in3 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
                     in3.putExtra("REFRESH","TAB3");
                     startActivity(in3);
                     return true;
                 case R.id.bottom_nav_account:
//                     finish();
//                     Intent in4 = new Intent(MainNavigationActivity.this,NavigationActivity.class);
//                     in4.putExtra("REFRESH","TAB4");
//                     startActivity(in4);
                     return true;
             }
             return false;
         }
     };


 }
