package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDaoImpl;
import pojos.User;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet(urlPatterns = "/login",loadOnStartup = 1)
public class AuthenticationServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	UserDaoImpl udao;

	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		// create dao instance
		try {
		udao = new UserDaoImpl();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		//clean up dao
		try {
			udao.cleanUp();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set resp cont type
		response.setContentType("text/html");
		//get PW
		try (PrintWriter pw = response.getWriter();){
			// get req params
			String email = request.getParameter("em");
			String password = request.getParameter("pass");
			//invoke dao's CRUD 	
			User user = udao.authenticateUser(email, password);
		
			if(user == null) {
				pw.print("<h3>Invalid login Please Try Again</h3><br><a href='login.html'>Login again</a>");
			}else {
			
			HttpSession session = request.getSession();
			
			session.setAttribute("user_info", user);
			
			if(user.getRole().equals("admin")) {//if user role is admin
				response.sendRedirect("admin_page");
			}else {
				if(user.isVotingStatus()) {//if voter already voted
					response.sendRedirect("logout");
				}
				else {//if user role is voter
					response.sendRedirect("candidate_list");
				}
			}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServletException("Error:",e);
		}
		//null => invalid login--> send err mesg n retry link
		//valid login : send success mesg + user details
	}

}
