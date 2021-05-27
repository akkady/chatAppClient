package socket;

import java.util.ArrayList;

public class User {
	private String userName;
	private ArrayList<String> friendsList;

	public User() {
		this.userName = null;
		friendsList = new ArrayList<String>();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ArrayList<String> getFriendsList() {
		return friendsList;
	}
	public void setFriendsList(String friend) {
		friendsList.add(friend);
	}
	
	
}
