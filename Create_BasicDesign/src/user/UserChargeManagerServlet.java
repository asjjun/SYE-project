package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UserChargeManagerServlet")
public class UserChargeManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    User user = new User();
    int index_resuult = 0;
    int resutl_userMoney = 0;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Gson gson = new Gson();
		String sel = gson.toJson(resutl_userMoney);
		response.getWriter().append(sel);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("userID");
		String chargeMoney = request.getParameter("chargeMoney");
		
		resutl_userMoney = chargeMoney(userID,chargeMoney);
		doGet(request, response);
	}
	
	
	public int chargeMoney(String userID,String chargeMoney) {
		user = new UserDAO().searchUser(userID);
		index_resuult = new UserDAO().modify_Money(userID, user.getUserMoney() + Integer.parseInt(chargeMoney));
		user = new UserDAO().searchUser(userID);
		return user.getUserMoney();
	}
}
