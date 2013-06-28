package com.jof.common;

import java.util.ArrayList;
import java.util.List;

public class JofConstant {
	public static final String USER_SESSION_KEY = "user_session_key";
	public static final String SYSTEM_USER="system";
	public static final String SEMICOLON = ";";
	
	public static final String sClASS_A_KEY = "A";

	public static final String sClASS_B_KEY = "B";

	public static final String sClASS_C_KEY = "C";

	public static final char sCLASS_A = 'A';
	public static final char sCLASS_B = 'B';
	public static final char sCLASS_C = 'C';
	public static final char sCLASS_N = 'N';

	public static char ACTIVE_FLAG_Y = 'Y';

	public static char ACTIVE_FLAG_N = 'N';

	public static String sACTIVE_FLAG_NO = "N";
	public static String sACTIVE_FLAG_YES = "Y";

	public static final String CAR_PLANT_FLG = "0";
	public static final String sFLAG_Y = "Y";
	public static final String sFLAG_N = "N";

	public static final String AGGR_COMP_PLANT_FLG = "1";

	/** MOUDULE_FLG */
	public static final String MOUDULE_FLG = "MODULE_";

	public static final String ITEM_FLG = "ITEM";

	public static final String SRS_RATING_PLANTTYPE_CAR = "car";

	public static final String SRS_RATING_PLANTTYPE_AGGR_COMP = "Aggr/Comp";

	/** COMMA */
	public static final String STRING_COMMA = ",";

	/** .zip */
	public static final String ZIP_TAG = ".zip";

	public static final String CURRENT_USER = "currentuser";

	// public static final String UPLOAD_TABS_FLG_0 = "0";

	// public static final String UPLOAD_TABS_FLG_1 = "1";

	public static final String ALL_STR = "All Suppliers";

	public static final boolean BOOLEAN_TRUE = true;

	public static final boolean BOOLEAN_FALSE = false;

	public static final String FAW_VW_STR = "FAW-VW";

	public static final String JVL_NOMAL_USER = "JVL normal user";

	public static final String JVL_KEY_USER = "JVL key user";

	public static final String JVG_NOMAL_USER = "JVG normal user";

	public static final String JVG_KEY_USER = "JVG key user";

	public static final String VGC_NOMAL_USER = "VGC normal user";

	public static final String VGC_KEY_USER = "VGC key user";

	public static final String SUPER_USER = "Super user";

	public static final String[] ORIGINAL_ROLES = { JVL_NOMAL_USER,
			JVL_KEY_USER, JVG_NOMAL_USER, JVG_KEY_USER, VGC_NOMAL_USER,
			VGC_KEY_USER, SUPER_USER };

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String BLANK = "";

	public static final String sSRS_RATING_STATE_INITIALIZED = "Initialized";

	public static final String sSRS_RATING_STATE_INPROGRESS = "InProgress";
	public static final String sSRS_RATING_STATE_COMPLETED = "Completed";
	public static final String sSRS_RATING_STATE_EXPIRED = "Expired";

	public static final String sSRS_RATING_JOB_USERNAME = "1";

	public static final String sSRS_RATING_FAW_VW = "FAW_VW";

	public static final String COMMA_DELIMITER = ",";

	// public static final char CHAR_Y = 'Y';

	public static final String ALL_SUPPLER_STR = "All";

	public static final int EXCEL_CELL_FIRST_COLUMN = 0;

	public static final int EXCEL_CELL_SECOND_COLUMN = 1;

	public static final int EXCEL_CELL_THIRD_COLUMN = 2;

	public static final int EXCEL_SHEET_ONE = 1;

	public static final long UPLOAD_FILE_SIZE = 1024 * 1024;

	public static final boolean NEED_UPDATE_DB = true;

	public static final String sMAIL_C_CLASS_ONCE = "Once";
	public static final String sMAIL_C_CLASS_TWICE = "Twice";
	public static final String sMAIL_C_CLASS_THREETIMES = "Three Times";
	public static final String sMAIL_B_CLASS_ONCE = "class_b_mail";
	public static final String sMAIL_ANNUAL_REPORT = "Annual Report";

	public static final String sPRIVILEGETYPE_ALL = "ALL";
	public static final String sPRIVILEGETYPE_JVL = "JVL";
	public static final String sPRIVILEGETYPE_JVG = "JVG";
	// PERMISSION DELEGATE
	// public static final String sDELEGATE_SUPER_VGC ="SUPER_VGC";
	public static final String sDELEGATE_SUPER_JVL = "SUPER_JVL";
	public static final String sDELEGATE_SUPER_JVG = "SUPER_JVG";

