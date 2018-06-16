public class Employees
{
	static Employees _instance = null;

	public static Employees instance() {
		if(_instance == null) {
			_instance = new Employees();
		}
		return(_instance);
	}

	HashMap<String, Employee> employeesMap;

	private Employees() {
		employeesMap = new HashMap<String, Employee>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/projectdb?user=user&" +
			"password=dict2018&serverTimezone=UTC&useSSL=false");
			readFromDB(conn);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void readFromDB(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null
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
				}
				addEmployee(row);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addEmployee(String... content) {
		employeesMap.put(content[0], new Employee(content[0], content[1], content[2], Integer.parseInt(content[3]), content[4], content[5], content[6]));
	}

	public Employee getEmployee(String i) {
		return(employeesMap.get(i));
	}
}
