package abs;

import bean.AidTreat;
/**
 * 表AidTreat操作接口
 * @author 2413324637
 *
 */
public interface IDBAidTreatOperator extends DBCloseAble{
	/**
	 * 根据id获取AidTreat,查询一次数据库
	 * @param id
	 * @return
	 */
	public AidTreat getAidTreat(String id);
	/**
	 * 检查数据库对应项有否
	 * @param aidReleaseID
	 * @return
	 */
	public boolean isAidTreatExits(String aidReleaseID,String handlerId);
	/**
	 * 更新对应项目，更新前检查是否有数据，有插入数据，无则更新数据
	 * @param aidReleaseID
	 * @return
	 */
	public boolean updateAidTreat(AidTreat aidTreat);
	/**
	 * 根据aidReleaseId、handlerId查找对应病单处理情况
	 * @param aidReleaseId 发布病单id
	 * @param handlerId 处理人id
	 * @return
	 */
	public AidTreat getAidTreat(String aidReleaseId,String handlerId);
}
