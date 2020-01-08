package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
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

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.activity.BookOverviewActivity;
import com.cpetsol.cpetsolutions.myaaptha.activity.HospitalOverviewActivity;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationDTO;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.validations.ValidationUtils;
import com.cpetsol.cpetsolutions.myaaptha.model.DrHospitalModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;
import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Admin on 5/4/2018.
 */

public class DrHospitaladapter  extends BaseAdapter {


    private final ArrayList<DrHospitalModel> userItems;
    private  Activity activity;
    private LayoutInflater inflater;
    private TextView TvBookClick,TvFees,TvLocality,TvHospName,Days,Map;
    private View localView;
    private Dialog mDialog;
    private TextView TvPDrExp,TvPDrQualif,TvPDrName,TvPDrLocality,TvPDrSpec,TvPDrHptlName,EtPBokDate;
    private Spinner SpinnerTimeSlots,SpinnerFamNames;
    private Button PopUpBook_button;
    private String familyName;
    private String loc;
    private String sunVals;
    private String friVals,monVals,tueVals,wedVals,thuVals,satVals;



    public DrHospitaladapter(Activity ex, ArrayList<DrHospitalModel> rowItems) {
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
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.doctorhospital_lvrow, null);
        TvHospName= (TextView) convertView.findViewById(R.id.DrHospName);
        TvLocality= (TextView) convertView.findViewById(R.id.DrLocality);
        TvFees= (TextView) convertView.findViewById(R.id.DrPrice);
        Days= (JustifiedTextView) convertView.findViewById(R.id.daysavailable);
        TvBookClick= (TextView) convertView.findViewById(R.id.Docors_book1);
        Map= (TextView) convertView.findViewById(R.id.map);
        final DrHospitalModel model=userItems.get(position);
        TvHospName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(activity,HospitalOverviewActivity.class);

