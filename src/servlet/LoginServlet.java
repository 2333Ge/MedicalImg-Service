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
 * 登录注册Servlet
 * @author 2413324637
 *
 */
public class LoginServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private EmployeeOperator dbEmployeeOperator;
	private UserOperator dbUserOperator;
	private DBUtils dbUtils;
	
	public LoginServlet() {
		dbEmployeeOperator = new EmployeeOperator();
		dbUserOperator = new UserOperator();
		dbUtils = new DBUtils();
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = TextUtils.getTextFromInputStream(request.getInputStream());
    	JSONObject json = JSONObject.parseObject(str);
    	JSONObject responceJson  = null;
    	int type = json.getInteger(ConstantKeyInAskJson.LOGIN_REQUEST_TYPE);
		switch(type) {
			case 0://登陆功能
				responceJson = attmeptToLoginIn(json);
				break;
			case 1:
				responceJson = attmeptToRegister(json);
				break;
			}
		
        
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
     * 登陆
     * @param requestJson
     * @return
     */
    private JSONObject attmeptToLoginIn(JSONObject requestJson) {
    	JSONObject responceJson = new JSONObject();
    	String phone = requestJson.getString(ConstantKeyInAskJson.LOGIN_PHONE);
		String password = requestJson.getString(ConstantKeyInAskJson.LOGIN_PASSWORD);
		User user = dbUserOperator.getUser(phone,password);
		if(user == null) {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.ERROR_PASSWORD);
        	return responceJson;
        }
		Employee employee = dbEmployeeOperator.getEmployee(user);
		if(employee == null){
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.LOGIN_RESPONSE_USER_TYPE, 0);
        	responceJson.put(ConstantKeyInJson.LOGIN_RESPONSE_USER, user.toJsonObjectExceptNull());
        }else {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.LOGIN_RESPONSE_USER_TYPE, 1);
        	responceJson.put(ConstantKeyInJson.LOGIN_RESPONSE_USER, employee.toJsonObjectExceptNull());
        }
		return responceJson;
    }
    /**
     * 注册
     * @param requestJson
     * @return
     */
    private JSONObject attmeptToRegister(JSONObject requestJson) {
    	JSONObject responceJson = new JSONObject();
    	String phone = requestJson.getString(ConstantKeyInAskJson.LOGIN_PHONE);
		String password = requestJson.getString(ConstantKeyInAskJson.LOGIN_PASSWORD);
		User user = new User();
		user.setPassword(password);
		user.setPhone(phone);
		dbUtils.insert(ConstantNameInSql.TABLE_USER_INFO,user );
		User result = dbUserOperator.getUser(phone, password);
		if(result == null) {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.ERROR_PASSWORD);
        }else{
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.LOGIN_RESPONSE_USER, result.toJsonObjectExceptNull());
        	System.out.println(result.toJsonObject() + "######");
        }
		return responceJson;
    }
    
    @Override
    public void destroy() {
    	super.destroy();
    	CloseUtils.close(dbEmployeeOperator);
    	CloseUtils.close(dbUserOperator);
    }
}
