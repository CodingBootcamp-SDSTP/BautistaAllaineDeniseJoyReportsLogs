import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;

public class XMLCreator
{
	static XMLStreamWriter writer;

	public XMLCreator(XMLStreamWriter w) {
		writer = w;
	}

	public void printEmployees(ArrayList<Employee> employeesArray) throws XMLStreamException {
		writer.writeStartElement("employeelist");
		for(Employee e : employeesArray) {
			printEmployee(e);
		}
		writer.writeEndElement();
	}

	public void printEmployee(Employee e) throws XMLStreamException {
		writer.writeStartElement("employee");
		writer.writeStartElement("id");
		writer.writeCharacters(e.getId());
		writer.writeEndElement();
		writer.writeStartElement("firstName");
		writer.writeCharacters(e.getFirstName());
		writer.writeEndElement();
		writer.writeStartElement("lastName");
		writer.writeCharacters(e.getLastName());
		writer.writeEndElement();
		writer.writeStartElement("age");
		writer.writeCharacters(Integer.toString(e.getAge()));
		writer.writeEndElement();
		writer.writeStartElement("department");
		writer.writeCharacters(e.getDepartment());
		writer.writeEndElement();
		writer.writeStartElement("username");
		writer.writeCharacters(e.getUserName());
		writer.writeEndElement();
		writer.writeStartElement("password");
		writer.writeCharacters(e.getPassword());
		writer.writeEndElement();
		writer.writeEndElement();
	}

	public void printSites(ArrayList<Site> sitesArray) throws XMLStreamException {
		writer.writeStartElement("siteslist");
		for(Site s : sitesArray) {
			printSite(s);
		}
		writer.writeEndElement();
	}

	public void printSite(Site s) throws XMLStreamException {
		writer.writeStartElement("site");
		writer.writeStartElement("id");
		writer.writeCharacters(s.getId());
		writer.writeEndElement();
		writer.writeStartElement("name");
		writer.writeCharacters(s.getName());
		writer.writeEndElement();
		writer.writeStartElement("address");
		writer.writeCharacters(s.getAddress());
		writer.writeEndElement();
		writer.writeEndElement();
	}

	public void printAlarms(ArrayList<Alarm> alarmsArray) throws XMLStreamException {
		writer.writeStartElement("alarmslist");
		for(Alarm a : alarmsArray) {
			printAlarm(a);
		}
		writer.writeEndElement();
	}

	public void printAlarm(Alarm a) throws XMLStreamException {
		writer.writeStartElement("alarm");
		writer.writeStartElement("id");
		writer.writeCharacters(a.getId());
		writer.writeEndElement();
		writer.writeStartElement("name");
		writer.writeCharacters(a.getName());
		writer.writeEndElement();
		writer.writeStartElement("description");
		writer.writeCharacters(a.getDescription());
		writer.writeEndElement();
		writer.writeStartElement("technology");
		writer.writeCharacters(a.getTechnology());
		writer.writeEndElement();
		writer.writeEndElement();
	}

	public void printLogs(ArrayList<Entry> entriesArray) throws XMLStreamException {
		writer.writeStartElement("entrieslist");
		for(Entry e : entriesArray) {
			printEntry(e);
		}
		writer.writeEndElement();
	}

	public void printEntry(Entry e) throws XMLStreamException {
		writer.writeStartElement("entry");
		writer.writeStartElement("id");
		writer.writeCharacters(Integer.toString(e.getId()));
		writer.writeEndElement();
		writer.writeStartElement("alarmid");
		writer.writeCharacters(e.getAlarmId());
		writer.writeEndElement();
		writer.writeStartElement("siteid");
		writer.writeCharacters(e.getSiteId());
		writer.writeEndElement();
		writer.writeStartElement("action");
		writer.writeCharacters(e.getAction());
		writer.writeEndElement();
		writer.writeStartElement("remarks");
		writer.writeCharacters(e.getRemarks());
		writer.writeEndElement();
		writer.writeStartElement("employeeid");
		writer.writeCharacters(e.getEmployeeId());
		writer.writeEndElement();
		writer.writeStartElement("time");
		writer.writeCharacters(e.getTime());
		writer.writeEndElement();
		writer.writeEndElement();
	}
}
