package com.cpetsol.cpetsolutions.myaaptha.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.adapter.UserSearchAdapter;
import com.cpetsol.cpetsolutions.myaaptha.helper.ApisHelper;
import com.cpetsol.cpetsolutions.myaaptha.helper.AsyncTaskHelper;
import com.cpetsol.cpetsolutions.myaaptha.model.UserSearchModel;
import com.cpetsol.cpetsolutions.myaaptha.util.SessinSave;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {
    private RelativeLayout Rl_listSuggest;
    private ListView listView,lvmsgslist;
    private FloatingActionButton AddFloatingButton,MsgFloatingButton;
    private Button BtnFriends,BtnRelatives;
    private TextView TvFrndBtm,TvReltBtm;
    private Dialog mDialog;
    private ListView lvfriendslist;
    private AutoCompleteTextView autoText;
    private Dialog msgDialog;
    private SearchView.SearchAutoComplete searchAutoComplete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_user_activity);


        ActionBar actionBar = getSupportActionBar();
        // Set below attributes to add logo in ActionBar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search  User");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0aabd3")));

        listView=(ListView)findViewById(R.id.listviewFR);
        Rl_listSuggest=(RelativeLayout)findViewById(R.id.rlsuggestlist);



        BtnFriends=(Button)findViewById(R.id.btnFriends);     TvFrndBtm=(TextView)findViewById(R.id.tvBtmfrnd);
        BtnRelatives=(Button)findViewById(R.id.btnRelatives); TvReltBtm=(TextView)findViewById(R.id.tvBtmRel);
       AddFloatingButton=(FloatingActionButton)findViewById(R.id.floatingButton);

        AddFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserPopUp();
            }
        });
        AsyncTaskRunner runner=new AsyncTaskRunner();
        runner.execute();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the search menu action bar.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_search_example_menu, menu);

        // Get the search menu.
        MenuItem searchMenu = menu.findItem(R.id.app_bar_menu_search);

        // Get SearchView object.
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);

        // Get SearchView autocomplete object.
      searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.BLACK);
        searchAutoComplete.setDropDownBackgroundResource(android.R.color.black);

        AddFRcAsyncTask runner=new AddFRcAsyncTask();
        runner.execute();


        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
                Toast.makeText(SearchUserActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });


        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AlertDialog alertDialog = new AlertDialog.Builder(SearchUserActivity.this).create();
                alertDialog.setMessage("Search keyword is " + query);
                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Get the share menu item.
       /* MenuItem shareMenuItem = menu.findItem(R.id.app_bar_menu_share);
        // Because it's actionProviderClass is ShareActionProvider, so after below settings
        // when click this menu item A sharable applications list will popup.
        // User can choose one application to share.
        ShareActionProvider shareActionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(shareMenuItem);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image*//*");
        shareActionProvider.setShareIntent(shareIntent);*/

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
switch (item.getItemId()){
    case android.R.id.home:
        finish();

}
        return true;
    }

    private void AddUserPopUp() {
        View uploadLocalView = View.inflate(SearchUserActivity.this, R.layout.add_user_profile, null);
//        AppHelper.setupHideleyboard(uploadLocalView,SearchUserActivity.this);
        uploadLocalView.startAnimation(AnimationUtils.loadAnimation(SearchUserActivity.this,
                R.anim.zoom_in_enter));
        this.mDialog = new Dialog(SearchUserActivity.this, R.style.NewDialog);
        this.mDialog.setContentView(uploadLocalView);
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


        lvfriendslist=(ListView)uploadLocalView.findViewById(R.id.lvAddFR);
        autoText=(AutoCompleteTextView)uploadLocalView.findViewById(R.id.autotextAdd);
        autoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.i("Edit S   ",s.toString());
                if(s.length()>0){
                    AddFRcAsyncTask runner=new AddFRcAsyncTask();
                    runner.execute();

                }
            }
        });

        ImageView BtnCancel = (ImageView) uploadLocalView.findViewById(R.id.cancel);
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDialog.isShowing()){mDialog.dismiss();}
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;
        private ArrayList<UserSearchModel> rowItems,rowItemsRel;
        private UserSearchAdapter adapter;
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SearchUserActivity.this,     "Please wait",   "loading...");
        }
        @Override
        protected String doInBackground(String... params) {

            String content= AsyncTaskHelper.makeServiceCall(ApisHelper.friendsAndRelatives+"/"+ SessinSave.getsessin("profile_id",SearchUserActivity.this)+"","GET",null);
            Log.i("===>",content);
            return content;
        }
        @Override
        protected void onPostExecute(String result) {
            rowItems = new ArrayList<UserSearchModel>();
            rowItemsRel = new ArrayList<UserSearchModel>();
            try {
                JSONArray array=new JSONArray(result);
                Log.i("array=>",array.toString());
                if(array.length()>0){
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        if(obj.getString("relation").equalsIgnoreCase("Friend")){
                            UserSearchModel item = new UserSearchModel(obj.getString("id"), obj.getString("fullName"), obj.getString("relation"));
                            rowItems.add(item);
                        }else {
                            UserSearchModel item = new UserSearchModel(obj.getString("id"), obj.getString("fullName"), obj.getString("relation"));
                            rowItemsRel.add(item);
                        }


                    }
                }else{
                    Rl_listSuggest.setVisibility(View.VISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            BtnFriends.setTextColor(getResources().getColor(R.color.white));     BtnRelatives.setTextColor(getResources().getColor(R.color.black));
            TvFrndBtm.setVisibility(View.VISIBLE);       TvReltBtm.setVisibility(View.INVISIBLE);
            adapter = new UserSearchAdapter(SearchUserActivity.this, rowItems);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            BtnFriends.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    BtnFriends.setBackgroundColor(getResources().getColor(R.color.white));         BtnRelatives.setBackgroundColor(getResources().getColor(R.color.white));
                    BtnFriends.setTextColor(getResources().getColor(R.color.white));         BtnRelatives.setTextColor(getResources().getColor(R.color.black));
                    TvFrndBtm.setVisibility(View.VISIBLE);       TvReltBtm.setVisibility(View.INVISIBLE);
                    adapter = new UserSearchAdapter(SearchUserActivity.this, rowItems);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            BtnRelatives.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    BtnFriends.setBackgroundColor(getResources().getColor(R.color.white));        BtnRelatives.setBackgroundColor(getResources().getColor(R.color.white));
                    BtnFriends.setTextColor(getResources().getColor(R.color.black));                  BtnRelatives.setTextColor(getResources().getColor(R.color.white));
                    TvReltBtm.setVisibility(View.VISIBLE);        TvFrndBtm.setVisibility(View.INVISIBLE);
                    adapter = new UserSearchAdapter(SearchUserActivity.this, rowItemsRel);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });

            if (progressDialog.isShowing()){progressDialog.dismiss();}


        }//onPostExecute
    }//AsuncTask class

    public class AddFRcAsyncTask extends AsyncTask<String, String, String> {

        private JSONArray array;
        private String[] PidsName,fullName;
//        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
//            pDialog=ProgressDialog.show(getActivity(),"Please wait...","Loading");
        }

        @Override
        protected String doInBackground(String... strings) {//Specilizatiion o/p is in list ie Pending here
            String content = AsyncTaskHelper.makeServiceCall(""+ ApisHelper.searchUsers, "GET", null);
//            String content = AsyncTaskHelper.makeServiceCall(""+ ApisHelper.searchUsers+"?term=Poo", "GET", null);
            Log.i("Content-->",content);
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
//            if(pDialog.isShowing()){pDialog.dismiss();}
            super.onPostExecute(s);

            ArrayList<String> scripts = new ArrayList<String>();
            try {
                array=new JSONArray(s);
                Log.i("array:=>",array.toString());
                fullName = new String[array.length()];
                PidsName= new String[array.length()];
//                 fullName=new ArrayList<String>();
//                PidsName=new ArrayList<String>();
                if(array.length()>0 )
                {
                    for (int i = 0; i < array.length(); i++){
                        JSONObject friendsObj = array.getJSONObject(i);
                        fullName[i]=(friendsObj.getString("fullName"));
                        PidsName[i]=(friendsObj.getString("id"));
                        scripts.add(fullName[i]);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchUserActivity.this, R.layout.spinner_dropdown, R.id.text1, scripts);
              lvfriendslist.setAdapter(arrayAdapter);
                    searchAutoComplete.setAdapter(arrayAdapter);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

           searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    String SelfullName=parent.getItemAtPosition(position).toString();

                    for(int j = 0; j <array.length(); j++){
                        if(SelfullName.equalsIgnoreCase(fullName[j])){
                            Log.i("Pids",PidsName[j]);

                            Intent in =new Intent(SearchUserActivity.this,UserProfileActivity.class);
                            in.putExtra("userProfile",PidsName[j]);
                            startActivity(in);

//                            getFragmentManager().beginTransaction().replace(R.id.nav_main_replace,  PProfileDetails.newInstance(PidsName[j])).commit();
                        }
                    }

                }

            });


        }
    }//AddEduAsyncTask

}
