public class Site
{
	String id, name, address;

	public Site(String i, String n, String a) {
		id = i;
		name = n;
		address = a;
	}

	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return(id);
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return(name);
	}

	public void setAddress(String a) {
		address = a;
	}

	public String getAddress() {
		return(address);
	}
}
