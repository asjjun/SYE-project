package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/AddFavoriteServlet")
public class AddFavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ArrayList<String> keyList = new ArrayList<String>();
	User user = new User();
	public String splitAry[];
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String primaryKey = request.getParameter("primaryKey");
		String status = request.getParameter("status");
		
		addFavorite(userID,primaryKey,status);
		doGet(request,response);
	}
	
	public void addFavorite(String userID,String primaryKey,String status) {
		user = new UserDAO().searchUser(userID);
		String modify_Favorite = "";
		int key =0;
		int k=0;
		
		if(user.getUserFavorite()==null) {
			user.setUserFavorite("");
		}
		
		if(status.equals("true")) {
			if(user.getUserFavorite().equals("")) {
				modify_Favorite = primaryKey;
			}
			else {
				modify_Favorite = user.getUserFavorite() + "," + primaryKey;
			}
		}
		else {
			if(user.getUserFavorite().contains(",")) {
				splitAry = user.getUserFavorite().split(",");
				
				for(int i =0;i<splitAry.length-1;i++) {
					keyList.add(splitAry[i]);
				}
			}
			else {
				keyList.add(user.getUserFavorite());
			}
			
			for(int i =0;i<keyList.size();i++) {
				if(primaryKey.equals(keyList.get(i))) {
					keyList.remove(i);
					break;
				}
			}
			
			if(keyList.size() > 0) {
				modify_Favorite = keyList.get(0);
			}
			
			for(int i=1;i<keyList.size();i++) {
				modify_Favorite += ("," + keyList.get(i));
			}
		}
		new UserDAO().modify_Favorite(userID,modify_Favorite);
	}
}
