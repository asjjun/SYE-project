package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class UserIDSearchServlet
 */
@WebServlet("/UserIDSearchServlet")
public class UserIDSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Boolean status = false;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
				
		Gson gson = new Gson();
		String sel = gson.toJson(status);
		response.getWriter().append(sel);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		
		status = Search(userID);
		doGet(request,response);
	}
	
	public Boolean Search(String userID) {
		return new UserDAO().searchUserID(userID);
	}
}
