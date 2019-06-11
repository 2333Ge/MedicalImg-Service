package abs;
/**
 * 将实体类插入数据库接口
 * @author 2413324637
 *
 */
public interface InsertJavaBeanToSqlAble {
	/**
	 * 将实体类转换成插入数据库中时的String,只包含insert into table values()括号中的部分，方便重载
	 * 注意为空的时候可以插入空,引用类型都要判断空情况，以免插入 'null' 字符
	 * @return
	 */
	public String toSqlString();
	/**
	 *得到将实体类插入数据库的语句，结合tosqlString方法,注意数据库中没有该表的时候
	 * @param s
	 * @return
	 */
	public String getInsertSqlText(String table);
	/**
	 * 向数据库插入数据前判断空情况的判
	 * @param s 待插入的数据
	 * @return
	 */
	public static String includingNull(String s) {
		
		return s == null ? null : "'" + s + "'";
		
	}
	
}
