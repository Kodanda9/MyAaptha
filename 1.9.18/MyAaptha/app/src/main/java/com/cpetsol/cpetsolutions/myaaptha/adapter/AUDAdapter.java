package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AppHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.model.AllUserDataModel;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.FileLoader;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.listener.FileRequestListener;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.pojo.FileResponse;
import com.cpetsol.cpetsolutions.myaaptha.util.Downloader.request.FileLoadRequest;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class AUDAdapter extends BaseAdapter {
    private final Activity activity;
    private final ArrayList<AllUserDataModel> userItems;
    private LayoutInflater inflater;
    private TextView TvFamMemName;
    private ImageView TvFileName;
    private TextView Tv1;
    private TextView TvName;
    private TextView uploadFile;
    private TextView tvFileName;
    private TextView Tv3;
    private TextView Tv2;
    private TextView TvFiles;
    private TextView tvGender,tvInsuranceId,tvInsuranceType,tvInsuranceProvider;
    private TextView tvdoc;
    private TextView tvdocuments;
    private TextView tvFamilyName;
    private TextView tvRelation;
    private TextView tvNumber;
    private TextView tvCompanyname;
    private TextView tvCateogory;
    private TextView tvTitle;
    private TextView tvQualification;
    private TextView tvDoctorName;
    private TextView tvSpecialization;
    private Typeface fontAwesomeFont;
    private ImageView imgPrivacy,imgGender;
    private LinearLayout LLDocCategory,FamilyMember;
    private View famMembersView,educationView,HealthPolicyView,prescriptionView,employmentView,otherdetailsView,familyDoctorView,emergencyContactView,uploadDocumentView,HealthDetailsView;
    private Dialog imageOrFileViewDialog,HealthPolicyDialog,famMembrsDialog,educationdialog,prescriptionDialog,employmentDialog,otherdetailsDialog,familyDoctorDialog,emergencyContactDialog,uploadDocumentDialog,HealthDetailsDialog;
    private EditText FMETDetails,FMETBirthday,FMETName,Empetcompany,PresETFN,InsuranceId,
            presETDAte,PresETDetail,UploadETDetail,Uploadetnumber,uploadETDAte;
    private RadioGroup FmRGPPolocy,FmRGGender,EdradioGroup,famradioGroup,RGPrivacypolocy,
            EmpradioGroup,PrescradioGroup,emerradioGroup,UploadradioGroup,OtherradioGroup,healthradioGroup;
    private Spinner FmSpRelation,PresDRSpec,emerFMSpin,UploadFMSpin,UploadsDoc,SpFmilyNames,SpInsuranceType,SpInsuranceProvider;
    private String fmPrivacyPolocy,eduPrivacyPolocy,empPrivacyPolocy,famPrivacyPolocy,prePrivacyPolocy,healthPrivacyPolocy,emerPrivacyPolocy,uploadPrivacyPolocy,otherPrivacyPolocy;
    private String selectedFamMembGender;

    private EditText ETdate,ETBirthday,fametnumber,ETDetails,emersETDAte,fametname,famETDetail,famETDAte,emerETDetail,emeretname,emeretnumber,otherETDAte,otherETDetail,healthETDetail,healthETDAte;
    private Spinner Eduspin,healthFMSpin,Eduqualspin,PresFMSpin,healthetcateogory,etcateogory;
    private RadioGroup RGGender;
    private String HPPrivacyPolocy;
    private RadioGroup HPPrivacypolocy;
    private int PICK_FROM_GALLERY;
    private int[] array;
    private String familyName;
    private String labname;



    public AUDAdapter(Activity ex, ArrayList<AllUserDataModel> rowItems) {
        this.activity=ex;
        this.userItems=rowItems;
    }
    @Override
    public int getCount() {
        return userItems.size();
    }
    @Override
    public Object getItem(int i) {
        return userItems.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.all_user_lv_row, null);
        fontAwesomeFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf");





       TvFamMemName= (TextView) convertView.findViewById(R.id.tvFMName);
       Tv1= (TextView) convertView.findViewById(R.id.tv1);
       Tv3= (TextView) convertView.findViewById(R.id.tv3);
       TvName= (TextView) convertView.findViewById(R.id.tvName);
       TvFiles= (TextView) convertView.findViewById(R.id.tvFiles);
        tvdoc= (TextView) convertView.findViewById(R.id.tvdoc);
        tvdocuments= (TextView) convertView.findViewById(R.id.tvdocuments);
        tvNumber= (TextView) convertView.findViewById(R.id.tvNumber);
        tvCompanyname= (TextView) convertView.findViewById(R.id.tvCompanyname);
        tvCateogory= (TextView) convertView.findViewById(R.id.tvCateogory);
        tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
        tvQualification= (TextView) convertView.findViewById(R.id.tvQualification);
        tvDoctorName= (TextView) convertView.findViewById(R.id.tvDoctorName);
        tvSpecialization= (TextView) convertView.findViewById(R.id.tvSpecialization);
        tvRelation= (TextView) convertView.findViewById(R.id.tvRelation);
        tvFamilyName= (TextView) convertView.findViewById(R.id.tvFamilyName);
        tvGender= (TextView) convertView.findViewById(R.id.tvGender);
        tvInsuranceId= (TextView) convertView.findViewById(R.id.tvinsurid);
        tvInsuranceType= (TextView) convertView.findViewById(R.id.tvinsurtype);
        tvInsuranceProvider= (TextView) convertView.findViewById(R.id.tvinsurprovider);
       imgGender= (ImageView) convertView.findViewById(R.id.imgGender);
       imgPrivacy= (ImageView) convertView.findViewById(R.id.imgPrivacy);

        LinearLayout    LLInsuranceId= (LinearLayout) convertView.findViewById(R.id.llinsuranceid);
        LinearLayout    LLInsuranceType= (LinearLayout) convertView.findViewById(R.id.llinsurancetype);
        LinearLayout     LLInsuranceProvider= (LinearLayout) convertView.findViewById(R.id.llinsuranceprovider);
       LLDocCategory= (LinearLayout) convertView.findViewById(R.id.lldocumentcateogory);



        LinearLayout llFiles = (LinearLayout) convertView.findViewById(R.id.llFiles);
        LinearLayout llsrelation = (LinearLayout) convertView.findViewById(R.id.llsrelation);
        LinearLayout lldocumentcateogory = (LinearLayout) convertView.findViewById(R.id.lldocumentcateogory);
        LinearLayout lldocuments = (LinearLayout) convertView.findViewById(R.id.lldocuments);
        LinearLayout llnumber = (LinearLayout) convertView.findViewById(R.id.llnumber);
        LinearLayout llcompanyname = (LinearLayout) convertView.findViewById(R.id.llcompanyname);
        LinearLayout llcateogory = (LinearLayout) convertView.findViewById(R.id.llcateogory);
        LinearLayout lltitle = (LinearLayout) convertView.findViewById(R.id.lltitle);
        LinearLayout llqualification = (LinearLayout) convertView.findViewById(R.id.llqualification);
        LinearLayout lldoctername = (LinearLayout) convertView.findViewById(R.id.lldoctername);
        LinearLayout llspecialization = (LinearLayout) convertView.findViewById(R.id.llspecialization);
        LinearLayout llrelation = (LinearLayout) convertView.findViewById(R.id.llrelation);
        LinearLayout llgender = (LinearLayout) convertView.findViewById(R.id.llgender);
        LinearLayout LLFamilyName = (LinearLayout) convertView.findViewById(R.id.llFamilyname);
        LinearLayout llfilename = (LinearLayout) convertView.findViewById(R.id.llFilename);
        LinearLayout LLDetails = (LinearLayout) convertView.findViewById(R.id.lldetails);
        LinearLayout LLFMName = (LinearLayout) convertView.findViewById(R.id.llNames);
        LinearLayout LLDate = (LinearLayout) convertView.findViewById(R.id.lldate);
        LinearLayout LLInsuranceID = (LinearLayout) convertView.findViewById(R.id.llinsuranceid);
        LinearLayout LLInsuranceTyp = (LinearLayout) convertView.findViewById(R.id.llinsurancetype);
        LinearLayout LLInsuranceProvide = (LinearLayout) convertView.findViewById(R.id.llinsuranceprovider);

        final AllUserDataModel model=userItems.get(position);
        tvInsuranceId.setText(model.getInsuranceId());
        tvInsuranceType.setText(model.getInsurerType());
        tvInsuranceProvider.setText(model.getInsuranceProvider());
        Log.i("model",model.toString());

        TvFamMemName.setText(model.getName());
        TvName.setText(model.getDocumentCategory());
        TvFiles.setText(model.getFile());
   //     tvdoc.setText(model.getDocumentCategory());
        tvdocuments.setText(model.getDocuments());
        tvNumber.setText(model.getNumber());
        tvCompanyname.setText(model.getCompanyName());
        tvCateogory.setText(model.getCategory());
        tvTitle.setText(model.getTitle());
        tvQualification.setText(model.getQualification());
        tvDoctorName.setText(model.getDoctorName());
        tvSpecialization.setText(model.getSpecailization());
        tvRelation.setText(model.getRelation());
        tvGender.setText(model.getGender());
        tvFamilyName.setText(model.getFamilyNames());




        TextView TvFileName= (TextView) convertView.findViewById(R.id.tvFileName);                   TvFileName.setTypeface(fontAwesomeFont);


        TextView  faiSymb = (TextView) convertView.findViewById(R.id.tvfai_symb);                   faiSymb.setTypeface(fontAwesomeFont);
        TextView TVEditIcon= (TextView) convertView.findViewById(R.id.tv_faiEdit);                   TVEditIcon.setTypeface(fontAwesomeFont);
        TextView TVDeleteIcon= (TextView) convertView.findViewById(R.id.tv_faiDelete);               TVDeleteIcon.setTypeface(fontAwesomeFont);

        TVDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog(model);
            }
        });


        TvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileLoader.with(activity)
                  .load("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg")
                  /*   .load("http://www.myaaptha.com/pp/UserFiles/900000010501/30_U_2018-calander.pdfdecryptedFile")*/
                        .fromDirectory("MyAaptha", FileLoader.DIR_EXTERNAL_PUBLIC)
                         .asFile(new FileRequestListener<File>() {
                            @Override
                            public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                                Glide.with(activity).load(response.getBody()).into(Imgpic);
                                showImageOrFile(model);
                            }

                            @Override
                            public void onError(FileLoadRequest request, Throwable t) {
                                Log.d("Activity", "onError: " + t.getMessage());
                            }
                        });



            }
        });

    //    Toast.makeText(activity,model.getDateCreated()+"    "+model.getDate(),Toast.LENGTH_LONG).show();
        if(model.getDate() == null || model.getDate().equalsIgnoreCase("null") || model.getDate().equalsIgnoreCase("") ){
            Tv1.setText("");
        }else{
            Tv1.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }


        Tv3.setText(model.getDetails());


        if(model.getName().equalsIgnoreCase("") ){
            LLFMName.setVisibility(View.GONE);}else {
            LLFMName.setVisibility(View.VISIBLE);
        }if(model.getDocumentCategory().equalsIgnoreCase("") ){
            LLDocCategory.setVisibility(View.GONE);
        }
        if(model.getFile().equalsIgnoreCase("" )||model.getFile().equalsIgnoreCase("null")) {
       llFiles.setVisibility(View.GONE);
        }else{
            llFiles.setVisibility(View.VISIBLE);
        }
        if (model.getDocumentCategory().equalsIgnoreCase("")){
            lldocumentcateogory.setVisibility(View.GONE);
        }else{
            lldocumentcateogory.setVisibility(View.GONE);
        }
        if(model.getDocuments().equalsIgnoreCase("")||model.getDocuments().equalsIgnoreCase("null")||model.getDocuments().isEmpty()){
            lldocuments.setVisibility(View.GONE);
        }else{
            lldocuments.setVisibility(View.VISIBLE);
        }
        if(model.getNumber().equalsIgnoreCase("")){
            llnumber.setVisibility(View.GONE);
        }else{
            llnumber.setVisibility(View.VISIBLE);
        }
        if(model.getCompanyName().equalsIgnoreCase("")){
            llcompanyname.setVisibility(View.GONE);
        }else{
            llcompanyname.setVisibility(View.VISIBLE);
        }
        if( model.getCategory().equalsIgnoreCase("")){
            llcateogory.setVisibility(View.GONE);
        }else{
            llcateogory.setVisibility(View.VISIBLE);
        }
        if(model.getTitle().equalsIgnoreCase("")){
            lltitle.setVisibility(View.GONE);
        }else{
            lltitle.setVisibility(View.VISIBLE);
        }
        if(model.getQualification().equalsIgnoreCase("")){
            llqualification.setVisibility(View.GONE);
        }else{
            llqualification.setVisibility(View.VISIBLE);
        }
        if(model.getDoctorName().equalsIgnoreCase("")){
            lldoctername.setVisibility(View.GONE);
        }else{
            lldoctername.setVisibility(View.VISIBLE);
        }
        if(model.getSpecailization().equalsIgnoreCase("")){
            llspecialization.setVisibility(View.GONE);
        }else{
            llspecialization.setVisibility(View.VISIBLE);
        }
        if(model.getRelation().equalsIgnoreCase("")){
            llrelation.setVisibility(View.GONE);
        }else{
            llrelation.setVisibility(View.VISIBLE);
        }
        if(model.getGender().equalsIgnoreCase("")||model.getGender().equalsIgnoreCase("null")){
            llgender.setVisibility(View.GONE);
        }else{
            llgender.setVisibility(View.VISIBLE);
        }
        if(model.getFamilyNames().equalsIgnoreCase("")){
            LLFamilyName.setVisibility(View.GONE);
        }else{
            LLFamilyName.setVisibility(View.VISIBLE);
        }
        if(model.getDate().equalsIgnoreCase("")|| model.getDate().isEmpty()||model.getDate().equalsIgnoreCase("null")){
            LLDate.setVisibility(View.GONE);
        }else{
            LLDate.setVisibility(View.VISIBLE);
        }
      if(model.getInsuranceId()=="null"|| model.getInsuranceId().equalsIgnoreCase("")||model.getInsuranceId().isEmpty()){
            LLInsuranceId.setVisibility(View.GONE);
           }else {
            LLInsuranceId.setVisibility(View.VISIBLE);

        }
        if(model.getInsurerType()=="null"|| model.getInsurerType().equalsIgnoreCase("")||model.getInsurerType().isEmpty()){
           LLInsuranceType.setVisibility(View.GONE);

        }else {
           LLInsuranceType.setVisibility(View.VISIBLE);

        }
        if(model.getInsuranceProvider()=="null"|| model.getInsuranceProvider().equalsIgnoreCase("")||model.getInsuranceProvider().isEmpty()){

            LLInsuranceProvider.setVisibility(View.GONE);
        }else {
            LLInsuranceProvider.setVisibility(View.VISIBLE);

        }
       if(model.getFileName().equalsIgnoreCase("")|| model.getFileName().equalsIgnoreCase("null")|| model.getFileName().isEmpty() ){
            TvFileName.setVisibility(View.GONE);
        }else {
           TvFileName.setVisibility(View.VISIBLE);
       }
           if(model.getDetails().equalsIgnoreCase("")|| model.getDetails().isEmpty() ){
               LLDetails.setVisibility(View.GONE);
           }else{
               LLDetails.setVisibility(View.VISIBLE);
           }


        TVEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Layouts:- education_fragment,employment_fragment,add_prescription_fragment,helath_details_fragmetnt,family_dr_details_frag,emergency_cont_details_fragment,upload_doc_fragment,other_details_fragment
                if(model.getDocumentCategory().equalsIgnoreCase("Family Members")){
                    FamilyMembersPopUp(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Education")){
                    EducationPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Prescription")){
                    PrescriptionPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Employment")){
                    EmploymentPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Health Data")){
                    HealthDetailsPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Upload Documents")){
                    UploadDocumentPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Emergency Contact")){
                    EmergencyContactPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Family Doctor")){
                    FamilyDoctorPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Other Details")){
                    OtherDetailsPopup(model);
                }
                else if(model.getDocumentCategory().equalsIgnoreCase("Health Policies")){
                    HealthPoliciesPopup(model);
                }


            }
        });

        if(model.getDocumentCategory().equalsIgnoreCase("Education Details")){
            faiSymb.setText(R.string.fai_graduation);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Family Members")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Employment Details")){
//            TvName.setText(model.getCompanyName());
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Prescription")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Family Doctor Details")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Emergency Contact Details")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Upload Documents")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Health Details")){
            faiSymb.setText(R.string.fai_profile);
        }else if(model.getDocumentCategory().equalsIgnoreCase("Other Details")){
            faiSymb.setText(R.string.fai_profile);
        }
        /*if(model.getGender().equalsIgnoreCase("Male")){
            imgGender.setBackground(activity.getResources().getDrawable(R.drawable.profile_male));
        }else if(model.getGender().equalsIgnoreCase("Female")){
            imgGender.setBackground(activity.getResources().getDrawable(R.drawable.profile_female));
        }else if(model.getGender().equalsIgnoreCase("Other")){
            imgGender.setBackground(activity.getResources().getDrawable(R.drawable.profile_female));
        }else if(model.getGender().equalsIgnoreCase("null")){
            imgGender.setVisibility(View.GONE);
        }*/



