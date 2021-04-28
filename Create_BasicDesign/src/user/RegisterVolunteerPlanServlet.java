package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterVolunteerPlanServlet")
public class RegisterVolunteerPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String vol_date = request.getParameter("vol_date");
		String vol_time = request.getParameter("vol_time");
		String palce = request.getParameter("palce");
		String vol_goal = request.getParameter("vol_goal");
		String activity_content = request.getParameter("activity_content");
		String vol_prepare = request.getParameter("vol_prepare");
		String etc = request.getParameter("etc");
				
		int s = register(userID,vol_date,vol_time,palce,vol_goal,activity_content,vol_prepare,etc);
	}

	public int register(String userID,String vol_date,String vol_time,String palce,String vol_goal,String activity_content,String vol_prepare,String etc) {
		VolunteerPlanModule vpm = new VolunteerPlanModule();
		try {
			vpm.setUserid(userID);
			vpm.setVol_date(vol_date);
			vpm.setVol_time(vol_time);
			vpm.setPalce(palce);
			vpm.setVol_goal(vol_goal);
			vpm.setActivity_content(activity_content);
			vpm.setVol_prepare(vol_prepare);
			vpm.setEtc(etc);
		}
		catch(Exception e) {
			return 0;//오류발생 0
		}
		return new UserDAO().registerPlan(vpm);
	}

}
