package abs;

import com.alibaba.fastjson.JSONObject;
/**
 * 将json串解析为实体类
 * @author 2413324637
 *
 */
public interface TransJsonToJavaBeanAble {
	/**
	 * 将json串解析为实体类
	 * @param json fastJson中jsonObject
	 */
	public void transJsonToJavaBean(JSONObject json);
}
