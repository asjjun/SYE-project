package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userBirth = request.getParameter("userBirth");
		String userAddr = request.getParameter("userAddr");
		String usermobile = request.getParameter("usermobile");
		int userMoney = Integer.parseInt(request.getParameter("userMoney"));
		String userFavorite = request.getParameter("userFavorite");
				
		register(userID,userPassword,userName,userGender,userBirth,userAddr,usermobile,userMoney,userFavorite);
	}

	public int register(String userID,String userPassword,String userName,String userGender,String userBirth,String userAddr,String usermobile,int userMoney,String userFavorite) {
		User user = new User();
		try {
			user.setUserCode("");
			user.setUserID(userID);
			user.setUserPassword(userPassword);
			user.setUserName(userName);
			user.setUserGender(userGender);
			user.setUserBirth(userBirth);
			user.setUserAddr(userAddr);
			user.setUsermobile(usermobile);
			user.setUserMoney(userMoney);
			user.setUserFavorite(userFavorite);
		}
		catch(Exception e) {
			return 0;//오류발생 0
		}
		return new UserDAO().registerUser(user);
	}
}
