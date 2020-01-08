package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.Database.DatabaseHelper;
import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.AUDAdapter;
import com.cpetsol.cpetsolutions.myaaptha.dbmodel.UserDataDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.model.AllUserDataModel;
import com.cpetsol.cpetsolutions.myaaptha.util.RealPathUtil;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class AllUsersDataActivity extends AppCompatActivity {
    private ArrayList<AllUserDataModel> rowItems;

    private boolean fabExpanded = false;
    private ListView LvAllUserData;
    private Dialog famMembrsDialog,educationdialog,prescriptionDialog,HealthPolicyDialog,
            employmentDialog,otherdetailsDialog,familyDoctorDialog,emergencyContactDialog,uploadDocumentDialog,HealthDetailsDialog;
    private FloatingActionButton fabSettings;
    private LinearLayout LFamilyMembers,LOtherDetails,LHealthDetails,LPrescription,LEducation,LFamilyDocter,LEmergencyContact,LUploadDocuments,
            LEmployment,LHealthPolicy;
    private View famMembersView,educationView,prescriptionView,HealthPolicyView,
            employmentView,otherdetailsView,familyDoctorView,emergencyContactView,uploadDocumentView,HealthDetailsView;
    private FrameLayout FmLayout;
    private RadioGroup RGPrivacypolocy,RGGender;
    private Spinner SpFmilyNames,SpRelation,DrRelation,SpInsuranceType,SpInsuranceProvider;
    private EditText ETDetails,EtNumber,EtDate,ETName,ETBirthday;
    private String selectedGender;
    private String fmPrivacyPolocy;
    private EditText DrDate,DrName;
    private Spinner PresFMSpin;
    private EditText presETDAte;
    private EditText PresETDetail,etnumber,InsuranceId;
    private Spinner sDoc,etcateogory;
    private EditText etname;
    private EditText Empetcompany;
    private LinearLayout EduSpin;
    private EditText ETdate;
    private Spinner Eduspin;
    private Spinner Eduqualspin;
    private TextView uploadFile;
    private int FILE_REQUEST=1200;
    private String filePath;
    private  long totalSize = 0;

    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = AllUsersDataActivity.class.getSimpleName();
    private String selectedFilePath;
  //  private String SERVER_URL = "http://Ip Address/a.php";
    private TextView tvFileName;
    private int serverResponseCode;
    private ProgressDialog dialog;
    private int PICK_FROM_GALLERY;
    private String HPPrivacyPolocy;
    private RadioGroup HPPrivacypolocy;
    private DatabaseHelper databaseHelper;
    private View localView;
    private SwipeRefreshLayout upload_swipe_refresh_layout;
    private RelativeLayout Norecords;
    private float spinnerTextSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_users_data_activit);

        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Users Data");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }});

        FmLayout = (FrameLayout) this.findViewById(R.id.activity_fabexample);
        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabSetting);
        LFamilyMembers = (LinearLayout) this.findViewById(R.id.layoutFABFamilyMembers);
        LOtherDetails = (LinearLayout) this.findViewById(R.id.layoutFABOtherDetails);
        LHealthDetails = (LinearLayout) this.findViewById(R.id.layoutFABHealthDetails);
        LPrescription = (LinearLayout) this.findViewById(R.id.layoutFABPrescription);
        LEducation = (LinearLayout) this.findViewById(R.id.LayoutFABEducation);
        LFamilyDocter = (LinearLayout) this.findViewById(R.id.layoutFABFamilyDocter);
        LEmergencyContact = (LinearLayout) this.findViewById(R.id.layoutFABEmergencyContacts);
        LUploadDocuments = (LinearLayout) this.findViewById(R.id.layoutFABUploadDocuments);
        LEmployment = (LinearLayout) this.findViewById(R.id.layoutFABEmployment);
        LHealthPolicy = (LinearLayout) this.findViewById(R.id.layoutFABHealthPolicy);
   //     Norecords = (RelativeLayout) this.findViewById(R.id.norecords);
        spinnerTextSize= (float) (getResources().getDimension(R.dimen.font_size) / getResources().getDisplayMetrics().density);

        upload_swipe_refresh_layout = (SwipeRefreshLayout) this.findViewById(R.id.swipe);



        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                    FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                } else {
                    openSubMenusFab();
                    FmLayout.setBackgroundColor(getResources().getColor(R.color.blue_aboutUs));
                }
            }
        });
        closeSubMenusFab();



        //Only main FAB is visible in the beginning


        upload_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
                upload_swipe_refresh_layout.setRefreshing(false);
            }
        });

       /* upload_swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                upload_swipe_refresh_layout.setRefreshing(true);
            }
        });*/

        LvAllUserData = (ListView)findViewById(R.id.lvAlluserData);
        AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
        FloatingActionButton fabEdu=(FloatingActionButton)findViewById(R.id.fab_addData);
        fabEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in =new Intent(AllUsersDataActivity.this,UserDataFormActivity.class);
                startActivity(in);
