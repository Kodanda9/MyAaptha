package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AddRoleToWebPageFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AddWebPageToRoleFragm;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AddaroleFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AdminFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ApplicationDocumentationFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorDetailsFragmen;
import com.cpetsol.cpetsolutions.myaaptha.fragment.DoctorsAdminFragments;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Dosages_DrugTypeFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Drugs_PricesFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EditRoleFrag;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EmployeeDataFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalDoctorsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalsApprovalFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.HospitalsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.LabTest_DetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Laboratory_DetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicalShopDetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicalShop_AdminFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MedicineStockDetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Medicines_HallFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Pathlab_Details_AdminFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Search_LabTestsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.Search_MedicinesFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ShowContactUsDataFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ViewHospitalOwnerFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.ViewHospitalsDataaFrag;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RolesActivity extends AppCompatActivity {
    private ListView RolesList;
    List<String> comingSoon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles);
        // getSupportActionBar().hide();

        overridePendingTransition(R.anim.up_from_bottom, R.anim.up_from_bottom);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Roles");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_icon);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.app_color)));
        RolesList=(ListView)findViewById(R.id.roleslist);
        prepareListData();
       RolesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
           String selectedItem=adapterView.getItemAtPosition(position).toString();
               if(selectedItem.equalsIgnoreCase(SessinSave.getsessin("FullName",RolesActivity.this)))
                   setTitle(selectedItem);
               Log.i("Selection --->",selectedItem);
//   String selectedItem = ((List) (listDataChild.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
               getSupportActionBar().setTitle(selectedItem);
               Fragment fragment = null;
               switch (selectedItem){
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
                   case "Add Web Page To Role": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddWebPageToRoleFragm()).commit();
                       // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                       break;
                   case "Add Role": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new AddaroleFragment()).commit();
                       // getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new UserProfileFragment()).commit();
                       break;


                   case "Hospitals Approval": getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new HospitalsApprovalFragment()).commit();
                       break;
                   case "Hospitals"  : getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new HospitalsFragment()).commit();
                       break;
                   case "Doctors_Admin"  :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new DoctorsAdminFragments()).commit();
                       break;
                  /* case "Registration"   :  getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame,new RegistrationFragment()).commit();
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

                   default:break;
               }


           }



           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });

    }

    private void prepareListData() {
        try{
            List<String> comingSoon = new ArrayList<String>();
            JSONArray array =new JSONArray(SessinSave.getsessin("UserTypes",RolesActivity.this));
            Log.i("++++++",array.toString());
            JSONObject obj =array.getJSONObject(0);

            for(int i=0;i<=100;i++){
                try {
                    if(obj.has(String.valueOf(i))){
                        comingSoon.add(obj.getString(String.valueOf(i)));
                    }
                }catch (Exception e){e.printStackTrace();}
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,comingSoon){
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    if (position % 2 == 0) { // we're on an even row
                        view.setBackgroundColor(Color.WHITE);
                    } else {
                        view.setBackgroundColor(ContextCompat.getColor(RolesActivity.this, R.color.lightgray));
                    }
                    return view;
                }
            };
            RolesList.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
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