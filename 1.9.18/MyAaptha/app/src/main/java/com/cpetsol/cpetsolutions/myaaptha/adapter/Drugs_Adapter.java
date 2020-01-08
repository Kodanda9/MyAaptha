package com.cpetsol.cpetsolutions.myaaptha.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cpetsol.cpetsolutions.myaaptha.R;
import com.cpetsol.cpetsolutions.myaaptha.model.Drugs_Model;

import java.util.ArrayList;

/**
 * Created by Admin on 5/31/2018.
 */

public class Drugs_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private View localView;
    public Dialog mDialog;
    private Activity activity;
    ArrayList<Drugs_Model>data;
   // Spinner drug;
    TextView drug, dosage,quant,thrquant,price,discount;
    Button edit,delete,hold;
    public Drugs_Adapter(Activity activity, ArrayList<Drugs_Model> rowItems) {
        this.activity=activity;
        this.data=rowItems;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null)
            view = inflater.inflate(R.layout.drugs_lv_row, null);

     edit=(Button)view.findViewById(R.id.edit);
     delete=(Button)view.findViewById(R.id.delete);
       hold=(Button)view.findViewById(R.id.hold);

       drug=(TextView) view.findViewById(R.id.drugname_drug);
        dosage=(TextView)view.findViewById(R.id.dosage_drug);
        quant=(TextView)view.findViewById(R.id.quantity_drug);
        thrquant=(TextView)view.findViewById(R.id.thresholdquantity_drug);
        price=(TextView)view.findViewById(R.id.price_drug);
        discount=(TextView)view.findViewById(R.id.discout_drug);

        final Drugs_Model drugs=data.get(i);

       // drug.setSelection(AppHelper.setValueToSpinner(drug,drugs.getDrugName()));
        drug.setText(drugs.getDrugName());
        //  sDoc.setSelection(AppHelper.setValueToSpinner(sDoc,model.getDocuments()));
        dosage.setText(drugs.getDosage());
        quant.setText(drugs.getQuantity());
       thrquant.setText(drugs.getThresholdQuantity());
        price.setText(drugs.getPrice());
        discount.setText(drugs.getDiscount());
        edit.setOnClickListener(new View.OnClickListener() {




            @Override
    public void onClick(View view) {
                EditForm();

    }
});

        return view;
    }

    private void EditForm() {

        localView = View.inflate(activity, R.layout.medicine_update_form, null);
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

     Spinner drug=(Spinner)this.mDialog.findViewById(R.id.drugname_drug);
   // EditText  dosagedrug=(EditText)this.mDialog.findViewById(R.id.dosage_drug);
     EditText   price=(EditText)this.mDialog.findViewById(R.id.price_drug);
     EditText   quantity=(EditText)this.mDialog.findViewById(R.id.quantity_drug);
     EditText   thresholdquant=(EditText)this.mDialog.findViewById(R.id.thresholdquantity_drug);
    EditText    discount=(EditText)this.mDialog.findViewById(R.id.discout_drug);
    Button    subm=(Button)this.mDialog.findViewById(R.id.drug_submit);
        subm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