if(model.getFileName().equalsIgnoreCase("")){
    TvFileName.setVisibility(View.INVISIBLE);
}else{
    TvFileName.setVisibility(View.VISIBLE);
}




        if(model.getView().equalsIgnoreCase("1")){

            imgPrivacy.setImageDrawable(activity.getResources().getDrawable(R.drawable.personalimg));
        }else if(model.getView().equalsIgnoreCase("2")){
           imgPrivacy.setImageDrawable(activity.getResources().getDrawable(R.drawable.publicimg));

        }else if(model.getView().equalsIgnoreCase("3")){
           imgPrivacy.setImageDrawable(activity.getResources().getDrawable(R.drawable.friendsimg));

        }else if(model.getView().equalsIgnoreCase("4")){
         imgPrivacy.setImageDrawable(activity.getResources().getDrawable(R.drawable.relativesimg));

        }else if(model.getView().equalsIgnoreCase("")||model.getView().equalsIgnoreCase("null")||model.getView().isEmpty()){
            imgPrivacy.setImageDrawable(activity.getResources().getDrawable(R.drawable.publicimg));

        }

        return convertView;
    }//getView

    private void alertDialog(final AllUserDataModel model) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Delete");
        builder.setMessage("Are you confirm to delete?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
           DeleteDataRunner runner=new DeleteDataRunner(model);
                runner.execute();
                dialog.dismiss();
                // Do nothing, but close the dialog

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    private void HealthPoliciesPopup(AllUserDataModel model) {
        HealthPolicyView=View.inflate(activity,R.layout.helath_policy_fragmetnt,null);
        HealthPolicyView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.HealthPolicyDialog=new Dialog(activity,R.style.NewDialog);
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

        String[] some_array = activity.getResources().getStringArray(R.array.PolicyType);

        SpInsuranceType.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.select_dialog_singlechoice,some_array));

        SpInsuranceType.setSelection(AppHelper.setValueToSpinner(SpInsuranceType,model.getInsurerType()));






        SpInsuranceProvider=(Spinner)this.HealthPolicyDialog.findViewById(R.id.sInsuranceprovider);

        String[] IP_array = activity.getResources().getStringArray(R.array.PolicyProvider);
        SpInsuranceProvider.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice                                                             ,IP_array));
        SpInsuranceProvider.setSelection(AppHelper.setValueToSpinner(SpInsuranceProvider,model.getInsuranceProvider()));
        SpInsuranceProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(activity.getResources().getDimension(R.dimen.padding_x2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getFamilyNames()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        presETDAte=(EditText)this.HealthPolicyDialog.findViewById(R.id.et_Date);
     LinearLayout    InsuType=(LinearLayout)this.HealthPolicyDialog.findViewById(R.id.llsInsurance);

     LinearLayout   LLInsuranceP=(LinearLayout) this.HealthPolicyDialog.findViewById(R.id.llsInsuranceProvider);
     EditText   LLInsuranceI=(EditText) this.HealthPolicyDialog.findViewById(R.id.insuranceid);
        InsuranceId = (EditText) this.HealthPolicyDialog.findViewById(R.id.insuranceid);  InsuranceId.setText(model.getInsuranceId());
        presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        PresETDetail = (EditText) this.HealthPolicyDialog.findViewById(R.id.et_details);   PresETDetail.setText(model.getDetails());
        etcateogory = (Spinner) this.HealthPolicyDialog.findViewById(R.id.et_Category);
        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,activity);
            }
        });
        tvFileName=(TextView)this.HealthPolicyDialog.findViewById(R.id.tvUploadTitle);
        Button submit=(Button)this.HealthPolicyDialog.findViewById(R.id.btn_submit);
        uploadFile=(TextView)this.HealthPolicyDialog.findViewById(R.id.tvUpload);
        if(model.getFileName().isEmpty()||model.getFileName().equalsIgnoreCase("")||model.getFileName().equalsIgnoreCase("null")){

        }else{
            tvFileName.setText(model.getFileName());
        }


        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(getPickImageChooserIntent(), 200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitOnClickHealthPolicy();

            }

            private void SubmitOnClickHealthPolicy() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.HealthPolicy_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.HealthPolicy_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.HealthPolicy_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, HealthPolicyView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{

                            HealthPoliciesTask task=new HealthPoliciesTask();
                            task.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });


        LinearLayout LLHeadiLine   = (LinearLayout) this.HealthPolicyDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.HealthPolicyDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthPolicyDialog.dismiss();

            }
        });











    }

    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = activity.getPackageManager();

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
        File getImage =activity.getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }
    private void doFileUpload(String path){

        final ProgressDialog prgDialog = new ProgressDialog(activity);
        prgDialog.setCancelable(false);


        prgDialog.setMessage("Calling Upload");
        prgDialog.show();

        try{

            String urlString =ApisHelper.fileupload+SessinSave.getsessin("profile_id",activity);

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


    private void showImageOrFile(AllUserDataModel model) {
        try {
            View imageOrFileView=View.inflate(activity,R.layout.showimageorview,null);
            imageOrFileView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
            this.imageOrFileViewDialog=new Dialog(activity,R.style.NewDialog);
            this.imageOrFileViewDialog.setContentView(imageOrFileView);
            this.imageOrFileViewDialog.setCancelable(true);
            this.imageOrFileViewDialog.show();

            Window window = this.imageOrFileViewDialog.getWindow();
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

//       WebView wbvw=(WebView)this.imageOrFileViewDialog.findViewById(R.id.webView);
            final ImageView Imgpic=(ImageView)this.imageOrFileViewDialog.findViewById(R.id.imgPic);
            ImageView imgClose=(ImageView)this.imageOrFileViewDialog.findViewById(R.id.closeDialog);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageOrFileViewDialog.dismiss();
                }
            });
//       wbvw.getSettings().setJavaScriptEnabled(true);
//       wbvw.loadUrl("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg");
//       wbvw.loadUrl("http://www.myaaptha.com/pp/UserFiles/900000010501/30_U_2018-calander.pdfdecryptedFile");


            File imgFile = new  File(Environment.getExternalStorageDirectory()+"/MyAaptha/1.jpg");

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Imgpic.setImageBitmap(myBitmap);

            }

