package model;

public class SessionManager {
	private static SessionManager sessionManager;
	private Runnable loadBookUITable;
	private Runnable loadBookInfoUITable;
	private User currentUser;

	private SessionManager() {

	}

	public static SessionManager getSessionManager() {
		if (sessionManager == null) {
			sessionManager = new SessionManager();
		}

		return sessionManager;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void setLoadBookInfoUItable(Runnable loadBookInfoUITable) {
		this.loadBookInfoUITable = loadBookInfoUITable;
	}

	public void setLoadBookUITable(Runnable loadTableData) {
		this.loadBookUITable = loadTableData;
	}
	
	public void loadBookInfoUITable() {
		loadBookInfoUITable.run();
	}

	public void loadBookUITable() {
		loadBookUITable.run();
	}

}
