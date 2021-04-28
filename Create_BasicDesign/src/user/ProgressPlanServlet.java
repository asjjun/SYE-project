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
 * Servlet implementation class ProgressPlanServlet
 */
@WebServlet("/ProgressPlanServlet")
public class ProgressPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ArrayList<VolunteerPlanModule> planList = new ArrayList<VolunteerPlanModule>();
	public int index=0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
				
		Gson gson = new Gson();
		String sel = gson.toJson(index);
		response.getWriter().append(sel);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		index = 0;
		planList.clear();
		planList = new UserDAO().FindPlanProgress(userID);
		
		for(int i =0;i<planList.size();i++){
			if(!planList.get(i).getEtc().equals("false")) {
				index++;
			}
		}
		
		doGet(request, response);
	}

}