//       //Asynchronously load file as generic file
//       FileLoader.with(activity)
//               .load("http://www.myaaptha.com/pp/UserFiles/900000010501/1.jpg")
//               .fromDirectory("MyAaptha", FileLoader.DIR_EXTERNAL_PUBLIC)
//               .asFile(new FileRequestListener<File>() {
//                   @Override
//                   public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                       Glide.with(activity).load(response.getBody()).into(Imgpic);
//                   }
//
//                   @Override
//                   public void onError(FileLoadRequest request, Throwable t) {
//                       Log.d("Activity", "onError: " + t.getMessage());
//                   }
//               });




        }catch (Exception e){e.printStackTrace();}
    }

    private void EmergencyContactPopup(final AllUserDataModel model) {
        emergencyContactView=View.inflate(activity,R.layout.emergency_cont_details_fragment,null);
        emergencyContactView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.emergencyContactDialog=new Dialog(activity,R.style.NewDialog);
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

       emerFMSpin=(Spinner)this.emergencyContactDialog.findViewById(R.id.sRelation);
        String[] some_array = activity.getResources().getStringArray(R.array.selRelation);

        emerFMSpin.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,some_array));


        emerFMSpin.setSelection(AppHelper.setValueToSpinner(emerFMSpin,model.getRelation()));
        // Spinner sDoc=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sDocs); sDoc.setSelection(AppHelper.setValueToSpinner(sDoc,model.getDocuments()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
         emersETDAte=(EditText)this.emergencyContactDialog.findViewById(R.id.et_Date);

        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            emersETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }

        emerETDetail = (EditText) this.emergencyContactDialog.findViewById(R.id.et_details);    emerETDetail.setText(model.getDetails());
        emeretname = (EditText) this.emergencyContactDialog.findViewById(R.id.et_Name);    emeretname.setText(model.getName());
        emeretnumber = (EditText) this.emergencyContactDialog.findViewById(R.id.et_Number);    emeretnumber.setText(model.getNumber());
        // EditText etcateogory = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_Category);    etcateogory.setText(model.getCategory());
        LinearLayout LLHeadiLine   = (LinearLayout) this.emergencyContactDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.emergencyContactDialog.findViewById(R.id.closeDialog);
        emerradioGroup=(RadioGroup)this.emergencyContactDialog.findViewById(R.id.pp_radioGroup);
        emerradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=emerradioGroup.indexOfChild(emergencyContactDialog.findViewById(emerradioGroup.getCheckedRadioButtonId()));
                emerPrivacyPolocy=String.valueOf(pos+1);
                // Toast.makeText(activity,eduPrivacyPolocy,Toast.LENGTH_LONG).show();
            }
        });


        RadioButton Privacy=(RadioButton)this.emergencyContactDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.emergencyContactDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.emergencyContactDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.emergencyContactDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }






        emersETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(emersETDAte,activity);
            }
        });
        uploadFile=(TextView)this.emergencyContactDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.emergencyContactDialog.findViewById(R.id.tvUploadTitle);
        tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        Button submit=(Button)this.emergencyContactDialog.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEmergencyonClick();

            }

            private void submitEmergencyonClick() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.FamilyDrDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.FamilyDrDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.FamilyDrDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, emergencyContactView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateEmergencyData runner =new UpdateEmergencyData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emergencyContactDialog.dismiss();
            }
        });



    }

    private void FamilyDoctorPopup(final AllUserDataModel model) {
        familyDoctorView=View.inflate(activity,R.layout.family_dr_details_frag,null);
        familyDoctorView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.familyDoctorDialog=new Dialog(activity,R.style.NewDialog);
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

        Spinner PresFMSpin=(Spinner)this.familyDoctorDialog.findViewById(R.id.sRelation);
        String[] some_array = activity.getResources().getStringArray(R.array.selRelation);
        PresFMSpin.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.select_dialog_singlechoice,some_array));
        PresFMSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(activity.getResources().getDimension(R.dimen.padding_x2));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        PresFMSpin.setSelection(AppHelper.setValueToSpinner(PresFMSpin,model.getRelation()));
       // Spinner sDoc=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sDocs); sDoc.setSelection(AppHelper.setValueToSpinner(sDoc,model.getDocuments()));
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        famETDAte=(EditText)this.familyDoctorDialog.findViewById(R.id.et_Date);


        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            famETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }


        famETDetail = (EditText) this.familyDoctorDialog.findViewById(R.id.et_details);    famETDetail.setText(model.getDetails());
         fametname = (EditText) this.familyDoctorDialog.findViewById(R.id.et_Name);    fametname.setText(model.getName());
        fametnumber = (EditText) this.familyDoctorDialog.findViewById(R.id.et_Number);    fametnumber.setText(model.getNumber());
        // EditText etcateogory = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_Category);    etcateogory.setText(model.getCategory());
        uploadFile=(TextView)this.familyDoctorDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.familyDoctorDialog.findViewById(R.id.tvUploadTitle);
        tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });


        RadioButton Privacy=(RadioButton)this.familyDoctorDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.familyDoctorDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.familyDoctorDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.familyDoctorDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }






        famradioGroup=(RadioGroup)this.familyDoctorDialog.findViewById(R.id.pp_radioGroup);
        Button submit=(Button)this.familyDoctorDialog.findViewById(R.id.btn_submit);
        famradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int   pos=famradioGroup.indexOfChild(familyDoctorDialog.findViewById(famradioGroup.getCheckedRadioButtonId()));
                famPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        famETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(famETDAte,activity);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnFamilydoctorClick();

            }

            private void submitOnFamilydoctorClick() {


                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.FamilyDrDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.FamilyDrDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.FamilyDrDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, familyDoctorView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateFamilyDoctorData runner =new UpdateFamilyDoctorData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }




            }
        });

        LinearLayout LLHeadiLine   = (LinearLayout) this.familyDoctorDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.familyDoctorDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyDoctorDialog.dismiss();
            }
        });
    }

    private void UploadDocumentPopup(final AllUserDataModel model) {
        uploadDocumentView=View.inflate(activity,R.layout.upload_doc_fragment,null);
        uploadDocumentView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.uploadDocumentDialog=new Dialog(activity,R.style.NewDialog);
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


        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();
        SpFmilyNames=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sFamilynames);
        UploadsDoc=(Spinner)this.uploadDocumentDialog.findViewById(R.id.sDocs);
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
        //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        uploadETDAte=(EditText)this.uploadDocumentDialog.findViewById(R.id.et_Date);
        String[] some_array = activity.getResources().getStringArray(R.array.selDocs);

        UploadsDoc.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,some_array));
        UploadsDoc.setSelection(AppHelper.setValueToSpinner(UploadsDoc,model.getDocuments()));

        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            uploadETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }



       UploadETDetail = (EditText) this.uploadDocumentDialog.findViewById(R.id.et_details);    UploadETDetail.setText(model.getDetails());
        Uploadetnumber = (EditText) this.uploadDocumentDialog.findViewById(R.id.et_Number);    Uploadetnumber.setText(model.getNumber());
       // EditText etcateogory = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_Category);    etcateogory.setText(model.getCategory());
      Button BtnUpdate=(Button)this.uploadDocumentDialog.findViewById(R.id.btn_submit);
        UploadradioGroup=(RadioGroup)this.uploadDocumentDialog.findViewById(R.id.pp_radioGroup);
        UploadradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=UploadradioGroup.indexOfChild(uploadDocumentDialog.findViewById(UploadradioGroup.getCheckedRadioButtonId()));
                uploadPrivacyPolocy=String.valueOf(pos+1);
                // Toast.makeText(activity,eduPrivacyPolocy,Toast.LENGTH_LONG).show();
            }
        });


        RadioButton Privacy=(RadioButton)this.uploadDocumentDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.uploadDocumentDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.uploadDocumentDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.uploadDocumentDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }


        uploadFile=(TextView)this.uploadDocumentDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.uploadDocumentDialog.findViewById(R.id.tvUploadTitle);
        tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnUploadDocumentClick();

            }

            private void submitOnUploadDocumentClick() {
                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.uploadDocs_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.uploadDocs_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.uploadDocs_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, uploadDocumentView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateDocumentsData runner =new UpdateDocumentsData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }





            }
        });
        uploadETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(uploadETDAte,activity);
            }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.uploadDocumentDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.uploadDocumentDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDocumentDialog.dismiss();
            }
        });
    }

    private void HealthDetailsPopup(final AllUserDataModel model) {
        HealthDetailsView=View.inflate(activity,R.layout.helath_details_fragmetnt,null);
        HealthDetailsView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.HealthDetailsDialog=new Dialog(activity,R.style.NewDialog);
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

        healthETDAte=(EditText)this.HealthDetailsDialog.findViewById(R.id.et_Date);
        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            healthETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }

      healthETDetail = (EditText) this.HealthDetailsDialog.findViewById(R.id.et_details);    healthETDetail.setText(model.getDetails());
        etcateogory = (Spinner) this.HealthDetailsDialog.findViewById(R.id.et_Category);  //  healthetcateogory.setSelection(Integer.parseInt(model.getCategory()));
       CateogoryTask task=new CateogoryTask(model);
        task.execute();


        Log.i("Categgggggggg-->",model.getCategory());
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();

        RadioButton Privacy=(RadioButton)this.HealthDetailsDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.HealthDetailsDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.HealthDetailsDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.HealthDetailsDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }


        uploadFile=(TextView)this.HealthDetailsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.HealthDetailsDialog.findViewById(R.id.tvUploadTitle);
        tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        Button BtnUpdate = (Button) this.HealthDetailsDialog.findViewById(R.id.btn_submit);
        healthradioGroup=(RadioGroup)this.HealthDetailsDialog.findViewById(R.id.pp_radioGroup);
       healthradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=healthradioGroup.indexOfChild(HealthDetailsDialog.findViewById(healthradioGroup.getCheckedRadioButtonId()));
               healthPrivacyPolocy=String.valueOf(pos+1);
                // Toast.makeText(activity,eduPrivacyPolocy,Toast.LENGTH_LONG).show();
            }
        });
        healthETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(healthETDAte,activity);
            }
        });

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitHealthDetailsClick();

            }

            private void submitHealthDetailsClick() {
                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.OtherDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.OtherDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.OtherDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, HealthDetailsView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateHealthData runner =new UpdateHealthData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.HealthDetailsDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.HealthDetailsDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HealthDetailsDialog.dismiss();
            }
        });
    }

    private void OtherDetailsPopup(final AllUserDataModel model) {
        otherdetailsView=View.inflate(activity,R.layout.other_details_fragment,null);
        otherdetailsView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.otherdetailsDialog=new Dialog(activity,R.style.NewDialog);
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

        Log.i("Model-->",model.toString());


     SpFmilyNames=(Spinner)this.otherdetailsDialog.findViewById(R.id.sFamilynames);
        //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
     //   EditText Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
        //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();



        presETDAte=(EditText)this.otherdetailsDialog.findViewById(R.id.et_Date);
        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }


      otherETDetail = (EditText) this.otherdetailsDialog.findViewById(R.id.et_details);    otherETDetail.setText(model.getDetails());
        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,activity);
            }
        });



        RadioButton Privacy=(RadioButton)this.otherdetailsDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.otherdetailsDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.otherdetailsDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.otherdetailsDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }

        uploadFile=(TextView)this.otherdetailsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.otherdetailsDialog.findViewById(R.id.tvUploadTitle);       tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        Button BtnUpdate = (Button) this.otherdetailsDialog.findViewById(R.id.btn_submit);
        OtherradioGroup=(RadioGroup)this.otherdetailsDialog.findViewById(R.id.pp_radioGroup);
        OtherradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=OtherradioGroup.indexOfChild(otherdetailsDialog.findViewById(OtherradioGroup.getCheckedRadioButtonId()));
                otherPrivacyPolocy=String.valueOf(pos+1);
                // Toast.makeText(activity,eduPrivacyPolocy,Toast.LENGTH_LONG).show();
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOtherDetailsClick();

            }

            private void submitOtherDetailsClick() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.OtherDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.OtherDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.OtherDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, otherdetailsView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateOtherData runner =new UpdateOtherData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.otherdetailsDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.otherdetailsDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherdetailsDialog.dismiss();
            }
        });

    }

    private void EmploymentPopup(final AllUserDataModel model) {
        employmentView=View.inflate(activity,R.layout.employment_fragment,null);
        employmentView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.employmentDialog=new Dialog(activity,R.style.NewDialog);
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
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();
        SpFmilyNames=(Spinner)this.employmentDialog.findViewById(R.id.sFamilynames); SpFmilyNames.setSelection(AppHelper.setValueToSpinner(SpFmilyNames,model.getFamilyNames()));
      //  EditText PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDetails());
       Empetcompany = (EditText) this.employmentDialog.findViewById(R.id.et_Company);    Empetcompany.setText(model.getCompanyName());
      //  EditText PresDRSpec = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrSpec);    PresDRSpec.setText(model.getDetails());
       presETDAte=(EditText)this.employmentDialog.findViewById(R.id.et_Date);
        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }

       PresETDetail = (EditText) this.employmentDialog.findViewById(R.id.et_details);    PresETDetail.setText(model.getDetails());
        EmpradioGroup=(RadioGroup)this.employmentDialog.findViewById(R.id.p_radioGroup);
        Button BtnUpdate = (Button) this.employmentDialog.findViewById(R.id.btn_submit);



        RadioButton Privacy=(RadioButton)this.employmentDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.employmentDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.employmentDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.employmentDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }


        EmpradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=EmpradioGroup.indexOfChild(employmentDialog.findViewById(EmpradioGroup.getCheckedRadioButtonId()));
                empPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnEmployClick();

            }

            private void submitOnEmployClick() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.EmpDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.EmpDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.EmpDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, employmentView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateEmploymentData runner =new UpdateEmploymentData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });
        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,activity);
            }
        });

        uploadFile=(TextView)this.employmentDialog.findViewById(R.id.tvUpload);
        if(model.getFileName().equalsIgnoreCase("")|| model.getFileName().equalsIgnoreCase("null")|| model.getFileName().isEmpty() ){

        }else {
            tvFileName.setText(model.getFileName());
        }

        tvFileName=(TextView)this.employmentDialog.findViewById(R.id.tvUploadTitle);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });


        LinearLayout LLHeadiLine   = (LinearLayout) this.employmentDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.employmentDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employmentDialog.dismiss();
            }
        });
    }



    private void PrescriptionPopup(final AllUserDataModel model) {
        prescriptionView=View.inflate(activity,R.layout.add_prescription_fragment,null);
        prescriptionView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.prescriptionDialog=new Dialog(activity,R.style.NewDialog);
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


       PresETFN = (EditText) this.prescriptionDialog.findViewById(R.id.et_DrName);    PresETFN.setText(model.getDoctorName());
        etcateogory = (Spinner) this.prescriptionDialog.findViewById(R.id.et_DrSpec);
        CateogoryTask task=new CateogoryTask(model);
        task.execute();
        presETDAte=(EditText)this.prescriptionDialog.findViewById(R.id.et_Date);
        FamilyMember=(LinearLayout)this.prescriptionDialog.findViewById(R.id.familynames);

        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            presETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }

        PresETDetail = (EditText) this.prescriptionDialog.findViewById(R.id.et_details);    PresETDetail.setText(model.getDetails());
        PrescradioGroup=(RadioGroup)this.prescriptionDialog.findViewById(R.id.pp_radioGroup);
        Button submit=(Button)this.prescriptionDialog.findViewById(R.id.btn_submit);
        PrescradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int   pos=PrescradioGroup.indexOfChild(prescriptionDialog.findViewById(PrescradioGroup.getCheckedRadioButtonId()));
                prePrivacyPolocy=String.valueOf(pos+1);
            }
        });
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();
        SpFmilyNames=(Spinner)this.prescriptionDialog.findViewById(R.id.sFamilynames);

        presETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(presETDAte,activity);
            }
        });


        RadioButton Privacy=(RadioButton)this.prescriptionDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.prescriptionDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.prescriptionDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.prescriptionDialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }


submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        OnSubmitPrescriptionClick();


    }

    private void OnSubmitPrescriptionClick() {

        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = activity.getResources().getStringArray(R.array.AddPresc_ids_array);
            String[] strErrMsgs = activity.getResources().getStringArray(R.array.AddPresc_errors_array);
            String[] strCompTypeArr = activity.getResources().getStringArray(R.array.AddPresc_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;

            }
            boolean isValidData = helper.validateData(activity, aList, prescriptionView);

            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                    Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                }else{
                    UpdatePrescriptionData runer = new UpdatePrescriptionData(model);    runer.execute();
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }


    }


});

        uploadFile=(TextView)this.prescriptionDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.prescriptionDialog.findViewById(R.id.tvUploadTitle);     tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });


        LinearLayout LLHeadiLine   = (LinearLayout) this.prescriptionDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);
        ImageView imgClise = (ImageView)this.prescriptionDialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionDialog.dismiss();
            }
        });
    }




    private void EducationPopup(final AllUserDataModel model) {
        educationView=View.inflate(activity,R.layout.education_fragment,null);
        educationView.startAnimation(AnimationUtils.loadAnimation(activity,R.anim.zoom_in_enter));
        this.educationdialog=new Dialog(activity,R.style.NewDialog);
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

        LinearLayout EduSpin   = (LinearLayout) this.educationdialog.findViewById(R.id.eduspin);     //   EduSpin.setVisibility(View.VISIBLE);
        otherETDAte=(EditText)this.educationdialog.findViewById(R.id.et_Date);


        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            otherETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }

  //      otherETDAte.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        otherETDAte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(otherETDAte,activity);
            }
        });


      ETDetails = (EditText) this.educationdialog.findViewById(R.id.et_details);    ETDetails.setText(model.getDetails());
        FamilyNamesAsyncTask runner =new FamilyNamesAsyncTask(model);    runner.execute();
        SpFmilyNames = (Spinner) this.educationdialog.findViewById(R.id.sFamilynames);   
        Eduqualspin=(Spinner)this.educationdialog.findViewById(R.id.sQualification);
        Eduqualspin.setSelection(AppHelper.setValueToSpinner(Eduqualspin,model.getQualification()));
        EducationalTask task=new EducationalTask(model);
        task.execute();
        Button BtnUpdate = (Button) this.educationdialog.findViewById(R.id.btn_submit);
        EdradioGroup=(RadioGroup)this.educationdialog.findViewById(R.id.pp_radioGroup);
        EdradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=EdradioGroup.indexOfChild(educationdialog.findViewById(EdradioGroup.getCheckedRadioButtonId()));
                eduPrivacyPolocy=String.valueOf(pos+1);
               // Toast.makeText(activity,eduPrivacyPolocy,Toast.LENGTH_LONG).show();
            }
        });


        RadioButton Privacy=(RadioButton)this.educationdialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.educationdialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.educationdialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.educationdialog.findViewById(R.id.rb4);

        if(model.getView().equalsIgnoreCase("1")){
            Privacy.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("2")){
            Public.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("3")){
            Friend.setChecked(true);
        }else if(model.getView().equalsIgnoreCase("4")){
            Relative.setChecked(true);
        }else {
            Public.setChecked(true);
        }



        uploadFile=(TextView)this.educationdialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.educationdialog.findViewById(R.id.tvUploadTitle);      tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });



  //      GetEducationAsyncTask runner =new GetEducationAsyncTask(model);   runner.execute();
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnEducationClick();


            }

            private void submitOnEducationClick() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.EducationDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.EducationDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.EducationDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, educationView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateEducationData runner =new UpdateEducationData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.educationdialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);




        ImageView CloseDialog = (ImageView) this.educationdialog.findViewById(R.id.closeDialog);
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationdialog.dismiss();
            }
        });
        ImageView imgClise = (ImageView)this.educationdialog.findViewById(R.id.closeDialog);
        imgClise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                educationdialog.dismiss();
            }
        });

    }


    private void FamilyMembersPopUp(final AllUserDataModel model) {
        famMembersView = View.inflate(activity, R.layout.add_family_details_frag, null);
        famMembersView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in_enter));
        this.famMembrsDialog = new Dialog(activity, R.style.NewDialog);
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


        LinearLayout llsrelation   = (LinearLayout) this.famMembrsDialog.findViewById(R.id.llsrelation);        //llsrelation.setVisibility(View.VISIBLE);
        FMETName   = (EditText) this.famMembrsDialog.findViewById(R.id.et_Name);        FMETName.setText(model.getName());
       ETdate = (EditText) this.famMembrsDialog.findViewById(R.id.et_Date);
        if(model.getDate().equalsIgnoreCase("")||model.getDate().equalsIgnoreCase("null")||model.getDate().isEmpty()){

        }else{
            ETdate.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(model.getDate()));
        }


        ETdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppHelper.getPerfectDate(ETdate,activity);
            }
        });

        FMETDetails = (EditText) this.famMembrsDialog.findViewById(R.id.et_details);    FMETDetails.setText(model.getDetails());
        FmSpRelation = (Spinner) this.famMembrsDialog.findViewById(R.id.sRelation);
        FmRGGender = (RadioGroup) this.famMembrsDialog.findViewById(R.id.radioSexGroup);
        FmRGPPolocy = (RadioGroup) this.famMembrsDialog.findViewById(R.id.fd_radioGroup);
        Button BtnUpdate = (Button) this.famMembrsDialog.findViewById(R.id.btn_submit);

        String[] some_array = activity.getResources().getStringArray(R.array.selRelation);
        FmSpRelation.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.select_dialog_singlechoice,some_array));
        FmSpRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                ((TextView)parent.getChildAt(0)).setTextSize(activity.getResources().getDimension(R.dimen.padding_x2));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        FmSpRelation.setSelection(AppHelper.setValueToSpinner(FmSpRelation,model.getRelation()));
        RadioButton Male=(RadioButton)this.famMembrsDialog.findViewById(R.id.radioMale);
        RadioButton Female=(RadioButton)this.famMembrsDialog.findViewById(R.id.radioFemale);
        RadioButton Other=(RadioButton)this.famMembrsDialog.findViewById(R.id.radioOther);

        if(model.getGender().equalsIgnoreCase("Male"))
        {
            Male.setChecked(true);
        }else if (model.getGender().equalsIgnoreCase("Female")){
            Female.setChecked(true);

        }else if(model.getGender().equalsIgnoreCase("Other")){
            Other.setChecked(true);
        }else{
            Male.setChecked(false);
            Female.setChecked(false);
            Other.setChecked(false);
        }


        RadioButton Privacy=(RadioButton)this.famMembrsDialog.findViewById(R.id.rb1);
        RadioButton Public=(RadioButton)this.famMembrsDialog.findViewById(R.id.rb2);
        RadioButton Friend=(RadioButton)this.famMembrsDialog.findViewById(R.id.rb3);
        RadioButton Relative=(RadioButton)this.famMembrsDialog.findViewById(R.id.rb4);

