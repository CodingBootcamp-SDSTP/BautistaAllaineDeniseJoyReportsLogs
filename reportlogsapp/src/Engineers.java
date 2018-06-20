import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Engineers
{
	static Engineers _instance = null;

	public static Engineers instance() {
		if(_instance == null) {
			_instance = new Engineers();
		}
		return(_instance);
	}

	HashMap<String, Engineer> engineersMap;

	private Engineers() {
		Connection conn = DatabaseConnector.instance().getConnection();
		engineersMap = new HashMap<String, Engineer>();
		/* try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb?user=user&" +
			"password=dict2018&serverTimezone=UTC&useSSL=false"); */
			readFromDB(conn);
		/* }
		catch(Exception e) {
			e.printStackTrace();
		} */
	}

	public void readFromDB(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM engineers;");
			while(rs.next()) {
				String[] row = {
					rs.getString("id"),
					rs.getString("firstName"),
					rs.getString("lastName"),
					Integer.toString(rs.getInt("age")),
					rs.getString("department"),
					rs.getString("username"),
					rs.getString("password")
				};
				addEngineer(row);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addEngineer(String... content) {
		engineersMap.put(content[5], new Engineer(content[0], content[1], content[2], Integer.parseInt(content[3]), content[4], content[5], content[6]));
	}

	public Engineer getEngineer(String u) {
		return(engineersMap.get(u));
	}

	public ArrayList<Engineer> getAllEngineers() {
		return(new ArrayList<Engineer>(engineersMap.values()));
	}
}
