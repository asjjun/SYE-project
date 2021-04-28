package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UserMoneyMangerServlet")
public class UserMoneyMangerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public int resutl_userMoney = 0;
	public int user_result =0;
	public int root_result =0;
	User user = new User();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		Gson gson = new Gson();
		String sel = gson.toJson(resutl_userMoney);
		response.getWriter().append(sel);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String primary = request.getParameter("primary");
		String fundingMoney = request.getParameter("fundingMoney");
		
		resutl_userMoney = FundingMoney(userID,primary,fundingMoney);
		doGet(request, response);
	}
	
	
	public int FundingMoney(String userID,String primary,String fundingMoney) {
		user = new UserDAO().searchUser(userID);
		user.setUserMoney(user.getUserMoney() - Integer.parseInt(fundingMoney));
		root_result = new UserDAO().rootFunding(userID,primary, Integer.parseInt(fundingMoney));
		user_result = new UserDAO().modify_Money(userID,user.getUserMoney());
		return user.getUserMoney();
	}

}
