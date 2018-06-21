public class Engineer
{
	private String userName, password, id, firstName, lastName, department;
	private int age;
	private boolean isAdmin;

	public Engineer(String i, String f, String l, int a, String d, String u, String p, boolean ia) {
		id = i;
		firstName = f;
		lastName = l;
		age = a;
		department = d;
		userName = u;
		password = p;
		isAdmin = ia;
	}

	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return(id);
	}

	public void setFirstName(String f) {
		firstName = f;
	}

	public String getFirstName() {
		return(firstName);
	}

	public void setAge(int a) {
		age = a;
	}

	public int getAge() {
		return(age);
	}

	public void setDepartment(String d) {
		department = d;
	}

	public String getDepartment() {
		return(department);
	}

	public void setLastName(String l) {
		lastName = l;
	}

	public String getLastName() {
		return(lastName);
	}

	public void setUserName(String u) {
		userName = u;
	}

	public String getUserName() {
		return(userName);
	}

	public void setPassword(String p) {
		password = p;
	}

	public String getPassword() {
		return(password);
	}

	public void setIsAdmin(boolean i) {
		isAdmin = i;
	}

	public boolean getIsAdmin() {
		return(isAdmin);
	}
}
