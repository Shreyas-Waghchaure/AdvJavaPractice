package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDaoImpl;
import pojos.Candidate;
import pojos.User;

/**
 * Servlet implementation class candidate_list
 */
@WebServlet("/candidate_list")
public class candidate_list extends HttpServlet {
//	private static final long serialVersionUID = 1L;
	CandidateDaoImpl canDao;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		try {
			canDao = new CandidateDaoImpl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException("Error in candidate init:",e);
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
		try {
			canDao.cleanup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error: "+ e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		try(PrintWriter pw = response.getWriter()){
			HttpSession h1  = request.getSession();
			
			User user = (User) h1.getAttribute("user_info");
			if(user != null) {
			pw.print("<h3>Welcome,"+user.getFirstName()+"</h3>");
			
			List<Candidate> candidate = canDao.getAllCandidates();
				pw.print("Candidate List");
				for (Candidate c : candidate) {
					pw.print("<ul><li>"+c.getName()+"   "+c.getParty()+"</ul></li>");
				}
			
			
			}else {
				pw.print("Something went wrong");
			}
			
		}catch (Exception e) {
			throw new ServletException("Error: ",e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
