package com.cpetsol.cpetsolutions.myaaptha.dbmodel;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = UserDataDTO.TABLE_NAME_GROUP)
public class UserDataDTO  implements Serializable {

    private static String TAG = UserDataDTO.class.getCanonicalName();

    public UserDataDTO() {
        // Don't forget the empty constructor, needed by ORMLite.
    }

    public static final String TABLE_NAME_GROUP = "userdata_group";

    public static final String FIELD_NAME_GROUPID     = "group_id";
    public static final String FIELD_NAME_FILES  = "files";
    public static final String FIELD_NAME_ID  = "id";
    public static final String FIELD_NAME_FAMILYNAMES   = "familyNames";
    public static final String FIELD_NAME_NAMES   = "name";
    public static final String FIELD_NAME_DOCUMENTCATEGORY   = "documentCategory";
    public static final String FIELD_NAME_DETAILS   = "details";
    public static final String FIELD_NAME_DOCUMENTS   = "documents";
    public static final String FIELD_NAME_NUMBER   = "number";
    public static final String FIELD_NAME_DATE   = "date";
    public static final String FIELD_NAME_COMPANYNAME   = "companyName";
    public static final String FIELD_NAME_CATEGORY   = "category";
    public static final String FIELD_NAME_TITLE   = "title";
    public static final String FIELD_NAME_QUALIFICATION   = "qualification";
    public static final String FIELD_NAME_DOCTORNAME   = "doctorName";
    public static final String FIELD_NAME_SPECILIZATION   = "specailization";
    public static final String FIELD_NAME_VIEW   = "view";
    public static final String FIELD_NAME_FILENAME   = "fileName";
    public static final String FIELD_NAME_RELATION   = "relation";
    public static final String FIELD_NAME_GENDER   = "gender";
    public static final String FIELD_NAME_DNO   = "dno";
    public static final String FIELD_NAME_INSURERTYPE   = "insurerType";
    public static final String FIELD_NAME_INSURANCEID   = "insuranceId";
    public static final String FIELD_NAME_INSURANCEPROVIDER   = "insuranceProvider";
    public static final String FIELD_NAME_FAMILYNAMEID   = "familyNameId";
    public static final String FIELD_NAME_ENCRYPTEDKEY   = "encryptedKey";
    public static final String FIELD_NAME_DATECREATED  = "dateCreated";
    public static final String FIELD_NAME_VIEWSTATUS   = "viewStatus";


