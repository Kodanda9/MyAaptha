package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.Database.DatabaseHelper;
import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.DoctorHospitalsActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.DoctorOverviewActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.HospitalOverviewActivity;
import com.cpetsol.cpetsolutions.myaaptha.dbmodel.DrHomeDTO;
import com.cpetsol.cpetsolutions.myaaptha.fragment.MainLoginSignUpFrag;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.model.DoctorsDataModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Admin on 10/14/2017.
 */

public class DoctorsDataAdapter extends BaseAdapter
//        implements DatePickerListener
{
    private ArrayList<DoctorsDataModel> DocorsItems;
    private final Activity activity;
    private LayoutInflater inflater;
    private TextView Book_button,TV_DrName,TV_DrSpec,TV_DrExperience,Tv_DrQualification,Tv_DrLocalities,TvDrHospitalName,Tv_DrWeekdays,Tv_DrPrice,Tv_DrAvailabledays,TV_Days;
    private Button PopUpBook_button;
    private View localView;
    private Dialog mDialog;
    private TextView TvPDrName,TvPDrQualif,TvPDrExp,TvPDrLocality,TvPDrSpec,TvPDrHptlName;
    private EditText EtPBokDate;
    private Spinner SpinnerFamNames,SpinnerTimeSlots;
    private String pGender,pMobileNo,pFullName,sunVals,monVals,tueVals,thuVals,wedVals,friVals,satVals;
    private String familyName;
    private DatabaseHelper databaseHelper;


    public DoctorsDataAdapter(Activity activity, ArrayList<DoctorsDataModel> rowItems) {
        this.activity=activity;
        this.DocorsItems=rowItems;
        Log.i("rowItems->",rowItems.toString());
    }
    @Override
    public int getCount() {
        return DocorsItems.size();
    }
    @Override
    public Object getItem(int i) {
        return DocorsItems.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.docors_data_lv_row, null);

        TV_DrName = (TextView) convertView.findViewById(R.id.DrName);
        TV_Days=(TextView)convertView.findViewById(R.id.days);
        TvDrHospitalName = (TextView) convertView.findViewById(R.id.DrHospitalName);
        Tv_DrQualification = (TextView) convertView.findViewById(R.id.DrQualification);
        TV_DrExperience = (TextView) convertView.findViewById(R.id.DrExperience);
        TV_DrSpec = (TextView) convertView.findViewById(R.id.DrSpec);
        Tv_DrWeekdays = (TextView) convertView.findViewById(R.id.DrWeekdays);
        Tv_DrPrice = (TextView) convertView.findViewById(R.id.DrPrice);
        Tv_DrAvailabledays = (TextView) convertView.findViewById(R.id.DrAvailabledays);

        ImageView drImage=(ImageView)convertView.findViewById(R.id.drImgHome);
        Book_button=(TextView) convertView.findViewById(R.id.Docors_book1);


        final DoctorsDataModel model=DocorsItems.get(position);
        Book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SessinSave.getsessin("profile_id",activity).isEmpty() || SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("")){
//                    Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();
//                    activity.getFragmentManager().beginTransaction() .replace(R.id.nav_content_Frame, new MainLoginSignUpFrag()).commit();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

                    // set title
                    alertDialogBuilder.setTitle("Book Appointment");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("You need to SignIn to book Appointment")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    activity.getFragmentManager().beginTransaction().replace(R.id.nav_content_Frame, new MainLoginSignUpFrag() ).commit();
                                }
                            })
                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }else{
                    Intent in =new Intent(activity,DoctorHospitalsActivity.class);
                    in.putExtra("DoctorId",model.getDoctorId() );
                    activity.startActivity(in);
//                    OpenBookAppoinmentpopUp(model);
                }
            }
        });

        TV_DrName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in= new Intent(activity,DoctorOverviewActivity.class);

                in.putExtra("DoctorId",model.getDoctorId() );
                in.putExtra("DrSpcelization",model.getSpecilization());
                activity.startActivity(in);
            }
        });
        TvDrHospitalName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in= new Intent(activity,HospitalOverviewActivity.class);

                in.putExtra("HospitalId",model.getHospitalId() );
                in.putExtra("DrSpcelization",model.getSpecilization());
                activity.startActivity(in);
            }
        });



        TV_DrName.setText("Dr."+model.getName());
        Tv_DrQualification.setText(model.getQulification());
        TV_DrExperience.setText(model.getExperience()+" years");
        TV_DrSpec.setText(model.getSpecilization());

        TvDrHospitalName.setText(model.getHospitalName()+" in "+model.getLocalities());
        Tv_DrPrice.setText("Rs."+model.getPrice());
        //    Tv_DrAvailabledays.setText(model.getAvailableDate());

        if(model.getSunday().equalsIgnoreCase("null")){
            sunVals ="";
        }else{
            sunVals ="SUN :"+model.getSunday()+"   ";
        }
        Log.i("+++++++++",model.getFriday());
        if(model.getFriday().equalsIgnoreCase("null")){
            friVals="";
        }else{
            friVals ="FRI :"+model.getFriday()+"   " ;
        }
        if(model.getMonday().equalsIgnoreCase("null")){
            monVals="";
        }else{
            monVals ="MON :"+model.getMonday()+"   " ;
        }
        if(model.getTuesday().equalsIgnoreCase("null")){
            tueVals="";
        }else{
            tueVals ="TUE :"+model.getTuesday()+"   " ;
        }
        if(model.getWednesday().equalsIgnoreCase("null")){
            wedVals="";
        }else{
            wedVals ="WED :"+model.getWednesday()+"   " ;
        }
        if(model.getThursday().equalsIgnoreCase("null")){
            thuVals="";
        }else{
            thuVals ="THU :"+model.getThursday()+"   " ;
        }
        if(model.getSaturday().equalsIgnoreCase("null")){
            satVals="";
        }else{
            satVals ="SAT :"+model.getSaturday();
        }



        TV_Days.setText(sunVals+monVals+tueVals+wedVals+thuVals+friVals+satVals);

       /* if(model.getSaturday().equalsIgnoreCase("")||model.getSaturday().equalsIgnoreCase("null")){

        }*/
        if(model.getGender().equalsIgnoreCase("Male")){
            drImage.setImageResource(R.drawable.profile_male);
        }else if(model.getGender().equalsIgnoreCase("Female")){
            drImage.setImageResource(R.drawable.profile_female);
        }else{
            drImage.setImageResource(R.drawable.profile_female);
        }
        DrHomeDTO dto=new DrHomeDTO();
        dto.setDoctorId(model.getDoctorId());
        dto.setName(model.getName());
        dto.setQulification(model.getQulification());
        dto.setExperience(model.getExperience());
        dto.setLocalities(model.getLocalities());
        dto.setSpecilization(model.getSpecilization());
        dto.setHospitalName(model.getHospitalName());
        dto.setClinicName(model.getClinicName());
        dto.setPrice(model.getPrice());
        dto.setGender(model.getGender());
        dto.setMonday(model.getMonday());
        dto.setTuesday(model.getTuesday());
        dto.setWednesday(model.getWednesday());
        dto.setThursday(model.getThursday());
        dto.setFriday(model.getFriday());
        dto.setSaturday(model.getSaturday());
        dto.setHospitalId(model.getHospitalId());
        try {
            final Dao<DrHomeDTO, Integer> studentDao = getHelper().getDrHomeDto();
            studentDao.create(dto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //This is the way to insert data into a database table



//        String strObj= SessinSave.getsessin("registrationDetails",activity);
//        Log.i("11111111111",strObj);
//        try {
//            JSONObject loginObj = new JSONObject(strObj);
//            if(loginObj.getString("profileId").isEmpty() || loginObj.getString("profileId").equalsIgnoreCase("null")){
//                pFullName=null;
//                pGender=null;
//            }else{
//                pFullName = loginObj.getString("fullName");
//                pMobileNo = loginObj.getString("mobileNo");
//            }
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        return convertView;
    }//getView

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(activity,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    private void OpenBookAppoinmentpopUp(final DoctorsDataModel model) {

        localView = View.inflate(activity, R.layout.doctors_book_popup, null);
        localView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.zoom_in_enter));
        this.mDialog = new Dialog(activity, R.style.NewDialog);
        this.mDialog.setContentView(localView);
        this.mDialog.setCancelable(true);
        this.mDialog.show();

        Window window = this.mDialog.getWindow();
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



        TvPDrName   = (TextView) this.mDialog.findViewById(R.id.popUpDrName);                       TvPDrName.setText(model.getName());
        TvPDrQualif = (TextView) this.mDialog.findViewById(R.id.popUpDrQualif);                     TvPDrQualif.setText(model.getQulification()+"years");
        TvPDrExp    = (TextView) this.mDialog.findViewById(R.id.popUpDrExp);                        TvPDrExp.setText(model.getExperience());
        TvPDrLocality= (TextView) this.mDialog.findViewById(R.id.popUpDrLocality);                  TvPDrLocality.setText(model.getLocalities());
        TvPDrSpec   = (TextView) this.mDialog.findViewById(R.id.popUpDrSpec);                       TvPDrSpec.setText(model.getSpecilization());
        TvPDrHptlName=(TextView) this.mDialog.findViewById(R.id.popUpDrHospName);                   TvPDrHptlName.setText(model.getHospitalName());
        EtPBokDate =  (EditText) this.mDialog.findViewById(R.id.popUpDrbookDate);
        SpinnerTimeSlots=(Spinner) this.mDialog.findViewById(R.id.PopUpSpinnerTimeSlots);
        SpinnerFamNames=(Spinner) this.mDialog.findViewById(R.id.PopUpSpinnerName);

        FamilyNamesAsyncTask runner=new FamilyNamesAsyncTask();   runner.execute();


//        HorizontalPicker picker= (HorizontalPicker)this.mDialog.findViewById(R.id.hordatePicker);
//        picker.setListener(this)
//                .setDays(45)
//                .setOffset(7)
//                .setDateSelectedColor(Color.DKGRAY)
//                .setDateSelectedTextColor(Color.WHITE)
//                .setMonthAndYearTextColor(Color.DKGRAY)
//                .setTodayButtonTextColor(activity.getResources().getColor(R.color.colorPrimary))
//                .setTodayDateTextColor(activity.getResources().getColor(R.color.colorPrimary))
//                .setTodayDateBackgroundColor(Color.GRAY)
//                .setUnselectedDayTextColor(Color.DKGRAY)
//                .setDayOfWeekTextColor(Color.DKGRAY )
//                .setUnselectedDayTextColor(activity.getResources().getColor(R.color.primaryTextColor))
//                .showTodayButton(true)
//                .init();
//        picker.setBackgroundColor(Color.LTGRAY);
//        picker.setDate(new DateTime());



        PopUpBook_button=(Button) this.mDialog.findViewById(R.id.PopUpDocors_book);

        EtPBokDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDatePicker(model);
            }
        });
        PopUpBook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOnClick(model);
            }
        });
        ImageView v = (ImageView) this.mDialog.findViewById(R.id.closeDialog);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });



    }

    public class FamilyNamesAsyncTask extends AsyncTask<String, String, String> {
        private ArrayList<String> famNamesList;

        @Override
        protected void onPreExecute() {
//            pDialog= ProgressDialog.show(getActivity(),"Please wait","Loading...");
        }
        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.familyNames+"/"+SessinSave.getsessin("profile_id",activity),"GET",null);
            Log.i("333333",ApisHelper.familyNames+"/"+SessinSave.getsessin("profile_id",activity));
            return content;
        }
        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Log.i("Drs  onPost:",s);
            super.onPostExecute(s);
            JSONArray array;
            try {
                famNamesList = new ArrayList<String>();
                array=new JSONArray(s);
                for(int i = 0; i < array.length(); i++){
                    JSONObject jsonNames=array.getJSONObject(i);
                    famNamesList.add(jsonNames.getString("fullName"));
                }

                famNamesList.add(0,"Select");
            }catch (Exception e){
                e.printStackTrace();
            }
            SpinnerFamNames.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,famNamesList));

            SpinnerFamNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



    private void submitOnClick(DoctorsDataModel model) {
        try{
            ValidationHelper helper=new ValidationHelper();
            String[] strIds = activity.getResources().getStringArray(R.array.main_doctorsbook_popup_ids_array);
            String[] strErrMsgs = activity.getResources().getStringArray(R.array.main_doctorsbook_popup_errors_array);
            String[] strCompTypeArr = activity.getResources().getStringArray(R.array.main_doctorsbook_popup_comptypes_array);
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
            boolean isValidData = helper.validateData(activity, aList, localView);
            if (!isValidData) {
                return;
            }else{

//                if( pFullName.equalsIgnoreCase("null")||pFullName.isEmpty() || pMobileNo.equalsIgnoreCase("null")||pMobileNo.isEmpty()  ){
                if(  SessinSave.getsessin("profile_id",activity).isEmpty() ||  SessinSave.getsessin("profile_id",activity).equalsIgnoreCase("") ){
                    Toast.makeText(activity,"You need to signIn to book appoinment", Toast.LENGTH_LONG).show();

                }else{
                    LoadBookAsyncTask runner=new LoadBookAsyncTask(model,pFullName,pMobileNo);
                    runner.execute();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void bookDatePicker(final DoctorsDataModel model) {
        final Calendar newDate = Calendar.getInstance();
//        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        DatePickerDialog currDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);

                TimeSlotsAsyncRunner runner= new TimeSlotsAsyncRunner(model,dateFormatter.format(newDate.getTime()));
                runner.execute();

                EtPBokDate.setText(dateFormatter.format(newDate.getTime()));
                EtPBokDate.setError(null);
            }

        },newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
//        currDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        newDate.add(Calendar.DATE, -0);
        currDatePickerDialog.getDatePicker().setMinDate(newDate.getTimeInMillis());
//        currDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        currDatePickerDialog.show();

    }

//    @Override
//    public void onDateSelected(DateTime dateSelected) {
//        Toast.makeText(activity,dateSelected.toString(),Toast.LENGTH_LONG).show();
//        Log.i("Date-->",dateSelected.toString());
//    }


    public class TimeSlotsAsyncRunner extends AsyncTask<String, String, String> {
        private final DoctorsDataModel model;
        JSONArray array;
        private ProgressDialog pDialog;
        private ArrayList<String> Timeslotslist;
        private String hospName,DrId,date;

        public TimeSlotsAsyncRunner(DoctorsDataModel model, String format) {
            this.model=model;
            this.date=format;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
            DrId=model.getDoctorId();
            hospName = model.getHospitalName();
        }

        @Override
        protected String doInBackground(String... strings) {

            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.loadTimeslots+SessinSave.getsessin("profile_id",activity)+"?doc_id="+DrId+"&bookedDate="+date+"&hospitalName="+hospName,"GET",null);
//            String content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/loadTimeslots/900000070901?doc_id=GEN102&bookedDate=04/16/2018&hospitalName=Prathima Hospitals","GET",null);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("Time Slots-->",s);
            if(pDialog.isShowing()){    pDialog.dismiss();    }
            Timeslotslist = new ArrayList<String>();
            Timeslotslist.add(0,"Select");
            try {
                JSONArray array=new JSONArray(s);
                for (int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);

                    Timeslotslist.add(obj.getString("timeSlot"));
                }
                SpinnerTimeSlots.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,Timeslotslist));

//                array=new JSONArray(s.substring(0,s.length()-1));
//                Log.i("array1111",array.toString());

//                arr_state = new String[array.length()];
//                arr_state_codes= new String[array.length()];
//
//                for(int i = 0; i < array.length(); i++){
//                    JSONObject jsonCountry=array.getJSONObject(k);
//                    arr_state[i] = jsonCountry.getString("qualification").trim();
//                    arr_state_codes[i] = jsonCountry.getString("id").trim();
//                    k++;
//                }
//                Qualificationlist = new ArrayList<String>(Arrays.asList(arr_state));
//                Qualificationlist.add(0,"Select");
            }catch (Exception e){
                e.printStackTrace();
            }
