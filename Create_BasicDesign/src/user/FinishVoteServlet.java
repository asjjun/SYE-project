package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/FinishVoteServlet")
public class FinishVoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public int plan_Key=0;
    ArrayList<VoteModule> voteAry = new ArrayList<VoteModule>();
    VoteResult vote_result = new VoteResult();
    EventModule module = new EventModule();
    public int total_Money =0;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Gson gson = new Gson();
		String Resultmodule = gson.toJson(module);
		response.getWriter().append(Resultmodule);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String reportNum = request.getParameter("reportNum");	
		FindPlanKey(reportNum);
		if(plan_Key!=0) {
			FindUserID(plan_Key);
			unUsedReport(reportNum);
			unUsedPlan(plan_Key);
			VoteResultAction(reportNum);
			removeVoteData(reportNum);
			doGet(request, response);
		}
	}
	
	public void FindPlanKey(String reportNum) {
		plan_Key = new UserDAO().PlanKeyOfNum(reportNum);
	}
	
	public void FindUserID(int plan_Key) {
		String userID = new UserDAO().SearchUserIDOfPlanKey(plan_Key);
		System.out.println(userID);
		module.clear();
		module.setUserID(userID);
	}
	
	public void unUsedReport(String reportNum) {
		new UserDAO().modify_ReportProgress(reportNum);
	}
	
	public void unUsedPlan(int plan_Key) {
		new UserDAO().modify_PlanProgress(plan_Key);
	}
	
	public void VoteResultAction(String reportNum) {
		voteAry = new UserDAO().searchVote(reportNum);
		total_Money = 0;
		vote_result.clear();
		for(int j=0;j<voteAry.size();j++) {
			if(voteAry.get(j).getStatus().equals("true")) {
				vote_result.setTrue_Index(vote_result.getTrue_Index() + 1);
			}
			else {
				vote_result.setFalse_Index(vote_result.getFalse_Index() + 1);
			}
		}
		
		if(vote_result.getTrue_Index() >= vote_result.getFalse_Index()) {
			total_Money = new UserDAO().SearchPlanFundingMoney(plan_Key);
			System.out.println(total_Money);
			new UserDAO().removeFundingData(plan_Key);
			module.setMoney(String.valueOf(total_Money));
		}
		else {
			module.setMoney("0");
		}
	}
	
	public void removeVoteData(String reportNum) {
		new UserDAO().removeVoteData(reportNum);
	}
}
