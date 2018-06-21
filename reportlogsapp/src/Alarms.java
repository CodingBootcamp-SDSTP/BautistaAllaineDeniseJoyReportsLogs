import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Alarms
{
	static Alarms _instance = null;

	public static Alarms instance() {
		if(_instance == null) {
			_instance = new Alarms();
		}
		return(_instance);
	}

	HashMap<String, Alarm> alarmsMap;

	private Alarms() {
		alarmsMap = new HashMap<String, Alarm>();
		Connection conn = DatabaseConnector.instance().getConnection();
		readFromDB(conn);
	}

	public void readFromDB(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM alarms;");
			while(rs.next()) {
				String[] row = {
					rs.getString("id"),
					rs.getString("name"),
					rs.getString("description"),
					rs.getString("technology")
				};
				addAlarm(row);
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

	public void addAlarm(String... content) {
		alarmsMap.put(content[0], new Alarm(content[0], content[1], content[2], content[3]));
	}

	public Alarm getAlarm(String i) {
		return(alarmsMap.get(i));
	}

	public ArrayList<Alarm> getAllAlarms() {
		return(new ArrayList<Alarm>(alarmsMap.values()));
	}
}
