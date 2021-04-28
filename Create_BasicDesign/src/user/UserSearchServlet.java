package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    User user = new User();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Gson gson = new Gson();
		String sel = gson.toJson(user);
		response.getWriter().append(sel);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		
		user = Search(userID);
		if(user.getUserFavorite()==null) {
			user.setUserFavorite("");
		}
		doGet(request,response);
	}

	public User Search(String userID) {
		
		return new UserDAO().searchUser(userID);
	}
    
    
}
