package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class UploadReflyServlet
 */
@WebServlet("/UploadReflyServlet")
public class UploadReflyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ArrayList<ReflyModule> replyAry = new ArrayList<ReflyModule>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Gson gson = new Gson();

		for(int i=0;i<replyAry.size();i++) {
			String allReply = gson.toJson(replyAry.get(i));
			response.getWriter().append(allReply);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String primary = request.getParameter("primary");	
		replyAry = UploadReply(primary);
		doGet(request,response);
	}

	public ArrayList<ReflyModule> UploadReply(String primary) {
		
		return new UserDAO().searchReply(primary);
	}
}
