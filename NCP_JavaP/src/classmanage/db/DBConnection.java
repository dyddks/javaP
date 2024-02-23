package classmanage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {
	
	public static Connection getConnection() {
	    Connection conn = null;
	    try {
	        conn = DriverManager.getConnection("jdbc:mysql://192.168.0.45:3306/javap", "root", "1234");
//	        System.out.println("Connection Success");
	    } catch (SQLException e) {
	        System.out.println("DB 연결 오류");
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public class DBClose {
	    public static void close(Connection conn, Statement stmt, ResultSet rs) {
	        try {
	            if(conn != null) {
	                conn.close();
	            }
	            if(stmt != null) {
	                stmt.close();
	            }
	            if(rs != null) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
