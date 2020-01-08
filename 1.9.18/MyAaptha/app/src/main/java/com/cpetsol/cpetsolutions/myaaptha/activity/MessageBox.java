package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.MsgSearchModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessageBox extends AppCompatActivity {

    private ListView lvmsgslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_box);
        getSupportActionBar().hide();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Messages");
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvmsgslist=(ListView)findViewById(R.id.msgfrnd);
      MsgsRunner runner=new MsgsRunner();
        runner.execute();


    }

    private class MsgsRunner extends AsyncTask<String,String,String> {
        private ArrayList<MsgSearchModel> item;


        @Override
        protected String doInBackground(String... strings) {
            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.friendsAndRelatives+"/"+ SessinSave.getsessin("profile_id",MessageBox.this)+"","GET",null);
            Log.i("===>",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            item=new ArrayList<MsgSearchModel>();
            try {
                JSONArray array=new JSONArray(s);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++){
                        JSONObject obj=array.getJSONObject(i);
                        MsgSearchModel model=new MsgSearchModel();
                        model.setId(obj.getString("id"));
                        model.setFullName(obj.getString("fullName"));
                        model.setRelation(obj.getString("relation"));
                        item.add(model);
                    } 
                }else{
                    Toast.makeText(MessageBox.this,"No Records",Toast.LENGTH_LONG).show();
                }
                MsgSearchAdapter adapter=new MsgSearchAdapter(MessageBox.this,item);
                lvmsgslist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
