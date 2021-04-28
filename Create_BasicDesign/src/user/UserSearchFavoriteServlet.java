package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


@WebServlet("/UserSearchFavoriteServlet")
public class UserSearchFavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	User user = new User();
	ArrayList<UploadPosterBoard> poster_ary = new ArrayList<UploadPosterBoard>();
	String favorite_Key[];
	Boolean status =true;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		if(status) {
			Gson gson = new Gson();
			for(int i=0;i<poster_ary.size();i++) {
				String allPoster = gson.toJson(poster_ary.get(i));
				response.getWriter().append(allPoster);
			}
		}
		else {
			response.getWriter().append("false");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		poster_ary.clear();
		status =true;
		
		searchFavorite(userID);
		doGet(request,response);
	}
	
	public void searchFavorite(String userID) {
		user = new UserDAO().searchUser(userID);
		if(user.getUserFavorite()==null) {
			user.setUserFavorite("");
		}
		
		if(!user.getUserFavorite().equals("")) {
			favorite_Key = user.getUserFavorite().split(",");
			for(int i =0;i<favorite_Key.length;i++) {
				poster_ary.add(new UserDAO().searchPoster(Integer.parseInt(favorite_Key[i])));
			}
		}
		else {
			status = false;
		}
	}
}
