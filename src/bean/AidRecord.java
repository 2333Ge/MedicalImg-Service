package bean;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;
/**
 * 上传的病单实体类，存放解析后的信息
 * @author 2413324637
 *
 */
public class AidRecord extends BasicInfo {
	private int sex;
	private int age;
	private String 	chiefComplaint;//主诉
	private String clinicalManifestation;//临床表现
	private String 	imagingFeatures;//影像表现
	private List<String> imgList;
	
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getChiefComplaint() {
		return chiefComplaint;
	}
	public void setChiefComplaint(String chiefComplaint) {
		this.chiefComplaint = chiefComplaint;
	}
	public String getClinicalManifestation() {
		return clinicalManifestation;
	}
	public void setClinicalManifestation(String clinicalManifestation) {
		this.clinicalManifestation = clinicalManifestation;
	}
	public String getImagingFeatures() {
		return imagingFeatures;
	}
	public void setImagingFeatures(String imagingFeatures) {
		this.imagingFeatures = imagingFeatures;
	}
	public List<String> getImgList() {
		return imgList;
	}
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}
	
	@Override
	public String toSqlString() {
    	StringBuilder sb = new StringBuilder();
		sb.append(super.toSqlString())
			.append(",") 
			.append(sex)
			.append(",") 
			.append(age)
			.append(",") 
			.append(InsertJavaBeanToSqlAble.includingNull(chiefComplaint))
			.append(",") 
			.append(InsertJavaBeanToSqlAble.includingNull(clinicalManifestation))
			.append(",") 
			.append(InsertJavaBeanToSqlAble.includingNull(imagingFeatures))
			.append(",");
			
		if(imgList == null || imgList.size() == 0) {
			sb.append("null");//空
		}else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(ConstantKeyInJson.AID_RECORD_IMG_LIST, this.imgList);
			sb.append("'")
			.append(jsonObject.toJSONString())
			.append("'");
		}
		return sb.toString();
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
		jsonObject.put(ConstantKeyInJson.AID_RECORD_AGE,age);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_SEX,sex);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_CHIEF_COMPLAINT,chiefComplaint);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_CLINICAL_MANIFESTATION,clinicalManifestation);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_IMAGEING_FEATURE,imagingFeatures);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_IMG_LIST,imgList);
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
		jsonObject.put(ConstantKeyInJson.AID_RECORD_AGE,age);
		jsonObject.put(ConstantKeyInJson.AID_RECORD_SEX,sex);
		if(chiefComplaint != null)jsonObject.put(ConstantKeyInJson.AID_RECORD_CHIEF_COMPLAINT,chiefComplaint);
		if(clinicalManifestation != null)jsonObject.put(ConstantKeyInJson.AID_RECORD_CLINICAL_MANIFESTATION,clinicalManifestation);
		if(imagingFeatures != null)jsonObject.put(ConstantKeyInJson.AID_RECORD_IMAGEING_FEATURE,imagingFeatures);
		if(imgList != null)jsonObject.put(ConstantKeyInJson.AID_RECORD_IMG_LIST,imgList);
    	return jsonObject;
	}
	@Override
	public void transSqlToJavaBean(ResultSet set) throws Exception {
		
		super.transSqlToJavaBean(set);
		try {
			this.setAge(set.getInt(ConstantNameInSql.AID_RECORD_AGE));
			this.setChiefComplaint(set.getString(ConstantNameInSql.AID_RECORD_CHIEF_COMPLAINT));
			this.setClinicalManifestation(set.getString(ConstantNameInSql.AID_RECORD_CLINICAL_MANIFESTATION));
			this.setImagingFeatures(set.getString(ConstantNameInSql.AID_RECORD_IMAGEING_FEATURE));
			transJsonToImageList(set.getString(ConstantNameInSql.AID_RECORD_IMG_LIST));
		}catch(Exception e){
			throw e;
		}
	}
	@Override
	public void transJsonToJavaBean(JSONObject json) {
		if(json == null) return;
		JSONObject basicJson = json.getJSONObject(ConstantKeyInJson.BEAN_BASIC_INFO);
		super.transJsonToJavaBean(basicJson);
		this.age = json.getInteger(ConstantKeyInJson.AID_RECORD_AGE);
		this.sex = json.getInteger(ConstantKeyInJson.AID_RECORD_SEX);
		this.chiefComplaint = json.getString(ConstantKeyInJson.AID_RECORD_CHIEF_COMPLAINT);
		this.clinicalManifestation = json.getString(ConstantKeyInJson.AID_RECORD_CLINICAL_MANIFESTATION);
		this.imagingFeatures = json.getString(ConstantKeyInJson.AID_RECORD_IMAGEING_FEATURE);
		transJsonToImageList(json.getString(ConstantKeyInJson.AID_RECORD_IMG_LIST));
	}
	
	private void transJsonToImageList(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		JSONArray imgJsonArray = jsonObject.getJSONArray(ConstantKeyInJson.AID_RECORD_IMG_LIST);
		if(imgList == null) imgList = new ArrayList<String>();
		if(imgJsonArray == null) return;
		for(int i = 0; i < imgJsonArray.size(); i++) {
			this.imgList.add(imgJsonArray.get(i).toString());
		}
		
	}
	
}
