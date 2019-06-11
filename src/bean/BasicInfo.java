package bean;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import abs.TransSqlToJavaBeanAble;
import abs.TransJavaBeanToJsonAble;
import abs.TransJsonToJavaBeanAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;
/**
 * 实体类，和数据库对应，这是所有实体类都有的字段，作为基类
 * @author 2413324637
 *
 */
public class BasicInfo implements InsertJavaBeanToSqlAble,TransJavaBeanToJsonAble,TransSqlToJavaBeanAble,TransJsonToJavaBeanAble{
	//为空的时候可以插入空,注意引用类型都要判断空情况
	private String id;
	private String greateTime;
	private String modifiedTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGreateTime() {
		return greateTime;
	}
	public void setGreateTime(String greateTime) {
		this.greateTime = greateTime;
	}
	public String getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toSqlString() {
		return new StringBuilder()
				.append(id)
				.append(",")
				.append(InsertJavaBeanToSqlAble.includingNull(greateTime))
				.append(",")
				.append(InsertJavaBeanToSqlAble.includingNull(modifiedTime))
				.append("")
				.toString();
		
	}
	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(ConstantKeyInJson.ID, id);
		jsonObject.put(ConstantKeyInJson.GMT_CREATE, greateTime);
		jsonObject.put(ConstantKeyInJson.GMT_MODIFIED,modifiedTime);
		return jsonObject;
	}
	
	@Override
	public JSONObject toJsonObjectWithoutSuperClass() {
		return toJsonObject();
	}
	@Override
	public JSONObject toJsonObjectExceptNull() {
		JSONObject jsonObject = new JSONObject();
		if(id != null) jsonObject.put(ConstantKeyInJson.ID, id);
		if(greateTime != null) jsonObject.put(ConstantKeyInJson.GMT_CREATE, greateTime);
		if(modifiedTime != null) jsonObject.put(ConstantKeyInJson.GMT_MODIFIED,modifiedTime);
		return jsonObject;
	}
	@Override
	public JSONObject toJsonObjectExceptNullWithoutSuperClass() {
		return toJsonObjectExceptNull();
	}
	
	@Override
	public void transSqlToJavaBean(ResultSet set) throws Exception{
		try {
			this.setId(set.getString(ConstantNameInSql.ID));
			this.setGreateTime(set.getString(ConstantNameInSql.GMT_CREATE));
			this.setModifiedTime(set.getString(ConstantNameInSql.GMT_MODIFIED));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			//id必须有，只有这种情况需要抛出捕获的异常
		}
		
		
	}
	
	@Override
	public void transJsonToJavaBean(JSONObject json) {
		if(json == null) return;
		this.id = json.getString(ConstantKeyInJson.ID);
		this.greateTime = json.getString(ConstantKeyInJson.GMT_CREATE);
		this.modifiedTime = json.getString(ConstantKeyInJson.GMT_MODIFIED);
	}
	
	/**
	 *结合tosqlString方法,注意数据库中没有该表的时候
	 * @param s
	 * @return
	 */
	@Override
	public String getInsertSqlText(String table) {
		return new StringBuilder("insert into ")
		    	.append(table)
		    	.append(" values (")
		    	.append(toSqlString())
		    	.append(");")
		    	.toString();
    }
}
