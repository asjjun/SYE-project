package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterVolunteerReportServlet")
public class RegisterVolunteerReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String vol_date = request.getParameter("vol_date");
		String vol_place = request.getParameter("vol_place");
		String activity_content = request.getParameter("activity_content");
		String vol_prepare_progress = request.getParameter("vol_prepare_progress");
		String image = request.getParameter("image");
		String plan_Key = request.getParameter("plan_key");
		String progress = request.getParameter("progress");
		
		register(userID,vol_date,vol_place,activity_content,vol_prepare_progress,image,plan_Key,progress);
	}

	public int register(String userID,String vol_date,String vol_place,String activity_content,String vol_prepare_progress,String image,String plan_key,String progress) {
		VolunteerReportModule vpm = new VolunteerReportModule();
		
		try {
			vpm.setUserid(userID);
			vpm.setVol_date(vol_date);
			vpm.setVol_place(vol_place);
			vpm.setActivity_content(activity_content);
			vpm.setVol_prepare_progress(vol_prepare_progress);
			vpm.setImage(image);
			vpm.setPlan_Key(Integer.parseInt(plan_key));
			vpm.setProgress(Integer.parseInt(progress));;
		}
		catch(Exception e) {
			return 0;//오류발생 0
		}
		return new UserDAO().registerReport(vpm);
	}


}