	// public static final String sDELEGATE_GROUP_VGC ="GROUP_VGC";
	public static final String sDELEGATE_GROUP_JVL = "GROUP_JVL";
	public static final String sDELEGATE_GROUP_JVG = "GROUP_JVG";

	public static final String sDELEGATE_NORMAL = "DELEGATE_NORMAL";

	public static final String sORGANIZATION_LEVEL_CODE_VGC = "VGC";
	public static final String sORGANIZATION_LEVEL_CODE_JVG = "JVG";
	public static final String sORGANIZATION_LEVEL_CODE_JVL = "JVL";

	public static final String sSESSION_ORGANIZATION_ID = "sSESSION_ORGANIZATION_ID";
	public static final String sSESSION_ACTION_NAME = "actionName";
	public static final String sSESSION_DELEGATE_RESULT = "delegateResult";

	public static final int AVAILABLE_TEMPLATE_ID = 1;

	public static final String DATE_FORMAT_YEAR_MONTH = "yyyy-MM";
	public static final String MESSAGE_NO_MATCHING = "no matching record found";
	public static final String FIELD_CLASS_LEVEL_LABEL = " class list";
	public static final String FIELD_CLASS_LEVEL_NAME = "classLevel";
	public static final String FIELD_PRE_SUPPLIER_LABEL = "Pre-series production";
	public static final String FIELD_PRE_SUPPLIER_NAME = "preSeriesFlag";
	public static final String FIELD_UNRATED_LABEL = "Unrated suppliers";
	public static final String FIELD_UNRATED_NAME = "stateCode";

	public static final char ORIGINAL_FLAG_Y = 'Y';
	public static final char ORIGINAL_FLAG_N = 'N';
	public static final String KEY_USER = "key user";
	
	public static final String SUPPLIER_OWENER_FAW="FAW";
	public static final String SUPPLIER_OWENER_SAIC="SAIC";
	
	public static final String SUPPLIER_CARMODEL_BRANDNAME = "Brand Name";
	public static final String SUPPLIER_CARMODEL_CARMODELNAME = "Car Model";	
	public static final String SUPPLIER_CARMODEL_PARTNAME = "Part Name";
	public static final String SUPPLIER_CARMODEL_PARTCODE = "Part Code";	
	



	public static final int DATE_TYPE_MONTH = 1;
	public static final int DATE_TYPE_QUARTER = 2;
	public static final float DEFAULT_SCORE = 100;
	public static final float DEFAULT_ZERO_SCORE = 0;
	public static final int DEFAULT_MIN_SCORE = 60;

	public static final String DOWNLOAD_TEMPLATE_BEANNM = "templateBean";
	public static final String DOWNLOAD_EXCEL_BEAN = "excelBean";

	public static final String DOWNLOAD_EXCEL_NAME = "_tobeRated";

	public static final String END_FLG = "END";

	/** templatePath */
	//public static final String sDOWNLOAD_PATH = SysConfig.getSysConfigProperties().getProperty("template.excel.path");

	public static final String sORIGINAL_TEMPLATE = "OriginalTemplate.xls";//"WEB-INF/classes/rating-template/OriginalTemplate.xls";

	public static final String sDOWNLOAD_TEMPLATE_PRE = "DownloadTemplate_";//"WEB-INF/classes/rating-template/DownloadTemplate_";
	
	public static final String sDOWNLOAD_TEMPLATE = "DownloadTemplate.xls";//"WEB-INF/classes/rating-template/DownloadTemplate.xls";
	
	public static final String sDOWNLOAD_TEMPLATE_REPORT = "OriginalTemplate_Report.xls";//"WEB-INF/classes/rating-template/OriginalTemplate_Report.xls";
	
	public static final String SUPPLIER_DATA_DOWNLOAD_EXCEL = "help/VGC_SRS_SUPPLIER_template.xls";
	
	public static final String sImgYes = "images/yes.png";
	
	public static final String sImgNo = "images/no.png";
	
	public static final String sImgCC = "images/cc.png";
	
	
	
	/** tempPath */
	public static final String TEMP_PATH = "temp";
	

	/** excle */
	public static final String EXCEL_FILE = ".xls";

	public static final int START_ROW = 10;

	public static final int START_COL = 13;

	public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	public static final String sSERVLETCONTEXTREALPATH = "ServletContextRealPath";

	public static final String sVALID_EXCEL = "VALID_RSR_RATING_EXCEL";
	
	public static final String sVALID_UPLOAD_SUPPLIER_EXCEL = "SUPPLIERS INFORMATION EXCEL";

	public static final String sEXCEL_SHEET1 = "Sheet1";

	public static final String sEXCEL_SHEET2 = "Sheet2";
	
	public static final String sEXCEL_SUPPLIERS = "suppliers";

	public static final String sNA = "NA";

	public static final String sHYPER = "-";

