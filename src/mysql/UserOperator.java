package mysql;

import static util.PrintUtils.println;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import abs.IDBUserOperator;
import abs.InsertJavaBeanToSqlAble;
import bean.User;
import util.CloseUtils;

public class UserOperator implements IDBUserOperator{
	
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    
	public UserOperator() {
		init();
	}
	/**
     * 初始化数据库配置。
     */
    public void init() {
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
    public User getUser(String id) {
    	User user = null;
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_USER_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            //println(sb.toString());
            if(set.next()) {
            	user = new User();
            	user.transSqlToJavaBean(set);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return user;
    }
    
    @Override
    public String getNickname(String id) {
		try {
			StringBuilder sb = new StringBuilder("select ");
            sb.append(ConstantNameInSql.USER_INFO_NICKNAME)
            .append(" from ")
            .append(ConstantNameInSql.TABLE_USER_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            if(set.next()) {
            	return set.getString(ConstantNameInSql.USER_INFO_NICKNAME);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
        }
		return null;
    }
    @Override
    public User getUser(String phone,String password) {
    	User user = null;
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_USER_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.USER_INFO_PHONE)
        	.append(" = ")
        	.append(InsertJavaBeanToSqlAble.includingNull(phone))
        	.append(" and ")
        	.append(ConstantNameInSql.USER_INFO_PASSWORD)
        	.append(" = ")
        	.append(InsertJavaBeanToSqlAble.includingNull(password))
        	.append(";");
            set = stt.executeQuery(sb.toString());
            if(set.next()) {
            	user = new User();
            	user.transSqlToJavaBean(set);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return user;
    }
    @Override
    public User getCommonInfo(String id) {
    	User user = null;
    	try {
			StringBuilder sb = new StringBuilder("select ");
            sb.append(ConstantNameInSql.ID).append(",")
            .append(ConstantNameInSql.USER_INFO_NICKNAME).append(",")
            .append(ConstantNameInSql.USER_INFO_HEAD_IMG)
            .append(" from ")
            .append(ConstantNameInSql.TABLE_USER_INFO)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            //println(sb.toString());
            if(set.next()) {
            	user = new User();
            	user.setId(set.getString(ConstantNameInSql.ID));
            	user.setNickname(set.getString(ConstantNameInSql.USER_INFO_NICKNAME));
            	user.setHeadImg(set.getString(ConstantNameInSql.USER_INFO_HEAD_IMG));
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return user;
    }
    @Override
    public boolean updateInfoByIndex(int infoIndex, String id, String value) {
    	try {
			StringBuilder sb = new StringBuilder("update ");
            sb.append(ConstantNameInSql.TABLE_USER_INFO)
            .append(" set ")
            .append(IDBUserOperator.getKey(infoIndex))
            .append(" = ")
            .append(InsertJavaBeanToSqlAble.includingNull(value))
            .append("  where ")
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
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
	}
}
