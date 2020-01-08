package com.cpetsol.cpetsolutions.myaaptha.helper.validations;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sai on 14-08-2017.
 */

public class ValidationHelper {

    private static final String TAG = ValidationHelper.class.getCanonicalName();
    private static ValidationHelper validationHelper;

    //Validation Type List
    public static final String COMPONENT_TYPE_SPINNER = "Spinner";
    public static final String COMPONENT_TYPE_TEXTVIEW = "TextView";
    public static final String COMPONENT_TYPE_EDITTEXT = "EditText";
    public static final String COMPONENT_TYPE_RADIO_BUTTON = "RadioButton";
    public static final String COMPONENT_TYPE_RADIO_GROUP = "RadioGroup";


    public static final String VALITAION_TYPE_NOT_EQUAL = "NOTEQUAL";
    public static final String VALITAION_TYPE_EQUAL = "EQUAL";
    public static final String VALITAION_TYPE_EMPTY = "EMPTY";
    public static final String VALITAION_TYPE_NOTEMPTY = "NOTEMPTY";
    public static final String VALITAION_TYPE_NOTSELECTED = "NOTSELECTED";
    public static final String VALITAION_TYPE_NOTCHECKED = "NOTCHECKED";

    public static final String OPERATION_TYPE_VALIDATION = "VALIDATION";
    public static final String OPERATION_TYPE_SETDATA_FORVIEW = "SETDATAFORVIEW";
    public static final String OPERATION_TYPE_CLEARVIEW = "CLEARVIEW";

    public static ValidationHelper getInstance() {
        if(validationHelper == null) {
            validationHelper = new ValidationHelper();
        }
        return  validationHelper;
    }

    //Validation Method
    boolean validateData = true;
    public boolean validateData(Context context , ArrayList<ValidationDTO> validationDTOs , View view){
        validateData = true;
        try{
           /* LayoutInflater inflater = LayoutInflater.from(context);
            View view= inflater.inflate(viewID, null, false);*/
            for(final ValidationDTO validationDTO : validationDTOs){

                //if(validationDTO.getOperType().equals(OPERATION_TYPE_VALIDATION)){


                    //Validating Spinner and Returns Error-Message
                    if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_SPINNER)){
                        Spinner spinner1 = (Spinner)view.findViewById(validationDTO.getComponentID());
                        TextView validateDataText;
                        if(validationDTO.getValidationType().equalsIgnoreCase(VALITAION_TYPE_NOTEMPTY)){
                            if(spinner1.getSelectedItemPosition() < 1){
//                            if(spinner1 != null) {
//                            if(spinner1 != null && spinner1.getSelectedItem() != null && spinner1.getSelectedItem().toString() == null || spinner1.getSelectedItem().toString().equals("")){
//                                spinner1.setSelection(0);
                                validateDataText = (TextView)spinner1.getSelectedView();
                                validateDataText.setError(validationDTO.getErrorMessage());
                                validateData = false;

                            }
                            //}
                        }
                    }


                    //Validating TextView and Retuns Errror-Message
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_TEXTVIEW)){
                        TextView textView = (TextView)view.findViewById(validationDTO.getComponentID());
                        if(validationDTO.getValidationType().equalsIgnoreCase(VALITAION_TYPE_NOTEMPTY)){
                            if(textView != null && textView.getText() != null && textView.getText().toString() == null || textView.getText().toString().equals("")){
                                textView.setError(validationDTO.getErrorMessage());
                                validateData = false;
                            }
                        }
                    }

                    //Validating EditText and Retuns Errror-Message
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_EDITTEXT)){
                        EditText editText = (EditText) view.findViewById(validationDTO.getComponentID());
                        if(validationDTO.getValidationType().equalsIgnoreCase(VALITAION_TYPE_NOTEMPTY)){
                            if(editText!= null && editText.getText()!= null && editText.getText().toString() == null || editText.getText().toString().equals("")){
                                editText.setError(validationDTO.getErrorMessage());
                                validateData = false;
                            }
                        }
                    }

                    //Validating Radio Button and Retuns Errror-Message
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_RADIO_BUTTON)){
                        RadioButton radioButton = (RadioButton) view.findViewById(validationDTO.getComponentID());
                        if(validationDTO.getValidationType().equalsIgnoreCase(VALITAION_TYPE_NOTEMPTY)){
                            if(!radioButton.isChecked()){
                                radioButton.setError(validationDTO.getErrorMessage());
                                validateData = false;
                            }
                        }
                    }

                    //Validating Radio Group and Retuns Errror-Message
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_RADIO_GROUP)){
                        RadioGroup radioGroup = (RadioGroup) view.findViewById(validationDTO.getComponentID());
                        if(validationDTO.getValidationType().equalsIgnoreCase(VALITAION_TYPE_NOTEMPTY)){
                            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
                                    if(!radioButton.isChecked()){
                                        radioButton.setError(validationDTO.getErrorMessage());
                                        validateData = false;
                                    }
                                }
                            });
                        }
                    }
                }
            //}
        }catch (Exception e){
            e.printStackTrace();
        }
        return validateData;
    }


    public void setDataforView(Context context , ArrayList<ValidationDTO> validationDTOs , LayoutInflater inflater, int viewID){
        try{
            for(final ValidationDTO validationDTO : validationDTOs) {
                View view = inflater.inflate(viewID, null);
                int position = 0;
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_SPINNER)){
                    Spinner spinner1 = (Spinner)view.findViewById(validationDTO.getComponentID());
                    position = getSpinnerPosition(spinner1,validationDTO.getValues());
                    spinner1.setSelection(position);
                }
                //Set Data for TextView
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_TEXTVIEW)){
                    TextView textView = (TextView)view.findViewById(validationDTO.getComponentID());
                    textView.setText(validationDTO.getValues());
                }

                //Set Data for EditText
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_EDITTEXT)){
                    EditText editText = (EditText) view.findViewById(validationDTO.getComponentID());
                    editText.setText(validationDTO.getValues());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearAllViewsData(Context context , ArrayList<ValidationDTO> validationDTOs , LayoutInflater inflater, int viewID){
        try{
            for(final ValidationDTO validationDTO : validationDTOs) {
                View view = inflater.inflate(viewID, null);
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_SPINNER)){
                    Spinner spinner1 = (Spinner)view.findViewById(validationDTO.getComponentID());
                    spinner1.setSelection(0);
                }


                //CLear TextView Data
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_TEXTVIEW)){
                    TextView textView = (TextView)view.findViewById(validationDTO.getComponentID());
                    textView.setText("");
                }

                //Clear EditText Data
                if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_EDITTEXT)){
                    EditText editText = (EditText) view.findViewById(validationDTO.getComponentID());
                    editText.setText("");
                }

                //Clear Radio Button Data
               if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_RADIO_BUTTON)){
                    RadioButton radioButton = (RadioButton) view.findViewById(validationDTO.getComponentID());
                    radioButton.setChecked(false);
                }

                //CLear Radio Group Data
               if(validationDTO.getComponentType() != null && validationDTO.getComponentType().equalsIgnoreCase(COMPONENT_TYPE_RADIO_GROUP)){
                    RadioGroup radioGroup = (RadioGroup) view.findViewById(validationDTO.getComponentID());
                    radioGroup.clearCheck();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getSpinnerPosition(Spinner spinner , String spinnerValue){
        int position = 0;
        try{
            if(spinnerValue != null && !spinnerValue.equals("")){
                position = ((ArrayAdapter)spinner.getAdapter()).getPosition(spinnerValue);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return position;
    }
}
