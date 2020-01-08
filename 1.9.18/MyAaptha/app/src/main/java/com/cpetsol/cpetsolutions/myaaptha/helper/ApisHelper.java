package com.cpetsol.cpetsolutions.myaaptha.helper;

/**
 * Created by AILOGIC3 on 3/24/2018.
 */

public class ApisHelper {
   private static String domain="http://myaaptha.com";
// private static String domain="http://localhost:8080";
//   private static String domain="http://192.168.1.15:8080";

    public static String drLocations=""+domain+"/pp/doctorsData?locations=1&specializations=&localities=";
    public static String drlocalitiesSearch=""+domain+"/pp/rest/localitiesSearch";
    public static String specilizationSearch=""+domain+"/pp/rest/specializationSearch";
    public static String drdoctorsData=""+domain+"/pp/doctorsData2";
    public static String drLoadTimeslots=""+domain+"/pp/loadTimeslots";
    public static String doctor_store=""+domain+"/pp/rest/doctor_store";
    public static String labTestSearch=""+domain+"/pp/rest/labTestSearch?term=";
    public static String hospitalsData=""+domain+"/pp/rest/hospitalsData/";
    public static String saveHospitalsData=""+domain+"/pp/rest/saveHospitalsData/";
    public static String unHoldLabTestDetails=""+domain+"/pp/labrest/unHoldLabTestDetails/";
    public static String holdLabTestDetails=""+domain+"/pp/labrest/holdLabTestDetails/";
    public static String allLabTestInfo=""+domain+"/pp/ad/allLabTestInfo";
    public static String cancelAppointment=""+domain+"/pp/cancelAppointment?opId=";

    public static String familyNames=""+domain+"/pp/allFamilyNames";
    public static String profileHomeRest=""+domain+"/pp/userRest/profileHome";
    public static String forgotNextMyAaptha=""+domain+"/pp/forgotNext";
    public static String allCountryList=""+domain+"/pp/profile/allCountryList";
    public static String allStateList=""+domain+"/pp/profile/allStateList";
    public static String allDistrictList=""+domain+"/pp/profile/allDistrictList";
    public static String allSubDistrictList=""+domain+"/pp/profile/allSubDistrictList";
    public static String allVillageList=""+domain+"/pp/profile/allVillageList";

    public static String otp=""+domain+"/pp/userRest/otp";
    public static String checkotp=""+domain+"/pp/userRest/checkotp";
    public static String contactUs=""+domain+"/pp/userRest/contactUs";

    public static String articlesInDetailed=""+domain+"/pp/userRest/articlesInDetailed/";
    public static String forumInDetailed=""+domain+"/pp/userRest/forumInDetailed/";
    public static String forumInDetailedForLike=""+domain+"/pp/userRest/forumInDetailedForLike";
    public static String storeAnswers=""+domain+"/pp/userRest/storeAnswers";
    public static String allForum=""+domain+"/pp/userRest/forums";
    public static String storeForum=""+domain+"/pp/userRest/storeForum";

    public static String generateOtp_changeMobileNo=""+domain+"/pp/generateOtp_changeMobileNo";
    public static String checkOtp_changeMobileNo=""+domain+"/pp/checkOtp_changeMobileNo";
    public static String storeUserDataMyAaptha=""+domain+"/pp/storeUserData/";

    public static String changePasswordNext=""+domain+"/pp/changePasswordNext";
    public static String checkChangeOtp=""+domain+"/pp/checkChangeOtp";
    public static String loadRoles=""+domain+"/pp/userRest/loadRoles";
    public static String webPages=""+domain+"/pp/userRest/webPages";




    public static String loadQuantityByDosage=""+domain+"/pp/loadQuantityByDosage?drugName=";
    public static String drugNameSearch=""+domain+"/pp/rest/drugNameSearch?term=";
    public static String loadDrugTypesByDrugName=""+domain+"/pp/rest/loadDrugTypesByDrugName?drugName=";
    public static String loadQuantityByDos4age=""+domain+"/pp/loadQuantityByDosage?drugName=";
    public static String prescription_save=""+domain+"/pp/prescription_save";
    public static String checkforgototpMyAaptha=""+domain+"/pp/checkforgototp";
    public static String storeForgotPassword=""+domain+"/pp/storeForgotPassword";
//    public static String profileHomeRest=""+domain+"/pp/profileHomeRest";
//    public static String forgotNextMyAaptha=""+domain+"/pp/forgotNext";

