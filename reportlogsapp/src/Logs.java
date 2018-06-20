import java.sql.*;
import java.util.LinkedHashMap;
import java.util.ArrayList;

public class Logs
{
	static Logs _instance = null;

	public static Logs instance() {
		if(_instance == null) {
			_instance = new Logs();
		}
		return(_instance);
	}

	LinkedHashMap<Integer, Entry> entriesMap;
	Connection conn = null;
	Sites sites;
	Alarms alarms;
	Engineers engineers;

	private Logs() {
		entriesMap = new LinkedHashMap<Integer, Entry>();
		/* try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb?user=user&" +
			"password=dict2018&serverTimezone=UTC&useSSL=false");
		}
		catch(Exception e) {
			e.printStackTrace();
		} */
		sites = Sites.instance();
		alarms = Alarms.instance();
		engineers = Engineers.instance();
		conn = DatabaseConnector.instance().getConnection();
		readFromDB();
	}

	public void readFromDB() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM logs ORDER BY time DESC;");
			while(rs.next()) {
				String[] row = {
					Integer.toString(rs.getInt("id")),
					rs.getString("alarmid"),
					rs.getString("siteid"),
					rs.getString("action"),
					rs.getString("remarks"),
					rs.getString("engineer"),
					Long.toString(rs.getTimestamp("time").getTime())
				};
				addEntry(row);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void writeToDB(String... data) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO logs ( alarmid, siteid, action, remarks, engineer, time ) VALUES ( ?, ?, ?, ?, ?, ? )");
			for(int i = 1; i < data.length - 1; i++) {
				ps.setString(i, data[i]);
			}
			ps.setObject(data.length - 1, new Timestamp(Long.parseLong(data[data.length - 1])));
			ps.executeUpdate();
			data[0] = Integer.toString(getLastID());
			addEntry(data);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int getLastID() {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id FROM logs ORDER BY id DESC LIMIT 1;");
			rs.next();
			return(rs.getInt(1));
		}
		catch(Exception e) {
			e.printStackTrace();
			return(-1);
		}
	}

	public void addEntry(String... content) {
		int id = Integer.parseInt(content[0]);
		Alarm alarm = alarms.getAlarm(content[1]);
		Site site = sites.getSite(content[2]);
		Engineer engineer = engineers.getEngineer(content[5]);
		entriesMap.put(id, new Entry(id, alarm, site, content[3], content[4], engineer, content[6]));
	}

	public Entry getEntry(int i) {
		return(entriesMap.get(i));
	}

	public ArrayList<Entry> searchEntries(ArrayList<Entry> array, String str, String type) {
		ArrayList<Entry> matchedEntries = new ArrayList<Entry>();
		for(Entry e : array) {
			if(e.match(str, type)) {
				matchedEntries.add(e);
			}
		}
		return(matchedEntries);
	}

	public ArrayList<Entry> getAllEntries() {
		return(new ArrayList<Entry>(entriesMap.values()));
	}
}
