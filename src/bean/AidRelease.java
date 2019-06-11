package bean;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;

/**
 * 发布的病单实体类
 */
public class AidRelease extends BasicInfo{
	//对应数据库aid_release
	private int state;
	private String uploadEmployeeId;
	private String handleEmployeeId;
	private AidRecord aidRecord; 


	public String getUploadEmployeeId() {
		return uploadEmployeeId;
	}

	public void setUploadEmployeeId(String uploadEmployeeId) {
		this.uploadEmployeeId = uploadEmployeeId;
	}
	public void setAidRecord(AidRecord aidRecord) {
		this.aidRecord = aidRecord;
	}
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	public String getHandleEmployeeId() {
		return handleEmployeeId;
	}

	public void setHandleEmployeeId(String handleEmployeeId) {
		this.handleEmployeeId = handleEmployeeId;
	}

	public AidRecord getAidRecord() {
		return aidRecord;
	}

	
	@Override
	public String toSqlString() {
		return new StringBuilder()
			.append(super.toSqlString())
			.append(",") 
			.append(InsertJavaBeanToSqlAble.includingNull(handleEmployeeId))
			.append(",") 
			.append(state)
			.append(",") 
			.append(aidRecord== null ? null:aidRecord.getId())
			.append(",") 
			.append(InsertJavaBeanToSqlAble.includingNull(uploadEmployeeId))
			.toString();
	}
	
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonObject = toJsonObjectWithoutSuperClass();
		jsonObject.put(ConstantKeyInJson.BEAN_BASIC_INFO,super.toJsonObject());
		return jsonObject;
	}
	@Override
	public JSONObject toJsonObjectWithoutSuperClass() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConstantKeyInJson.AID_RELEASE_AID_RECORD,aidRecord.toJsonObject());
		jsonObject.put(ConstantKeyInJson.AID_RELEASE_HANDLE_EMPLOYEE_ID,handleEmployeeId);
		jsonObject.put(ConstantKeyInJson.AID_RELEASE_UPLOAD_EMPLOYEE_ID,uploadEmployeeId);
		jsonObject.put(ConstantKeyInJson.AID_RELEASE_STATE,state);
		return jsonObject;
	}
	@Override
	public JSONObject toJsonObjectExceptNull() {
		JSONObject jsonObject = toJsonObjectExceptNullWithoutSuperClass();
		jsonObject.put(ConstantKeyInJson.BEAN_BASIC_INFO,super.toJsonObject());
		return jsonObject;
	}
	
	@Override
	public JSONObject toJsonObjectExceptNullWithoutSuperClass() {
		JSONObject jsonObject = new JSONObject();
    	if(aidRecord != null) jsonObject.put(ConstantKeyInJson.AID_RELEASE_AID_RECORD, aidRecord.toJsonObjectExceptNull());
    	if(handleEmployeeId != null) jsonObject.put(ConstantKeyInJson.AID_RELEASE_HANDLE_EMPLOYEE_ID,handleEmployeeId);
    	if(uploadEmployeeId != null) jsonObject.put(ConstantKeyInJson.AID_RELEASE_UPLOAD_EMPLOYEE_ID,uploadEmployeeId);
    	jsonObject.put(ConstantKeyInJson.AID_RELEASE_STATE,state);
    	return jsonObject;
	}
	@Override
	public void transSqlToJavaBean(ResultSet set) throws Exception {
		
		try {
			super.transSqlToJavaBean(set);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}
		if(aidRecord == null) {
			aidRecord = new AidRecord();//注意查询数据库时间
			this.setAidRecord(aidRecord);
		}try {
			aidRecord.setId(set.getString(ConstantNameInSql.AID_RELEASE_AID_RECORD_ID));
			this.setHandleEmployeeId(set.getString(ConstantNameInSql.AID_RELEASE_HANDLE_EMPLOYEE_ID));
			this.setState(set.getInt(ConstantNameInSql.AID_RELEASE_STATE));
			this.setUploadEmployeeId(set.getString(ConstantNameInSql.AID_RELEASE_UPLOAD_EMPLOYEE_ID));
		}catch(Exception e){
			throw e;
		}
		
	}
	@Override
	public void transJsonToJavaBean(JSONObject json) {
		if(json == null) return;
		JSONObject basicJson = json.getJSONObject(ConstantKeyInJson.BEAN_BASIC_INFO);
		super.transJsonToJavaBean(basicJson);
		JSONObject aidRecordJson = json.getJSONObject(ConstantKeyInJson.AID_RELEASE_AID_RECORD);
		AidRecord aidRecord = new AidRecord();
		aidRecord.transJsonToJavaBean(aidRecordJson);
		this.aidRecord = aidRecord;
		this.handleEmployeeId = json.getString(ConstantKeyInJson.AID_RELEASE_HANDLE_EMPLOYEE_ID);
		this.uploadEmployeeId = json.getString(ConstantKeyInJson.AID_RELEASE_UPLOAD_EMPLOYEE_ID);
		this.state = json.getInteger(ConstantKeyInJson.AID_RELEASE_STATE);
	}
    	
}
