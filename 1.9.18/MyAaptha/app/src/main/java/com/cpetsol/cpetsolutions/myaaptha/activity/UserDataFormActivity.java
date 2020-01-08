package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AddFamilyDetails;
import com.cpetsol.cpetsolutions.myaaptha.fragment.AddPrescriptionFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EducationFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EmergencyContDetailsFrag;
import com.cpetsol.cpetsolutions.myaaptha.fragment.EmploymentFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.FamilyDrDetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.HelathDetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.OtherDetailsFragment;
import com.cpetsol.cpetsolutions.myaaptha.fragment.UploadDocFragment;

public class UserDataFormActivity extends Activity {
    private Spinner SpCategory;
    private String selCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data_form_activity);

        SpCategory   = (Spinner)findViewById(R.id.sDocCategory);
        SpCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selCategory = SpCategory.getSelectedItem().toString();
                SellectSpinnerCategory(selCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }//onCreate


    private void SellectSpinnerCategory(String s) {
        switch (s){
            case "Add Family Members":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new AddFamilyDetails()).commit();
                break;
            case "Education Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new EducationFragment()).commit();
                break;
            case "Employment Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new EmploymentFragment()).commit();
                break;
            case "Add Prescription":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new AddPrescriptionFragment()).commit();
                break;
            case "Health Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new HelathDetailsFragment()).commit();
                break;
            case "Family Doctor Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new FamilyDrDetailsFragment()).commit();
                break;
            case "Emergency Contact Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new EmergencyContDetailsFrag()).commit();
                break;
            case "Upload Documents":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new UploadDocFragment()).commit();
                break;
            case "Other Details":
                getFragmentManager().beginTransaction().replace(R.id.adduserdata_content_frame1, new OtherDetailsFragment()).commit();
                break;
        }
    }//SellectSpinnerCategory


}
