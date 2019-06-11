package servlet;

import java.io.IOException;

import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;


import constant.ConstantKeyInAskJson;
import constant.ConstantKeyInJson;
import constant.HttpResponseCode;


import mysql.EmployeeOperator;
import mysql.UserOperator;
import util.CloseUtils;
import util.TextUtils;

import bean.Employee;
import bean.User;

/**
 * 查询用户信息，包括employee
 */
public class UserInfoServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	private EmployeeOperator dbEmployeeOperator;
	private UserOperator dbUserOperator;
	public UserInfoServlet() {
		dbEmployeeOperator = new EmployeeOperator();
		dbUserOperator = new UserOperator();
	}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String str = TextUtils.getTextFromInputStream(request.getInputStream());
    	JSONObject json = JSONObject.parseObject(str);
    	JSONObject responceJson  = null;
    	int type = json.getInteger(ConstantKeyInAskJson.REQUEST_TYPE);
		int employeeOrUser = json.getInteger(ConstantKeyInAskJson.EMPLOYEE_OR_USER);
		if(employeeOrUser == 0) {//工作人员
			switch(type) {
				case 1://查询用户常用信息功能
					responceJson = selectEmployeeCommonInfo(json);
					break;
				case 2://登陆功能
					responceJson = attmeptToLoginIn(json);
					break;
				case 3://获取昵称功能
					responceJson = getNickName(json);
					break;
			}
		}else {//普通用户
			
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
    private JSONObject getNickName(JSONObject requestJson) {
    	JSONObject responceJson = new JSONObject();
    	String id = requestJson.getString(ConstantKeyInAskJson.ID);
    	String nickname = dbEmployeeOperator.getNickname(id);
    	if(nickname == null) {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SERVER_EXCEPTION);
        }else {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.NET_NICKNAME, nickname);
        }
    	return responceJson;
    }
    /**
     * 登陆
     * @param requestJson
     * @return
     */
    private JSONObject attmeptToLoginIn(JSONObject requestJson) {
    	JSONObject responceJson = new JSONObject();
    	String phone = requestJson.getString(ConstantKeyInAskJson.PHONE);
		String password = requestJson.getString(ConstantKeyInAskJson.PASSWORD);
		User user = dbUserOperator.getUser(phone,password);
		if(user == null) {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.ERROR_PASSWORD);
        }else {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.NET_USER_INFO, user.toJsonObjectExceptNull());
        	
        }
		return responceJson;
    }
    /**
     * 查询工作人员基本可见信息
     * @param requestJson
     * @return
     */
    private JSONObject selectEmployeeCommonInfo(JSONObject requestJson) {
    	JSONObject responceJson = new JSONObject();
    	String id = requestJson.getString(ConstantKeyInAskJson.ID);
		Employee employee = dbEmployeeOperator.getCommonInfo(id);
		if(employee == null) {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SERVER_EXCEPTION);
        }else {
        	responceJson.put(ConstantKeyInJson.RESPONSE_CODE, HttpResponseCode.SUCCESS);
        	responceJson.put(ConstantKeyInJson.NET_USER_INFO, employee.toJsonObjectExceptNull());
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
