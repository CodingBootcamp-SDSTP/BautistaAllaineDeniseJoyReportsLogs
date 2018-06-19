public class Entry
{
	private String alarmid, siteid, action, remarks, employeeid, time;
	final int ID;

	public Entry(int i, String aid, String s, String a, String r, String e, String t) {
		ID = i;
		alarmid = aid;
		siteid = s;
		action = a;
		remarks = r;
		employeeid = e;
		time = t;
	}

	public int getId() {
		return(ID);
	}

	public void setAlarmId(String a) {
		alarmid = a;
	}

	public String getAlarmId() {
		return(alarmid);
	}

	public void setSiteId(String s) {
		siteid = s;
	}

	public String getSiteId() {
		return(siteid);
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

	public void setEmployeeId(String e) {
		employeeid = e;
	}

	public String getEmployeeId() {
		return(employeeid);
	}

	public void setTime(String t) {
		time = t;
	}

	public String getTime() {
		return(time);
	}
}
