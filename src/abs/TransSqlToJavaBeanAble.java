package abs;

import java.sql.ResultSet;
/**
 * 将sql语句的结果集转换成实体类
 * @author 2413324637
 *
 */
public interface TransSqlToJavaBeanAble {
	/**
	 * 将sql语句的结果集转换成实体类
	 * @param set 数据库查询结果
	 * @throws Exception
	 */
	public void transSqlToJavaBean(ResultSet set) throws Exception;
}