	public static final String sTRUE = "TRUE";

	public static final String sEXCEL_DONE = "DONE";
	
	
	
	
	/** EXCEL REPORT CONSTANT */
	
	public static final String sMONTH = "Month";
	
	public static final String sQUARTER = "Quarter";
	
	public static final String sEXCEL_REPORT_SHEET_LINECHART = "LineChartData";
	
	public static final String sEXCEL_REPORT_SHEET_PIECHART = "PieChartData";
	
	public static final String sEXCEL_REPORT_SHEET_DATATABLE = "TableData";
	
	public static final int EXCEL_REPORT_START_ROW = 10;
	
	public static final String sEXCEL_REPORT_RATING = "Rating";
	
	public static final String sEXCEL_REPORT_GRADE = "Grade";
	
	public static final String sEXCEL_REPORT_RANK = "Rank";
	

	public static final int REPORT_CALCULATION_TYPE_SUPPLIER = 1;
	
	public static final int REPORT_CALCULATION_TYPE_ORGINAZATION = 2;
	
	public static final String DOWNLOAD_REPORT_NAME = "_ReportData";
	
	public static final String PACKAGE_OUT_SLT = "SLT";
	public static final String PACKAGE_OUT_KLT = "KLT";
	public static final String PACKAGE_OUT_GLT = "GLT";
	public static final String PACKAGE_IN_ONEWAY = "one way";
	public static final String PACKAGE_IN_RESUABLE = "reusable";
	
	public static final String SUPPLIER_STATUS_PRESERIES = "Preseries";
	//TODO: Normal status should be removed in Phase 2. Normal status should be replaced by Series status.
	public static final String SUPPLIER_STATUS_NORMAL = "Normal"; 
	public static final String SUPPLIER_STATUS_POTENTIAL = "Potential";
	public static final String SUPPLIER_STATUS_SERIES = "Series";
	public static final String SUPPLIER_STATUS_PRECERIES_SERIES = "Preseries_Series";
	
	public static final String SUPPLIER_EXCEL_LC = "LC";
	public static final String SUPPLIER_EXCEL_CKD = "CKD";
	
	//Supplier Column Name
//	public static final String EXCEL_COLUMN_SUPPLIER_NO = "Supplier No.";
//	public static final String EXCEL_COLUMN_DUNS_NO = "DUNS No.";
//	public static final String EXCEL_COLUMN_SUPPLIER_NAME_CN = "Supplier Chinese Name";
//	public static final String EXCEL_COLUMN_SUPPLIER_NAME_EN = "Supplier English Name";
//	public static final String EXCEL_COLUMN_SUPPLIER_ADDRESS = "ADDRESS";	

	
	//Supplier column's maximum length
	public static final int SUPPLIER_NO_MAXLENGTH = 32;
	public static final int DUNS_NO_MAXLENGTH = 32;
	public static final int SUPPLIER_NAME_CN_MAXLENGTH = 256;
	public static final int SUPPLIER_ADDRESS_MAXLENGTH = 512;
	public static final int SUPPLIER_TIMEZONE_MAXLENGTH = 32;
	public static final int SUPPLIER_POBOX_ADDRESS_MAXLENGTH = 512;
	public static final int SUPPLIER_COUNTRY_MAXLENGTH = 64;
	public static final int SUPPLIER_PROVINCE_MAXLENGTH =64;
	public static final int SUPPLIER_CITY_MAXLENGTH = 64;
	public static final int SUPPLIER_WEBSITE_MAXLENGTH = 256;
	public static final int SUPPLIER_ZIP_CODE_MAXLENGTH = 32;
	public static final int SUPPLIER_OWNER_MAXLENGTH = 32;
	public static final int SUPPLIER_REMARKS_MAXLENGTH = 512;
	public static final int SUPPLIER_BIG_SIZE_FLAG_MAXLENGTH = 1;
	public static final int SUPPLIER_STATUS_MAXLENGTH = 32;
	
	//Contact Column Name
	public static final int CONTACT_NAME_MAXLENGTH = 32;
	public static final int CONTACT_LANG_MAXLENGTH = 32;
	public static final int CONTACT_PHONE_MAXLENGTH = 32;
	public static final int CONTACT_MOBILE_PHONE_MAXLENGTH = 64;
	public static final int CONTACT_FAX_MAXLENGTH = 32;
	public static final int CONTACT_EMAIL_MAXLENGTH = 64;

	//EXPORT Supplier INFO
	public static final String EXPORT_SUPPLIER_INFO_TEMP_PATH = "supplierTemp";
	public static final String EXPORT_SUPPLIER_INFO_FILE_NAME = "VGC_SRS_SUPPLIER_";
	
	public static final String SPACE_DELIMITER =" ";
	
