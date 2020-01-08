package com.cpetsol.cpetsolutions.myaaptha.fragment;


import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddFamilyDetails extends Fragment {


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    final int GALLERY_REQUEST=2200;
    private View rootView;
    private EditText ETDetails,ETDate,ETName;
    private Spinner SRelation;
    private Button btnSubmit;
    private Dialog mDialog;
//    private GalleryPhoto galleryPhoto;
    private String photoPath;
    private String uploadName;
    private TextView TvUpoloadName;
    private  long totalSize = 0;
    private RadioGroup privacyGroup;
    private String ppId="1";
    private RadioGroup radioGenderGroup;
    private String selectedGender;

    public AddFamilyDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView != null)        {
            ViewGroup parent=(ViewGroup)rootView.getParent();
            if(parent != null)
            {  parent.removeView(rootView);  }
        }//if
        try{
            rootView=inflater.inflate(R.layout.add_family_details_frag,container,false);
//            rootView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.enter_from_right));
//            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
//            AppHelper.setupHideleyboard(rootView,getActivity());

//            fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
            init();


        }catch (Exception e){   e.printStackTrace();     }
        return rootView;
    }//onCreate

    private void init() {
        ETName=(EditText)rootView.findViewById(R.id.et_Name);
        SRelation=(Spinner)rootView.findViewById(R.id.sRelation);
        ETDate=(EditText)rootView.findViewById(R.id.et_Date);
        ETDetails=(EditText)rootView.findViewById(R.id.et_details);
        btnSubmit=(Button) rootView.findViewById(R.id.btn_submit);
        TvUpoloadName = (TextView) rootView.findViewById(R.id.tvUploadTitle);
        TextView TvUpload = (TextView) rootView.findViewById(R.id.tvUpload);
        TvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                galleryPhoto = new GalleryPhoto(getActivity());
//                Intent in = galleryPhoto.openGalleryIntent();
//                startActivityForResult(in, GALLERY_REQUEST);

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validations();
            }
        });
        radioGenderGroup = (RadioGroup)rootView.findViewById(R.id.radioSexGroup);
        privacyGroup=(RadioGroup)rootView.findViewById(R.id.fd_radioGroup);
        privacyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                int   pos=privacyGroup.indexOfChild(rootView.findViewById(privacyGroup.getCheckedRadioButtonId()));
                ppId= String.valueOf(pos+1);
            }
        });
        ETDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppHelper.DatePickerDialodPopUp(getActivity(),ETDate);
            }
        });
        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioMale:
                        selectedGender="Male";
                        break;
                    case R.id.radioFemale:
                        selectedGender="Female";
                        break;
                    case R.id.radioOther:
                        selectedGender="Other";
                        break;
                }
            }
        });
    }//inIt
    private void validations() {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = getResources().getStringArray(R.array.addFamDetails_ids_array);
            String[] strErrMsgs = getResources().getStringArray(R.array.addFamDetails_errors_array);
            String[] strCompTypeArr = getResources().getStringArray(R.array.addFamDetails_comptypes_array);
            ArrayList<ValidationDTO> aList = new ArrayList<ValidationDTO>();

            int iPos = 0;
            for(String strCompType:strCompTypeArr){
                ValidationDTO valDTO=new ValidationDTO();
                valDTO.setComponentType(strCompType);
                valDTO.setComponentID(ValidationUtils.getIdResourceByName(getActivity(),strIds[iPos]));
                valDTO.setErrorMessage(strErrMsgs[iPos]);
                aList.add(valDTO);
                iPos++;
            }
            boolean isValidData = helper.validateData(getActivity().getBaseContext(), aList, rootView);
            if (!isValidData) {
                return;
            }else{
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }//val

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {
        ProgressDialog progressDialog;
        String EName,EBirthday,EDetails,sRelation  ;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
            EName=ETName.getText().toString();
            sRelation=SRelation.getSelectedItem().toString();
            EBirthday=ETDate.getText().toString();
            EDetails=ETDetails.getText().toString();
        }
        @Override
        protected String doInBackground(String... strings) {
            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("documentCategory","Add Family Members");
                obj.accumulate("name",EName);
                obj.accumulate("gender",selectedGender);
                obj.accumulate("relation",sRelation);
                obj.accumulate("date",EBirthday);
                obj.accumulate("details",EDetails);
                obj.accumulate("fileName",uploadName);
                obj.accumulate("view",ppId);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.storeUserDataMyAaptha+ SessinSave.getsessin("profile_id",getActivity()),"POST",obj);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj=new JSONObject(s);
                if(obj.getString("status").equalsIgnoreCase("SUCCESS")){
                    Toast.makeText(getActivity(),"Successfully saved", Toast.LENGTH_LONG).show();
//                    getActivity().getFragmentManager().beginTransaction().replace(R.id.main_nav_content_frame, new AllUserData()).commit();
//                    Intent in=new Intent(getActivity(),NavigationActivity.class);
//                    startActivity(in);
//                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(),"Some Issue is going on, we will Solve this ASAP", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("AddEduAsyncTask onPost:",s);
            super.onPostExecute(s);
            progressDialog.dismiss();
//            getFragmentManager().beginTransaction().replace(R.id.nav_content, new AddAdvanceFragment()).commit();
//            android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();//Refresh Fragmet
//            fragmentManager.beginTransaction().replace(R.id.nav_main_replace, new PD_Guardian()).commit();
        }
    }//NavSubmitAsyncTask

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode == getActivity().RESULT_OK) {
//            Bitmap imageBitmap;
//            if (requestCode == REQUEST_IMAGE_CAPTURE ) {
//                Bundle extras = data.getExtras();
//                imageBitmap = (Bitmap) extras.get("data");
////                profileImg.setImageBitmap(imageBitmap);
//            }
////            if (requestCode == CAMERA_REQUEST) {
////                String photoPath = cameraPhoto.getPhotoPath();
////                Log.i("photoPath-->",photoPath);
//////                try {
//////                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
//////                    Photo.setImageBitmap(bitmap); //imageView is your ImageView
//////                } catch (FileNotFoundException e) {
//////                    e.printStackTrace();
//////                }
////            }
//            else  if(requestCode == GALLERY_REQUEST){
//                galleryPhoto.setPhotoUri(data.getData());
//                photoPath = galleryPhoto.getPath();
//                Log.i("Photo path",photoPath);
//                try {
//                    imageBitmap = BitmapFactory.decodeFile(photoPath);
////                    profileImg.setImageBitmap(imageBitmap);
////                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
////                    profileImg.setImageBitmap(bitmap);
////                    selectedImage = photoPath;
//                    new UploadFileToServer().execute();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            //converting image to base64 string
////            ByteArrayOutputStream baos =new ByteArrayOutputStream();
////            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos );
////            byte[] imageBytes = baos.toByteArray();
////            final String imageString=Base64.encodeToString(imageBytes,Base64.DEFAULT);
////            Log.i("1111111111",imageString);
//        }
//    }//onActivityResult
//    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
//        ProgressDialog progressDialog;
//        @Override
//        protected void onPreExecute() {
//            // setting progress bar to zero
//            progressDialog = ProgressDialog.show(getActivity(),     "Please wait",   "loading...");
//            super.onPreExecute();
//        }
//
////        @Override
////        protected void onProgressUpdate(Integer... progress) {
////            // Making progress bar visible
//////            progressBar.setVisibility(View.VISIBLE);
////
////            // updating progress bar value
//////            progressBar.setProgress(progress[0]);
////
////            // updating percentage value
//////            txtPercentage.setText(String.valueOf(progress[0]) + "%");
////        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            return uploadFile();
//        }
//        @SuppressWarnings("deprecation")
//        private String uploadFile() {
//            String responseString = null;
//
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpPost httppost = new HttpPost(ApisHelper.fileuploadMyAaptha+ SessinSave.getsessin("profile_id",getActivity()));
//
//            try {
//                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
//                        new AndroidMultiPartEntity.ProgressListener() {
//
//                            @Override
//                            public void transferred(long num) {
//                                publishProgress((int) ((num / (float) totalSize) * 100));
//                            }
//                        });
//
//                File sourceFile = new File(photoPath);
//
//                // Adding file data to http body
//                entity.addPart("file", new FileBody(sourceFile));
//
////                              Extra parameters if you want to pass to server
////                entity.addPart("website", new StringBody("www.androidhive.info"));
////                entity.addPart("email", new StringBody("abc@gmail.com"));
//
//                totalSize = entity.getContentLength();
//                httppost.setEntity(entity);
//
//                // Making server call
//                HttpResponse response = httpclient.execute(httppost);
//                HttpEntity r_entity = response.getEntity();
//
//                int statusCode = response.getStatusLine().getStatusCode();
//                if (statusCode == 200) {
//                    // Server response
//                    responseString = EntityUtils.toString(r_entity);
//                } else {
//                    responseString = "Error occurred! Http Status Code: "
//                            + statusCode;
//                    Log.i("Line 1200","Error occurred!");
//                }
//
//            } catch (ClientProtocolException e) {
//                responseString = e.toString();
//            } catch (IOException e) {
//                responseString = e.toString();
//            }finally {
//            }
//
//            return responseString;
//
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            Log.e("OnPOst", "Response from server: " + result);
//            // showing the server response in an alert dialog
////            showAlert(result);
//            Pattern p = Pattern.compile("\"([^\"]*)\"");
//            Matcher m = p.matcher(result);
//            while (m.find()) {
//                uploadName=m.group(1);
//            }
//            TvUpoloadName.setText(uploadName);
////            super.onPostExecute(result);
//            if(progressDialog.isShowing()){ progressDialog.dismiss(); }
//        }
//
//    }






}
