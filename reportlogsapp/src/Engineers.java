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
	Connection conn = null;

	private Engineers() {
		conn = DatabaseConnector.instance().getConnection();
		engineersMap = new HashMap<String, Engineer>();
		readFromDB();
	}

	public void readFromDB() {
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
					rs.getString("password"),
					Boolean.toString(rs.getBoolean("isadmin"))
				};
				addEngineer(row);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try{ if(stmt != null) stmt.close(); }
			catch(Exception e) { e.printStackTrace(); }
			try{ if(rs != null) rs.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}

	public void writeToDB(String... data) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO engineers ( id, firstName, lastName, age, department, username, password, isadmin ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )");
			for(int i = 0; i < data.length - 1; i++) {
				if(i == 3) {
					continue;
				}
				ps.setString(i + 1, data[i]);
				// System.out.println(data[i]);
			}
			ps.setInt(4, Integer.parseInt(data[3]));
			ps.setBoolean(8, Boolean.parseBoolean(data[7]));
			ps.executeUpdate();
			addEngineer(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try{ if(ps != null) ps.close(); }
			catch(Exception e) { e.printStackTrace(); }
		}
	}

	public void addEngineer(String... content) {
		engineersMap.put(content[5], new Engineer(content[0], content[1], content[2], Integer.parseInt(content[3]), content[4], content[5], content[6], Boolean.parseBoolean(content[7])));
	}

	public Engineer getEngineer(String u) {
		return(engineersMap.get(u));
	}

	public ArrayList<Engineer> getAllEngineers() {
		return(new ArrayList<Engineer>(engineersMap.values()));
	}
}
