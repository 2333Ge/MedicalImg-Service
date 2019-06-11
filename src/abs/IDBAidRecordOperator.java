package abs;

import bean.AidRecord;
/**
 * 数据库 表 AdiRecord操作接口
 * @author 2413324637
 *
 */
public interface IDBAidRecordOperator extends DBCloseAble{
	/**
	 * 根据id查找AidRecord
	 * @param id id
	 * @return AidRecord
	 */
	public AidRecord getAidRecord(String id);
}
