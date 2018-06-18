public class Alarm
{
	String id, name, description, technology;

	public Alarm(String i, String n, String d, String t) {
		id = i;
		name = n;
		description = d;
		technology = t;
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

	public void setDescription(String d) {
		description = d;
	}

	public String getDescription() {
		return(description);
	}

	public void setTechnology(String t) {
		technology = t;
	}

	public String getTechnology() {
		return(technology);
	}
}
