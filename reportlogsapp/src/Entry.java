public class Entry
{
	private String action, remarks,time;
	private Alarm alarm;
	private Site site;
	private Engineer engineer;
	final int ID;

	public Entry(int i, Alarm al, Site s, String a, String r, Engineer e, String t) {
		ID = i;
		alarm = al;
		site = s;
		action = a;
		remarks = r;
		engineer = e;
		time = t;
	}

	public int getId() {
		return(ID);
	}

	public void setAlarm(Alarm a) {
		alarm = a;
	}

	public Alarm getAlarm() {
		return(alarm);
	}

	public void setSite(Site s) {
		site = s;
	}

	public Site getSite() {
		return(site);
	}

	public void setAction(String a) {
		action = a;
	}

	public String getAction() {
		return(action);
	}

	public void setRemarks(String r) {
		remarks = r;
	}

	public String getRemarks() {
		return(remarks);
	}

	public void setEngineer(Engineer e) {
		engineer = e;
	}

	public Engineer getEngineer() {
		return(engineer);
	}

	public void setTime(String t) {
		time = t;
	}

	public String getTime() {
		return(time);
	}

	public boolean match(String str, String type) {
		boolean ismatch = false;
		switch(type) {
			case "id":
				if(Integer.toString(ID).contains(str)) {
					ismatch = true;
				}
				break;
			case "alarm":
				String alarmLower = alarm.getName().toLowerCase();
				if(alarmLower.contains(str)) {
					ismatch = true;
				}
				break;
			case "site":
				String siteLower = site.getName().toLowerCase();
				if(siteLower.contains(str)) {
					ismatch = true;
				}
				break;
			case "action":
				if(action.toLowerCase().contains(str)) {
					ismatch = true;
				}
				break;
			case "remarks":
				if(remarks.toLowerCase().contains(str)) {
					ismatch = true;
				}
				break;
			case "engineer":
				String firstLower = engineer.getFirstName().toLowerCase();
				String lastLower = engineer.getLastName().toLowerCase();
				if(firstLower.contains(str) || lastLower.contains(str)) {
					ismatch = true;
				}
				break;
		}
		return(ismatch);
	}
}