                in.putExtra("Hospitalname",model.getHospitalName() );
                in.putExtra("Location",model.getLocalities() );
                in.putExtra("DoctorId",model.getDoctorId() );
         //       in.putExtra("DrSpcelization",model.getSpecilization());
                activity.startActivity(in);
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/"+model.getLongitude())));
            }
        });


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


        Days.setText(sunVals+monVals+tueVals+wedVals+thuVals+friVals+satVals);



        Log.i("",""+position+ "  "+model.getDoctorId()+"    "+model.getExperience()+"   "+model.getHospitalName());
        if(model.getLocation().equalsIgnoreCase("1")){
                    loc="Hyderabad";
}
        TvHospName.setText(model.getHospitalName());
        TvLocality.setText(model.getAddress()+","+model.getLocalities()+","+loc);
        TvFees.setText("Rs."+model.getPrice());


        TvBookClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenBookAppoinmentpopUp(model);
            }
        });


        return convertView;
    }





    private void OpenBookAppoinmentpopUp(final DrHospitalModel model) {

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
        TvPDrQualif = (TextView) this.mDialog.findViewById(R.id.popUpDrQualif);                     TvPDrQualif.setText(model.getQulification());
        TvPDrExp    = (TextView) this.mDialog.findViewById(R.id.popUpDrExp);                        TvPDrExp.setText(model.getExperience()+" Years");
        TvPDrLocality= (TextView) this.mDialog.findViewById(R.id.popUpDrLocality);                  TvPDrLocality.setText(model.getLocalities());
        TvPDrSpec   = (TextView) this.mDialog.findViewById(R.id.popUpDrSpec);                       TvPDrSpec.setText(model.getSpecialization());
        TvPDrHptlName=(TextView) this.mDialog.findViewById(R.id.popUpDrHospName);                   TvPDrHptlName.setText(model.getHospitalName());
        EtPBokDate =  (EditText) this.mDialog.findViewById(R.id.popUpDrbookDate);
        SpinnerTimeSlots=(Spinner) this.mDialog.findViewById(R.id.PopUpSpinnerTimeSlots);
        SpinnerFamNames=(Spinner) this.mDialog.findViewById(R.id.PopUpSpinnerName);

        FamilyNamesAsyncTask runner=new FamilyNamesAsyncTask();   runner.execute();

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

    private void bookDatePicker(final DrHospitalModel model) {
        final Calendar newDate = Calendar.getInstance();

//        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DatePickerDialog currDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);


                TimeSlotsAsyncRunner runner = new TimeSlotsAsyncRunner(model, dateFormatter.format(newDate.getTime()));
                runner.execute();

                EtPBokDate.setText(dateFormatter.format(newDate.getTime()));
                EtPBokDate.setError(null);
            }

        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        currDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        newDate.add(Calendar.DATE, -0);
        //      currDatePickerDialog.getDatePicker().setMinDate(newDate.getTimeInMillis());
        currDatePickerDialog.getDatePicker().setMaxDate(newDate.getTimeInMillis() + (1000 * 60 * 60 * 24 * 24));

        currDatePickerDialog.show();
    }


    private void submitOnClick(DrHospitalModel model) {
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
                    LoadBookAsyncTask runner=new LoadBookAsyncTask(model);
                    runner.execute();
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public class FamilyNamesAsyncTask extends AsyncTask<String, String, String> {
        private ArrayList<String> famNamesList;

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

    public class TimeSlotsAsyncRunner extends AsyncTask<String, String, String> {
        private final DrHospitalModel model;
        JSONArray array;
        private ProgressDialog pDialog;
        private ArrayList<String> Timeslotslist;
        private String hospName,DrId,date;

        public TimeSlotsAsyncRunner(DrHospitalModel model, String format) {
            this.model=model;
            this.date=format;
        }

        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
            DrId=model.getDoctorId();
            hospName = model.getHospitalId();
        }

        @Override
        protected String doInBackground(String... strings) {
             String content= AsyncTaskHelper.makeServiceCall(ApisHelper.loadTimeslots+SessinSave.getsessin("profile_id",activity)+"?doc_id="+DrId+"&bookedDate="+date+"&hospitalName="+hospName,"GET",null);
      //       String content= AsyncTaskHelper.makeServiceCall("http://www.myaaptha.com/pp/rest/loadTimeslots/"+SessinSave.getsessin("profile_id",activity)+"?doc_id="+DrId+"&bookedDate="+date+"&hospitalName="+hospName,"GET",null);
            Log.i("TimeSlot Api-->",content);
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

            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


    public class LoadBookAsyncTask extends AsyncTask<String, String, String> {
        private final DrHospitalModel model;
        private ProgressDialog pDialog;
        String drHospitalName,drName,hospId,drId,drSpec,timeslots,localities,drBookDate ,pMobileNo ,patientName ;
        public LoadBookAsyncTask(DrHospitalModel model) {
            this.model=model;
        }


        @Override
        protected void onPreExecute() {
            pDialog= ProgressDialog.show(activity,"Please wait","Loading...");
            drName= model.getName();
            drId= model.getDoctorId();
            hospId = model.getHospitalId();
            drSpec= model.getSpecialization();
//            drAvailabilty= model.getDoctorAvailability();
            drHospitalName= model.getHospitalName();
            localities= model.getLocalities();
            drBookDate= EtPBokDate.getText().toString();
            timeslots= SpinnerTimeSlots.getSelectedItem().toString();
            patientName=SpinnerFamNames.getSelectedItem().toString();
            pMobileNo = SessinSave.getsessin("MobileNo",activity);

            Log.i("asdf",pMobileNo);
        }

        @Override
        protected String doInBackground(String... strings) {

            JSONObject obj= null;
            try{
                obj= new JSONObject();
                obj.accumulate("hospitalId",hospId);
                obj.accumulate("hospitalName",drHospitalName);
                obj.accumulate("name",drName);
                obj.accumulate("docId",drId);
                obj.accumulate("specilization",drSpec);
                obj.accumulate("localities",localities);
                obj.accumulate("timeslot",timeslots);
                obj.accumulate("bookAppointment",drBookDate);
                obj.accumulate("familyNames",patientName);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.i("obj:-->",obj.toString());
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.saveBookAppointment+ SessinSave.getsessin("profile_id",activity)+"/"+pMobileNo,"POST",obj);
    Log.i("Api==>",ApisHelper.saveBookAppointment+ SessinSave.getsessin("profile_id",activity)+"/"+pMobileNo);
//            String content= AsyncTaskHelper.makeServiceCall("http://localhost:8081/pp/rest/saveBookAppointment/"+ SessinSave.getsessin("profile_id",activity)+"/"+pMobileNo,"POST",obj);
            return content;


//            String url=""+ ApisHelper.doctor_store+"/"+SessinSave.getsessin("profile_id",activity)+"/"+familyName+"/"+pMobileNo+"?id="+drId+"&bookAppointment="+drBookDate+"&timeslot.timeslot="+timeslots+"&availStatus="+"&hospitalName="+drHospitalName+"&name="+drName+"&specilization="+drSpec+"";
//            String content= AsyncTaskHelper.makeServiceCall(url,"GET",null);
//            Log.i("URL--->",url);
//            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            if(pDialog.isShowing()){pDialog.dismiss();}
            if(mDialog.isShowing()){mDialog.dismiss();}
            super.onPostExecute(s);
            Toast.makeText(activity,"hii"+s,Toast.LENGTH_LONG).show();
            JSONObject objValues=new JSONObject();
            Log.i("Str",s);
            Toast.makeText(activity,"Successfully Booked", Toast.LENGTH_LONG).show();
            try {
                objValues.accumulate("DrName",drName);
                objValues.accumulate("DrId",drId);
                objValues.accumulate("DrSpec",drSpec);
//                objValues.accumulate("DrAvail",drAvailabilty);
                objValues.accumulate("DrHosp",drHospitalName);
                objValues.accumulate("DrBookDate",drBookDate);
                objValues.accumulate("DrTimeslot",timeslots);
                objValues.accumulate("patientName",patientName);
                objValues.accumulate("AppointmentId",s);

                Intent in =new Intent(activity,BookOverviewActivity.class);
                in.putExtra("modelObj",objValues.toString());
                Log.i("Bhavani",objValues.toString());
                activity.startActivity(in);

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            android.app.FragmentManager fragmentManager = activity.getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.pd_guardianLL, BookAppoinmentpreview.newInstance(model,s) /**new Hd_EmergencyUpdate()**/).commit();

        }
    }//LoadBookAsyncTask




}
