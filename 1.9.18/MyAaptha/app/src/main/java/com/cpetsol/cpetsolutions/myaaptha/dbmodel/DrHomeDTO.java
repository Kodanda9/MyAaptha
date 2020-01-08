package com.cpetsol.cpetsolutions.myaaptha.dbmodel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Admin on 7/24/2018.
 */
@DatabaseTable(tableName = DrHomeDTO.TABLE_NAME_GROUP)
public class DrHomeDTO implements Serializable {
    private static String TAG = DrHomeDTO.class.getCanonicalName();
    public DrHomeDTO() {
    }
    public static final String TABLE_NAME_GROUP = "doctorsdatagroup";
    public static final String FIELD_NAME_GROUPID     = "group_id";
    public static final String FIELD_NAME_dOCTORID = "doctorId";
    public static final String FIELD_NAME_NAME  = "name";
    public static final String FIELD_NAME_QULIFICATION =  "qulification" ;
    public static final String FIELD_NAME_EXPERIENCE  = "experience";
    public static final String FIELD_NAME_LOCALITIES =  "localities" ;
    public static final String FIELD_NAME_SPECILIZATION =  "specilization";
    public static final String FIELD_NAME_HOSPITALNAME =  "hospitalName";
    public static final String FIELD_NAME_CLINICNAME  =   "clinicName";
    public static final String FIELD_NAME_PRICE =   "price";
    public static final String FIELD_NAME_GENDER  =   "gender";
    public static final String FIELD_NAME_SUNDAY  =   "sunday";
    public static final String FIELD_NAME_MONDAY   =  "monday" ;
    public static final String FIELD_NAME_TUESDAY   =  "tuesday" ;
    public static final String FIELD_NAME_WEDNESDAY   =  "wednesday";
    public static final String FIELD_NAME_THURSDAY   = "thursday";
    public static final String FIELD_NAME_FRIDAY  ="friday";
    public static final String FIELD_NAME_SATURDAY  = "saturday";
    public static final String FIELD_NAME_HOSPITALID=   "hospitalId";
    public static final String FIELD_NAME_PROFILEID  =   "profileId";

    @DatabaseField(columnName = FIELD_NAME_GROUPID, generatedId = true)
    private int grPpd ;
    @DatabaseField(columnName = FIELD_NAME_dOCTORID)
    String doctorId;
    @DatabaseField(columnName = FIELD_NAME_NAME)
    String name;
    @DatabaseField(columnName = FIELD_NAME_QULIFICATION)
    String qulification;
    @DatabaseField(columnName = FIELD_NAME_EXPERIENCE)
    String experience;
    @DatabaseField(columnName = FIELD_NAME_LOCALITIES)
    String localities;
    @DatabaseField(columnName = FIELD_NAME_SPECILIZATION)
    String specilization;
    @DatabaseField(columnName = FIELD_NAME_HOSPITALNAME)
    String hospitalName;
    @DatabaseField(columnName = FIELD_NAME_CLINICNAME)
    String clinicName;
    @DatabaseField(columnName = FIELD_NAME_PRICE)
    String price;

    @DatabaseField(columnName = FIELD_NAME_GENDER)
    String gender;
    @DatabaseField(columnName = FIELD_NAME_SUNDAY)
    String sunday;
    @DatabaseField(columnName = FIELD_NAME_MONDAY)
    String monday;
    @DatabaseField(columnName = FIELD_NAME_TUESDAY)
    String tuesday;
    @DatabaseField(columnName = FIELD_NAME_WEDNESDAY)
    String wednesday;
    @DatabaseField(columnName = FIELD_NAME_THURSDAY)
    String thursday;
    @DatabaseField(columnName = FIELD_NAME_FRIDAY)
    String friday;
    @DatabaseField(columnName = FIELD_NAME_SATURDAY)
    String saturday;
    @DatabaseField(columnName = FIELD_NAME_HOSPITALID)
    String hospitalId;
    @DatabaseField(columnName = FIELD_NAME_PROFILEID)
    String profileId;

    public static void setTAG(String TAG) {
        DrHomeDTO.TAG = TAG;
    }

    public static String getTAG() {
        return TAG;
    }

    public void setGrPpd(int grPpd) {
        this.grPpd = grPpd;
    }


    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQulification(String qulification) {
        this.qulification = qulification;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setLocalities(String localities) {
        this.localities = localities;
    }

    public void setSpecilization(String specilization) {
        this.specilization = specilization;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public static String getFieldNameProfileid() {
        return FIELD_NAME_PROFILEID;
    }

    public static String getTableNameGroup() {
        return TABLE_NAME_GROUP;
    }

    public static String getFieldNameGroupid() {
        return FIELD_NAME_GROUPID;
    }

    public static String getFieldNamedoctorid() {
        return FIELD_NAME_dOCTORID;
    }

    public static String getFieldNameName() {
        return FIELD_NAME_NAME;
    }

    public static String getFieldNameQulification() {
        return FIELD_NAME_QULIFICATION;
    }

    public static String getFieldNameExperience() {
        return FIELD_NAME_EXPERIENCE;
    }

    public static String getFieldNameLocalities() {
        return FIELD_NAME_LOCALITIES;
    }

    public static String getFieldNameSpecilization() {
        return FIELD_NAME_SPECILIZATION;
    }

    public static String getFieldNameHospitalname() {
        return FIELD_NAME_HOSPITALNAME;
    }

    public static String getFieldNameClinicname() {
        return FIELD_NAME_CLINICNAME;
    }

    public static String getFieldNameGender() {
        return FIELD_NAME_GENDER;
    }

    public static String getFieldNamePrice() {
        return FIELD_NAME_PRICE;
    }

    public static String getFieldNameSunday() {
        return FIELD_NAME_SUNDAY;
    }

    public static String getFieldNameMonday() {
        return FIELD_NAME_MONDAY;
    }

    public static String getFieldNameTuesday() {
        return FIELD_NAME_TUESDAY;
    }

    public static String getFieldNameWednesday() {
        return FIELD_NAME_WEDNESDAY;
    }

    public static String getFieldNameThursday() {
        return FIELD_NAME_THURSDAY;
    }

    public static String getFieldNameSaturday() {
        return FIELD_NAME_SATURDAY;
    }

    public static String getFieldNameFriday() {
        return FIELD_NAME_FRIDAY;
    }

    public static String getFieldNameHospitalid() {
        return FIELD_NAME_HOSPITALID;
    }
}
