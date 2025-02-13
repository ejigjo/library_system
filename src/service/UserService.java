package service;

import model.User;

public interface UserService {
	public boolean registerUser(User user);

	public User getUserByUsername(String username);
}
