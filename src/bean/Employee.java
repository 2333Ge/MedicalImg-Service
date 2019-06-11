package bean;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;
/**
 *  医务人员实体类
 * @author 2413324637
 *
 */
public class Employee extends BasicInfo {
	
	private String employeeId;
	private String department;
	private String room;
	private String hospitaId;
	private User basicInfo;
	
	public User getBasicInfo() {
		return basicInfo;
	}
	public void setBasicInfo(User basicInfo) {
		this.basicInfo = basicInfo;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public String getHospitaId() {
		return hospitaId;
	}
	public void setHospitaId(String hospitaId) {
		this.hospitaId = hospitaId;
	}
	@Override
	public String toSqlString() {
		return new StringBuilder()
				.append(super.toSqlString())
				.append(",") 
				.append(basicInfo == null? "0":basicInfo.getId())
				.append(",") 
				.append(InsertJavaBeanToSqlAble.includingNull(employeeId))
				.append(",") 
				.append(InsertJavaBeanToSqlAble.includingNull(department))
				.append(",") 
				.append(InsertJavaBeanToSqlAble.includingNull(room))
				.append(",") 
				.append(InsertJavaBeanToSqlAble.includingNull(hospitaId))
				.toString();
	}
	
	
	@Override
	public void transSqlToJavaBean(ResultSet set) throws Exception {
		try {
			super.transSqlToJavaBean(set);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw e1;
		}if(basicInfo == null) {
			basicInfo = new User();//注意查询数据库时间
			this.setBasicInfo(basicInfo);
		}try {
			basicInfo.setId(set.getString(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID));
			this.setDepartment(set.getString(ConstantNameInSql.EMPLOYEE_INFO_DEPARTMENT));
			this.setEmployeeId(set.getString(ConstantNameInSql.EMPLOYEE_INFO_EMPLOYEE_ID));
			this.setRoom(set.getString(ConstantNameInSql.EMPLOYEE_INFO_ROOM));
			this.setHospitaId(set.getString(ConstantNameInSql.EMPLOYEE_INFO_HOSPITAL_ID));
		}catch(Exception e){
			throw e;
		}
	}
	@Override
    public JSONObject toJsonObjectExceptNull() {
    	JSONObject jsonObject = toJsonObjectExceptNullWithoutSuperClass();
    	jsonObject.put(ConstantKeyInJson.BEAN_BASIC_INFO,super.toJsonObjectExceptNull());
    	return jsonObject;
    }
    
    @Override
    public JSONObject toJsonObjectExceptNullWithoutSuperClass() {
    	JSONObject jsonObject = new JSONObject();
    	if(employeeId != null) jsonObject.put(ConstantKeyInJson.EMPLOYEE_INFO_EMPLOYEE_ID, employeeId);
    	if(department != null) jsonObject.put(ConstantKeyInJson.EMPLOYEE_INFO_DEPARTMENT, department);
    	if(room != null) jsonObject.put(ConstantKeyInJson.EMPLOYEE_INFO_ROOM, room);
    	if(hospitaId != null) jsonObject.put(ConstantKeyInJson.EMPLOYEE_INFO_HOSPITAL_ID, hospitaId);
    	if(basicInfo != null) jsonObject.put(ConstantKeyInJson.EMPLOYEE_INFO_BASIC_INFO, basicInfo.toJsonObjectExceptNull());
    	return jsonObject;
    }
    @Override
	public void transJsonToJavaBean(JSONObject json) {
    	if(json == null) return;
		JSONObject basicJson = json.getJSONObject(ConstantKeyInJson.BEAN_BASIC_INFO);
		super.transJsonToJavaBean(basicJson);
		this.employeeId = json.getString(ConstantKeyInJson.EMPLOYEE_INFO_EMPLOYEE_ID);
		this.department = json.getString(ConstantKeyInJson.EMPLOYEE_INFO_DEPARTMENT);
		this.room = json.getString(ConstantKeyInJson.EMPLOYEE_INFO_ROOM);
		this.hospitaId = json.getString(ConstantKeyInJson.EMPLOYEE_INFO_HOSPITAL_ID);
		if(basicInfo == null) {
			User basic = new User();
			this.basicInfo = basic;
		}
		this.basicInfo.transJsonToJavaBean(json.getJSONObject(ConstantKeyInJson.EMPLOYEE_INFO_BASIC_INFO));
	}

}
