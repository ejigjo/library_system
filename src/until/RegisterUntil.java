package until;

import javax.swing.JOptionPane;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class RegisterUntil {
	private final static int level = 1;
	private static UserService userService = new UserServiceImpl();

	public static void registerAction(String username, String password, String email, String name) {
		if (username.isEmpty() || password.isEmpty() || email.isEmpty() || name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "使用者資料不能為空", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		} else if (checkUsername(username)) {
			JOptionPane.showMessageDialog(null, "使用者已註冊", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}

		if (registerSuccess(username, password, email, name)) {
			JOptionPane.showMessageDialog(null, "註冊成功", "資訊", JOptionPane.INFORMATION_MESSAGE, null);
			return;
		} else {
			JOptionPane.showMessageDialog(null, "註冊失敗", "註冊失敗請聯繫系統管理員", JOptionPane.ERROR_MESSAGE, null);
			return;
		}

	}

	private static boolean registerSuccess(String username, String password, String email, String name) {
		User user = new User();
		user.setEmail(email);
		user.setLevel(level);
		user.setName(name);
		user.setPassword(password);
		user.setUsername(username);
		return userService.registerUser(user);
	}

	private static boolean checkUsername(String username) {
		User user = userService.getUserByUsername(username);
		if (user.getUsername() == null || user.getUsername().isEmpty()) {
			return false;
		}

		return true;
	}
}
