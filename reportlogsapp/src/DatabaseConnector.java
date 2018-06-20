import java.sql.*;

public class DatabaseConnector
{
	static DatabaseConnector _instance = null;

	public static DatabaseConnector instance() {
		if(_instance == null) {
			_instance = new DatabaseConnector();
		}
		return(_instance);
	}

	static Connection conn = null;

	private DatabaseConnector() {
		createConnection();
	}

	private void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb?user=user&" +
			"password=dict2018&serverTimezone=UTC&useSSL=false");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return(conn);
	}
}
