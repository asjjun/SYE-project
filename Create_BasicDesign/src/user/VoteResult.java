package user;

public class VoteResult {
	private int true_Index = 0;
	private int false_Index = 0;
	
	public int getTrue_Index() {
		return true_Index;
	}
	public void setTrue_Index(int true_Index) {
		this.true_Index = true_Index;
	}
	public int getFalse_Index() {
		return false_Index;
	}
	public void setFalse_Index(int false_Index) {
		this.false_Index = false_Index;
	}
	
	public void clear() {
		true_Index = 0;
		false_Index = 0;
	}
}