    @DatabaseField(columnName = FIELD_NAME_GROUPID, generatedId = true)
    private int grPpd ;
    @DatabaseField(columnName = FIELD_NAME_FILES)
    private String files;
    @DatabaseField(columnName = FIELD_NAME_ID)
    private String id;
    @DatabaseField(columnName = FIELD_NAME_FAMILYNAMES)
    private String familyNames;
    @DatabaseField(columnName = FIELD_NAME_NAMES)
    private String name;
    @DatabaseField(columnName = FIELD_NAME_DOCUMENTCATEGORY)
    private String documentCategory;
    @DatabaseField(columnName = FIELD_NAME_DETAILS)
    private String details;
    @DatabaseField(columnName = FIELD_NAME_DOCUMENTS)
    private String documents;
    @DatabaseField(columnName = FIELD_NAME_NUMBER)
    private String number;
    @DatabaseField(columnName = FIELD_NAME_DATE)
    private String date;
    @DatabaseField(columnName = FIELD_NAME_COMPANYNAME)
    private String companyName;
    @DatabaseField(columnName = FIELD_NAME_CATEGORY)
    private String category;
    @DatabaseField(columnName = FIELD_NAME_TITLE)
    private String title;
    @DatabaseField(columnName = FIELD_NAME_QUALIFICATION)
    private String qualification;
    @DatabaseField(columnName = FIELD_NAME_DOCTORNAME)
    private String doctorName;
    @DatabaseField(columnName = FIELD_NAME_SPECILIZATION)
    private String specailization;
    @DatabaseField(columnName = FIELD_NAME_VIEW)
    private String view;
    @DatabaseField(columnName = FIELD_NAME_FILENAME)
    private String fileName;
    @DatabaseField(columnName = FIELD_NAME_RELATION)
    private String relation;
    @DatabaseField(columnName = FIELD_NAME_GENDER)
    private String gender;
    @DatabaseField(columnName = FIELD_NAME_DNO)
    private String dno;
    @DatabaseField(columnName = FIELD_NAME_INSURERTYPE)
    private String insurerType;
    @DatabaseField(columnName = FIELD_NAME_INSURANCEID)
    private String insuranceId;
    @DatabaseField(columnName = FIELD_NAME_INSURANCEPROVIDER)
    private String insuranceProvider;
    @DatabaseField(columnName = FIELD_NAME_FAMILYNAMEID)
    private String familyNameId;
    @DatabaseField(columnName = FIELD_NAME_ENCRYPTEDKEY)
    private String encryptedKey;
    @DatabaseField(columnName = FIELD_NAME_DATECREATED)
    private String dateCreated;
    @DatabaseField(columnName = FIELD_NAME_VIEWSTATUS)
    private String viewStatus;


    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        UserDataDTO.TAG = TAG;
    }

    public static String getTableNameGroup() {
        return TABLE_NAME_GROUP;
    }

    public static String getFieldNameGroupid() {
        return FIELD_NAME_GROUPID;
    }

    public static String getFieldNameFiles() {
        return FIELD_NAME_FILES;
    }

    public static String getFieldNameId() {
        return FIELD_NAME_ID;
    }

    public static String getFieldNameFamilynames() {
        return FIELD_NAME_FAMILYNAMES;
    }

    public static String getFieldNameNames() {
        return FIELD_NAME_NAMES;
    }

    public static String getFieldNameDocumentcategory() {
        return FIELD_NAME_DOCUMENTCATEGORY;
    }

    public static String getFieldNameDetails() {
        return FIELD_NAME_DETAILS;
    }

    public static String getFieldNameDocuments() {
        return FIELD_NAME_DOCUMENTS;
    }

    public static String getFieldNameNumber() {
        return FIELD_NAME_NUMBER;
    }

    public static String getFieldNameDate() {
        return FIELD_NAME_DATE;
    }

    public static String getFieldNameCompanyname() {
        return FIELD_NAME_COMPANYNAME;
    }

    public static String getFieldNameCategory() {
        return FIELD_NAME_CATEGORY;
    }

    public static String getFieldNameTitle() {
        return FIELD_NAME_TITLE;
    }

    public static String getFieldNameQualification() {
        return FIELD_NAME_QUALIFICATION;
    }

    public static String getFieldNameDoctorname() {
        return FIELD_NAME_DOCTORNAME;
    }

    public static String getFieldNameSpecilization() {
        return FIELD_NAME_SPECILIZATION;
    }

    public static String getFieldNameView() {
        return FIELD_NAME_VIEW;
    }

    public static String getFieldNameFilename() {
        return FIELD_NAME_FILENAME;
    }

    public static String getFieldNameRelation() {
        return FIELD_NAME_RELATION;
    }

    public static String getFieldNameGender() {
        return FIELD_NAME_GENDER;
    }

    public static String getFieldNameDno() {
        return FIELD_NAME_DNO;
    }

    public static String getFieldNameInsurertype() {
        return FIELD_NAME_INSURERTYPE;
    }

    public static String getFieldNameInsuranceid() {
        return FIELD_NAME_INSURANCEID;
    }

    public static String getFieldNameInsuranceprovider() {
        return FIELD_NAME_INSURANCEPROVIDER;
    }

    public static String getFieldNameFamilynameid() {
        return FIELD_NAME_FAMILYNAMEID;
    }

    public static String getFieldNameEncryptedkey() {
        return FIELD_NAME_ENCRYPTEDKEY;
    }

    public static String getFieldNameDatecreated() {
        return FIELD_NAME_DATECREATED;
    }

    public static String getFieldNameViewstatus() {
        return FIELD_NAME_VIEWSTATUS;
    }

    public int getGrPpd() {
        return grPpd;
    }

    public void setGrPpd(int grPpd) {
        this.grPpd = grPpd;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFamilyNames() {
        return familyNames;
    }

    public void setFamilyNames(String familyNames) {
        this.familyNames = familyNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(String documentCategory) {
        this.documentCategory = documentCategory;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecailization() {
        return specailization;
    }

    public void setSpecailization(String specailization) {
        this.specailization = specailization;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDno() {
        return dno;
    }

    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getInsurerType() {
        return insurerType;
    }

    public void setInsurerType(String insurerType) {
        this.insurerType = insurerType;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getFamilyNameId() {
        return familyNameId;
    }

    public void setFamilyNameId(String familyNameId) {
        this.familyNameId = familyNameId;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(String viewStatus) {
        this.viewStatus = viewStatus;
    }
}
