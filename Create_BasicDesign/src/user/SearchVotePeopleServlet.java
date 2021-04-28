package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/SearchVotePeopleServlet")
public class SearchVotePeopleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ArrayList<VoteModule> voteAry = new ArrayList<VoteModule>();
	int plan_Key =0;
	int fundingNumber =0;
	int voteNumber =0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");		
		
		response.getWriter().append(voteNumber + "&" + fundingNumber);
		fundingNumber=0;
		voteNumber=0;
		plan_Key=0;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String Num = request.getParameter("Num");
		
		SearchVoteNumber(Num);
		FindPlanKey(Num);
		SearchFundingNumber(plan_Key);
		doGet(request, response);
	}
	
	public void SearchVoteNumber(String Num) {
		voteAry.clear();
		voteAry = new UserDAO().searchVote(Num);
		voteNumber = voteAry.size();
	}
	
	public void FindPlanKey(String reportNum) {
		plan_Key = new UserDAO().PlanKeyOfNum(reportNum);
	}

	public void SearchFundingNumber(int plan_Key) {
		fundingNumber = new UserDAO().searchFundingNumber(plan_Key);
	}
}
