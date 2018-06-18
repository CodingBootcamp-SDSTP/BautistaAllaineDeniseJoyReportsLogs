import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import java.util.ArrayList;

public class AlarmsServlet extends HttpServlet
{
	Alarms alarms = null;

	public void init() throws ServletException {
		alarms = Alarms.instance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String qs = request.getQueryString();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();
		try{
			XMLStreamWriter writer = xof.createXMLStreamWriter(out);
			response.setContentType("text/xml");
			XMLCreator xc = new XMLCreator(writer);
			if(qs != null) {
				String id = request.getParameter("id");
				if(id != null) {
					if(alarms.getAlarm(id) != null) {
						writer.writeStartDocument();
						xc.printAlarm(alarms.getAlarm(id));
						writer.writeEndDocument();
						writer.close();
					}
				}
			}
			else {
				writer.writeStartDocument();
				xc.printAlarms(alarms.getAllAlarms());
				writer.writeEndDocument();
				writer.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		alarms = null;
	}
}
