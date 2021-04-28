package user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UploadPosterServlet")
public class UploadPosterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<UploadPosterBoard> posterList = new ArrayList<UploadPosterBoard>();
	ArrayList<VolunteerPlanModule> planList = new ArrayList<VolunteerPlanModule>();
	ArrayList<VolunteerReportModule> reportList = new ArrayList<VolunteerReportModule>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Gson gson = new Gson();

		for(int i=0;i<posterList.size();i++) {
			String allPoster = gson.toJson(posterList.get(i));
			response.getWriter().append(allPoster);
		}
		for(int i=0;i<planList.size();i++) {
			String allPoster = gson.toJson(planList.get(i));
			response.getWriter().append(allPoster);
		}
		for(int i=0;i<reportList.size();i++) {
			String allPoster = gson.toJson(reportList.get(i));
			response.getWriter().append(allPoster);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		posterList.clear();
		planList.clear();
		reportList.clear();
		posterList = new UserDAO().uploadPoster();
		planList = new UserDAO().uploadPlan();
		reportList = new UserDAO().uploadReport();
		doGet(request,response);
	}
}