 //   public static String fileuploadMyAaptha=""+domain+"/pp/fileupload/";
//    public static String storeUserDataMyAaptha=""+domain+"/pp/storeUserData/";
    public static String saveRegistration=""+domain+"/pp/saveRegistration";
    public static String docAppointments=""+domain+"/pp/rest/docAppointments";
    public static String pastAppointment=""+domain+"/pp/rest/pastAppointment";
    public static String userAppointments=""+domain+"/pp/userRest/userAppointments";
    public static String userPastAppointment=""+domain+"/pp/u/userPastAppointment";
  //  public static String allUserData=""+domain+"/pp/u/allUserData/";
  public static String deleteUserData=""+domain+"/pp/userRest/deleteUserData?id=";
  public static String medicalShopDetails=""+domain+"/pp/medicalRest/medicalShopDetails/";
    public static String storeUserData=""+domain+"/pp/storeUserData/";
    public static String appointment=""+domain+"/pp/rest/appointment?docId=";
    public static String doctorViewPage=""+domain+"/pp/rest/doctorViewPage?specialization=";
    public static String inBox=""+domain+"/pp/rest/inBox/";
    public static String pastAppointments=""+domain+"/pp/userRest/pastAppointments/";
    public static String patientPrescriptions=""+domain+"/pp/rest/patientPrescriptions?appId=";
    public static String allUsersDetailsById=""+domain+"/pp/userRest/allUsersDetailsById/";
    public static String updateUserData=""+domain+"/pp/userRest/updateUserData?id=";
    public static String saveUpdateUserData=""+domain+"/pp/userRest/saveUpdateUserData";
    public static String loadTimeslots=""+domain+"/pp/rest/loadTimeslots/";
    public static String approveHospital=""+domain+"/pp/rest/approveHospital?hospitalId=";
    public static String rejectHospital=""+domain+"/pp/rest/rejectHospital?hospitalId=";
 //   public static String locations=""+domain+"/pp/rest /locations";
    public static String updateHospitals=""+domain+"/pp/rest/updateHospitals";
    public static String confirmRequest=""+domain+"/pp/userRest/confirmRequest/";
    public static String deleteRequest=""+domain+"/pp/userRest/deleteRequest/";
    public static String deleteLabTestDetails=""+domain+"/pp/labrest/deleteLabTestDetails/";
 //   public static String listLabTests=""+domain+"pp/labrest/listLabTests";
    public static String updateLabTestDetails=""+domain+"/pp/labrest/updateLabTestDetails/";
    public static String approveMedicalShopDetails=""+domain+"/pp/medicalRest/approveMedicalShopDetails?id=";
    public static String rejectMedicalShopDetails=""+domain+"/pp/medicalRest/rejectMedicalShopDetails?id=";
    public static String updateMedicinesHall=""+domain+"/pp/medicalRest/updateMedicinesHall?id=";
    public static String storeMedicinesHall=""+domain+"/pp/medicalRest/storeMedicinesHall/";
    public static String deleteMedicinesHall=""+domain+"/pp/medicalRest/deleteMedicinesHall?id=";
    public static String allDosages=""+domain+"/pp/ad/allDosages/";
    public static String approvePathLabDetails=""+domain+"/pp/labrest/approvePathLabDetails?id=";
    public static String rejectPathLabDetails=""+domain+"/pp/labrest/rejectPathLabDetails?id=";
    public static String allSubCategories=""+domain+"/pp/ad/allSubCategories";
    public static String storeSubCategories=""+domain+"/pp/ad/storeSubCategories/";
    public static String storeCategories=""+domain+"/pp/ad/storeCategories/";
    public static String allCategories=""+domain+"/pp/ad/allCategories";
    public static String allUserData=""+domain+"/pp/allUserData/";
    public static String allUserData1=""+domain+"/pp/u/allUserData/";
    public static String allArticleNames=""+domain+"/pp/userRest/allArticleNames";
    public static String specializations=""+domain+"/pp/rest/specializations";
    public static String qualifications=""+domain+"/pp/rest/qualifications";
    public static String locations=""+domain+"/pp/rest/locations";
    public static String allDrugPriceList=""+domain+"/pp/medicalRest/allDrugPriceList/";
    public static String drugsList=""+domain+"/pp/medicalRest/drugsList";
    public static String drugPrice=""+domain+"/pp/medicalRest/drugPrice/allDosages/";
    public static String storeDrugPrice=""+domain+"/pp/medicalRest/storeDrugPrice/";
    public static String hospitalsApproval=""+domain+"/pp/rest/hospitalsApproval";
    public static String viewHospitals=""+domain+"/pp/rest/viewHospitals";
    public static String saveHospitals=""+domain+"/pp/rest/saveHospitals";
    public static String saveLabOwnerDetails=""+domain+"/pp/labrest/saveLabOwnerDetails/";
    public static String loadStates=""+domain+"/pp/medicalRest/loadStates";
    public static String allDistrictList1=""+domain+"/pp/medicalRest/medicalShop/allDistrictList/";
    public static String viewLabOwnerInfo=""+domain+"/pp/labrest/viewLabOwnerInfo/";
    public static String allLabTests=""+domain+"/pp/labrest/allLabTests/";
    public static String listLabTests=""+domain+"/pp/labrest/listLabTests";
    public static String saveTest=""+domain+"/pp/labrest/saveTest/";
    public static String viewAllMedicalShopDetails=""+domain+"/pp/medicalRest/viewAllMedicalShopDetails";
    public static String saveMedicalOwnerDetails=""+domain+"/pp/medicalRest/viewAllMedicalShopDetails";
    public static String allMedicinesHall=""+domain+"/pp/medicalRest/allMedicinesHall";
    public static String allMedicineStockDetails=""+domain+"/pp/medicalRest/allMedicineStockDetails/";
    public static String viewAllPathLabDetails=""+domain+"/pp/labrest/viewAllPathLabDetails";
    public static String labTestDetails=""+domain+"/pp/labrest/labTestDetails?pid=";
    public static String generateMedicinesBill=""+domain+"/pp/medicalRest/generateMedicinesBill/";
    public static String medicinesData=""+domain+"/pp/medicalRest/medicinesData?mobileOrName=";
    public static String allStateList1=""+domain+"/pp/profile/allStateList/1";
    public static String viewHospitalsData=""+domain+"/pp/rest/viewHospitalsData/";
    public static String employeeData=""+domain+"/pp/rest/employeeData/";
    public static String viewEmployeeData=""+domain+"/pp/viewEmployeeData/";
    public static String adddoctorsave=""+domain+"/pp/rest/add_doctor_save/";
    public static String updateDoctor=""+domain+"/pp/rest/updateDoctor/";
    public static String hospitalDoctors=""+domain+"/pp/rest/hospitalDoctors/";
    public static String doctors_Admin=""+domain+"/pp/rest/doctors_Admin";
    public static String viewHospitalOwner=""+domain+"/pp/rest/viewHospitalOwner";
    public static String allProfileDetails=""+domain+"/pp/rest/allProfileDetails?pid=";
    public static String saveEmployeeData=""+domain+"/pp/rest/saveEmployeeData/";










    public static String allFamilyNames=""+domain+"/pp/allFamilyNames/";
    public static String fileupload=""+domain+"/pp/fileupload/";
    public static String androidErrors=""+domain+"/pp/userRest/androidErrors";
    public static String friendsAndRelatives=""+domain+"/pp/userRest/friendsAndRelatives";
    public static String unFriend=""+domain+"/pp/userRest/unFriend";
    public static String searchUsers=""+domain+"/pp/userRest/searchUsers";
    public static String saveBookAppointment=""+domain+"/pp/rest/saveBookAppointment/";









}
