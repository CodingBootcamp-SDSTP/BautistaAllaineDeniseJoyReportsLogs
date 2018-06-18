import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import java.util.ArrayList;

public class EmployeesServlet extends HttpServlet
{
	Employees employees = null;

	public void init() throws ServletException {
		employees = Employees.instance();
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
				String username = request.getParameter("username");
				if(username != null) {
					if(employees.getEmployee(username) != null) {
						writer.writeStartDocument();
						xc.printEmployee(employees.getEmployee(username));
						writer.writeEndDocument();
						writer.close();
					}
				}
			}
			else {
				writer.writeStartDocument();
				xc.printEmployees(employees.getAllEmployees());
				writer.writeEndDocument();
				writer.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		employees = null;
	}

}