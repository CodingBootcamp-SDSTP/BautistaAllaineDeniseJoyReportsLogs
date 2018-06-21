import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;

public class AddEngineerServlet extends HttpServlet
{
	Engineers engineers = null;

	public void init() throws ServletException {
		engineers = Engineers.instance();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		BufferedReader reader = request.getReader();
		String content = reader.readLine();
		String[] rows = content.split("&");
		String[] data = new String[rows.length];
		for(int i = 0; i < rows.length; i++) {
			String[] split = rows[i].split("=");
			if(split.length < 2) {
				data[i] = null;
			}
			else {
				data[i] = (rows[i].split("="))[1];
			}
		}
		engineers.writeToDB(data);
	}

	public void destroy() {
		engineers = null;
	}

}
