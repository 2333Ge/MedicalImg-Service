package mysql;


public class ConstantNameInSql {
	
	//table名
	public final static String TABLE_AID_CASE = "aid_case";
	public final static String TABLE_AID_RECORD = "aid_record";
	public final static String TABLE_AID_RELEASE = "aid_release";
	public final static String TABLE_AID_TREAT = "aid_treat";
	public final static String TABLE_EMPLOYEE_INFO = "employee_info";
	public final static String TABLE_USER_INFO = "user_info";
	
	//公有属性
	public final static String ID = "id";
	public final static String GMT_CREATE = "gmt_create";
	public final static String GMT_MODIFIED = "gmt_modified";
	//各表私有属性
	//普通用户信息
	public final static String USER_INFO_NAME = "name";
	public final static String USER_INFO_NICKNAME = "nickname";
	public final static String USER_INFO_HEAD_IMG = "head_img";
	public final static String USER_INFO_PHONE = "phone";
	public final static String USER_INFO_EMAIL = "email";
	public final static String USER_INFO_PASSWORD = "password";
	public final static String USER_INFO_SEX = "sex";//0男1女
	public final static String USER_INFO_BIRTHDAY = "birthday";
	//医务人员信息
	public final static String EMPLOYEE_INFO_BASIC_INFO_ID = "basic_info_id";
	public final static String EMPLOYEE_INFO_EMPLOYEE_ID = "employee_id";//工号
	public final static String EMPLOYEE_INFO_DEPARTMENT = "department";// 科
	public final static String EMPLOYEE_INFO_ROOM = "room";//室
	public final static String EMPLOYEE_INFO_HOSPITAL_ID = "hospital_id";
	// 病例
	public final static String AID_CASE_AID_RECORD_ID = "aid_record_id";
	public final static String AID_CASE_AID_RELEASE_ID = "aid_release_id";
	public final static String AID_CASE_AID_TREAT_ID = "aid_treat_id";
	//病单（医院）
	public final static String AID_RECORD_SEX = "sex";
	public final static String AID_RECORD_AGE = "age";
	public final static String AID_RECORD_IMG_LIST = "img_list";
	public final static String AID_RECORD_CHIEF_COMPLAINT = "chief_complaint";//主诉
	public final static String AID_RECORD_CLINICAL_MANIFESTATION = "clinical_manifestation";//临床表现
	public final static String AID_RECORD_IMAGEING_FEATURE = "imageing_feature";//影像表现
	//病单（发布的，给指定医生）
	public final static String AID_RELEASE_HANDLE_EMPLOYEE_ID = "handle_employee_id";
	public final static String AID_RELEASE_STATE = "state";
	public final static String AID_RELEASE_AID_RECORD_ID = "aid_record_id";
	public final static String AID_RELEASE_UPLOAD_EMPLOYEE_ID = "upload_employee_id";
	//病单处理
	public final static String AID_TREAT_AID_RELEASE_ID = "aid_release_id";
	public final static String AID_TREAT_CLINICAL_DIAGNOSIS = "clinical_diagnosis";//临床诊断
	public final static String AID_TREAT_ANALYSIS = "analysis";//本例分析
	public final static String AID_TREAT_AID_SUMMARY = "aid_summary";//病例小结
	public final static String AID_TREAT_HANDLER_ID = "handler_id";//处理人id
	public final static String AID_TREAT_REVIEWER_ID = "reviewer_id";//复审人id
	
	public final static int BOY_INT_IN_MYSQL = 0;
	public final static int GIRL_INT_IN_MYSQL = 1;
	
	
}