if(model.getView().equalsIgnoreCase("1")){
    Privacy.setChecked(true);
}else if(model.getView().equalsIgnoreCase("2")){
    Public.setChecked(true);
}else if(model.getView().equalsIgnoreCase("3")){
    Friend.setChecked(true);
}else if(model.getView().equalsIgnoreCase("4")){
    Relative.setChecked(true);
}else {
    Public.setChecked(true);
}

        uploadFile=(TextView)this.famMembrsDialog.findViewById(R.id.tvUpload);
        tvFileName=(TextView)this.famMembrsDialog.findViewById(R.id.tvUploadTitle);       tvFileName.setText(model.getFileName());
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });


        FmRGGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId)
                { case R.id.radioMale:
                    selectedFamMembGender="Male";
                    break;
                    case R.id.radioFemale:
                        selectedFamMembGender="Female";
                        break;
                    case R.id.radioOther:
                        selectedFamMembGender="Other";
                        break;
                }
            }
        });

        FmRGPPolocy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=FmRGPPolocy.indexOfChild(famMembrsDialog.findViewById(FmRGPPolocy.getCheckedRadioButtonId()));
                fmPrivacyPolocy=String.valueOf(pos+1);
            }
        });
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnClick();


            }

            private void submitOnClick() {

                try{
                    ValidationHelper helper=new ValidationHelper();
                    String[] strIds = activity.getResources().getStringArray(R.array.addFamDetails_ids_array);
                    String[] strErrMsgs = activity.getResources().getStringArray(R.array.addFamDetails_errors_array);
                    String[] strCompTypeArr = activity.getResources().getStringArray(R.array.addFamDetails_comptypes_array);
                    ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

                    int iPos = 0;
                    for(String strCompType:strCompTypeArr){
                        ValidationDTO valDTO=new ValidationDTO();
                        valDTO.setComponentType(strCompType);
                        valDTO.setComponentID(ValidationUtils.getIdResourceByName(activity,strIds[iPos]));
                        valDTO.setErrorMessage(strErrMsgs[iPos]);
                        aList.add(valDTO);
                        iPos++;

                    }
                    boolean isValidData = helper.validateData(activity, aList, famMembersView);

                    if (!isValidData) {
                        return;
                    }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                        if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                            Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
                        }else{
                            UpdateFamilyMembersData runner =new UpdateFamilyMembersData(model);  runner.execute();
                        }

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        LinearLayout LLHeadiLine   = (LinearLayout) this.famMembrsDialog.findViewById(R.id.llHeading);        LLHeadiLine.setVisibility(View.VISIBLE);

        GetFamilyMembersAsyncTask runner =new GetFamilyMembersAsyncTask(model);   runner.execute();


        ImageView CloseDialog = (ImageView) this.famMembrsDialog.findViewById(R.id.closeDialog);
        CloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                famMembrsDialog.dismiss();
            }
        });


    }//popUp
    private class GetFamilyMembersAsyncTask extends AsyncTask<String, String, String> {
        private final AllUserDataModel model;
        private ProgressDialog pDialog;

        public GetFamilyMembersAsyncTask(AllUserDataModel mdl) {
            model =mdl;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... params) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.updateUserData+model.getId()+"" ,"GET",null);
       //     String content= AsyncTaskHelper.makeServiceCall( "http://myaaptha.com/pp/userRest/updateUserData?id="+model.getId()+"" ,"GET",null);
            Log.i("Bhavani",content);

            return content;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject obj=new JSONObject(result);
                FMETName.setText(obj.getString("name"));
             //   FMETBirthday.setText(AppHelper.ConvertJsonDateWithSecOnlyDate(obj.getString("date")));
                FMETBirthday.setText(obj.getString("date"));
                FMETDetails.setText(obj.getString("details"));
                FmSpRelation.setSelection(AppHelper.setValueToSpinner(FmSpRelation,obj.getString("relation")));
                if(obj.getString("gender").equalsIgnoreCase("Male")){
                    FmRGGender.check(R.id.radioMale);
                }else  if(obj.getString("gender").equalsIgnoreCase("Female")){
                    FmRGGender.check(R.id.radioFemale);
                }else  if(obj.getString("gender").equalsIgnoreCase("Other")){
                    FmRGGender.check(R.id.radioOther);
                }

