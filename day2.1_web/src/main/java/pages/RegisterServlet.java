package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDaoImpl;
import pojos.User;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns = "/register",loadOnStartup = 1)
public class RegisterServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	
	UserDaoImpl udao;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void init() throws ServletException {
		// create dao instance
		try {
		udao = new UserDaoImpl();
		}catch(Exception e) {
			throw new ServletException("Error in"+ getClass(),e);
		}
	}
	public void destroy() {
		//clean up dao
		try {
			udao.cleanUp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		User user = new User(request.getParameter("fname"),request.getParameter("lname"),request.getParameter("em"), request.getParameter("pass"),Date.valueOf(request.getParameter("date")));
		try(PrintWriter pw = response.getWriter();){
			udao.registerNewVoter(user);
			pw.print("<h3>Registartion Successful</h3><br><a href='login.html'>Login again</a>");
		} catch (Exception e) {
			throw new ServletException("Error in"+ getClass(),e);
		}
	}

}
