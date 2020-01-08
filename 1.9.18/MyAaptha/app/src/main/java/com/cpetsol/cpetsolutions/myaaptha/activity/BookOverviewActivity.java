package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BookOverviewActivity extends Activity {

    private TextView TvHspName,TvDateTime,TvDrSpec,TvDrName,TvApptId,PatientName,opid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_overview_activity);
       String s = getIntent().getStringExtra("modelObj");
        Log.i("Bhavani",s);

        ImageView v = (ImageView) findViewById(R.id.closeDialog);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
                Intent i=new Intent(BookOverviewActivity.this,MenuAppointmentActivity.class);
                startActivity(i);
            }
        });
        //     TvApptId = (TextView)findViewById(R.id.tv_apptId);
        PatientName = (TextView)findViewById(R.id.tv_ptnme);
        TvDrName = (TextView)findViewById(R.id.tv_DrName);
        TvDrSpec = (TextView)findViewById(R.id.tv_DrSpec);
        TvDateTime = (TextView)findViewById(R.id.tv_DateTime);
        opid = (TextView)findViewById(R.id.opid);
        TvHspName = (TextView)findViewById(R.id.tv_HspName);
        try {
            JSONObject obj = new JSONObject(s);
            Log.i("Model-->",obj.getString("AppointmentId"));
       //     TvApptId.setText(obj.getString("AppointmentId"));
            TvDrName.setText(obj.getString("DrName"));
            opid.setText(obj.getString("AppointmentId"));
            TvDrSpec.setText(obj.getString("DrSpec"));
            TvHspName.setText(obj.getString("DrHosp"));
            TvDateTime.setText(obj.getString("DrBookDate")+" "+obj.getString("DrTimeslot"));
            PatientName.setText(obj.getString("patientName"));


//            TvApptId.setText(obj.getString("patientName"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
