package mysql;

import static util.PrintUtils.println;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import abs.IDBEmployeeOperator;
import abs.InsertJavaBeanToSqlAble;
import bean.Employee;
import bean.User;
import util.CloseUtils;

public class EmployeeOperator implements IDBEmployeeOperator{
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    private UserOperator userOperator;
	public EmployeeOperator() {
		init();
	}
	/**
     * 初始化数据库配置。
     */
    public void init() {
    	userOperator = new UserOperator();
    	conn = DBHelper.getConnection();
    	if (conn == null){
    		println("数据库连接为空");
    		return;
    	}else {
    		try {
				stt = conn.createStatement();
			} catch (Exception e) {
				println("创建sql语句对象失败");
			}
    	}
    	
    }
    @Override
    public Employee getEmployee(String id) {
    	Employee employee = null;
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            //println(sb.toString());
            if(set.next()) {
            	employee = new Employee();
            	employee.transSqlToJavaBean(set);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return employee;
    	
    }
    @Override
    public Employee getEmployeeIncludeBasicInfo(String id) {
    	Employee employee = getEmployee(id);
    	String basicId = employee.getBasicInfo().getId();
    	User basicInfo = userOperator.getUser(basicId);
    	employee.setBasicInfo(basicInfo);
    	return employee;
    }
    
    @Override
    public Employee getCommonInfo(String id) {
    	Employee employee = null;
    	try {
			StringBuilder sb = new StringBuilder("select ");
            sb.append(ConstantNameInSql.ID).append(",")
            .append(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID).append(",")
            .append(ConstantNameInSql.EMPLOYEE_INFO_DEPARTMENT).append(",")
            .append(ConstantNameInSql.EMPLOYEE_INFO_ROOM)
            .append(" from ")
            .append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            println(sb.toString());
            if(set.next()) {
            	employee = new Employee();
            	employee.setId(set.getString(ConstantNameInSql.ID));
            	employee.setDepartment(set.getString(ConstantNameInSql.EMPLOYEE_INFO_DEPARTMENT));
            	employee.setRoom(set.getString(ConstantNameInSql.EMPLOYEE_INFO_ROOM));
            	User user = new User();
            	user = userOperator.getCommonInfo(set.getString(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID));
            	employee.setBasicInfo(user);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return employee;
    }
    @Override
    public String getNickname(String id) {
    	try {
			StringBuilder sb = new StringBuilder("select ");
            sb.append(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID)
            .append(" from ")
            .append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            println(sb.toString());
            if(set.next()) {
            	return userOperator.getNickname(set.getString(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID));
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	return null;
    }
    @Override
    public Employee getEmployee(User user) {
    	Employee employee = null;
    	try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID)
        	.append(" = ")
        	.append(user.getId())
        	.append(";");
            set = stt.executeQuery(sb.toString());
            println(sb.toString());
            if(set.next()) {
            	employee = new Employee();
            	employee.transSqlToJavaBean(set);
            	employee.setBasicInfo(user);
            	return employee;
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	return null;
    }
    
    @Override
    public boolean updateInfoByIndex(boolean isNormal, int infoIndex, String id, String value) {
    	if(isNormal) {
    		String basicId = getBasicId(id);
    		boolean isSuccess = userOperator.updateInfoByIndex(infoIndex, basicId, value);
    		return isSuccess;
    	}else {
    		try {
    			StringBuilder sb = new StringBuilder("update ");
                sb.append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
                .append(" set ")
                .append(IDBEmployeeOperator.getKey(infoIndex))
                .append(" = ")
                .append(InsertJavaBeanToSqlAble.includingNull(value))
                .append(" where ")
                .append(ConstantNameInSql.ID)
                .append(" = ")
                .append(id)
            	.append(";");
                stt.execute(sb.toString());
            } catch (Exception e) {
            	e.printStackTrace();
            	return false;
            }
        	return true;
    	}
    }
    
    @Override
    public String getBasicId(String id) {
    	try {
			StringBuilder sb = new StringBuilder("select ");
            sb.append(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID)
            .append(" from ")
            .append(ConstantNameInSql.TABLE_EMPLOYEE_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            if(set.next()) {
            	return set.getString(ConstantNameInSql.EMPLOYEE_INFO_BASIC_INFO_ID);
        	}
            
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
    	return null;
    	
    }
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
        CloseUtils.close(userOperator);
	}

}
