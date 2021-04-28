package user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/UserVoteMangerServlet")


public class UserVoteMangerServlet extends HttpServlet {
	ArrayList<VoteModule> voteAry = new ArrayList<VoteModule>();
	VoteResult vote_result = new VoteResult();
	
	public Boolean status_sel = false;
	public String status = "";
	ArrayList<FundingModule> funding_ary = new ArrayList<FundingModule>();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");		
		
		if(status.equals("false") || status.equals("trueNot")) {
			Gson gson = new Gson();
			String sel = gson.toJson(status);
			response.getWriter().append(sel);
		}
		else {
			Gson gson = new Gson();
			String voteResult = gson.toJson(vote_result);
			response.getWriter().append(voteResult);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String plankey = request.getParameter("plankey");
		String primary = request.getParameter("primary");
		
		vote_result.setTrue_Index(0);
		vote_result.setFalse_Index(0);
		status_sel = false;
				
		status = FundStatusUser(userID,plankey);
		
		if(status.equals("false")) {
			doGet(request,response);
		}
		else {
			status = VoteStatusUser(userID,primary);
			doGet(request,response);
		}
	}
	
	public String FundStatusUser(String userID,String plankey) {
		funding_ary = new UserDAO().searchFundingUser(userID);
		
		for(int i =0;i<funding_ary.size();i++) {
			if(funding_ary.get(i).getPrimaryKey() == Integer.parseInt(plankey)) {
				return "true";
			}
		}
		return "false";
	}
	
	public String VoteStatusUser(String userID,String primary) {
		voteAry = new UserDAO().searchVote(primary);
		
		for(int i=0;i<voteAry.size();i++) {
			if(voteAry.get(i).getUserID().equals(userID)) {
				status_sel = true;
				//투표를 이미했다.
			}
		}
		
		if(status_sel) {
			for(int j=0;j<voteAry.size();j++) {
				if(voteAry.get(j).getStatus().equals("true")) {
					vote_result.setTrue_Index(vote_result.getTrue_Index() + 1);
				}
				else {
					vote_result.setFalse_Index(vote_result.getFalse_Index() + 1);
				}
			}
			return "trueOk";
		}
		else {
			return "trueNot";
		}
	}
}
