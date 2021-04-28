package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterPosterServlet
 */
@WebServlet("/RegisterPosterServlet")
public class RegisterPosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String boardTitle = request.getParameter("boardTitle");
		String boardContent = request.getParameter("boardContent");
		String imgURL = request.getParameter("imgURL");
				
		register(userID,boardTitle,boardContent,imgURL);
	}

	public int register(String userID,String boardTitle,String boardContent,String imgURL) {
		UploadPosterBoard upb = new UploadPosterBoard();
		try {
			upb.setPrimaryKey("");
			upb.setUserID(userID);
			upb.setBoardTitle(boardTitle);
			upb.setBoardContent(boardContent);
			upb.setImgURL(imgURL);
		}
		catch(Exception e) {
			return 0;//오류발생 0
		}
		return new UserDAO().registerPoster(upb);
	}
}