	public static final String EXPORT_TEMPLATE_TEMP_PATH = "templateTemp";
	public static final String EXPORT_TEMPLATE_FILE_NAME = "HistoryTemplate_";
	public static final String UNDERLINE_DELIMITER = "_";
	
	public static final String AUDIT_AUDITTABLE_SUPPLIER = "Supplier";
	public static final String AUDIT_AUDITTABLE_POTENTIALSUPPLIER = "PotentialSupplier";
	public static final String AUDIT_AUDITSUPPLIERID_SUPPLIER = "supplierNo";
	public static final String AUDIT_AUDITSUPPLIERID_POTENTIALSUPPLIER = "id";
	public static final String EXPORT_AUDIT_TEMP_PATH = "auditTemp";
	public static final String EXPORT_AUDIT_FILE_NAME = "AUDIT_";		
	public static final String AUDIT_FIRST_RADIO = "1";
	public static final String AUDIT_SECOND_RADIO = "2";
	public static final String AUDIT_CCLASS_RADIO = "3";
	public static final String AUDIT_NOT_AUDITED_RADIO = "4";


	public static final String AUDIT_LEVEL_A = "A";
	public static final String AUDIT_LEVEL_B = "B";
	public static final String AUDIT_LEVEL_C = "C";
	public static final String AUDIT_LEVEL_EMPTY = " ";
	
	
	public static char AUDITHISTORY_REMOVE_FLAG_Y = 'Y';
	public static char AUDITHISTORY_REMOVE_FLAG_N = 'N';
	
	public static final String SENDDATALOG_STATUS_Y = "0";
	public static final String SENDDATALOG_STATUS_N = "1";
	
	public static final String WEBSERVICE_DATA_CREATOR = "1";	
	public static final String WEBSERVICE_STUTAS_NEWSUPPLIERSUCCESS = "0";	
	public static final String WEBSERVICE_STUTAS_NEWSUPPLIERFAILED = "1";	
	public static final String WEBSERVICE_STUTAS_RATING = "rating";	
	public static final String WEBSERVICE_STUTAS_NORATING = "noRating";	
	public static final String WEBSERVICE_TYPE_KPIRATING = "1";	
	public static final String WEBSERVICE_TYPE_AUDITRATING = "0";	

	public static final String SUPPLIER_COUNTRY_INTERNAL = "China";
	public static final String SUPPLIER_COUNTRY_INTERNAL2 = "CHN";
	
	//The contact info is from star-monitor
	public static final char CONTACT_SOURCING_LOGISTICS = 'L';
	public static final char CONTACT_SOURCING_SRS = 'S';
    
	public static final String COLON_DELIMITER = ":";
	public static final String PERIOD_DELIMITER = ".";
	//Role
	public static final String EXPORT_ROLE_MANAGEMENT_FILE_NAME = "RoleList";
	public static final String EXPORT_ROLE_MANAGEMENT_TEMP_PATH = "roleTemp";
	public static final String EXPORT_ROLE_COLUMN_USERNAME = "User Name";
	public static final String EXPORT_ROLE_COLUMN_ORGANIZATION = "Organization";
	public static final String EXPORT_ROLE_COLUMN_ROLE = "Role";
	public static final String EXPORT_FILE_TYPE = "excel";
	public static final String EXPORT_PRIVILEGE_COLUMN_TITLE = "User Name / Privilege";
	
	//privilege
	public static final String EXPORT_PRIVILEGE_SHEET_NAME = "current privileges";
	
	
	public static List getClassLevelList(){
	    List<String> list =new ArrayList<String>();
	    list.add(sClASS_A_KEY);
	    list.add(sClASS_B_KEY);
	    list.add(sClASS_C_KEY);
	    return list;
	}
	
	
	public static List<String> getOutPackagingList(){
	    List<String> list =new ArrayList<String>();
	    list.add(PACKAGE_OUT_SLT);
	    list.add(PACKAGE_OUT_KLT);
	    list.add(PACKAGE_OUT_GLT);
	    return list;
	}	
	
	public static List<String> getInPackagingList(){
	    List<String> list =new ArrayList<String>();
	    list.add(PACKAGE_IN_ONEWAY);
	    list.add(PACKAGE_IN_RESUABLE);
	    return list;
	}	
	
	public static List<String> getSupplierOwnerList(){
	       	List<String> list =new ArrayList<String>();
	        list.add(SUPPLIER_OWENER_FAW);
	        list.add(SUPPLIER_OWENER_SAIC);
	        return list;
	}
	
	public static List<String> getSupplierLCCKDList(){
	       	List<String> list =new ArrayList<String>();
	        list.add(SUPPLIER_EXCEL_LC);
	        list.add(SUPPLIER_EXCEL_CKD);
	        return list;
	}	
}
