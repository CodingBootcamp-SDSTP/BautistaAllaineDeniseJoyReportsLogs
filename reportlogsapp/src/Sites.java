import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;

public class Sites
{
	static Sites _instance = null;

	public static Sites instance() {
		if(_instance == null) {
			_instance = new Sites();
		}
		return(_instance);
	}

	HashMap<String, Site> sitesMap;

	private Sites() {
		sitesMap = new HashMap<String, Site>();
		Connection conn = DatabaseConnector.instance().getConnection();
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
			rs = stmt.executeQuery("SELECT * FROM sites;");
			while(rs.next()) {
				String[] row = {
					rs.getString("id"),
					rs.getString("name"),
					rs.getString("address")
				};
				addSite(row);
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

	public void addSite(String... content) {
		sitesMap.put(content[0], new Site(content[0], content[1], content[2]));
	}

	public Site getSite(String i) {
		return(sitesMap.get(i));
	}

	public ArrayList<Site> getAllSites() {
		return(new ArrayList<Site>(sitesMap.values()));
	}
}
