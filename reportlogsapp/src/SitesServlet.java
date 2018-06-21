import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;

public class SitesServlet extends HttpServlet
{
	Sites sites = null;

	public void init() throws ServletException {
		sites = Sites.instance();
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
					if(sites.getSite(id) != null) {
						writer.writeStartDocument();
						xc.printSite(sites.getSite(id));
						writer.writeEndDocument();
						writer.close();
					}
				}
			}
			else {
				writer.writeStartDocument();
				xc.printSites(sites.getAllSites());
				writer.writeEndDocument();
				writer.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		sites = null;
	}
}
