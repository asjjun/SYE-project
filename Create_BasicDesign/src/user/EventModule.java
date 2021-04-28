package user;

public class EventModule {
	private String userID="";
	private String Money = "";
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getMoney() {
		return Money;
	}
	public void setMoney(String money) {
		Money = money;
	}
	
	public void clear() {
		userID = "";
		Money = "";
	}
}