////            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_dropdown,R.id.text1,Stateslist);//    android:id="@android:id/text1"
//            SpinnerTimeSlots.setAdapter(new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,Qualificationlist));
//            SpinnerTimeSlots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    qualificationName=parent.getItemAtPosition(position).toString().trim();
//                    Log.i("qualificationName>",qualificationName);
//
//                    for(int i = 0; i <array.length(); i++){
//                        if(qualificationName.equalsIgnoreCase(arr_state[i])){
//                            SpecAsyncTask runner=new SpecAsyncTask(model.getSpecialization(),arr_state_codes[i]);
//                            runner.execute();
//                        }
//                    }
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//                }
//            });


        }
    }

    public class LoadBookAsyncTask extends AsyncTask<String, String, String> {
        private final DoctorsDataModel model;
        private ProgressDialog pDialog;
        private ArrayList<String> Timeslotslist;
        private String drId,drBookDate,timeslots,drAvailabilty,drHospitalName,drName,drSpec,pFullName,pMobileNo    ;

        public LoadBookAsyncTask(DoctorsDataModel model, String pFullName, String pMobileNo) {
            this.model=model;
            this.pFullName=pFullName;
            this.pMobileNo=pMobileNo;
        }


        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
            drName= model.getName();
            drId= model.getDoctorId();
            drSpec= model.getSpecilization();

            drHospitalName= model.getHospitalName();
            drBookDate= EtPBokDate.getText().toString();
            timeslots= SpinnerTimeSlots.getSelectedItem().toString();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url=""+ ApisHelper.doctor_store+"/"+SessinSave.getsessin("profile_id",activity)+"/"+familyName+"/"+pMobileNo+"?id="+drId+"&bookAppointment="+drBookDate+"&timeslot.timeslot="+timeslots+"&availStatus="+drAvailabilty+"&hospitalName="+drHospitalName+"&name="+drName+"&specilization="+drSpec+"";
            String content= AsyncTaskHelper.makeServiceCall(url,"GET",null);
            Log.i("URL",url);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            if(pDialog.isShowing()){pDialog.dismiss();}
            if(mDialog.isShowing()){mDialog.dismiss();}
            super.onPostExecute(s);
            JSONObject objValues=new JSONObject();
            Log.i("Str",s);
            Toast.makeText(activity,s, Toast.LENGTH_LONG).show();
            Toast.makeText(activity,"Successfully Booked", Toast.LENGTH_LONG).show();
            try {
                objValues.accumulate("DrName",drName);
                objValues.accumulate("DrId",drId);
                objValues.accumulate("DrSpec",drSpec);
                objValues.accumulate("DrAvail",drAvailabilty);
                objValues.accumulate("DrHosp",drHospitalName);
                objValues.accumulate("DrBookDate",drBookDate);
                objValues.accumulate("DrTimeslot",timeslots);
                objValues.accumulate("patientName",familyName);
                objValues.accumulate("AppointmentId",s);

//                Intent in =new Intent(activity,BookOverview.class);
//                in.putExtra("modelObj",objValues.toString());
//                activity.startActivity(in);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.pd_guardianLL, BookAppoinmentpreview.newInstance(model,s) /**new Hd_EmergencyUpdate()**/).commit();

        }
    }//LoadBookAsyncTask



}
