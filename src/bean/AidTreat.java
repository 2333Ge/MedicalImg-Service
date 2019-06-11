package bean;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;
/**
 * 病单诊断实体类
 * @author 2413324637
 *
 */
public class AidTreat extends BasicInfo {
	private String aidReleaseId;
	private String clinicalDiagnosis;//临床诊断
	private String analysis;
	private String aidSummary;
	private String handlerId;
	private String reviewerId;
	
	
	@Override
    public JSONObject toJsonObjectExceptNull() {
    	JSONObject jsonObject = toJsonObjectExceptNullWithoutSuperClass();
    	jsonObject.put(ConstantKeyInJson.BEAN_BASIC_INFO,super.toJsonObjectExceptNull());
    	return jsonObject;
    }
    
   
    @Override
    public JSONObject toJsonObjectExceptNullWithoutSuperClass() {
    	JSONObject jsonObject = new JSONObject();
    	if(aidReleaseId != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_AID_RELEASE_ID, aidReleaseId);
    	if(clinicalDiagnosis != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_CLINICAL_DIAGNOSIS, clinicalDiagnosis);
    	if(analysis != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_ANALYSIS, analysis);
    	if(aidSummary != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_AID_SUMMARY, aidSummary);
    	if(handlerId != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_HANDLER_ID, handlerId);
    	if(reviewerId != null) jsonObject.put(ConstantKeyInJson.AID_TREAT_REVIEWER_ID, reviewerId);
    	return jsonObject;
    }
    @Override
	public void transJsonToJavaBean(JSONObject json) {
    	if(json == null) return;
		JSONObject basicJson = json.getJSONObject(ConstantKeyInJson.BEAN_BASIC_INFO);
		super.transJsonToJavaBean(basicJson);
		this.aidReleaseId = json.getString(ConstantKeyInJson.AID_TREAT_AID_RELEASE_ID);
		this.clinicalDiagnosis = json.getString(ConstantKeyInJson.AID_TREAT_CLINICAL_DIAGNOSIS);
		this.analysis = json.getString(ConstantKeyInJson.AID_TREAT_ANALYSIS);
		this.aidSummary = json.getString(ConstantKeyInJson.AID_TREAT_AID_SUMMARY);
		this.handlerId = json.getString(ConstantKeyInJson.AID_TREAT_HANDLER_ID);
		this.reviewerId = json.getString(ConstantKeyInJson.AID_TREAT_REVIEWER_ID);
	}
    @Override
    public void transSqlToJavaBean(ResultSet set) throws Exception {
    	
    	try {
    		this.aidReleaseId = set.getString(ConstantNameInSql.AID_TREAT_AID_RELEASE_ID);
    		this.clinicalDiagnosis = set.getString(ConstantNameInSql.AID_TREAT_CLINICAL_DIAGNOSIS);
    		this.analysis = set.getString(ConstantNameInSql.AID_TREAT_ANALYSIS);
    		this.aidSummary = set.getString(ConstantNameInSql.AID_TREAT_AID_SUMMARY);
    		this.handlerId = set.getString(ConstantNameInSql.AID_TREAT_HANDLER_ID);
    		this.reviewerId = set.getString(ConstantNameInSql.AID_TREAT_REVIEWER_ID);}
    	catch(Exception e){
			throw e;
		}
    }
	@Override
	public String toSqlString() {
		return new StringBuilder()
				   .append(super.toSqlString())
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(aidReleaseId))
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(clinicalDiagnosis))
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(analysis))
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(aidSummary))
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(handlerId))
				   .append(",")
				   .append(InsertJavaBeanToSqlAble.includingNull(reviewerId))
				   .toString();
	}
	
	public String getAidSummary() {
		return aidSummary;
	}

	public void setAidSummary(String aidSummary) {
		this.aidSummary = aidSummary;
	}

	public String getAidReleaseId() {
		return aidReleaseId;
	}
	public void setAidReleaseId(String aidReleaseId) {
		this.aidReleaseId = aidReleaseId;
	}
	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}
	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getHandlerId() {
		return handlerId;
	}
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}
	public String getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}
	
}