//                ((RadioButton)famMembrsDialog.findViewById(R.id.rb1)).setTypeface(fontAwesomeFont);
//                ((RadioButton)famMembrsDialog.findViewById(R.id.rb2)).setTypeface(fontAwesomeFont);
//                ((RadioButton)famMembrsDialog.findViewById(R.id.rb3)).setTypeface(fontAwesomeFont);
//                ((RadioButton)famMembrsDialog.findViewById(R.id.rb4)).setTypeface(fontAwesomeFont);
                if(obj.getString("view").equalsIgnoreCase("1")){
//                   ((RadioButton)famMembrsDialog.findViewById(R.id.rb1)).setChecked(true);
                    FmRGPPolocy.check(R.id.rb1);
                }else if(obj.getString("view").equalsIgnoreCase("2")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb2)).setChecked(true);
                    FmRGPPolocy.check(R.id.rb2);
                }else if(obj.getString("view").equalsIgnoreCase("3")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb3)).setChecked(true);
                    FmRGPPolocy.check(R.id.rb3);
                }else if(obj.getString("view").equalsIgnoreCase("4")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb4)).setChecked(true);
                    FmRGPPolocy.check(R.id.rb4);
                }

            }catch (Exception e){
            }
            if(pDialog.isShowing()){  pDialog.dismiss();  }
        }//onPostExecute


    }//AsuncTask class
    public class UpdateFamilyMembersData extends AsyncTask<String, String, String> {
        String fmdetails,fmetbirth,fmrelation,fmdoccat,fmname;
        private final AllUserDataModel model;
        ProgressDialog progressDialog;
        public UpdateFamilyMembersData(AllUserDataModel mdl) {
            model =mdl;
        }
        @Override
        protected void onPreExecute() {

  //          progressDialog = ProgressDialog.show(activity,     "Please wait",   "loading...");
            fmdetails=FMETDetails.getText().toString();

          fmetbirth=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(ETdate.getText().toString());

            fmrelation=FmSpRelation.getSelectedItem().toString();
            fmdoccat=model.getDocumentCategory();
            fmname=FMETName.getText().toString();



        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames","");
                obj.accumulate("name",fmname);
                obj.accumulate("documentCategory",fmdoccat);
                obj.accumulate("details",fmdetails);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",fmetbirth);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",fmPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation",fmrelation);
                obj.accumulate("gender",selectedFamMembGender);
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

Log.i("Request",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
       //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
         //   String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/saveUpdateUserData","POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
//            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask


    private class GetEducationAsyncTask extends AsyncTask<String,String,String>{
        private  AllUserDataModel mode;
        public GetEducationAsyncTask(AllUserDataModel model) {
           this.mode=model;

        }

        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall( ApisHelper.updateUserData+mode.getId()+"" ,"GET",null);
      //      String content= AsyncTaskHelper.makeServiceCall( "http://myaaptha.com/pp/userRest/updateUserData?id="+mode.getId()+"" ,"GET",null);
            Log.i("Bhavani",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj=new JSONObject(s);
                otherETDAte.setText(obj.getString("date"));
                ETDetails.setText(obj.getString("details"));
                Eduspin.setSelection(AppHelper.setValueToSpinner(Eduspin,obj.getString("familyNames")));
                Eduqualspin.setSelection(AppHelper.setValueToSpinner(Eduqualspin,obj.getString("qualification")));

                if(obj.getString("view").equalsIgnoreCase("1")){
//                   ((RadioButton)famMembrsDialog.findViewById(R.id.rb1)).setChecked(true);
                    EdradioGroup.check(R.id.rb1);
                }else if(obj.getString("view").equalsIgnoreCase("2")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb2)).setChecked(true);
                    EdradioGroup.check(R.id.rb2);
                }else if(obj.getString("view").equalsIgnoreCase("3")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb3)).setChecked(true);
                    EdradioGroup.check(R.id.rb3);
                }else if(obj.getString("view").equalsIgnoreCase("4")){
//                    ((RadioButton)famMembrsDialog.findViewById(R.id.rb4)).setChecked(true);
                    EdradioGroup.check(R.id.rb4);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdateEducationData extends AsyncTask<String,String,String> {
        private String FamilyNames,qualification,date,details,doc;
        private  AllUserDataModel model;
        ProgressDialog progressDialog;
        public UpdateEducationData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
          FamilyNames= SpFmilyNames.getSelectedItem().toString();
            qualification=Eduqualspin.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(otherETDAte.getText().toString());



            details=ETDetails.getText().toString();
            doc=model.getDocumentCategory().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames",FamilyNames);
                obj.accumulate("name","");
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification",qualification);
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",eduPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
       //     String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/saveUpdateUserData","POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdateEmploymentData extends AsyncTask<String,String,String>{
        private AllUserDataModel model;
        String company,date,details,doc,familyname;
        public UpdateEmploymentData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            familyname=SpFmilyNames.getSelectedItem().toString();
           company=Empetcompany.getText().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(presETDAte.getText().toString());
            details=PresETDetail.getText().toString();
            doc=model.getDocumentCategory();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames",familyname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
                obj.accumulate("companyName",company);
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",empPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
      //      String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/saveUpdateUserData","POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdatePrescriptionData extends AsyncTask<String,String,String> {
        private  AllUserDataModel model;
        String doctername,specialization,date,details,doc,familyname;
        public UpdatePrescriptionData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            familyname=SpFmilyNames.getSelectedItem().toString();
          doctername=PresETFN.getText().toString();
            specialization=etcateogory.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(presETDAte.getText().toString());
            details=PresETDetail.getText().toString();
            doc=model.getDocumentCategory();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames","");
                obj.accumulate("name","");
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category",specialization);
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",prePrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated",date);
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);

        //    String content= AsyncTaskHelper.makeServiceCall("http://myaaptha.com/pp/userRest/saveUpdateUserData","POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }
    }

    private class UpdateEmergencyData extends AsyncTask<String,String,String> {
        String name,relation,number,date,details,doc;
        private  AllUserDataModel model;
        public UpdateEmergencyData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            name=emeretname.getText().toString();
            relation=emerFMSpin.getSelectedItem().toString();
            number=emeretnumber.getText().toString();

            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(emersETDAte.getText().toString());
            details=emerETDetail.getText().toString();
            doc=model.getDocumentCategory();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames","");
                obj.accumulate("name",name);
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number",number);
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",emerPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation",relation);
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdateDocumentsData extends AsyncTask<String,String,String>{
        private AllUserDataModel model;
        String document,number,date,details,doc,fammem;
        public UpdateDocumentsData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            fammem=SpFmilyNames.getSelectedItem().toString();
            document=UploadsDoc.getSelectedItem().toString();
            number=Uploadetnumber.getText().toString();
            details=UploadETDetail.getText().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(uploadETDAte.getText().toString());
            doc=model.getDocumentCategory();

        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames",fammem);
                obj.accumulate("name","");
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents",document);
                obj.accumulate("number",number);
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",uploadPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdateOtherData extends AsyncTask<String,String,String> {
        String date,details,document,famname;
        private AllUserDataModel model;
        public UpdateOtherData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            famname=SpFmilyNames.getSelectedItem().toString();
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(presETDAte.getText().toString());
            document=model.getDocumentCategory();
            details=otherETDetail.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames",famname);
                obj.accumulate("name","");
                obj.accumulate("documentCategory",document);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category","");
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",otherPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }
    }

    private class UpdateHealthData extends AsyncTask<String,String,String>{
        private AllUserDataModel model;
        private String cateogory,date,details,doc,name;
        public UpdateHealthData(AllUserDataModel model) {
            this.model=model;
        }

        @Override
        protected void onPreExecute() {
            cateogory=healthetcateogory.getSelectedItem().toString();

             name=SpFmilyNames.getSelectedItem().toString();
            Log.i("++++++",name);
            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(healthETDAte.getText().toString());
            details=healthETDetail.getText().toString();
            doc=model.getDocumentCategory();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("file",null);
                obj.accumulate("id",model.getId());
                obj.accumulate("familyNames",name);
                obj.accumulate("name","");
                obj.accumulate("documentCategory",doc);
                obj.accumulate("details",details);
                obj.accumulate("documents","");
                obj.accumulate("number","");
                obj.accumulate("date",date);
                obj.accumulate("companyName","");
                obj.accumulate("category",cateogory);
                obj.accumulate("title","");
                obj.accumulate("qualification","");
                obj.accumulate("doctorName","");
                obj.accumulate("specailization","");
                obj.accumulate("view",healthPrivacyPolocy);
                obj.accumulate("fileName","");
                obj.accumulate("relation","");
                obj.accumulate("gender","");
                obj.accumulate("dno","");
                obj.accumulate("familyNameId","");
                obj.accumulate("encryptedKey","");
                obj.accumulate("dateCreated","");
                obj.accumulate("viewStatus","");

                Log.i("Bhavani",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData,"POST",obj);
            Log.i("Bhavani===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            if(s.equalsIgnoreCase("true")){
                Toast.makeText(activity,"Successfully Updated",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(activity,"Not Updated",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(s);
        }

        }

    private class UpdateFamilyDoctorData extends AsyncTask<String,String,String> {
        private AllUserDataModel model;
        String date, details, name, number, doc;

        public UpdateFamilyDoctorData(AllUserDataModel model) {
            this.model = model;
        }

        @Override
        protected void onPreExecute() {

            date=AppHelper.ConvertDateFormatDDMMYYYY2YYYYMMDD(famETDAte.getText().toString());

            details = famETDetail.getText().toString();
            name = fametname.getText().toString();
            number = fametnumber.getText().toString();
            doc = model.getDocumentCategory();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj = null;
            try {
                obj = new JSONObject();
                obj.accumulate("file", null);
                obj.accumulate("id", model.getId());
                obj.accumulate("familyNames", "");
                obj.accumulate("name", name);
                obj.accumulate("documentCategory", doc);
                obj.accumulate("details", details);
                obj.accumulate("documents", "");
                obj.accumulate("number", number);
                obj.accumulate("date", date);
                obj.accumulate("companyName", "");
                obj.accumulate("category", "");
                obj.accumulate("title", "");
                obj.accumulate("qualification", "");
                obj.accumulate("doctorName", "");
                obj.accumulate("specailization", "");
                obj.accumulate("view", famPrivacyPolocy);
                obj.accumulate("fileName", "");
                obj.accumulate("relation", "");
                obj.accumulate("gender", "");
                obj.accumulate("dno", "");
                obj.accumulate("familyNameId", "");
                obj.accumulate("encryptedKey", "");
                obj.accumulate("dateCreated", "");
                obj.accumulate("viewStatus", "");

                Log.i("Bhavani", obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content = AsyncTaskHelper.makeServiceCall(ApisHelper.saveUpdateUserData, "POST", obj);
            Log.i("Bhavani===>", content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:", s);
            if (s.equalsIgnoreCase("true")) {
                Toast.makeText(activity, "Successfully Updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "Not Updated", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class FamilyNamesAsyncTask extends AsyncTask<String, String, String> {
        private final AllUserDataModel model;
        private ArrayList<String> famNamesList;

        public FamilyNamesAsyncTask(AllUserDataModel mdl) {
            model = mdl;
        }

        @Override
        protected void onPreExecute() {
//            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.familyNames+"/"+ SessinSave.getsessin("profile_id",activity),"GET",null);
            Log.i("333333",ApisHelper.familyNames+"/"+SessinSave.getsessin("profile_id",activity));
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("Drs  onPost:",s);
            JSONArray array;
            try {
                famNamesList = new ArrayList<String>();
                array=new JSONArray(s);
                for(int i = 0; i < array.length(); i++){
                    JSONObject jsonNames=array.getJSONObject(i);
                    famNamesList.add(jsonNames.getString("fullName"));
                }

                famNamesList.add(0,"Select");
                famNamesList.add(1,SessinSave.getsessin("FullName",activity));
            }catch (Exception e){
                e.printStackTrace();
            }
            SpFmilyNames.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,famNamesList));
            SpFmilyNames.setSelection(AppHelper.setValueToSpinner(SpFmilyNames,model.getFamilyNames()));
            SpFmilyNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ((TextView)parent.getChildAt(0)).setTextSize(spinnerTextSize);
                    familyName=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }
    }//AddEduAsyncTask

    private class HealthPoliciesTask extends AsyncTask<String,String,String>{
        String date,detail,familyname,filename,InsuranceType,InsuranceProvider,InsurId;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          progressDialog = ProgressDialog.show(activity, "Please wait", "loading...");
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
                obj.accumulate("file",null);
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
                obj.accumulate("viewStatus","");
                obj.accumulate("insurerType",InsuranceType);
                obj.accumulate("insuranceId",InsurId);
                obj.accumulate("insuranceProvider",InsuranceProvider);

                Log.i("Obj-->",obj.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
            //     Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserData+SessinSave.getsessin("profile_id",activity),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("AddEduAsyncTask onPost:",s);
            try {
                JSONObject mainObj = new JSONObject(s);
                if(mainObj.getString("status").equalsIgnoreCase("SUCCESS")){
//                    JSONArray array =new JSONArray(mainObj.getString("result"));
                    Toast.makeText(activity,"Successfully Added",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){ e.printStackTrace();  }

            super.onPostExecute(s);
           progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }

    private class DeleteDataRunner extends AsyncTask<String,String,String>{
        String Id;
        private AllUserDataModel mode;
        public DeleteDataRunner(AllUserDataModel model) {
            this.mode=model;

        }

        @Override
        protected void onPreExecute() {
            Id=mode.getId();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String Content=AsyncTaskHelper.makeServiceCall(ApisHelper.deleteUserData+Id,"GET",null);
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("result=======>",s);
            Toast.makeText(activity,"Successfully Deleted",Toast.LENGTH_LONG).show();


        }
    }

    private class CateogoryTask extends AsyncTask<String,String,String> {
        private final AllUserDataModel mode;
        private ProgressDialog progressDialog;
        private ArrayList<String> cateogorylist;
        private ArrayList<String> cateogorylistid;


        public CateogoryTask(AllUserDataModel model) {
            mode=model;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(activity, "Please wait", "loading...");
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
                cateogorylist = new ArrayList<String>();
                JSONArray array=new JSONArray(result);

                if (array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);

                      cateogorylist.add(object.getString("specialization"));
               //       cateogorylistid.add(object.getString("id"));

                    }

                }
                cateogorylist.add(0,"Select");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            etcateogory.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,cateogorylist));
            etcateogory.setSelection(AppHelper.setValueToSpinner(etcateogory,mode.getCategory()));

            etcateogory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ((TextView)parent.getChildAt(0)).setTextSize(spinnerTextSize);
                    familyName=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }
    }

    private class EducationalTask extends AsyncTask<String,String,String>{
        String[] arr_lab,arr_lab_codes;
        private ArrayList<String> qualificationlist;
        private AllUserDataModel mode;

        public EducationalTask(AllUserDataModel model) {
            mode=model;
        }


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

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Eduqualspin.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,qualificationlist));

            Eduqualspin.setSelection(AppHelper.setValueToSpinner(Eduqualspin,mode.getQualification()));
            Eduqualspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ((TextView)parent.getChildAt(0)).setTextSize(spinnerTextSize);
                    familyName=parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}

