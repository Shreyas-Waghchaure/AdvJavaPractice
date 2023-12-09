package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
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
 * Servlet implementation class admin_page
 */
@WebServlet("/admin_page")
public class admin_page extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	CandidateDaoImpl canDao;
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			canDao = new CandidateDaoImpl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException();
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
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		
		try(PrintWriter pw = response.getWriter()){
			
			HttpSession h2 = request.getSession();
			
			User admin = (User)h2.getAttribute("user_info");
			
			pw.print("<h1>Welcome,Admin  "+admin.getFirstName()+"</h1>");
			
			List<Candidate> top2= canDao.getTopTwo();
			pw.print("<h3>Top Two Candidate</h3>");
			for(Candidate c : top2) {
				pw.print("<ul><li>"+c.getName()+"    "+c.getParty()+"    "+c.getVotes()+"</li></ul>");
			}
			pw.print("<h3>Party Wise votes </h3>");
			Map<String,Integer>votes= canDao.partyWiseVotes();
			
			votes.forEach((k,v)->pw.print("<ul><li>"+k+"   "+v+"</ul></li>"));
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServletException();
		}
		
	}


}
