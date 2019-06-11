package bean;

import java.sql.ResultSet;

import com.alibaba.fastjson.JSONObject;

import abs.InsertJavaBeanToSqlAble;
import constant.ConstantKeyInJson;
import mysql.ConstantNameInSql;
/**
 * 基本用户实体类
 * @author 2413324637
 *
 */
public class User extends BasicInfo{
    
    private  String name;
    private  String nickname;
    private  String headImg;
    private  String phone;
    private  String email;
    private  String password;
    private  String birthday;
    private  int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    @Override
	public String toSqlString() {
	   return new StringBuilder()
			   .append(super.toSqlString())
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(name))
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(nickname))
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(headImg))
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(phone))
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(email))
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(password))
			   .append(",")
			   .append(sex)
			   .append(",")
			   .append(InsertJavaBeanToSqlAble.includingNull(birthday))
			   .toString();
    }
    
    @Override
    public JSONObject toJsonObjectExceptNull() {
    	JSONObject jsonObject = toJsonObjectExceptNullWithoutSuperClass();
    	jsonObject.put(ConstantKeyInJson.BEAN_BASIC_INFO,super.toJsonObjectExceptNull());
    	return jsonObject;
    }
    
    /**
     * 不包含password
     */
    @Override
    public JSONObject toJsonObjectExceptNullWithoutSuperClass() {
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.put(ConstantKeyInJson.USER_INFO_SEX, sex);
    	if(name != null) jsonObject.put(ConstantKeyInJson.USER_INFO_NAME, name);
    	if(nickname != null) jsonObject.put(ConstantKeyInJson.USER_INFO_NICKNAME, nickname);
    	if(birthday != null) jsonObject.put(ConstantKeyInJson.USER_INFO_BIRTHDAY, birthday);
    	if(email != null) jsonObject.put(ConstantKeyInJson.USER_INFO_EMAIL, email);
    	if(phone != null) jsonObject.put(ConstantKeyInJson.USER_INFO_PHONE, phone);
    	if(headImg != null) jsonObject.put(ConstantKeyInJson.USER_INFO_HEAD_IMG, headImg);
    	return jsonObject;
    }
    @Override
	public void transJsonToJavaBean(JSONObject json) {
    	if(json == null) return;
		JSONObject basicJson = json.getJSONObject(ConstantKeyInJson.BEAN_BASIC_INFO);
		super.transJsonToJavaBean(basicJson);
		this.sex = json.getInteger(ConstantKeyInJson.USER_INFO_SEX);
		this.name = json.getString(ConstantKeyInJson.USER_INFO_NAME);
		this.nickname = json.getString(ConstantKeyInJson.USER_INFO_NICKNAME);
		this.birthday = json.getString(ConstantKeyInJson.USER_INFO_BIRTHDAY);
		this.email = json.getString(ConstantKeyInJson.USER_INFO_EMAIL);
		this.phone = json.getString(ConstantKeyInJson.USER_INFO_PHONE);
		this.headImg = json.getString(ConstantKeyInJson.USER_INFO_HEAD_IMG);
	}
    @Override
    public void transSqlToJavaBean(ResultSet set) throws Exception {
    	super.transSqlToJavaBean(set);
		try {
			this.setBirthday(set.getString(ConstantNameInSql.USER_INFO_BIRTHDAY));
			this.setName(set.getString(ConstantNameInSql.USER_INFO_NAME));
			this.setEmail(set.getString(ConstantNameInSql.USER_INFO_EMAIL));
			this.setNickname(set.getString(ConstantNameInSql.USER_INFO_NICKNAME));
			this.setPhone(set.getString(ConstantNameInSql.USER_INFO_PHONE));
			this.setHeadImg(set.getString(ConstantNameInSql.USER_INFO_HEAD_IMG));
			this.setSex(set.getInt(ConstantNameInSql.USER_INFO_SEX));
		}catch(Exception e){
			throw e;
		}
    }
	
    
}
