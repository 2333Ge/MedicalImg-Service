package mysql;


import java.math.BigInteger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import abs.DBCloseAble;
import abs.InsertJavaBeanToSqlAble;
import util.CloseUtils;
import static util.PrintUtils.*;
/**
 * 数据库中所有表公用的方法
 * @author 2413324637
 *
 */
public class DBUtils implements DBCloseAble {
	
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    
    public DBUtils() {
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
   
    
    /**
     * 查询指定表的所有元素
     * @param tableName 表名
     */
    public void selectAll(final String tableName) {
        try {
            String Sql = "select * from " + tableName;
            set = stt.executeQuery(Sql);
            // 获取数据
            while (set.next()) {
            	System.out.println("getString(1)" + set.getString(1) + "\tgetStr:"
                        + set.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 向指定表中添加一条新数据
     */
    public void insert(String table,InsertJavaBeanToSqlAble insertSqlAble){
        try {
            
            //定义sql语句
            String sql = insertSqlAble.getInsertSqlText(table);//获取Statement对象
            System.out.println(sql);
            //执行sql语句
            stt.executeUpdate(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    /**
     * 向指定表中添加一条新数据,并返回id
     */
    public String insertAndReturnId(String table,InsertJavaBeanToSqlAble insertSqlAble){
        try {
            //定义sql语句
            String sql = insertSqlAble.getInsertSqlText(table);//获取Statement对象
            System.out.println(sql);
            //执行sql语句
            stt.executeUpdate(sql);
            sql = "SELECT LAST_INSERT_ID();";
            // 返回结果集
            set = stt.executeQuery(sql);
            // 获取数据
            if (set.next()) {
            	return set.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    /**
     * 返回最后一条数据id,缺点：多表情况，优点：适用高并发
     * 参考{@link https://www.cnblogs.com/panxuejun/p/6180506.html}
     */
    public String getLastId(){
        try {
            String sql = "SELECT LAST_INSERT_ID();";
            // 返回结果集
            set = stt.executeQuery(sql);
            // 获取数据
            if (set.next()) {
            	return set.getString(1) == null ? "1":set.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 返回最后一条数据id,缺点：不适用高并发
     * @param table
     * @return
     */
    public String getLastId(String table){
        try {//缺点：多表情况，优点：适用高并发
        	String sql = "select max(id) from "+ table +";";
            // 返回结果集
            set = stt.executeQuery(sql);
            // 获取数据
            if (set.next()) {
            	return set.getString(1) == null ? "1":set.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 返回最后一条数据id+1
     */
    public String getAboveLastId(){
        try {
            String sql = "SELECT LAST_INSERT_ID();";
            // 返回结果集
            set = stt.executeQuery(sql);
            // 获取数据
            if (set.next()) {
            	String last = set.getString(1);
            	if(last == null) return "1";
            	BigInteger bigInt = new BigInteger(last);
            	return bigInt.add(new BigInteger("1")).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 返回最后一条id+1
     * @param table
     * @return
     */
    public String getAboveLastId(String table){
        try {
        	String sql = "select max(id) from "+ table +";";
            // 返回结果集
            set = stt.executeQuery(sql);
            // 获取数据
            if (set.next()) {
            	String last = set.getString(1);
            	if(last == null) return "1";
            	BigInteger bigInt = new BigInteger(last);
            	return bigInt.add(new BigInteger("1")).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return null;
    }
    
    /**
     * 释放资源
     */
    @Override
    public void close() {
    	CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
    }

}