package user;

import java.util.ArrayList;

public class Transaction {
	public ArrayList<EventModule> getTransaction() {
		return transaction;
	}

	public void setTransaction(ArrayList<EventModule> transaction) {
		this.transaction = transaction;
	}

	private ArrayList<EventModule> transaction = new ArrayList<EventModule>();
	
}
