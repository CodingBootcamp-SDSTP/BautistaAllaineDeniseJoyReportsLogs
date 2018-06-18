import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class PasswordServlet extends HttpServlet
{
	Employees employees = null;

	public void init() throws ServletException {
		employees = Employees.instance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String question = request.getParameter("username");
		if(question != null) {
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			out.print("<password>");
			if(employees.getEmployee(question) != null) {
				Employee e = employees.getEmployee(question);
				out.print(e.getPassword());
			}
			out.print("</password>");
			out.close();
		}
	}

	public void destroy() {
		employees = null;
	}
}
