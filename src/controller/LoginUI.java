package controller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import until.LoginUntil;

public class LoginUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField account;
	private JPasswordField password;
	private JLabel lblCaptcha;
	private JTextField txtCaptcha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI frame = new LoginUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 384, 471);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setFont(new Font("华文琥珀", Font.PLAIN, 24));
		lblNewLabel.setBounds(53, 137, 128, 31);
		lblNewLabel.setForeground(new Color(217, 217, 217));
		contentPane.add(lblNewLabel);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setForeground(new Color(217, 217, 217));
		lblPassword.setFont(new Font("华文琥珀", Font.PLAIN, 24));
		lblPassword.setBounds(53, 187, 128, 30);
		contentPane.add(lblPassword);

		account = new JTextField();
		account.setBounds(186, 137, 133, 21);
		contentPane.add(account);
		account.setColumns(10);

		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(191, 196, 128, 21);
		contentPane.add(password);

		JLabel lblCaptchaTitle = new JLabel("CAPTCHA");
		lblCaptchaTitle.setForeground(Color.WHITE);
		lblCaptchaTitle.setFont(new Font("华文琥珀", Font.PLAIN, 18));
		lblCaptchaTitle.setBounds(82, 238, 88, 30);
		contentPane.add(lblCaptchaTitle);

		String captcha = LoginUntil.makeCaptcha();
		lblCaptcha = new JLabel(captcha);
		lblCaptcha.setBackground(new Color(255, 255, 255));
		lblCaptcha.setForeground(Color.YELLOW);
		lblCaptcha.setFont(new Font("Courier New", Font.BOLD, 24));
		lblCaptcha.setBounds(181, 247, 77, 21);
		contentPane.add(lblCaptcha);

		txtCaptcha = new JTextField();
		txtCaptcha.setBounds(244, 245, 64, 21);
		contentPane.add(txtCaptcha);
		txtCaptcha.setColumns(10);

		JButton loginButton = new JButton("LOGIN");
		loginButton.setBounds(85, 294, 85, 23);
		contentPane.add(loginButton);

		JButton btnReg = new JButton("REGISTER");
		btnReg.setBounds(193, 294, 100, 23);
		contentPane.add(btnReg);

		JLabel lblbar = new JLabel("書本才不會劈腿");
		lblbar.setForeground(new Color(255, 255, 0));
		lblbar.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblbar.setBounds(93, 32, 176, 31);
		contentPane.add(lblbar);

		JLabel lblbar_1 = new JLabel("圖書館");
		lblbar_1.setForeground(new Color(168, 140, 115));
		lblbar_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblbar_1.setBounds(139, 73, 88, 31);
		contentPane.add(lblbar_1);

		loginButton.addActionListener(e -> {
			String inputCaptcha = txtCaptcha.getText();
			String accountStr = account.getText().trim();
			String passwordStr = password.getText().trim();
			LoginUntil.loginAction(LoginUI.this, captcha, inputCaptcha, accountStr, passwordStr);
		});
		
		btnReg.addActionListener(e->{
			new RegisterUI().setVisible(true);
				dispose();
				});
	}
}
