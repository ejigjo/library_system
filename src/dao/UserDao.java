package dao;

import model.User;

public interface UserDao {
	public boolean insertUser(User user);
	
	public User getUserByUsername(String username);
}
