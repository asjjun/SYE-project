package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/LoginProcServlet")
public class LoginProcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	User user = new User();
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
		String userPassword = request.getParameter("userPassword");
		
		status = confrimUser(userID,userPassword);
		doGet(request,response);
	}
	
		
	public boolean confrimUser(String userID,String userPassword) {
		user = new UserDAO().searchUser(userID);
		if(user.getUserPassword()!=null && user.getUserPassword().equals(userPassword)) {
			return true;
		}
		return false;
	}

}
