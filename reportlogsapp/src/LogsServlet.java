import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import java.util.ArrayList;

public class LogsServlet extends HttpServlet
{
	Logs logs = null;

	public void init() throws ServletException {
		logs = Logs.instance();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String qs = request.getQueryString();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		try{
			XMLStreamWriter writer = xof.createXMLStreamWriter(out);
			response.setContentType("text/xml");
			XMLCreator xc = new XMLCreator(writer);
			ArrayList<Entry> entries = logs.getAllEntries();
			if(qs != null) {
				String id = request.getParameter("id");
				if(id != null) {
					entries = logs.searchEntries(entries, id, "id");
				}
				String alarm = request.getParameter("alarm");
				if(alarm != null) {
					entries = logs.searchEntries(entries, alarm.toLowerCase(), "alarm");
				}
				String site = request.getParameter("site");
				if(site != null) {
					entries = logs.searchEntries(entries, site.toLowerCase(), "site");
				}
				String action = request.getParameter("action");
				if(action != null) {
					entries = logs.searchEntries(entries, action.toLowerCase(), "action");
				}
				String remarks = request.getParameter("remarks");
				if(remarks != null) {
					entries = logs.searchEntries(entries, remarks.toLowerCase(), "remarks");
				}
				String engineer = request.getParameter("engineer");
				if(engineer != null) {
					entries = logs.searchEntries(entries, engineer.toLowerCase(), "engineer");
				}
			}
			writer.writeStartDocument();
			xc.printLogs(entries);
			writer.writeEndDocument();
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		logs = null;
	}
}
