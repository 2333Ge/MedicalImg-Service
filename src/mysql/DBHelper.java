package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
	private static Connection Conn; // 数据库连接对象

    // 数据库连接地址
	private static String URL = "jdbc:mysql://localhost:3306/medical_img?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";

    // 数据库的用户名
    private static String userName = "root";
    // 数据库的密码
    private static String password = "20152430141Aa";

    /**
     * * @Description: TODO 获取访问数据库的Connection对象
     * @param @return
     * @return Connection 连接数据的对象
     * @author 情绪i
     */
    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver"); // 加载驱动

            //System.out.println("加载驱动成功!!!");
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        try {

            //通过DriverManager类的getConenction方法指定三个参数,连接数据库
            Conn = DriverManager.getConnection(URL, userName, password);
            //System.out.println("连接数据库成功!!!");

            //返回连接对象
            return Conn;

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
