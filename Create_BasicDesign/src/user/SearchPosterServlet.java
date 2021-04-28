package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class SearchPosterServlet
 */
@WebServlet("/SearchPosterServlet")
public class SearchPosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    UploadPosterBoard upb = new UploadPosterBoard();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		request.toString();
		response.setContentType("text/html;charset=UTF-8");
				
		Gson gson = new Gson();
		String sel = gson.toJson(upb);
		response.getWriter().append(sel);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int primaryKey = Integer.parseInt(request.getParameter("primaryKey"));
		
		upb = Search(primaryKey);
		doGet(request,response);
	}

	public UploadPosterBoard Search(int primaryKey) {
		
		return new UserDAO().searchPoster(primaryKey);
	}

}
