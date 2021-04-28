package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/Board_DB?serverTimezone=Asia/Seoul&useSSL=false";
			String dbID = "root";   // 입력 후 사용
			String dbPassword = ""; // 입력 후 사용
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<FundingModule> searchFundingUser(String userID){
		String SQL = "SELECT * FROM rootfundingtbl WHERE userID LIKE ?";
		ArrayList<FundingModule> fundingList = new ArrayList<FundingModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				FundingModule fundingmodule = new FundingModule();
				fundingmodule.setNum(rs.getInt(1));
				fundingmodule.setPrimaryKey(rs.getInt(2));
				fundingmodule.setUserID(rs.getString(3));
				fundingmodule.setFundingMoney(rs.getInt(4));
				fundingList.add(fundingmodule);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return fundingList;
	}
	
	public int searchFundingNumber(int plan_Key){
		String SQL = "SELECT * FROM rootfundingtbl WHERE primarykey LIKE ?";
		int index=0;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,plan_Key);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				index++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return index;
	}
	
	public int modify_MemberInfo(String userID,String userName,String userGender,String userAddr,String usermobile) {
		String SQL = "update usertbl set name=\"" + userName + "\" ,usergender=\"" + userGender + "\" ,addr=\"" + userAddr + "\",mobile=\"" + usermobile + "\" where userID=\"" + userID + "\"";
		try {
			pstmt = conn.prepareStatement(SQL);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int modify_Favorite(String userID,String userFavorite) {
		String SQL = "update usertbl set userfavorite=\"" + userFavorite + "\" where userID=\"" + userID + "\"";
		try {
			pstmt = conn.prepareStatement(SQL);
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int modify_PlanProgress(int plan_Key) {
		String SQL = "update volunteerplan set etc=? where num=" + plan_Key + "";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "false");
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int modify_ReportProgress(String reportNum) {
		String SQL = "update reportvolunteer set progress=? where num=" + Integer.parseInt(reportNum) + "";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, 0);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int modify_Money(String userID,int chargeMoney) {
		String SQL = "update usertbl set usermoney=? where userID=\"" + userID + "\"";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, chargeMoney);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int rootFunding(String userID,String primary,int fundingMoney) {
		String SQL = "INSERT INTO rootfundingtbl VALUES (?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setString(2,primary);
			pstmt.setString(3,userID);
			pstmt.setInt(4,fundingMoney);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int registerReport(VolunteerReportModule vpm) {
		String SQL = "INSERT INTO reportvolunteer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setString(2,vpm.getUserid());
			pstmt.setString(3,vpm.getVol_date());
			pstmt.setString(4, vpm.getVol_place());
			pstmt.setString(5, vpm.getActivity_content());
			pstmt.setString(6, vpm.getVol_prepare_progress());
			pstmt.setString(7, vpm.getImage());
			pstmt.setInt(8, vpm.getPlan_Key());
			pstmt.setInt(9, vpm.getProgress());
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int PlanKeyOfNum(String reportNum) {
		String SQL = "SELECT * FROM reportvolunteer WHERE progress = 1 AND num LIKE ?";
		int plan_Key = 0;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,Integer.parseInt(reportNum));
			rs = pstmt.executeQuery();
			while(rs.next()) {
				plan_Key = rs.getInt(8);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return plan_Key;
	}
	
	
	public int SearchPlanKey(String userID) {
		String SQL = "SELECT * FROM reportvolunteer WHERE progress = 1 AND userID LIKE ?";
		int plan_Key = 0;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				plan_Key = rs.getInt(8);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return plan_Key;
	}
	
	public String SearchUserIDOfPlanKey(int plan_Key) {
		String SQL = "SELECT * FROM reportvolunteer WHERE progress = 1 AND plan_Key LIKE ?";
		String userID ="";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,plan_Key);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				userID = rs.getString(2);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return userID;
	}
	
	public int SearchplanNum(String userID) {
		String SQL = "SELECT * FROM volunteerplan WHERE userID LIKE ?";
		int Num = 0;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Num = rs.getInt(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Num;
	}
	
	public int registerPlan(VolunteerPlanModule vpm) {
		String SQL = "INSERT INTO volunteerplan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setString(2,vpm.getUserid());
			pstmt.setString(3,vpm.getVol_date());
			pstmt.setString(4, vpm.getVol_time());
			pstmt.setString(5, vpm.getPalce());
			pstmt.setString(6,vpm.getVol_goal());
			pstmt.setString(7, vpm.getActivity_content());
			pstmt.setString(8,vpm.getVol_prepare());
			pstmt.setString(9, vpm.getEtc());
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public User searchUser(String userID){
		String SQL = "SELECT * FROM usertbl WHERE userID LIKE ?";
		User user = new User();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				user.setUserCode(rs.getString(1));
				user.setUserID(rs.getString(2));
				user.setUserPassword(rs.getString(3));
				user.setUserName(rs.getString(4));
				user.setUserGender(rs.getString(5));
				user.setUserBirth(rs.getString(6));
				user.setUserAddr(rs.getString(7));
				user.setUsermobile(rs.getString(8));
				user.setUserMoney(rs.getInt(9));
				user.setUserFavorite(rs.getString(10));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public int SearchPlanFundingMoney(int planNum){
		String SQL = "SELECT * FROM rootfundingtbl WHERE primarykey LIKE ?";
		int total_Money = 0;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,planNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				total_Money += rs.getInt(4);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return total_Money;
	}
	
	public ArrayList<VoteModule> searchVote(String primary){
		String SQL = "SELECT * FROM votetbl WHERE primarykey LIKE ?";
		ArrayList<VoteModule> voteAry = new ArrayList<VoteModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,primary);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VoteModule vote = new VoteModule();
				vote.setNum(rs.getInt(1));
				vote.setPrimary(rs.getString(2));
				vote.setUserID(rs.getString(3));
				vote.setStatus(rs.getString(4));
				voteAry.add(vote);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return voteAry;
	}
	
	public ArrayList<ReflyModule> searchReply(String primary){
		String SQL = "SELECT * FROM reply WHERE primarykey LIKE ?";
		ArrayList<ReflyModule> replyAry = new ArrayList<ReflyModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,primary);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReflyModule reflymodule = new ReflyModule();
				reflymodule.setNum(rs.getInt(1));
				reflymodule.setPrimaryKey(rs.getInt(2));
				reflymodule.setUserID(rs.getString(3));
				reflymodule.setReplycontent(rs.getString(4));
				reflymodule.setWRI_DTT(rs.getString(5));
				replyAry.add(reflymodule);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return replyAry;
	}
	
	public Boolean searchUserID(String userID){
		String SQL = "SELECT * FROM usertbl WHERE userID LIKE ?";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public UploadPosterBoard searchPoster(int primaryKey){
		String SQL = "SELECT * FROM boardtbl WHERE num LIKE ?";
		UploadPosterBoard upb = new UploadPosterBoard();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, primaryKey);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				upb.setPrimaryKey(rs.getString(1));
				upb.setUserID(rs.getString(2));
				upb.setBoardTitle(rs.getString(3));
				upb.setBoardContent(rs.getString(4));
				upb.setImgURL(rs.getString(5));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return upb;
	}
	
	public int removeFundingData(int plan_Key) {
		String SQL = "DELETE FROM rootfundingtbl WHERE primarykey=?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, plan_Key);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int removeVoteData(String reportNum) {
		String SQL = "DELETE FROM votetbl WHERE primarykey=?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(reportNum));
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int registerUser(User user) {
		String SQL = "INSERT INTO usertbl VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setString(2,user.getUserID());
			pstmt.setString(3,user.getUserPassword());
			pstmt.setString(4, user.getUserName());
			pstmt.setString(5, user.getUserGender());
			pstmt.setString(6,user.getUserBirth());
			pstmt.setString(7, user.getUserAddr());
			pstmt.setString(8,user.getUsermobile());
			pstmt.setInt(9, user.getUserMoney());
			pstmt.setString(10, user.getUserFavorite());
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}

	public int registerVote(String primary,String userID,String status) {
		String SQL = "INSERT INTO votetbl VALUES (?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setInt(2,Integer.parseInt(primary));
			pstmt.setString(3,userID);
			pstmt.setString(4, status);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	
	public int registerPoster(UploadPosterBoard uploadposterboard) {
		String SQL = "INSERT INTO boardtbl VALUES (?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setString(2,uploadposterboard.getUserID());
			pstmt.setString(3,uploadposterboard.getBoardTitle());
			pstmt.setString(4, uploadposterboard.getBoardContent());
			pstmt.setString(5, uploadposterboard.getImgURL());
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public int registerReply(String userID,String primary,String replycontent) {
		String SQL = "INSERT INTO reply VALUES (?, ?, ?, ?, now())";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,null);
			pstmt.setInt(2,Integer.parseInt(primary));
			pstmt.setString(3,userID);
			pstmt.setString(4, replycontent);
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
	
	public ArrayList<UploadPosterBoard> uploadPoster() {
		String SQL = "SELECT * FROM boardtbl ORDER BY num DESC LIMIT 5;";
		ArrayList<UploadPosterBoard> posterList = new ArrayList<UploadPosterBoard>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				UploadPosterBoard ulpboard = new UploadPosterBoard();
				ulpboard.setPrimaryKey(rs.getString(1));
				ulpboard.setUserID(rs.getString(2));
				ulpboard.setBoardTitle(rs.getString(3));
				ulpboard.setBoardContent(rs.getString(4));
				ulpboard.setImgURL(rs.getString(5));
				posterList.add(ulpboard);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return posterList;
	}
	
	public ArrayList<Integer> FindReportProgress(String userID) {
		String SQL = "SELECT * FROM reportvolunteer WHERE userid LIKE ?";
		ArrayList<Integer> reportProgressList = new ArrayList<Integer>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int progressIndex = 0;
				progressIndex = rs.getInt(9);
				reportProgressList.add(progressIndex);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reportProgressList;
	}
	
	public ArrayList<VolunteerPlanModule> FindPlanProgress(String userID) {
		String SQL = "SELECT * FROM volunteerplan WHERE userid LIKE ?";
		ArrayList<VolunteerPlanModule> planList = new ArrayList<VolunteerPlanModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VolunteerPlanModule planboard = new VolunteerPlanModule();
				planboard.setNum(rs.getInt(1));
				planboard.setUserid(rs.getString(2));
				planboard.setVol_date(rs.getString(3));
				planboard.setVol_time(rs.getString(4));
				planboard.setPalce(rs.getString(5));
				planboard.setVol_goal(rs.getString(6));
				planboard.setActivity_content(rs.getString(7));
				planboard.setVol_prepare(rs.getString(8));
				planboard.setEtc(rs.getString(9));
				planList.add(planboard);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return planList;
	}
	
	public ArrayList<VolunteerPlanModule> uploadPlan() {
		String SQL = "select*from volunteerplan  where etc !=\"false\" ORDER BY num desc limit 2;";
		ArrayList<VolunteerPlanModule> planList = new ArrayList<VolunteerPlanModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VolunteerPlanModule planboard = new VolunteerPlanModule();
				planboard.setNum(rs.getInt(1));
				planboard.setUserid(rs.getString(2));
				planboard.setVol_date(rs.getString(3));
				planboard.setVol_time(rs.getString(4));
				planboard.setPalce(rs.getString(5));
				planboard.setVol_goal(rs.getString(6));
				planboard.setActivity_content(rs.getString(7));
				planboard.setVol_prepare(rs.getString(8));
				planboard.setEtc(rs.getString(9));
				planList.add(planboard);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return planList;
	}
	
	
	
	public ArrayList<VolunteerReportModule> uploadReport() {
		String SQL = "SELECT * FROM reportvolunteer where progress = 1 ORDER BY num DESC LIMIT 2;";
		ArrayList<VolunteerReportModule> reportList = new ArrayList<VolunteerReportModule>();
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				VolunteerReportModule reportboard = new VolunteerReportModule();
				reportboard.setNum(rs.getInt(1));
				reportboard.setUserid(rs.getString(2));
				reportboard.setVol_date(rs.getString(3));
				reportboard.setVol_place(rs.getString(4));
				reportboard.setActivity_content(rs.getString(5));
				reportboard.setVol_prepare_progress(rs.getString(6));
				reportboard.setImage(rs.getString(7));
				reportboard.setPlan_Key(rs.getInt(8));
				reportList.add(reportboard);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reportList;
	}
	
	
	public Transaction make_currentTransaction() {
		String SQL = "SELECT * FROM usertbl;";
		Transaction transaction = new Transaction();
		EventModule module;
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				module = new EventModule();
				module.setUserID(rs.getString(2));
				module.setMoney(String.valueOf(rs.getInt(9)));
				transaction.getTransaction().add(module);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return transaction;
	}
	
	public int modify_Transaction(EventModule eventmodule) {
		String SQL = "update usertbl set usermoney=? where userID=\"" + eventmodule.getUserID() + "\"";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, Integer.parseInt(eventmodule.getMoney()));
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;//데이터베이스 오류
	}
}

//Connection - DriverManager
//jdbc드라이버를 통해서 커넥션을 만드는역할


//PreparedStatement 
//커넥터를 통해서 SQL문을 보내거나 받아서 특정매서드를 통해 ResultSet에 테이블형식으로 넘겨주는역할


//ResultSet
//SQL 문 중에서 Select 문을 사용한 질의의 경우 성공 시 결과물로 ResultSet을 반환한다. ResultSet은 SQL 질의에 의해 생성된 테이블을 담고 있다














