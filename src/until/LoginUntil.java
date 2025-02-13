package until;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.mindrot.jbcrypt.BCrypt;

import controller.MainPanel;
import model.SessionManager;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class LoginUntil {
	private static final String CAPTCHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";// 驗證碼函數
	private static final int CAPTCHA_LENGTH = 4;
	private static UserService userService = new UserServiceImpl();

	public static void loginAction(JFrame currentFrame, String strCaptcha, String inputCaptcha, String username,
			String password) {

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "帳號密碼不能空白", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		if (!checkCaptcha(strCaptcha, inputCaptcha)) {
			JOptionPane.showMessageDialog(null, "驗證碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE, null);
			return;
		}
		if (checkUser(username, password)) {
			JOptionPane.showMessageDialog(null, "登入成功", "訊息", JOptionPane.INFORMATION_MESSAGE, null);
			User user = userService.getUserByUsername(username);
			SessionManager.getSessionManager().setCurrentUser(user);
			MainPanel libraryUI = new MainPanel();
			libraryUI.setVisible(true);
			currentFrame.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "帳號密碼錯誤", "錯誤", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	private static boolean checkUser(String username, String pwd) {
		User user = userService.getUserByUsername(username);

		return user != null && user.getPassword() != null && BCrypt.checkpw(pwd, user.getPassword());
	}

	public static String makeCaptcha() {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < CAPTCHA_LENGTH; i++) {
			sb.append(CAPTCHA_CHARS.charAt(r.nextInt(CAPTCHA_CHARS.length())));
		}

		return sb.toString();
	}

	private static boolean checkCaptcha(String strCaptcha, String inputCaptcha) {
		if (inputCaptcha.equals(strCaptcha)) {
			return true;
		} else {
			return false;
		}
	}
}