//                UserDataForm();
            }
        });


    }//onCreate

    private void openSubMenusFab() {
        LFamilyMembers.setVisibility(View.VISIBLE);

        LHealthPolicy.setVisibility(View.VISIBLE);
        LHealthPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { HealthPolicyPopup();    }
        });
        LEmployment.setVisibility(View.VISIBLE);
        LEmployment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { EmploymentPopup();    }
        });
        LEducation.setVisibility(View.VISIBLE);
        LEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationPopup();
            }
        });
        LUploadDocuments.setVisibility(View.VISIBLE);
        LUploadDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadDocumentPopup();
            }
        });
        LEmergencyContact.setVisibility(View.VISIBLE);
        LEmergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { EmergencyContactPopup();     }
        });
        LHealthDetails.setVisibility(View.VISIBLE);
        LHealthDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthDetailsPopup();
            }
        });
        LOtherDetails.setVisibility(View.VISIBLE);
        LOtherDetails.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherDetailsPopup();
            }
        });
        LFamilyDocter.setVisibility(View.VISIBLE);
        LFamilyDocter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FamilyDoctorPopup();
            }
        });
        LPrescription.setVisibility(View.VISIBLE);
        LPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionPopup();
            }
        });
        fabSettings.setImageResource(R.drawable.ic_close);
        fabExpanded = true;
        LFamilyMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FamilyMembersPopUp();
            }
        });

    }

    private void HealthPolicyPopup() {
        HealthPolicyView=View.inflate(AllUsersDataActivity.this,R.layout.helath_policy_fragmetnt,null);
        HealthPolicyView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.HealthPolicyDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.HealthPolicyDialog.setContentView(HealthPolicyView);
        this.HealthPolicyDialog.setCancelable(true);
        this.HealthPolicyDialog.show();

        Window window = this.HealthPolicyDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.HealthPolicyDialog.findViewById(R.id.sFamilynames);
        SpInsuranceType=(Spinner)this.HealthPolicyDialog.findViewById(R.id.sInsurance);
        SpInsuranceProvider=(Spinner)this.HealthPolicyDialog.findViewById(R.id.sInsuranceprovider);
        String[] some_array = getResources().getStringArray(R.array.PolicyType);
        SpInsuranceType.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,some_array));
        SpInsuranceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        String[] IP_array = getResources().getStringArray(R.array.PolicyProvider);
        SpInsuranceProvider.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,IP_array));
        SpInsuranceProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        SpInsuranceType.setSelection(AppHelper.setValueToSpinner(SpInsuranceType,model.getInsurerType()));


        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        presETDAte=(EditText)this.HealthPolicyDialog.findViewById(R.id.et_Date);
        InsuranceId = (EditText) this.HealthPolicyDialog.findViewById(R.id.insuranceid); //   presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        PresETDetail = (EditText) this.HealthPolicyDialog.findViewById(R.id.et_details);  //  PresETDetail.setText(model.getDetails());
        etcateogory = (Spinner) this.HealthPolicyDialog.findViewById(R.id.et_Category);
        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,AllUsersDataActivity.this);
            }
        });
        Button submit=(Button)this.HealthPolicyDialog.findViewById(R.id.btn_submit);
        uploadFile=(TextView)this.HealthPolicyDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.HealthPolicyDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        RGPrivacypolocy = (RadioGroup) this.HealthPolicyDialog.findViewById(R.id.fd_radioGroup);
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(HealthPolicyDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                HPPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask();    runner.execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitOnClickHealthPolicy();

            }
        });


        LinearLayout LLHeadiLine   = (LinearLayout) this.HealthPolicyDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.HealthPolicyDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthPolicyDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });

    }

    private void SubmitOnClickHealthPolicy() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.HealthPolicy_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.HealthPolicy_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.HealthPolicy_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, HealthPolicyView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{

                    UserDataDTO dtp = new UserDataDTO();
                    dtp.setFiles("");
                    dtp.setId("");
                    dtp.setFamilyNames("");
                    dtp.setName("a");
                    dtp.setDocumentCategory("a");
                    dtp.setDetails("a");
                    dtp.setDocuments("a");
                    dtp.setNumber("a");
                    dtp.setDate("a");
                    dtp.setCompanyName("a");
                    dtp.setCategory("a");
                    dtp.setTitle("a");
                    dtp.setQualification("a");
                    dtp.setDoctorName("a");
                    dtp.setSpecailization("a");
                    dtp.setView("a");
                    dtp.setFileName(tvFileName.getText().toString());
                    dtp.setRelation("a");
                    dtp.setGender("a");
                    dtp.setDno("a");
                    dtp.setInsurerType(SpInsuranceType.getSelectedItem().toString());
                    dtp.setInsuranceId(InsuranceId.getText().toString());
                    dtp.setInsuranceProvider("a");
                    dtp.setFamilyNameId("a");
                    dtp.setEncryptedKey("a");
                    dtp.setDateCreated(presETDAte.getText().toString());
                    dtp.setViewStatus("a");


                    try {
                        // Now, need to interact with StudentDetails table/object, so initialize DAO for that
                        final Dao<UserDataDTO, Integer> studentDao = getHelper().getUserDataDao();

                        //This is the way to insert data into a database table
                        studentDao.create(dtp);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }







                    HealthPoliciesTask task=new HealthPoliciesTask();      task.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void EmergencyContactPopup() {
        emergencyContactView=View.inflate(AllUsersDataActivity.this,R.layout.emergency_cont_details_fragment,null);
        emergencyContactView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.emergencyContactDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.emergencyContactDialog.setContentView(emergencyContactView);
        this.emergencyContactDialog.setCancelable(true);
        this.emergencyContactDialog.show();

        Window window = this.emergencyContactDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        PresFMSpin=(Spinner)this.emergencyContactDialog.findViewById(R.id.sRelation);// PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getRelation()));
        String[] some_array = getResources().getStringArray(R.array.selRelation);
        PresFMSpin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,some_array));
        PresFMSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        // Spinner sDoc=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sDocs); sDoc.setSelection(AppHelper.setValueToSpinner(sDoc,model.getDocuments()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        ETBirthday=(EditText)this.emergencyContactDialog.findViewById(R.id.et_Date);     // presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
       PresETDetail = (EditText) this.emergencyContactDialog.findViewById(R.id.et_details);  //  PresETDetail.setText(model.getDetails());
         etname = (EditText) this.emergencyContactDialog.findViewById(R.id.et_Name);   // etname.setText(model.getName());
        etnumber = (EditText) this.emergencyContactDialog.findViewById(R.id.et_Number);  //  etnumber.setText(model.getDetails());
        // EditText etcateogory = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_Category);    etcateogory.setText(model.getCategory());
      Button BtnSubmit=(Button)this.emergencyContactDialog.findViewById(R.id.btn_submit);
        ETBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { AppHelper.getPerfectDate(ETBirthday,AllUsersDataActivity.this);
            }
        });
        RGPrivacypolocy = (RadioGroup) this.emergencyContactDialog.findViewById(R.id.pp_radioGroup);
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(emergencyContactDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        uploadFile=(TextView)this.emergencyContactDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.emergencyContactDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 try {
            if (ActivityCompat.checkSelfPermission(AllUsersDataActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AllUsersDataActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(getPickImageChooserIntent(), 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        });


        /*try {
            if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/





    BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEmergencyonClick();

            }
        });

        LinearLayout LLHeadiLine   = (LinearLayout) this.emergencyContactDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.emergencyContactDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emergencyContactDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
    }

    private void submitEmergencyonClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.FamilyDrDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.FamilyDrDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.FamilyDrDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, emergencyContactView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    EmergencyRunner runner=new EmergencyRunner();
                    runner.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void getPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override //Override from ActivityCompat.OnRequestPermissionsResultCallback Interface
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                }
                return;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11) {
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                doFileUpload(realPath);
            }
            // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19) {
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                doFileUpload(realPath);
            }

            // SDK > 19 (Android 4.4)
            else {
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                doFileUpload(realPath);
            }

        }
    }


    private void HealthDetailsPopup() {
        HealthDetailsView=View.inflate(AllUsersDataActivity.this,R.layout.helath_details_fragmetnt,null);
        HealthDetailsView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.HealthDetailsDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.HealthDetailsDialog.setContentView(HealthDetailsView);
        this.HealthDetailsDialog.setCancelable(true);
        this.HealthDetailsDialog.show();

        Window window = this.HealthDetailsDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.HealthDetailsDialog.findViewById(R.id.sFamilynames);

        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        presETDAte=(EditText)this.HealthDetailsDialog.findViewById(R.id.et_Date);   //   presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        PresETDetail = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_details);  //  PresETDetail.setText(model.getDetails());
       //  PresETDetail.setText(model.getDetails());
         etcateogory = (Spinner) this.HealthDetailsDialog.findViewById(R.id.et_Category);
        CateogoryTask task=new CateogoryTask();
        task.execute();
        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,AllUsersDataActivity.this);
            }
        });
        RGPrivacypolocy = (RadioGroup) this.HealthDetailsDialog.findViewById(R.id.pp_radioGroup);
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(HealthDetailsDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
       Button submit=(Button)this.HealthDetailsDialog.findViewById(R.id.btn_submit);
        uploadFile=(TextView)this.HealthDetailsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.HealthDetailsDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask();    runner.execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitHealthDetailsClick();

            }
        });


        LinearLayout LLHeadiLine   = (LinearLayout) this.HealthDetailsDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.HealthDetailsDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthDetailsDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });

        SpFmilyNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             //   ((TextView)adapterView.getChildAt(0)).setTextSize((int)getResources().getDimension(R.dimen.radioButtonText));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void submitHealthDetailsClick() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.OtherDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.OtherDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.OtherDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, HealthDetailsView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    HealthDetailsTask task=new HealthDetailsTask();
                    task.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void OtherDetailsPopup() {
        otherdetailsView=View.inflate(AllUsersDataActivity.this,R.layout.other_details_fragment,null);
        otherdetailsView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.otherdetailsDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.otherdetailsDialog.setContentView(otherdetailsView);
        this.otherdetailsDialog.setCancelable(true);
        this.otherdetailsDialog.show();

        Window window = this.otherdetailsDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.otherdetailsDialog.findViewById(R.id.sFamilynames);
        EtDate=(EditText)this.otherdetailsDialog.findViewById(R.id.et_Date);
        ETDetails = (EditText) this.otherdetailsDialog.findViewById(R.id.et_details);
        RGPrivacypolocy = (RadioGroup) this.otherdetailsDialog.findViewById(R.id.pp_radioGroup);
        EtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(EtDate,AllUsersDataActivity.this);
            }
        });
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(otherdetailsDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask();    runner.execute();
        uploadFile=(TextView)this.otherdetailsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.otherdetailsDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        Button BtnSubmit   = (Button) this.otherdetailsDialog.findViewById(R.id.btn_submit);
        LinearLayout LLHeadiLine   = (LinearLayout) this.otherdetailsDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.otherdetailsDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherdetailsDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOtherDetailsClick();

            }
        });
    }

    private void submitOtherDetailsClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.OtherDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.OtherDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.OtherDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, otherdetailsView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    OtherDetails runner  = new OtherDetails();   runner.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void FamilyDoctorPopup() {
        familyDoctorView=View.inflate(AllUsersDataActivity.this,R.layout.family_dr_details_frag,null);
        familyDoctorView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.familyDoctorDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.familyDoctorDialog.setContentView(familyDoctorView);
        this.familyDoctorDialog.setCancelable(true);
        this.familyDoctorDialog.show();

        Window window = this.familyDoctorDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpRelation=(Spinner)this.familyDoctorDialog.findViewById(R.id.sRelation);
        String[] some_array = getResources().getStringArray(R.array.selRelation);
        SpRelation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,some_array));

        SpRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        EtDate=(EditText)this.familyDoctorDialog.findViewById(R.id.et_Date);
        ETDetails= (EditText) this.familyDoctorDialog.findViewById(R.id.et_details);
        ETName = (EditText) this.familyDoctorDialog.findViewById(R.id.et_Name);
        EtNumber = (EditText) this.familyDoctorDialog.findViewById(R.id.et_Number);
        RGPrivacypolocy = (RadioGroup) this.familyDoctorDialog.findViewById(R.id.pp_radioGroup);
        LinearLayout LLHeadiLine   = (LinearLayout) this.familyDoctorDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.familyDoctorDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyDoctorDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
        Button BtnSubmit =  (Button) this.familyDoctorDialog.findViewById(R.id.btn_submit);
        EtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(EtDate,AllUsersDataActivity.this);
            }
        });
        uploadFile=(TextView)this.familyDoctorDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.familyDoctorDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitOnFamilydoctorClick();

            }
        });
    }

    private void submitOnFamilydoctorClick() {


        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.FamilyDrDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.FamilyDrDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.FamilyDrDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, familyDoctorView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    FamilyDoctorDetails runner =new FamilyDoctorDetails(); runner.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }




    }

    private void PrescriptionPopup() {
        prescriptionView=View.inflate(AllUsersDataActivity.this,R.layout.add_prescription_fragment,null);
        prescriptionView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.prescriptionDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.prescriptionDialog.setContentView(prescriptionView);
        this.prescriptionDialog.setCancelable(true);
        this.prescriptionDialog.show();

        Window window = this.prescriptionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.prescriptionDialog.findViewById(R.id.sFamilynames);
        DrName = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);
        etcateogory = (Spinner) this.prescriptionDialog.findViewById(R.id.et_DrSpec);

        CateogoryTask task=new CateogoryTask();
        task.execute();


        DrDate =(EditText)this.prescriptionDialog.findViewById(R.id.et_Date);
        ETDetails = (EditText) this.prescriptionDialog.findViewById(R.id.et_details);
        RGPrivacypolocy = (RadioGroup) this.prescriptionDialog.findViewById(R.id.pp_radioGroup);
        LinearLayout LLHeadiLine   = (LinearLayout) this.prescriptionDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        Button BtnSubmit = (Button)this.prescriptionDialog.findViewById(R.id.btn_submit);

        FamilyNamesAsyncTask sRunner =new FamilyNamesAsyncTask();   sRunner.execute();
        DrDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(DrDate,AllUsersDataActivity.this);
            }
        });
        uploadFile=(TextView)this.prescriptionDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.prescriptionDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnSubmitPrescriptionClick();


            }
        });
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(prescriptionDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        ImageView imgClise = (ImageView)this.prescriptionDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
    }

    private void OnSubmitPrescriptionClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.AddPresc_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.AddPresc_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.AddPresc_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, prescriptionView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    AddPrescrptDetails runer = new AddPrescrptDetails();    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void EducationPopup() {
        educationView=View.inflate(AllUsersDataActivity.this,R.layout.education_fragment,null);
        educationView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.educationdialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.educationdialog.setContentView(educationView);
        this.educationdialog.setCancelable(true);
        this.educationdialog.show();

        Window window = this.educationdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

         EduSpin   = (LinearLayout) this.educationdialog.findViewById(R.id.eduspin);

        //  EduSpin.setVisibility(View.VISIBLE);
        ETBirthday   = (EditText) this.educationdialog.findViewById(R.id.et_Date);     //   ETdate.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
   //    ETBirthday = (EditText) this.educationdialog.findViewById(R.id.et_Birthday); // ETBirthday.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        ETDetails = (EditText) this.educationdialog.findViewById(R.id.et_details);  //  ETDetails.setText(model.getDetails());
        SpFmilyNames = (Spinner) this.educationdialog.findViewById(R.id.sFamilynames); //  Eduspin.setSelection(AppHelper.setValueToSpinner(Eduspin,model.getRelation()));
         Eduqualspin=(Spinner)this.educationdialog.findViewById(R.id.sQualification);
        EducationalTask task=new EducationalTask();
        task.execute();
      /*  String[] some_array = getResources().getStringArray(R.array.selQualification);
        Eduqualspin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,some_array));*/
       /* Eduqualspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        // Eduqualspin.setSelection(AppHelper.setValueToSpinner(Eduqualspin,model.getQualification()));
        Button BtnSubmit=(Button)this.educationdialog.findViewById(R.id.btn_submit);
        RGPrivacypolocy = (RadioGroup) this.educationdialog.findViewById(R.id.pp_radioGroup);
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(educationdialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask();    runner.execute();
        ETBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { AppHelper.getPerfectDate(ETBirthday,AllUsersDataActivity.this);
            }
        });
        uploadFile=(TextView)this.educationdialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.educationdialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitOnEducationClick();
                  }
        });

       /* ImageView CloseDialog = (ImageView) this.educationdialog.findViewById(R.id.closeDialog);
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationdialog.dismiss();
            }
        });*/
        LinearLayout LLHeadiLine   = (LinearLayout) this.educationdialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.educationdialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationdialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });

    }

    private void submitOnEducationClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.EducationDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.EducationDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.EducationDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, educationView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    EducationRunner runer = new EducationRunner();    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void UploadDocumentPopup() {
        uploadDocumentView=View.inflate(AllUsersDataActivity.this,R.layout.upload_doc_fragment,null);
        uploadDocumentView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.uploadDocumentDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.uploadDocumentDialog.setContentView(uploadDocumentView);
        this.uploadDocumentDialog.setCancelable(true);
        this.uploadDocumentDialog.show();

        Window window = this.uploadDocumentDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sFamilynames); //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        sDoc=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sDocs);
        String[] some_array = getResources().getStringArray(R.array.selDocs);
        sDoc.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,some_array));

        sDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //sDoc.setSelection(AppHelper.setValueToSpinner(sDoc,model.getDocuments()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        ETBirthday=(EditText)this.uploadDocumentDialog.findViewById(R.id.et_Date);  //    presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
         PresETDetail = (EditText) this.uploadDocumentDialog.findViewById(R.id.et_details);  //  PresETDetail.setText(model.getDetails());
        etnumber = (EditText) this.uploadDocumentDialog.findViewById(R.id.et_Number);  //  etnumber.setText(model.getDetails());
        // EditText etcateogory = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_Category);    etcateogory.setText(model.getCategory());
        Button BtnSubmit=(Button)this.uploadDocumentDialog.findViewById(R.id.btn_submit);
        RGPrivacypolocy = (RadioGroup) this.uploadDocumentDialog.findViewById(R.id.pp_radioGroup);
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(uploadDocumentDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask();    runner.execute();
        ETBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { AppHelper.getPerfectDate(ETBirthday,AllUsersDataActivity.this);
            }
        });
        uploadFile=(TextView)this.uploadDocumentDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.uploadDocumentDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnUploadDocumentClick();

               }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.uploadDocumentDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.uploadDocumentDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDocumentDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
    }

    private void submitOnUploadDocumentClick() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.uploadDocs_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.uploadDocs_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.uploadDocs_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, uploadDocumentView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    UploadDocument runer = new UploadDocument();    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }





    }

    private void EmploymentPopup() {
        employmentView=View.inflate(AllUsersDataActivity.this,R.layout.employment_fragment,null);
        employmentView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this,R.anim.zoom_in_enter));
        this.employmentDialog=new Dialog(AllUsersDataActivity.this,R.style.NewDialog);
        this.employmentDialog.setContentView(employmentView);
        this.employmentDialog.setCancelable(true);
        this.employmentDialog.show();

        Window window = this.employmentDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        SpFmilyNames=(Spinner)this.employmentDialog.findViewById(R.id.sFamilynames); //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);   // Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        ETBirthday=(EditText)this.employmentDialog.findViewById(R.id.et_Date);     // presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
      PresETDetail = (EditText) this.employmentDialog.findViewById(R.id.et_details);   // PresETDetail.setText(model.getDetails());
      Button BtnSubmit=(Button)this.employmentDialog.findViewById(R.id.btn_submit);
        RGPrivacypolocy = (RadioGroup) this.employmentDialog.findViewById(R.id.p_radioGroup);
        ETBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { AppHelper.getPerfectDate(ETBirthday,AllUsersDataActivity.this);
            }
        });
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(employmentDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask sRunner =new FamilyNamesAsyncTask();   sRunner.execute();
        uploadFile=(TextView)this.employmentDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.employmentDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnEmployClick();

                   }
        });



        LinearLayout LLHeadiLine   = (LinearLayout) this.employmentDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.employmentDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employmentDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });
    }

    private void submitOnEmployClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.EmpDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.EmpDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.EmpDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, employmentView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    EmploymentRunner runer = new EmploymentRunner();    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void FamilyMembersPopUp() {
        famMembersView = View.inflate(AllUsersDataActivity.this, R.layout.add_family_details_frag, null);
        famMembersView.startAnimation(AnimationUtils.loadAnimation(AllUsersDataActivity.this, R.anim.zoom_in_enter));
        this.famMembrsDialog = new Dialog(AllUsersDataActivity.this, R.style.NewDialog);
        this.famMembrsDialog.setContentView(famMembersView);
        this.famMembrsDialog.setCancelable(true);
        this.famMembrsDialog.show();

        Window window = this.famMembrsDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);



        LinearLayout llsrelation = (LinearLayout) this.famMembrsDialog.findViewById(R.id.llsrelation);        //llsrelation.setVisibility(View.VISIBLE);
        ETName = (EditText) this.famMembrsDialog.findViewById(R.id.et_Name);
        ETBirthday = (EditText) this.famMembrsDialog.findViewById(R.id.et_Date);
        ETDetails = (EditText) this.famMembrsDialog.findViewById(R.id.et_details);
        SpRelation = (Spinner) this.famMembrsDialog.findViewById(R.id.sRelation);
        RGGender = (RadioGroup) this.famMembrsDialog.findViewById(R.id.radioSexGroup);
        RGPrivacypolocy = (RadioGroup) this.famMembrsDialog.findViewById(R.id.fd_radioGroup);
        Button BtnSubmit = (Button) this.famMembrsDialog.findViewById(R.id.btn_submit);

        String[] some_array = getResources().getStringArray(R.array.selRelation);
        SpRelation.setAdapter(new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,some_array));

        SpRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(getResources().getDimension(R.dimen.padding_x2));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        RGGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId)
                {   case R.id.radioMale:selectedGender="Male";         break;
                    case R.id.radioFemale:  selectedGender="Female";   break;
                    case R.id.radioOther: selectedGender="Other";      break;
                }
            }
        });

        ETBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { AppHelper.getPerfectDate(ETBirthday,AllUsersDataActivity.this);
            }
        });
        RGPrivacypolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=RGPrivacypolocy.indexOfChild(famMembrsDialog.findViewById(RGPrivacypolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        uploadFile=(TextView)this.famMembrsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.famMembrsDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnClick();

            }
        });

        LinearLayout LLHeadiLine = (LinearLayout) this.famMembrsDialog.findViewById(R.id.llHeading);  LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView) this.famMembrsDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                famMembrsDialog.dismiss();
                closeSubMenusFab();
                FmLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
                AsyncTaskRunner runner = new AsyncTaskRunner();   runner.execute();
            }
        });

    }//popUp

    private void submitOnClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = this.getResources().getStringArray(R.array.addFamDetails_ids_array);
            String[] strErrMsgs = this.getResources().getStringArray(R.array.addFamDetails_errors_array);
            String[] strCompTypeArr = this.getResources().getStringArray(R.array.addFamDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(this,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(this, aList, famMembersView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",this).isEmpty() ||  SessinSave.getsessin("profile_id",this).equalsIgnoreCase("") ){
                    Toast.makeText(this,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    AddFamilyMembers runer = new AddFamilyMembers();
                    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void closeSubMenusFab() {
        LFamilyMembers.setVisibility(View.INVISIBLE);
        LEmployment.setVisibility(View.INVISIBLE);
        LEducation.setVisibility(View.INVISIBLE);
        LUploadDocuments.setVisibility(View.INVISIBLE);
        LEmergencyContact.setVisibility(View.INVISIBLE);
        LHealthDetails.setVisibility(View.INVISIBLE);
        LOtherDetails.setVisibility(View.INVISIBLE);
        LFamilyDocter.setVisibility(View.INVISIBLE);
        LPrescription.setVisibility(View.INVISIBLE);
        LHealthPolicy.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_action_plus);
        fabExpanded = false;
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String>   {
        private ArrayList<AllUserDataModel> rowItems;
        private String pId;
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(AllUsersDataActivity.this,"Please wait","Loading...");
            pId= SessinSave.getsessin("profile_id",AllUsersDataActivity.this);
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allUserData1+pId  ,"GET",null);
            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            rowItems = new ArrayList<AllUserDataModel>();
            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0) {
                    Log.i("array:=>", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        AllUserDataModel item = new AllUserDataModel();

                        item.setFamilyNames( obj.getString("familyNames"));
                        item.setFile( obj.getString("files"));
                        item.setSpecailization( obj.getString("specailization"));
                        item.setDno( obj.getString("dno"));
                        item.setFamilyNameId( obj.getString("familyNameId"));
                        item.setDocuments( obj.getString("documents"));
                        item.setRelation( obj.getString("relation"));
                        item.setDocumentCategory( obj.getString("documentCategory"));
                        item.setNumber( obj.getString("number"));
                        item.setCompanyName( obj.getString("companyName"));
                        item.setDate( obj.getString("date"));
                        item.setId( obj.getString("id"));
                        item.setQualification( obj.getString("qualification"));
                        item.setTitle( obj.getString("title"));
                        item.setCategory( obj.getString("category"));
                        item.setDetails( obj.getString("details"));
                        item.setViewStatus( obj.getString("viewStatus"));
                        item.setName( obj.getString("name"));
                        item.setFileName( obj.getString("fileName"));
                        item.setDateCreated( obj.getString("dateCreated"));
                        item.setGender( obj.getString("gender"));
                        item.setView( obj.getString("view"));
                        item.setDoctorName( obj.getString("doctorName"));
                        item.setInsuranceId(obj.getString("insuranceId"));
                        item.setInsurerType(obj.getString("insurerType"));
                        item.setInsuranceProvider(obj.getString("insuranceProvider"));
//                        AllUserDataModel item = new AllUserDataModel(obj.getString("file"), obj.getString("id"), obj.getString("familyNames"), obj.getString("name"), obj.getString("documentCategory"), obj.getString("details"), obj.getString("documents"), obj.getString("number"), obj.getString("date"), obj.getString("fileName"),obj.getString("relation"),obj.getString("gender"),obj.getString("dno"),obj.getString("dateCreated"),obj.getString("viewStatus")  );
                        rowItems.add(item);
                    }
                }else {

                }
            }catch (Exception e){
            }
            AUDAdapter adapter = new AUDAdapter(AllUsersDataActivity.this, rowItems);
            LvAllUserData.setAdapter(adapter);
            if(pDialog.isShowing()){  pDialog.dismiss();  }
            adapter.notifyDataSetChanged();
        }//onPostExecute


    }//AsuncTask class
    private class FamilyNamesAsyncTask extends AsyncTask<String, String, String> {
        private String pId;
        private ProgressDialog pDialog;
        private ArrayList<String> famNamesItems;

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(AllUsersDataActivity.this,"Please wait","Loading...");
            pId= SessinSave.getsessin("profile_id",AllUsersDataActivity.this);
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.allFamilyNames+pId  ,"GET",null);
            Log.i("familynames",content);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostexc:",result);
            famNamesItems = new ArrayList<String>();
            try {
                JSONArray array=new JSONArray(result);
                if(array.length()>0) {
                    Log.i("array:=>", array.toString());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        famNamesItems.add(obj.getString("fullName"));
                    }

                }else {
                }
                famNamesItems.add(0,"---Select Family Members---");
                famNamesItems.add(1,SessinSave.getsessin("FullName",AllUsersDataActivity.this));
            }catch (Exception e){
            }
            ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(AllUsersDataActivity.this, android.R.layout.simple_list_item_single_choice, famNamesItems);
              SpFmilyNames.setAdapter(spinnerArrayAdapter);

