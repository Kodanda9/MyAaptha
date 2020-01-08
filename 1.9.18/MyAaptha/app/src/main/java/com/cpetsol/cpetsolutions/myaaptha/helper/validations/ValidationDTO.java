package com.cpetsol.cpetsolutions.myaaptha.helper.validations;

import java.io.Serializable;

/**
 * Created by sai on 14-08-2017.
 */

public class ValidationDTO implements Serializable {

    private String componentType;// UI Component Name like Spinner, EditText etc.
    private int componentID; // layout resource id
    private String validationType = ValidationHelper.VALITAION_TYPE_NOTEMPTY; // Validation Type like [Equal, Not Equal etc.]
    private String errorMessage;// Error Message to be displayed for validation
    private String values; //Data to be autopopulated for Operation [SetDateForView]
    private String operType; // type of operation like validation/ClearView/SetDataForView

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public int getComponentID() {
        return componentID;
    }

    public void setComponentID(int componentID) {
        this.componentID = componentID;
    }

    public String getValidationType() {
        return validationType;
    }

    public void setValidationType(String validationType) {
        this.validationType = validationType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
}
