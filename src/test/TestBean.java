package test;


import bean.User;
public class TestBean {

	public static void main(String[] args) {
		testJsonToBeanAble();
	}
	
	static void testJsonToBeanAble() {
		User user = new User();
		user.setBirthday("setBirthday");
		user.transJsonToJavaBean(user.toJsonObjectExceptNull());
	}
}
