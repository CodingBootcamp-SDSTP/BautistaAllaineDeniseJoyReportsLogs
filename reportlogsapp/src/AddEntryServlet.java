import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import java.util.ArrayList;
import java.io.BufferedReader;

public class AddEntryServlet extends HttpServlet
{
	Logs logs = null;

	public void init() throws ServletException {
		logs = Logs.instance("from addentry");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String content = reader.readLine();
		String[] rows = content.split("&");
		String[] data = new String[rows.length + 1];
		for(int i = 1; i < rows.length + 1; i++) {
			String[] split = rows[i - 1].split("=");
			if(split.length < 2) {
				data[i] = null;
			}
			else {
				data[i] = (rows[i - 1].split("="))[1];
			}
		}
		logs.writeToDB(data);
	}

	public void destroy() {
		logs = null;
	}

}
