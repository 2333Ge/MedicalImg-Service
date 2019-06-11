package mysql;

import static util.PrintUtils.println;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import abs.IDBAidRecordOperator;
import bean.AidRecord;
import util.CloseUtils;

public class AidRecordOperator implements IDBAidRecordOperator{
	private Connection conn = null;
    private Statement stt = null;// 传递sql语句
    private ResultSet set = null;// 结果集
    
	public AidRecordOperator() {
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
	public AidRecord getAidRecord(String id) {
		AidRecord aidRecord = null;
		try {
			StringBuilder sb = new StringBuilder("select * from ");
            sb.append(ConstantNameInSql.TABLE_AID_RECORD)
            .append(" where ")
        	.append(ConstantNameInSql.ID)
        	.append(" = ")
        	.append(id)
        	.append(";");
            set = stt.executeQuery(sb.toString());
            println(sb.toString());
            if(set.next()) {
            	aidRecord = new AidRecord();
            	aidRecord.transSqlToJavaBean(set);
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return aidRecord;
	}
	
	@Override
	public void close() {
		CloseUtils.close(set);
        CloseUtils.close(conn);
        CloseUtils.close(stt);
	}

}
