package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import bean.Employee;
import bean.User;
import constant.ConstantKeyInAskJson;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;
import mysql.ConstantNameInSql;
import mysql.DBUtils;
import mysql.EmployeeOperator;
import mysql.UserOperator;
import util.CloseUtils;
import util.TextUtils;
/**
 * 修改用户信息Servlet
 * @author 2413324637
 *
 */
public class UserSettingServlet extends HttpServlet{
		
		
		private static final long serialVersionUID = 1L;
		private EmployeeOperator dbEmployeeOperator;
		private UserOperator dbUserOperator;
		private DBUtils dbUtils;
		
		public UserSettingServlet() {
			dbEmployeeOperator = new EmployeeOperator();
			dbUserOperator = new UserOperator();
			dbUtils = new DBUtils();
		}
		
		@Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String str = TextUtils.getTextFromInputStream(request.getInputStream());
	    	JSONObject json = JSONObject.parseObject(str);
	    	JSONObject responceJson  = null;
	    	boolean personTypeIsNormal = json.getBoolean(ConstantKeyInAskJson.USER_SETTING_PERSON_TYPE);
	    	int index = json.getIntValue(ConstantKeyInAskJson.USER_SETTING_TYPE);
	    	String id = json.getString(ConstantKeyInAskJson.USER_SETTING_ID);
	    	String value = json.getString(ConstantKeyInAskJson.USER_SETTING_VALUE);
	    	boolean infoTypeIsNormal = json.getBoolean(ConstantKeyInAskJson.USER_SETTING_INFO_TYPE);
	    	
	    	responceJson = attmeptToUpdataInfo( personTypeIsNormal, infoTypeIsNormal,  index, id, value);
			
	        
	        response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.print(responceJson.toJSONString());
	    	CloseUtils.close(out);
	        
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
	    
	    /**
	     * 
	     * @param requestJson
	     * @return
	     */
	    private JSONObject attmeptToUpdataInfo(boolean personTypeIsNormal,boolean infoTypeIsNormal, int index,String id,String value) {
	    	boolean isSuccess = false;
	    	JSONObject responseJson = new JSONObject();
	    	if(personTypeIsNormal) {//修改的是普通用户信息
	    		if(infoTypeIsNormal) {
	    			isSuccess = dbUserOperator.updateInfoByIndex(index, id, value);
	    		}else {//转职成工作人员
	    			User user = new User();
	    			user.setId(id);
	    			Employee e = new Employee();
	    			e.setBasicInfo(user);
	    			String newID = dbUtils.insertAndReturnId(ConstantNameInSql.TABLE_EMPLOYEE_INFO, e);
	    			isSuccess = dbEmployeeOperator.updateInfoByIndex(infoTypeIsNormal, index, newID, value);
	    			if(isSuccess) {
	    				responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
	    				Employee e0 = dbEmployeeOperator.getEmployee(newID);
	    				responseJson.put(ConstantKeyInJson.USER_SETTING_RESPONSE_USER, e0.toJsonObjectExceptNull());
	    			}else {
	    				responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.FAIL);
	    			}
	    			return responseJson;
	    			
	    		}
	    	}else {
	    		isSuccess = dbEmployeeOperator.updateInfoByIndex(infoTypeIsNormal, index, id, value);
	    	}
	    	//System.out.println(personTypeIsNormal+"###############"+infoTypeIsNormal+"###############"+isSuccess);
	    	if( isSuccess ) {
	    		responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
	        }else {
	        	responseJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.FAIL);
	        }
	    	return responseJson;
	    }
	    
	    
	    @Override
	    public void destroy() {
	    	super.destroy();
	    	CloseUtils.close(dbEmployeeOperator);
	    	CloseUtils.close(dbUserOperator);
	    }
}