//            SpFmilyNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
//                  getResources().getDimension(R.dimen.radioButtonText));
//                }  ((TextView)parent.getChildAt(0)).setTextSize(
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });

            if(pDialog.isShowing()){  pDialog.dismiss();  }
            spinnerArrayAdapter.notifyDataSetChanged();

        }//onPostExecute


    }//AsuncTask class

    public class AddFamilyMembers extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String name, birthday,details,relation,filename;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this,     "Please wait",   "loading...");
            name=ETName.getText().toString();
          birthday=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETBirthday.getText().toString());
            details=ETDetails.getText().toString();
            relation=SpRelation.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames","");
                obj.accumulate("name",name);
                obj.accumulate("documentCategory","Family Members");
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",birthday);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation",relation);
                obj.accumulate("gender",selectedGender);
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
         Log.i("StoredData-->",ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this));
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask
    public class AddPrescrptDetails extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String familyName,name, date,details,relation,filename;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this,     "Please wait",   "loading...");
            name=DrName.getText().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(DrDate.getText().toString());
            details=ETDetails.getText().toString();
            relation=etcateogory.getSelectedItem().toString();
            familyName=SpFmilyNames.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familyName);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Prescription");
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category",relation);
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName",name);
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask
    public class FamilyDoctorDetails extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String name, date,number,details,relation,filename;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this,     "Please wait",   "loading...");
            name=ETName.getText().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(EtDate.getText().toString());

            details=ETDetails.getText().toString();
            number=EtNumber.getText().toString();
            relation=SpRelation.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames","");
                obj.accumulate("name",name);
                obj.accumulate("documentCategory","Family Doctor");
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number",number);
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation",relation);
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
     //       String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/storeUserData/"+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask
    public class OtherDetails extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        private String  date,details,famName,filename;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this,     "Please wait",   "loading...");
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(EtDate.getText().toString());
            details=ETDetails.getText().toString();
            famName=SpFmilyNames.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",famName);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Other Details");
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/storeUserData/"+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask


    private class HealthDetailsTask extends AsyncTask<String,String,String> {
        String familymember,date,detail,cateogory,filename;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
         progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
            familymember=SpFmilyNames.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(presETDAte.getText().toString());

            detail=PresETDetail.getText().toString();
            cateogory=etcateogory.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familymember);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Health Data");
                obj.accumulate("details",detail);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category",cateogory);
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
        }

    private class UploadDocument extends AsyncTask<String,String,String>{
        String familyname,doc,detail,number,date,filename;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
            familyname=SpFmilyNames.getSelectedItem().toString();
            doc=sDoc.getSelectedItem().toString();
            detail=PresETDetail.getText().toString();
            number=etnumber.getText().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETBirthday.getText().toString());

            filename=tvFileName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familyname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Upload Documents");
                obj.accumulate("details",detail);
                obj.accumulate("documents",doc);
                obj.accumulate("number",number);
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }

    private class EmergencyRunner extends AsyncTask<String,String,String> {
        String relation,date,detail,name,number,filename;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
            relation=PresFMSpin.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETBirthday.getText().toString());
            detail=PresETDetail.getText().toString();
            name=etname.getText().toString();
            number=etnumber.getText().toString();
            filename=tvFileName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames","");
                obj.accumulate("name",name);
                obj.accumulate("documentCategory","Emergency Contact");
                obj.accumulate("details",detail);
                obj.accumulate("documents","");
                obj.accumulate("number",number);
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation",relation);
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }

    private class EmploymentRunner extends AsyncTask<String,String,String>{
        String company,date,detail,filename,familyname;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
            company=Empetcompany.getText().toString();
            familyname=SpFmilyNames.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETBirthday.getText().toString());
            detail=PresETDetail.getText().toString();
            filename=tvFileName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familyname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Employment");
                obj.accumulate("details",detail);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName",company);
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }

    private class EducationRunner extends AsyncTask<String,String,String> {
        String date,detail,familyname,educationdegree,filename;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");

            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETBirthday.getText().toString());
            detail=ETDetails.getText().toString();
          familyname=SpFmilyNames.getSelectedItem().toString();
            educationdegree=Eduqualspin.getSelectedItem().toString();
            filename=tvFileName.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familyname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Education");
                obj.accumulate("details",detail);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification",educationdegree);
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }



    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private void doFileUpload(String path){

        final ProgressDialog prgDialog = new ProgressDialog(AllUsersDataActivity.this);
        prgDialog.setCancelable(false);


        prgDialog.setMessage("Uploading file....");
        prgDialog.show();

        try{

            String urlString =ApisHelper.fileupload+SessinSave.getsessin("profile_id",AllUsersDataActivity.this);

            File file=new File(path);

            RequestParams params = new RequestParams();
            params.put("file",file);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(urlString, params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("123456789-->","onSuccess");
                    prgDialog.cancel();
                    String s=new String(responseBody);
                    Log.i("123456789-->",s);
                    Pattern p = Pattern.compile("\"([^\"]*)\"");
                    Matcher m = p.matcher(s);
                    while (m.find()) {
                        tvFileName.setText(m.group(1));
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("123456789-->","onFailure");
                    prgDialog.cancel();

                }
            });

        }
        catch (Exception e)
        { Log.i("123456789-->","Exceptiobn");
            prgDialog.cancel();
        }
    }


    private class HealthPoliciesTask extends AsyncTask<String,String,String>{
        String date,detail,familyname,filename,InsuranceType,InsuranceProvider,InsurId;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(presETDAte.getText().toString());
            detail=PresETDetail.getText().toString();
            familyname=SpFmilyNames.getSelectedItem().toString();
            InsuranceType=SpInsuranceType.getSelectedItem().toString();
            InsuranceProvider=SpInsuranceProvider.getSelectedItem().toString();
            InsurId=InsuranceId.getText().toString();
            filename=tvFileName.getText().toString();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("files",null);
                obj.accumulate("id","");
                obj.accumulate("familyNames",familyname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory","Health Policies");
                obj.accumulate("details",detail);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
//                obj.accumulate("date","1525237200000");
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",HPPrivacyPolocy);
                obj.accumulate("fileName",filename);
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("insurerType",InsuranceType);
                obj.accumulate("insuranceId",InsurId);
                obj.accumulate("insuranceProvider",InsuranceProvider);
                obj.accumulate("viewStatus","");

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",AllUsersDataActivity.this),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(AllUsersDataActivity.this,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }

    private class CateogoryTask extends AsyncTask<String,String,String>{
        private ProgressDialog progressDialog;
        private ArrayList<String> speclist;
        private String quant;
        private String[] arr_lab,arr_lab_codes;
        private ArrayList<String> Lablist;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AllUsersDataActivity.this, "Please wait", "loading...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.specializations,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                JSONArray array=new JSONArray(result);
                arr_lab = new String[array.length()];
                arr_lab_codes= new String[array.length()];
                if (array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                        arr_lab[i]=object.getString("specialization");
                        arr_lab_codes[i]=object.getString("id");
                    }
                    Lablist = new ArrayList<String>(Arrays.asList(arr_lab));
                    Lablist.add(0,"---Select specialization---");

                }
                etcateogory.setAdapter(new ArrayAdapter<String>(AllUsersDataActivity.this,android.R.layout.simple_list_item_single_choice,Lablist));



            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }

    private class EducationalTask extends AsyncTask<String,String,String>{
        String[] arr_lab,arr_lab_codes;
        private ArrayList<String> qualificationlist;


        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.qualifications,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray array=new JSONArray(s);
                arr_lab = new String[array.length()];
                arr_lab_codes= new String[array.length()];
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                      arr_lab[i]=object.getString("qualification");
                        arr_lab_codes[i]=object.getString("id");
                    }
                    qualificationlist = new ArrayList<String>(Arrays.asList(arr_lab));
                    qualificationlist.add(0,"---Select qualification---");

                }
                Eduqualspin.setAdapter(new ArrayAdapter<String>(AllUsersDataActivity.this,android.R.layout.simple_list_item_single_choice,qualificationlist));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
