public class Employee
{
	private String userName, password, id, firstName, lastName, department;
	private int age;

	public Employee(String i, String f, String l, int a, String d, String u, String p) {
		id = i;
		firstName = f;
		lastName = l;
		age = a;
		department = d;
		userName = u;
		password = p;
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

	public void setAge(String a) {
		age = a;
	}

	public String getAge() {
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
}
